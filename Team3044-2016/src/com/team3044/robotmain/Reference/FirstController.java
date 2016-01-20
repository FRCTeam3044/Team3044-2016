package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class FirstController {
	private static FirstController instance = null;
	
	private Joystick firstJoy;
	
	private FirstController() {
		firstJoy = new Joystick(0);
	}
	
	public double getLeftX() {
		return firstJoy.getRawAxis(0);
	}

	public double getLeftY() {
		return firstJoy.getRawAxis(1);
	}

	public double getRightX() {
		return firstJoy.getRawAxis(4);
	}

	public double getRightY() {
		return firstJoy.getRawAxis(5);
	}

	public double getTriggerRight() {
		return firstJoy.getRawAxis(3);
	}

	public double getTriggerLeft() {
		return firstJoy.getRawAxis(2);
	}
	public boolean getRawButton(int num) {
		if (!DriverStation.getInstance().isAutonomous()) {
			return firstJoy.getRawButton(num);

		} else {
			return false;
		}
	}
    
}
