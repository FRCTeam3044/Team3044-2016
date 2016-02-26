package com.team3044.robotmain.RobotCode;

import java.awt.Component;

import com.team3044.robotmain.Reference.CommonArea;
import com.team3044.robotmain.Reference.Components;
import com.team3044.robotmain.Reference.FirstController;
import com.team3044.robotmain.RobotCode.Gate.state;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	// Defense defense = new Defense();
	Drive drive = new Drive();
	Shooter shooter = new Shooter();
	Gate gate = new Gate();
	VisionCalc vision = new VisionCalc();

	private double movexFeetSpeed = .3;
	private double Dashboard;
	CameraController camController;

	public void robotInit() {
		camController = new CameraController();
		vision.init();
		Components.getInstance().init();
		drive.driveInit();
		vision.Reset();
		// defense.defenseInit();
		shooter.shooterInit();
		gate.gateInit();

	}

	public void autonomousInit() {
		// Drive
		CommonArea.leftAutoSpeed = 0;
		CommonArea.rightAutoSpeed = 0;
		CommonArea.leftDesiredEncoderValue = 0;
		CommonArea.rightDesiredEncoderValue = 0;
		CommonArea.movexFeet = false;
		CommonArea.atDistance = false;

		// Shooter
		CommonArea.shooterVisionTopSpeed = 0;
		CommonArea.shooterVisionBotSpeed = 0;
		CommonArea.aimFlag = false;
		CommonArea.shooterMotorFlag = false;
		CommonArea.shootFlag = false;
		CommonArea.isShot = false;

		// Gate
		CommonArea.gateCalibrated = false;

		// PickUp
		CommonArea.portcullisFlag = false;

		// Arm
		CommonArea.armCalibrated = false;
		Components.getInstance().leftFrontDrive.setAnalogPosition(0);
		Components.getInstance().rightFrontDrive.setAnalogPosition(0);
		Components.getInstance().leftFrontDrive.setPosition(0);
		Components.getInstance().rightFrontDrive.setPosition(0);
		Components.getInstance().leftFrontDrive.setEncPosition(0);
		Components.getInstance().rightFrontDrive.setEncPosition(0);
		startLeft = Components.getInstance().leftFrontDrive.getAnalogInPosition();
		startRight = Components.getInstance().rightFrontDrive.getAnalogInPosition();
		// Vision
		CommonArea.isTargetSeen = false;
		CommonArea.isAligned = false;
		CommonArea.isUpToSpeed = false;
		CommonArea.autoShot = false;
		CommonArea.angleToTarget = 0;
		CommonArea.distanceFromTarget = 0;
		autoZeroState = 0;
		autoOneState = 0;
	}

	int autoZeroState = 0;
	int autoOneState = 0;
	final int RIGHTENC = 11000;
	int startLeft = 0;
	int startRight = 0;
	int j = 0;

	final int RIGHTENCMOVE = 3000;

	void lowBarShoot() {
		SmartDashboard.putString("DB/String 9", String.valueOf(autoZeroState));
		switch (autoZeroState) {
		case 0:
			j = 0;
			CommonArea.isManualDrive = false;
			CommonArea.leftDriveSpeed = .4;
			CommonArea.rightDriveSpeed = .35;
			Components.getInstance().leftFrontDrive.setAnalogPosition(0);
			Components.getInstance().rightFrontDrive.setAnalogPosition(0);
			CommonArea.gateDown = true;
			startLeft = Components.getInstance().leftFrontDrive.getAnalogInPosition();
			startRight = Components.getInstance().rightFrontDrive.getAnalogInPosition();
			autoZeroState = 1;
			SmartDashboard.putString("DB/String 5",
					String.valueOf(Components.getInstance().leftFrontDrive.getAnalogInPosition() - startLeft));
			SmartDashboard.putString("DB/String 6",
					String.valueOf(Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight));
			break;
		case 1:
			if (!Components.getInstance().GateDownLimit.get()) {
				CommonArea.gateDown = false;
				CommonArea.leftDriveSpeed = .4;
				CommonArea.rightDriveSpeed = .35;
				autoZeroState = 2;
			}
			break;
		case 2:
			SmartDashboard.putString("DB/String 5",
					String.valueOf(Components.getInstance().leftFrontDrive.getAnalogInPosition() - startLeft));
			SmartDashboard.putString("DB/String 6",
					String.valueOf(Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight));
			/*
			 * if (Components.getInstance().leftFrontDrive.getAnalogInPosition()
			 * - startLeft < -LEFTENC) { CommonArea.leftDriveSpeed = 0;
			 * CommonArea.rightDriveSpeed = 0; }
			 */
			if (Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight > RIGHTENC) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
			}
			if ((Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight > RIGHTENC)
			/*
			 * || (Components.getInstance().leftFrontDrive.getAnalogInPosition()
			 * - startLeft > LEFTENC)
			 */) {
				autoZeroState = 3;
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = .4;
				// CommonArea.leftDriveSpeed = .4;
			}
			break;
		case 3:
			j += 1;
			if (j > 50) {
				CommonArea.rightDriveSpeed = 0;
				CommonArea.gateUp = true;
				autoZeroState = 4;
			}
			break;
		case 4:
			if (SmartDashboard.getNumber("ANGLE", 0) < -10) {
				CommonArea.rightDriveSpeed = .4;
			} else {
				CommonArea.rightDriveSpeed = .4;
				CommonArea.leftDriveSpeed = .35;
				autoZeroState = 5;
			}
			break;
		case 5:
			if (SmartDashboard.getNumber("DIST", 0) < 170) {
				CommonArea.rightDriveSpeed = 0;
				CommonArea.leftDriveSpeed = 0;
				CommonArea.autoAlign = true;
				autoZeroState = 6;
				SmartDashboard.putString("DB/String 3", "STATE 6");
			}
			break;
		case 6:

			break;
		}
	}

	int moatState = 0;
	final int MOATENC = 8000;
	final int MOATENCMOVE = 8000;//3000;

	void moatShoot() {
		SmartDashboard.putString("DB/String 9", String.valueOf(moatState));
		switch (moatState) {
		case 0:
			j = 0;
			CommonArea.isManualDrive = false;
			CommonArea.leftDriveSpeed = .6;
			CommonArea.rightDriveSpeed = .55;
			Components.getInstance().leftFrontDrive.setAnalogPosition(0);
			Components.getInstance().rightFrontDrive.setAnalogPosition(0);
			CommonArea.gateDown = false;
			startLeft = Components.getInstance().leftFrontDrive.getAnalogInPosition();
			startRight = Components.getInstance().rightFrontDrive.getAnalogInPosition();
			moatState = 2;
			SmartDashboard.putString("DB/String 5",
					String.valueOf(Components.getInstance().leftFrontDrive.getAnalogInPosition() - startLeft));
			SmartDashboard.putString("DB/String 6",
					String.valueOf(Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight));
			break;
		case 1:

			break;
		case 2:
			SmartDashboard.putString("DB/String 5",
					String.valueOf(Components.getInstance().leftFrontDrive.getAnalogInPosition() - startLeft));
			SmartDashboard.putString("DB/String 6",
					String.valueOf(Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight));

			if (Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight > MOATENC) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
			}
			if ((Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight > MOATENC)) {
				moatState = 3;

			}
			break;
		case 3:
			j += 1;
			if (j > 50) {
				CommonArea.rightDriveSpeed = 0;
				
				moatState = 4;
			}
			break;
		case 4:
			moatState = 5;
			break;
		case 5:
			if (SmartDashboard.getNumber("DIST", 0) > 140) {
				CommonArea.rightDriveSpeed = 0;
				CommonArea.leftDriveSpeed = 0;
				CommonArea.autoAlign = true;
				moatState = 6;
				SmartDashboard.putString("DB/String 3", "STATE 6");
			}else{
				CommonArea.rightDriveSpeed = -.4;
				CommonArea.leftDriveSpeed = -.4;
			}
			break;
		case 6:

			break;
		}
	}

	void moveToDefense() {
		SmartDashboard.putString("DB/String 0", String.valueOf(autoOneState));
		switch (autoOneState) {
		case 0:
			j = 0;
			CommonArea.isManualDrive = false;
			CommonArea.leftDriveSpeed = .4;
			CommonArea.rightDriveSpeed = .4;
			Components.getInstance().leftFrontDrive.setAnalogPosition(0);
			Components.getInstance().rightFrontDrive.setAnalogPosition(0);
			startLeft = Components.getInstance().leftFrontDrive.getAnalogInPosition();
			startRight = Components.getInstance().rightFrontDrive.getAnalogInPosition();
			autoOneState = 1;
			SmartDashboard.putString("DB/String 5",
					String.valueOf(Components.getInstance().leftFrontDrive.getAnalogInPosition() - startLeft));
			SmartDashboard.putString("DB/String 6",
					String.valueOf(Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight));
			break;
		case 1:
			SmartDashboard.putString("DB/String 3",
					String.valueOf(Components.getInstance().leftFrontDrive.getAnalogInPosition() - startLeft));
			SmartDashboard.putString("DB/String 8",
					String.valueOf(Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight));

			if (Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight > MOATENCMOVE) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
			}
			if ((Components.getInstance().rightFrontDrive.getAnalogInPosition() - startRight > MOATENCMOVE)) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				CommonArea.gateUp = true;
			}
			break;
		}
	}
	int portcullisState = 0;
	
	void portcullis(){
		switch (portcullisState) {
		case 0:
			if(Components.getInstance().GateUpLimit.get()){
				CommonArea.gateUp = true;
			}
			CommonArea.isManualDrive = false;
			CommonArea.leftDriveSpeed = -.4;
			CommonArea.rightDriveSpeed = -.4;

			Components.getInstance().leftFrontDrive.setAnalogPosition(0);
			Components.getInstance().rightFrontDrive.setAnalogPosition(0);
			
			startLeft = Components.getInstance().leftFrontDrive.getAnalogInPosition();
			startRight = Components.getInstance().rightFrontDrive.getAnalogInPosition();
			portcullisState = 1;
		
			break;
		case 1:
			if(!Components.getInstance().GateUpLimit.get()){
				CommonArea.gateUp = false;
				Components.getInstance().gateTalon.setEncPosition(0);
				CommonArea.gateDown = true;
				portcullisState = 2;
			}
			break;
		case 2:
			if(Components.getInstance().gateTalon.getEncPosition() > 1300){
				CommonArea.gateDown = false;
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
			}
			break;
			}
		}
	

	public void autonomousPeriodic() {

		Dashboard = SmartDashboard.getDouble("DB/Slider 0");
		if (Dashboard == 0) {
			this.lowBarShoot();
		} else if (Dashboard == 1) {
			if (!CommonArea.autoShot) {
				CommonArea.autoAlign = true;
			}
		} else if (Dashboard == 2) {
			this.moveToDefense();
		} else if (Dashboard == 3) {
			if (!CommonArea.autoShot) {
				CommonArea.autoAlign = true;
			}
		} else if (Dashboard == 4) {
			this.moatShoot();
		} else if (Dashboard == 5) {
			this.portcullis();
		}
		drive.driveTeleopPeriodic();
		// CommonArea.CommonPeriodic();
		// defense.defenseAutoPeriodic();
		shooter.shooterTeleopPeriodic();
		gate.gateTeleopPeriodic();
		vision.Vision();

	}

	public void teleopInit() {
		vision.Reset();

	}

	public void teleopPeriodic() {
		CommonArea.CommonPeriodic();
		drive.driveTeleopPeriodic();
		// defense.defenseTeleopPeriodic();
		shooter.shooterTeleopPeriodic();
		gate.gateTeleopPeriodic();
		vision.Vision();
		SmartDashboard.putNumber("DRIVECURRENT", Components.getInstance().leftFrontDrive.getOutputCurrent());
		camController.step();

	}

	public void disabledInit() {

	}

	public void disabledPeriodic(){
		//gate.gateTestPeriodic();
		//camController.step();
		SmartDashboard.putString("DB/String 0", String.valueOf(Components.getInstance().gateTalon.getEncPosition()));
	}

	int TEST_DRIVE_STATE = 0;
	final int DRIVE_FORWARD = 0;
	final int DRIVE_BACK = 1;
	final int DRIVE_NORMAL = 2;
	final int ENCODER_DISTANCE_ONE_METER_Left = 3000;
	int count = 0;

	public void testInit() {
		drive.driveInit();

	}

	public void testPeriodic() {
		CommonArea.CommonPeriodic();
		SmartDashboard.putNumber("DRIVECURRENT", Components.getInstance().leftFrontDrive.getOutputCurrent());
		drive.driveTeleopPeriodic();
		//drive.DriveRightPID(FirstController.getInstance().getLeftY() * 10);
		//drive.DriveLeftPID(FirstController.getInstance().getLeftY() * 10);

	}
}
