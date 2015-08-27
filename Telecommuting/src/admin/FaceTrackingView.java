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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	private static HCamera cameraHandle;

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

	public static void closeCamera() {
		FSDKCam.CloseVideoCamera(cameraHandle);
		FSDKCam.FinalizeCapturing();
		FSDK.Finalize();
	}

	// ///////////////////////////////////////////////////////////// Lookalike!!!
	public class TFaceRecord {
		public FSDK.FSDK_FaceTemplate.ByReference FaceTemplate;
		public FSDK.TFacePosition.ByReference FacePosition;
		public FSDK.FSDK_Features.ByReference FacialFeatures;
		public String ImageFileName;
		public FSDK.HImage image;
		public FSDK.HImage faceImage;
	}

	public final java.util.List<TFaceRecord> FaceList = new ArrayList<TFaceRecord>();
	public int FaceDetectionThreshold = 3;
	public int FARValue = 100;
	public static final int width = 640;
	public static final int height = 480;

	public void menuEnrollFace() {
		// Assuming that faces are vertical (HandleArbitraryRotations = false)
		// to speed up face detection
		FSDK.SetFaceDetectionParameters(false, true, 384);
		FSDK.SetFaceDetectionThreshold(FaceDetectionThreshold);

		String fileName = "C:\\temp\\2.jpg";

		TFaceRecord fr = new TFaceRecord();
		fr.ImageFileName = "2.jpg";
		fr.image = new HImage();

		int res = FSDK.LoadImageFromFileW(fr.image, fileName);

		fr.FacePosition = new TFacePosition.ByReference();
		res = FSDK.DetectFace(fr.image, fr.FacePosition);

		fr.faceImage = new HImage(); // face이미지만 따로 설정
		FSDK.CreateEmptyImage(fr.faceImage);
		FSDK.CopyRect(fr.image,
				(int) (fr.FacePosition.xc - fr.FacePosition.w * 0.5),
				(int) (fr.FacePosition.yc - fr.FacePosition.w * 0.5),
				(int) (fr.FacePosition.xc + fr.FacePosition.w * 0.5),
				(int) (fr.FacePosition.yc + fr.FacePosition.w * 0.5),
				fr.faceImage);

		fr.FacialFeatures = new FSDK.FSDK_Features.ByReference();
		res = FSDK.DetectEyesInRegion(fr.image, fr.FacePosition,
				fr.FacialFeatures);

		fr.FaceTemplate = new FSDK.FSDK_FaceTemplate.ByReference();
		res = FSDK.GetFaceTemplateInRegion(fr.image, fr.FacePosition,
				fr.FaceTemplate);

		FaceList.add(fr);
		if(FaceList != null)
		System.out.println("이미지가 저장됩니다!");

	}

	public class Sortable {
		public float similarity;
		public int index;
	}

	public void menuMatchFace() {

		String fileName = "C:\\temp\\test.jpg";

		TFaceRecord fr = new TFaceRecord();
		fr.ImageFileName = "test.jpg";
		fr.image = new HImage();

		int res = FSDK.LoadImageFromFileW(fr.image, fileName);

		fr.FacePosition = new TFacePosition.ByReference();
		res = FSDK.DetectFace(fr.image, fr.FacePosition);

		fr.faceImage = new HImage();
		FSDK.CreateEmptyImage(fr.faceImage);
		FSDK.CopyRect(fr.image,
				(int) (fr.FacePosition.xc - fr.FacePosition.w * 0.5),
				(int) (fr.FacePosition.yc - fr.FacePosition.w * 0.5),
				(int) (fr.FacePosition.xc + fr.FacePosition.w * 0.5),
				(int) (fr.FacePosition.yc + fr.FacePosition.w * 0.5),
				fr.faceImage);

		fr.FaceTemplate = new FSDK.FSDK_FaceTemplate.ByReference();
		res = FSDK.GetFaceTemplateInRegion(fr.image, fr.FacePosition,
				fr.FaceTemplate);

		// Matching
		float ThresholdByReference[] = new float[1];
		FSDK.GetMatchingThresholdAtFAR(FARValue / 100.0f, ThresholdByReference);
		float Threshold = ThresholdByReference[0];
		int MatchedCount = 0;
		float SimilarityByReference[] = new float[1];

		java.util.List<Sortable> sim_ind = new ArrayList<Sortable>();
		for (int i = 0; i < FaceList.size(); ++i) {
			FSDK.MatchFaces(fr.FaceTemplate, FaceList.get(i).FaceTemplate,
					SimilarityByReference);
			float Similarity = SimilarityByReference[0];
			if (Similarity >= Threshold) {
				Sortable s = new Sortable();
				s.index = i;
				s.similarity = Similarity;
				sim_ind.add(s);
				++MatchedCount;
				System.out.println("입장 가능!!!! 얼굴이 확인 되었다."+Similarity);
			}
		}

		if (MatchedCount == 0) {
			System.out.println("하나도 안맞아!!!!!!!!!!!!");
			FSDK.FreeImage(fr.image);
			FSDK.FreeImage(fr.faceImage);
			return;
		}

		Collections.sort(sim_ind, new Comparator<Sortable>() {
			@Override
			public int compare(Sortable obj1, Sortable obj2) {
				return ((Float) obj2.similarity).compareTo(obj1.similarity);
			}
		});

	}
	
	public static void main(String[] args) {
		FaceTrackingView ft  = new FaceTrackingView();
		ft.menuEnrollFace();
		ft.menuMatchFace();
	}

}
