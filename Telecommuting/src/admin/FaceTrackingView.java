/*
 * FaceTrackingView.java
 * 
 * To edit GUI in visual editor of Netbeans 7.2+ you can install Swing Application Framework plugin:
 * http://plugins.netbeans.org/plugin/43853/swing-application-framework-support
 * Do not forget to restart Netbeans after installing the plugin!
 */

package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Luxand.FSDK;
import Luxand.FSDK.FSDK_IMAGEMODE;
import Luxand.FSDK.HImage;
import Luxand.FSDK.HTracker;
import Luxand.FSDK.TFacePosition;
import Luxand.FSDKCam;
import Luxand.FSDKCam.FSDK_VideoFormats;
import Luxand.FSDKCam.HCamera;
import Luxand.FSDKCam.TCameras;

/**
 * The application's main frame.
 */
public class FaceTrackingView extends JPanel {

	public static HImage imageHandle;
	public static boolean saveFlag = false;

	public static Timer drawingTimer;
	private HCamera cameraHandle;

	public FaceTrackingView() {

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		try {
			int r = FSDK
					.ActivateLibrary("bPOFbM5ekk9uCr9x75NEbpkV8UsG/NAoExxkGAk+POsNvUVdz0IdfXKog23Y89TnCneozsWdtKoaj2J2OdM9q9iNuUgTBU10gLNqcnUvxeuKZDcGzfaUmyUCIkqfnE/MbLpIuprI8Bvauj2B5NU6xu3trvDnhszFPA2/JyKdstk=");
			if (r != FSDK.FSDKE_OK) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please run the License Key Wizard (Start - Luxand - FaceSDK - License Key Wizard)",
								"Error activating FaceSDK",
								JOptionPane.ERROR_MESSAGE);
				System.exit(r);
			}
		} catch (java.lang.UnsatisfiedLinkError e) {
			JOptionPane.showMessageDialog(this, e.toString(), "Link Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		FSDK.Initialize();

		final HTracker tracker = new HTracker();
		FSDK.CreateTracker(tracker);

		// set realtime face detection parameters
		int[] err = new int[1];
		FSDK.SetTrackerMultipleParameters(
				tracker,
				"RecognizeFaces=false; HandleArbitraryRotations=false; DetermineFaceRotationAngle=false; InternalResizeWidth=100; FaceDetectionThreshold=5;",
				err);

		FSDKCam.InitializeCapturing();

		TCameras cameraList = new TCameras();
		int count[] = new int[1];
		FSDKCam.GetCameraList(cameraList, count);
		String cameraName = cameraList.cameras[0];

		FSDK_VideoFormats formatList = new FSDK_VideoFormats();
		FSDKCam.GetVideoFormatList(cameraName, formatList, count);
		FSDKCam.SetVideoFormat(cameraName, formatList.formats[0]);

		cameraHandle = new HCamera();
		int r = FSDKCam.OpenVideoCamera(cameraName, cameraHandle);
		if (r != FSDK.FSDKE_OK) {
			JOptionPane.showMessageDialog(this, "Error opening camera");
			System.exit(r);
		}

		// Timer to draw image from camera

		drawingTimer = new Timer(40, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imageHandle = new HImage();
				if (FSDKCam.GrabFrame(cameraHandle, imageHandle) == FSDK.FSDKE_OK) {
					Image awtImage[] = new Image[1];
					if (FSDK.SaveImageToAWTImage(imageHandle, awtImage,
							FSDK_IMAGEMODE.FSDK_IMAGE_COLOR_24BIT) == FSDK.FSDKE_OK) {
						BufferedImage bufImage = null;

						TFacePosition.ByReference facePosition = new TFacePosition.ByReference();

						long[] IDs = new long[256]; // maximum of 256 faces
													// detected
						long[] faceCount = new long[1];

						FSDK.FeedFrame(tracker, 0, imageHandle, faceCount, IDs);
						for (int i = 0; i < faceCount[0]; ++i) {
							FSDK.GetTrackerFacePosition(tracker, 0, IDs[i],
									facePosition);

							int left = facePosition.xc
									- (int) (facePosition.w * 0.6);
							int top = facePosition.yc
									- (int) (facePosition.w * 0.5);
							int w = (int) (facePosition.w * 1.2);

							bufImage = new BufferedImage(awtImage[0]
									.getWidth(null), awtImage[0]
									.getHeight(null),
									BufferedImage.TYPE_INT_ARGB);
							Graphics gr = bufImage.getGraphics();
							gr.drawImage(awtImage[0], 0, 0, null);
							gr.setColor(Color.green);
							gr.drawRect(left, top, w, w); // draw face rectangle
						}

						getGraphics().drawImage(
								(bufImage != null) ? bufImage : awtImage[0], 0,
								0, null);
					}
					FSDK.SaveImageToFile(FaceTrackingView.imageHandle,
							"C:\\Temp\\test.jpg");
					FSDK.FreeImage(FaceTrackingView.imageHandle);
				}
			}
		});

		drawingTimer.start();
	}

	public void closeCamera() {
		FSDKCam.CloseVideoCamera(cameraHandle);
		FSDKCam.FinalizeCapturing();
		FSDK.Finalize();
	}

	// ///////////////////////////////////////////////////////////// Lookalike!!!
	
	
	

}
