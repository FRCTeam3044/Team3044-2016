
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;

public class Drive {
	Components components = new Components();
	FirstController controller = FirstController.getInstance();

	double leftSpeed;
	double rightSpeed;
	boolean halfSpeed;
	boolean visionFlag;
	double leftDrive;
	double rightDrive;

	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;

	public double leftDriveEncoder;
	public double rightFrontEncoder;

	public void driveInit() {

		leftFrontDrive = new CANTalon(1);
		leftBackDrive = new CANTalon(2);
		rightFrontDrive = new CANTalon(3);
		rightBackDrive = new CANTalon(4);

	}

	public void driveAutoPeriodic() {

		leftSpeed = leftDrive;
		rightSpeed = -rightDrive;

		if (Math.abs(leftSpeed) < .1){
			leftSpeed = 0;
		}
		if (Math.abs(rightSpeed) < .1){
			rightSpeed = 0;

		}
		leftFrontDrive.set(leftSpeed);
		leftBackDrive.set(leftSpeed);
		rightFrontDrive.set(rightSpeed);
		rightBackDrive.set(rightSpeed);
	}

	public void driveTeleopPeriodic() {

		if (visionFlag){
			leftSpeed = leftDrive;
			rightSpeed = -rightDrive;

		} else {
			leftSpeed = (controller.getLeftY());
			rightSpeed = (-controller.getRightY());
			halfSpeed = (controller.getRawButton(controller.BUTTON_A));

			if (halfSpeed){
				leftSpeed = leftSpeed*.5;
				rightSpeed = rightSpeed*.5;
			}

		}
		if (Math.abs(leftSpeed) < .1){
			leftSpeed = 0;
		}
		if (Math.abs(rightSpeed) < .1){
			rightSpeed = 0;

		}
		leftFrontDrive.set(leftSpeed);
		leftBackDrive.set(leftSpeed);
		rightFrontDrive.set(rightSpeed);
		rightBackDrive.set(rightSpeed);


	}

}
