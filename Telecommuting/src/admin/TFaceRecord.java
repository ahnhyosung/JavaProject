package admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

import main.MainFrame;
import user.UserMenuBar;
import Luxand.FSDK;
import Luxand.FSDK.HImage;
import Luxand.FSDK.TFacePosition;

public class TFaceRecord {

	public FSDK.FSDK_FaceTemplate.ByReference FaceTemplate;
	public FSDK.TFacePosition.ByReference FacePosition;
	public FSDK.FSDK_Features.ByReference FacialFeatures;
	public String ImageFileName;
	public FSDK.HImage image;
	public FSDK.HImage faceImage;

	public final java.util.List<TFaceRecord> FaceList = new ArrayList<TFaceRecord>();
	public int FaceDetectionThreshold = 3;
	public int FARValue = 100;
	public static final int width = 640;
	public static final int height = 480;
	
	public File f;
	
	

	public TFaceRecord() {
		try {
			int r = FSDK
					.ActivateLibrary("bPOFbM5ekk9uCr9x75NEbpkV8UsG/NAoExxkGAk+POsNvUVdz0IdfXKog23Y89TnCneozsWdtKoaj2J2OdM9q9iNuUgTBU10gLNqcnUvxeuKZDcGzfaUmyUCIkqfnE/MbLpIuprI8Bvauj2B5NU6xu3trvDnhszFPA2/JyKdstk=");
			if (r != FSDK.FSDKE_OK) {
				JOptionPane
						.showMessageDialog(
								main.MainFrame.frame,
								"Please run the License Key Wizard (Start - Luxand - FaceSDK - License Key Wizard)",
								"Error activating FaceSDK",
								JOptionPane.ERROR_MESSAGE);
				System.exit(r);
			}
		} catch (java.lang.UnsatisfiedLinkError e) {
			JOptionPane.showMessageDialog(main.MainFrame.frame, e.toString(),
					"Link Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		FSDK.Initialize();
		
		f = new File("C:\\Temp\\facematch\\");
	}

	public void menuEnrollFace() {
		// Assuming that faces are vertical (HandleArbitraryRotations = false)
		// to speed up face detection
		FSDK.SetFaceDetectionParameters(false, true, 384);
		FSDK.SetFaceDetectionThreshold(FaceDetectionThreshold);

		

		String fileName = null;

		System.out.println(f.length());

		for (String fileList : f.list()) {
			fileName = fileList;

			System.out.println(fileName);
			TFaceRecord fr = new TFaceRecord();
			fr.ImageFileName = fileName;
			fr.image = new HImage();

			int res = FSDK.LoadImageFromFileW(fr.image, "C:\\Temp\\facematch\\"
					+ fileName);

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
			if (FaceList != null)
				System.out.println("이미지가 저장됩니다!");

		}
		System.out.println(FaceList.size());

	}

	public class Sortable {
		public float similarity;
		public int index;
	}

	public float menuMatchFace() {

		String fileName = "C:\\temp\\in\\in.jpg";

		TFaceRecord fr = new TFaceRecord();
		fr.ImageFileName = "in.jpg";
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
		float Threshold = 0.9f;
		int MatchedCount = 0;
		float SimilarityByReference[] = new float[1];

		java.util.List<Sortable> sim_ind = new ArrayList<Sortable>();
		float Similarity = 0;

		// ///////////////////////////////

		String fileName2 = null;

		for (String fileList : f.list()) {
			fileName2 = fileList;

			System.out.println("test : " + fileName2);
		}
		// ///////////////////////////////

		float maxSimNum = 0.0f;
		int maxArryNum = 0;

		for (int i = 0; i < FaceList.size(); ++i) {
			FSDK.MatchFaces(fr.FaceTemplate, FaceList.get(i).FaceTemplate,
					SimilarityByReference);
			Similarity = SimilarityByReference[0];

			System.out.println(i + "번째 : " + Similarity);
			if (Similarity >= Threshold) {

				if (Similarity > maxSimNum) {
					maxSimNum = Similarity;
					maxArryNum = i;
				}

				Sortable s = new Sortable();
				s.index = i;
				s.similarity = Similarity;
				sim_ind.add(s);
				++MatchedCount;
				System.out.println("입장 가능!!!! 얼굴이 확인 되었다." + Similarity);

			}

			
		}
		
		

		if (MatchedCount == 0) {
			System.out.println("하나도 안맞아!!!!!!!!!!!!" + Similarity);
			FSDK.FreeImage(fr.image);
			FSDK.FreeImage(fr.faceImage);
		}

		Collections.sort(sim_ind, new Comparator<Sortable>() {
			@Override
			public int compare(Sortable obj1, Sortable obj2) {
				return ((Float) obj2.similarity).compareTo(obj1.similarity);
			}
		});
		return maxSimNum;
	}
}
