
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;

public class Drive {
	Components components = new Components();
	FirstController controller = FirstController.getInstance();
	CommonArea commonarea = new CommonArea();

	double leftAutoSpeed;
	double rightAutoSpeed;
	double leftDriveSpeed;
	double rightDriveSpeed;

	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;

	public double leftDriveEncoder;
	public double rightFrontEncoder;

	public void driveInit() {

		CANTalon leftFrontDrive = components.leftFrontDrive;
		CANTalon leftBackDrive = components.leftFrontDrive;
		CANTalon rightFrontDrive = components.rightFrontDrive;
		CANTalon rightBackDrive = components.rightBackDrive;

	}

	public void driveAutoPeriodic() {

		leftAutoSpeed = commonarea.leftAutoSpeed;
		rightAutoSpeed = -commonarea.rightAutoSpeed;

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
	}

	public void driveTeleopPeriodic() {

		if (commonarea.aimFlag) {
			leftDriveSpeed = commonarea.leftDriveSpeed;
			rightDriveSpeed = -commonarea.rightDriveSpeed;

		} else {
			leftDriveSpeed = (controller.getLeftY());
			rightDriveSpeed = (-controller.getRightY());

			if (controller.getTriggerLeft()>.5) {
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

}
