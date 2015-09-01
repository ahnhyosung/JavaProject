package camera;

public class CloseCamera {
	public void close() {
		if (FaceTrackingView.drawingTimer != null
				&& FaceTrackingView.drawingTimer.isRunning()) {
			FaceTrackingView.drawingTimer.stop();
			FaceTrackingView.closeCamera();
			System.out.println("카메라 종료");
		}
	}
}
