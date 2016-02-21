package com.team3044.robotmain.RobotCode;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.team3044.robotmain.Reference.FirstController;

import edu.wpi.first.wpilibj.CameraServer;

public class CameraController {
	int cam1, cam2;
	private Image frame;
	CameraServer server;
	int count = 0;
	int currentCamId = 0;

	public CameraController() {
		cam1 = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		cam2 = NIVision.IMAQdxOpenCamera("cam2", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		server = CameraServer.getInstance();
		count = 0;
		currentCamId = cam1;
		NIVision.IMAQdxConfigureGrab(cam1);
		NIVision.IMAQdxStartAcquisition(cam1);
	}

	private void switchCamera(int id) {
		if (id == 0) {
			currentCamId = cam1;
			NIVision.IMAQdxStopAcquisition(cam2);
			NIVision.IMAQdxConfigureGrab(cam1);
			NIVision.IMAQdxStartAcquisition(cam1);
		} else {
			currentCamId = cam2;
			NIVision.IMAQdxStopAcquisition(cam1);
			NIVision.IMAQdxConfigureGrab(cam2);
			NIVision.IMAQdxStartAcquisition(cam2);
		}

	}

	public void step() {
		count += 1;
		if (count > 3) {
			NIVision.IMAQdxGrab(currentCamId, frame, 1);
			server.setImage(frame);
			count = 0;
		}
		if(FirstController.getInstance().getRawButton(FirstController.BUTTON_START)){
			this.switchCamera(0);
		}else if(FirstController.getInstance().getRawButton(FirstController.BUTTON_BACK)){
			this.switchCamera(1);
		}
	}

}
