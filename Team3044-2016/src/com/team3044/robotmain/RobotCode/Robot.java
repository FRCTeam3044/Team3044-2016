package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.CommonArea;
import com.team3044.robotmain.Reference.Components;
import com.team3044.robotmain.Reference.FirstController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	//Defense defense = new Defense();
	Drive drive = new Drive();
	Shooter shooter = new Shooter();
	Gate gate = new Gate();

	// VisionCalc vision = new VisionCalc();

	public void robotInit() {
		Components.getInstance().init();
		drive.driveInit();
		// defense.defenseInit();
		shooter.shooterInit();
		gate.gateInit();
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		if(SmartDashboard.getDouble("DB/Slider 0") == 0){
			
		} else if(SmartDashboard.getDouble("DB/Slider 0") == 1){
			
		} else if(SmartDashboard.getDouble("DB/Slider 0") == 2){
			
		} else if(SmartDashboard.getDouble("DB/Slider 0") == 3){
			
		} else if(SmartDashboard.getDouble("DB/Slider 0") == 4){
			
		} else if(SmartDashboard.getDouble("DB/Slider 0") == 5){
			
		}
		// drive.driveAutoPeriodic();
		// defense.defenseAutoPeriodic();
		// shooter.shooterAutoPeriodic();
		// gate.gateAutoPeriodic();
		// vision.Vision();
	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {
		// drive.driveTeleopPeriodic();
		// defense.defenseTeleopPeriodic();
		// shooter.shooterTeleopPeriodic();
		// gate.gateTeleopPeriodic();
		// vision.Vision();
	}

	public void disabledInit() {

	}

	public void disabledPeriodic() {

	}

	int TEST_DRIVE_STATE = 0;
	final int DRIVE_FORWARD = 0;
	final int DRIVE_BACK = 1;
	final int DRIVE_NORMAL = 2;
	final int ENCODER_DISTANCE_ONE_METER_Left = 3000; // Need to change
	int count = 0;

	public void testInit() {
		// count = 0;
		// TEST_DRIVE_STATE = DRIVE_NORMAL;
		// CommonArea.isManualDrive = true;
		// Components.getInstance().leftFrontDrive.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
		// Components.getInstance().rightFrontDrive.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
		// Components.getInstance().leftFrontDrive.setAnalogPosition(0);
		// Components.getInstance().rightFrontDrive.setAnalogPosition(0);
		// Components.getInstance().leftFrontDrive.setPosition(0);
		// Components.getInstance().rightFrontDrive.setPosition(0);

		// Components.getInstance().leftFrontDrive.getAnalogInPosition();
	}

	int subtract = 0;

	public void testPeriodic() {
		shooter.shooterTestPeriodic();
		gate.gateTestPeriodic();
		drive.testPeriodic();
		// Components.getInstance().leftBackDrive.set(.3);
		// Components.getInstance().leftFrontDrive.set(.3);

		SmartDashboard.putString("DB/String 0", String.valueOf(Components
				.getInstance().leftFrontDrive.getAnalogInPosition()));
		SmartDashboard.putString("DB/String 1", String.valueOf(Components
				.getInstance().rightFrontDrive.getAnalogInPosition()));
		/*
		 * if (FirstController.getInstance()
		 * .getRawButton(FirstController.BUTTON_B)) { testInit(); } switch
		 * (TEST_DRIVE_STATE) { /* case DRIVE_NORMAL: if
		 * (FirstController.getInstance().getRawButton(
		 * FirstController.BUTTON_START)) { TEST_DRIVE_STATE = DRIVE_FORWARD; //
		 * count = 0; Components.getInstance().leftFrontDrive.setPosition(0);
		 * Components.getInstance().rightFrontDrive.setPosition(0); subtract =
		 * Components.getInstance().leftFrontDrive .getAnalogInPosition();
		 * CommonArea.leftDriveSpeed = .3; CommonArea.rightDriveSpeed = .3;
		 * CommonArea.isManualDrive = false; } break; case DRIVE_FORWARD: if
		 * (FirstController.getInstance().getRawButton(
		 * FirstController.BUTTON_BACK)) { CommonArea.leftDriveSpeed = 0;
		 * CommonArea.rightDriveSpeed = 0;
		 * 
		 * this.TEST_DRIVE_STATE = DRIVE_NORMAL; CommonArea.isManualDrive =
		 * true; }
		 * 
		 * if (Components.getInstance().leftFrontDrive.getAnalogInPosition() <
		 * -ENCODER_DISTANCE_ONE_METER_Left) { CommonArea.leftDriveSpeed = 0;
		 * 
		 * }
		 * 
		 * if (Components.getInstance().rightFrontDrive.getAnalogInPosition() >
		 * ENCODER_DISTANCE_ONE_METER_Left) { CommonArea.rightDriveSpeed = 0; }
		 * 
		 * if (Components.getInstance().rightFrontDrive.getAnalogInPosition() >
		 * ENCODER_DISTANCE_ONE_METER_Left &&
		 * Components.getInstance().leftFrontDrive .getAnalogInPosition() <
		 * -ENCODER_DISTANCE_ONE_METER_Left) { TEST_DRIVE_STATE = DRIVE_BACK;
		 * CommonArea.rightDriveSpeed = -.3; CommonArea.leftDriveSpeed = -.3; }
		 * 
		 * break; case DRIVE_BACK: if
		 * (FirstController.getInstance().getRawButton(
		 * FirstController.BUTTON_BACK)) { CommonArea.leftDriveSpeed = 0;
		 * CommonArea.rightDriveSpeed = 0; this.TEST_DRIVE_STATE = DRIVE_NORMAL;
		 * CommonArea.isManualDrive = true; }
		 * 
		 * if (Components.getInstance().leftFrontDrive.getAnalogInPosition() >
		 * -1) { CommonArea.leftDriveSpeed = 0; }
		 * 
		 * if (Components.getInstance().rightFrontDrive.getAnalogInPosition() <
		 * 1) { CommonArea.rightDriveSpeed = 0; }
		 * 
		 * if (Components.getInstance().rightFrontDrive.getAnalogInPosition() <
		 * 1 && Components.getInstance().leftFrontDrive .getAnalogInPosition() >
		 * -1) { if (count <= 5) { TEST_DRIVE_STATE = DRIVE_FORWARD;
		 * CommonArea.rightDriveSpeed = .3; CommonArea.leftDriveSpeed = .3;
		 * count++; } else { TEST_DRIVE_STATE = DRIVE_NORMAL;
		 * CommonArea.rightDriveSpeed = 0; CommonArea.leftDriveSpeed = 0;
		 * CommonArea.isManualDrive = true; } }
		 * 
		 * break;
		 */
	}
}
