package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class SecondaryController {
	private static SecondaryController instance = null;
	
	private Joystick secondJoy;
	
	private SecondaryController() {
		secondJoy = new Joystick(1);
	}
	
	public double getLeftX() {
		return secondJoy.getRawAxis(0);
	}

	public double getLeftY() {
		return secondJoy.getRawAxis(1);
	}

	public double getRightX() {
		return secondJoy.getRawAxis(4);
	}

	public double getRightY() {
		return secondJoy.getRawAxis(5);
	}

	public double getTriggerRight() {
		return secondJoy.getRawAxis(3);
	}

	public double getTriggerLeft() {
		return secondJoy.getRawAxis(2);
	}
	
	public boolean getRawButton(int num) {
		if (!DriverStation.getInstance().isAutonomous()) {
			return secondJoy.getRawButton(num);

		} else {
			return false;
		}
	}
}