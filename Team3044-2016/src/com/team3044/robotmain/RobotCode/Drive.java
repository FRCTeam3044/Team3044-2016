
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;

public class Drive {
	
	FirstController controller = FirstController.getInstance();
	
	double leftAutoSpeed;
	double rightAutoSpeed;
	double leftDriveSpeed;
	double rightDriveSpeed;

	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;

	public void driveInit() {

		CANTalon leftFrontDrive = Components.leftFrontDrive;
		CANTalon leftBackDrive = Components.leftFrontDrive;
		CANTalon rightFrontDrive = Components.rightFrontDrive;
		CANTalon rightBackDrive = Components.rightBackDrive;

	}

	public void driveAutoPeriodic() {

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
	}

	public void driveTeleopPeriodic() {

		if (!CommonArea.isManualDrive) {
			leftDriveSpeed = CommonArea.leftDriveSpeed;
			rightDriveSpeed = -CommonArea.rightDriveSpeed;

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
