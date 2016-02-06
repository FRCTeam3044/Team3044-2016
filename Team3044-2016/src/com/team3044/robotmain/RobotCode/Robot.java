package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.CommonArea;
import com.team3044.robotmain.Reference.Components;
import com.team3044.robotmain.Reference.FirstController;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Defense defense = new Defense();
	Drive drive = new Drive();
	Shooter shooter = new Shooter();
	Components components = new Components();
	Gate gate = new Gate();

	public void robotInit() {
		components.init();
		drive.driveInit();
		defense.defenseInit();
		shooter.shooterInit();
		gate.gateInit();
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		drive.driveAutoPeriodic();
		defense.defenseAutoPeriodic();
		shooter.shooterAutoPeriodic();
		gate.gateAutoPeriodic();
	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {
		drive.driveTeleopPeriodic();
		defense.defenseTeleopPeriodic();
		shooter.shooterTeleopPeriodic();
		gate.gateTeleopPeriodic();
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {

	}

	int TEST_DRIVE_STATE = 0;
	final int DRIVE_FORWARD = 0;
	final int DRIVE_BACK = 1;
	final int DRIVE_NORMAL = 2;
	final int ENCODER_DISTANCE_ONE_METER = 3000; // Need to change
	int count = 0;

	public void testInit() {
		count = 0;
		TEST_DRIVE_STATE = DRIVE_NORMAL;
	}

	public void testPeriodic() {

		SmartDashboard
				.putString("DB/String 0", String
						.valueOf(components.leftFrontDrive
								.getAnalogInPosition()));
		SmartDashboard.putString("DB/String 1", String
				.valueOf(components.rightFrontDrive.getAnalogInPosition()));

		switch (TEST_DRIVE_STATE) {
		case DRIVE_NORMAL:
			if (FirstController.getInstance().getRawButton(
					FirstController.BUTTON_START)) {
				TEST_DRIVE_STATE = DRIVE_FORWARD;
				count = 0;
				components.leftFrontDrive.setPosition(0);
				components.rightFrontDrive.setPosition(0);
				CommonArea.leftDriveSpeed = .5;
				CommonArea.rightDriveSpeed = .5;
				CommonArea.isManualDrive = false;
			}
			break;
		case DRIVE_FORWARD:
			if (FirstController.getInstance().getRawButton(
					FirstController.BUTTON_BACK)) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				this.TEST_DRIVE_STATE = DRIVE_NORMAL;
				CommonArea.isManualDrive = true;
			}

			if (components.leftFrontDrive.getAnalogInPosition() > ENCODER_DISTANCE_ONE_METER) {
				CommonArea.leftDriveSpeed = 0;
			}

			if (components.rightFrontDrive.getAnalogInPosition() > ENCODER_DISTANCE_ONE_METER) {
				CommonArea.rightDriveSpeed = 0;
			}

			if (components.rightFrontDrive.getAnalogInPosition() > ENCODER_DISTANCE_ONE_METER
					&& components.leftFrontDrive.getAnalogInPosition() > ENCODER_DISTANCE_ONE_METER) {
				TEST_DRIVE_STATE = DRIVE_BACK;
				CommonArea.rightDriveSpeed = -.5;
				CommonArea.leftDriveSpeed = -.5;
			}

			break;
		case DRIVE_BACK:
			if (FirstController.getInstance().getRawButton(
					FirstController.BUTTON_BACK)) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				this.TEST_DRIVE_STATE = DRIVE_NORMAL;
				CommonArea.isManualDrive = true;
			}

			if (components.leftFrontDrive.getAnalogInPosition() < 1) {
				CommonArea.leftDriveSpeed = 0;
			}

			if (components.rightFrontDrive.getAnalogInPosition() < 1) {
				CommonArea.rightDriveSpeed = 0;
			}

			if (components.rightFrontDrive.getAnalogInPosition() < 1
					&& components.leftFrontDrive.getAnalogInPosition() < 1) {
				if (count <= 5) {
					TEST_DRIVE_STATE = DRIVE_FORWARD;
					CommonArea.rightDriveSpeed = .5;
					CommonArea.leftDriveSpeed = .5;
					count ++;
				} else {
					TEST_DRIVE_STATE = DRIVE_NORMAL;
					CommonArea.rightDriveSpeed = 0;
					CommonArea.leftDriveSpeed = 0;
					CommonArea.isManualDrive = true;
				}
			}

			break;
		}
		drive.driveTeleopPeriodic();
	}
}
