package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;

public class Drive {

	FirstController controller = FirstController.getInstance();

	public enum state {
		stopped, moveLeftMotor, moveRightMotor, moveBothMotors
	}

	state autoDriveState = state.stopped;
	double leftAutoSpeed;
	double rightAutoSpeed;
	double leftDriveSpeed;
	double rightDriveSpeed;
	double rightDesiredEncoderValue;
	double leftDesiredEncoderValue;

	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;

	public double encoderTolerance = 50;

	public boolean isAtDistance(double current, double desired) {
		if (desired + encoderTolerance < current
				|| desired - encoderTolerance > current) {
			return true;
		} else {
			return false;
		}
	}

	public void driveInit() {

		leftFrontDrive = Components.getInstance().leftFrontDrive;
		leftBackDrive = Components.getInstance().leftFrontDrive;
		rightFrontDrive = Components.getInstance().rightFrontDrive;
		rightBackDrive = Components.getInstance().rightBackDrive;

		leftFrontDrive.setPosition(0);
		rightFrontDrive.setPosition(0);

		leftFrontDrive.enableBrakeMode(true);
		rightFrontDrive.enableBrakeMode(true);
		leftBackDrive.enableBrakeMode(true);
		rightBackDrive.enableBrakeMode(true);
	}

	public void driveAutoPeriodic() {
		boolean movexFeet = CommonArea.movexFeet;
		double rightCurrentEncoderValue = rightFrontDrive.getAnalogInRaw();
		double leftCurrentEncoderValue = leftFrontDrive.getAnalogInRaw();

		boolean leftOnTarget = isAtDistance(leftCurrentEncoderValue,
				leftDesiredEncoderValue);
		boolean rightOnTarget = isAtDistance(rightCurrentEncoderValue,
				rightDesiredEncoderValue);

		leftAutoSpeed = CommonArea.leftAutoSpeed;
		rightAutoSpeed = -CommonArea.rightAutoSpeed;

		if (Math.abs(leftAutoSpeed) < .1) {
			leftAutoSpeed = 0;
		}
		if (Math.abs(rightAutoSpeed) < .1) {
			rightAutoSpeed = 0;
		}

		leftFrontDrive.set(leftAutoSpeed);
		leftBackDrive.set(leftAutoSpeed);
		rightFrontDrive.set(rightAutoSpeed);
		rightBackDrive.set(rightAutoSpeed);

		switch (autoDriveState) {
		case stopped:
			if (!rightOnTarget && leftOnTarget && movexFeet) {
				rightFrontDrive.set(rightAutoSpeed);
				rightBackDrive.set(rightAutoSpeed);
				autoDriveState = state.moveRightMotor;

			} else if (!leftOnTarget && rightOnTarget && movexFeet) {
				leftFrontDrive.set(leftAutoSpeed);
				leftBackDrive.set(leftAutoSpeed);
				autoDriveState = state.moveLeftMotor;

			} else if (!leftOnTarget && !rightOnTarget && movexFeet) {
				leftFrontDrive.set(leftAutoSpeed);
				leftBackDrive.set(leftAutoSpeed);
				rightFrontDrive.set(rightAutoSpeed);
				rightBackDrive.set(rightAutoSpeed);
				autoDriveState = state.moveBothMotors;
			} else if (leftOnTarget && rightOnTarget && movexFeet) {
				CommonArea.atDistance = true;
			}
			break;
		case moveLeftMotor:
			if (rightOnTarget && leftOnTarget) {
				autoDriveState = state.stopped;

			} else if (!rightOnTarget && !leftOnTarget) {
				leftFrontDrive.set(leftAutoSpeed);
				leftBackDrive.set(leftAutoSpeed);
				rightFrontDrive.set(rightAutoSpeed);
				rightBackDrive.set(rightAutoSpeed);
				autoDriveState = state.moveBothMotors;
			}
			break;
		case moveRightMotor:
			if (rightOnTarget && leftOnTarget) {
				autoDriveState = state.stopped;

			} else if (!rightOnTarget && !leftOnTarget) {
				leftFrontDrive.set(leftAutoSpeed);
				leftBackDrive.set(leftAutoSpeed);
				rightFrontDrive.set(rightAutoSpeed);
				rightBackDrive.set(rightAutoSpeed);
				autoDriveState = state.moveBothMotors;
			}
			break;
		case moveBothMotors:
			if (rightOnTarget && leftOnTarget) {
				autoDriveState = state.stopped;

			} else if (!rightOnTarget && leftOnTarget) {
				rightFrontDrive.set(rightAutoSpeed);
				rightBackDrive.set(rightAutoSpeed);
				autoDriveState = state.moveRightMotor;

			} else if (!leftOnTarget && rightOnTarget) {
				leftFrontDrive.set(leftAutoSpeed);
				leftBackDrive.set(leftAutoSpeed);
				autoDriveState = state.moveLeftMotor;
			}
			break;
		}
	}

	public void driveTeleopPeriodic() {

		if (!CommonArea.isManualDrive) {
			leftDriveSpeed = CommonArea.leftDriveSpeed;
			rightDriveSpeed = -CommonArea.rightDriveSpeed;

		} else {
			leftDriveSpeed = (controller.getLeftY());
			rightDriveSpeed = (-controller.getRightY());

			if (controller.getTriggerLeft() > .5) {
				leftDriveSpeed = leftDriveSpeed * .5;
				rightDriveSpeed = rightDriveSpeed * .5;
			}
		}

		if (Math.abs(leftDriveSpeed) < .1) {
			leftDriveSpeed = 0;
		}
		if (Math.abs(rightDriveSpeed) < .1) {
			rightDriveSpeed = 0;
		}
		leftFrontDrive.set(leftDriveSpeed);
		leftBackDrive.set(leftDriveSpeed);
		rightFrontDrive.set(rightDriveSpeed);
		rightBackDrive.set(rightDriveSpeed);
	}

	public void testPeriodic() {

		leftFrontDrive.set(SmartDashboard.getDouble("DB/ Slider 0"));
		leftBackDrive.set(SmartDashboard.getDouble("DB/ Slider 1"));
		rightFrontDrive.set(SmartDashboard.getDouble("DB/ Slider 2"));
		rightBackDrive.set(SmartDashboard.getDouble("DB/ SLider 3"));

		SmartDashboard.putString("DB/ String 0",
				String.valueOf(leftFrontDrive.getAnalogInRaw()));
		SmartDashboard.putString("DB/ String 1",
				String.valueOf(rightFrontDrive.getAnalogInRaw()));

		driveTeleopPeriodic();
	}
}
