package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class FirstController {
	private static FirstController instance = null;
	
	private Joystick firstJoy;
	
	public static int BUTTON_X = 3;
	public static int BUTTON_Y = 4;
	public static int BUTTON_B = 2;
	public static int BUTTON_A = 1;

	public static int BUTTON_RT = 6;
	public static int BUTTON_LT = 5;
	public static int BUTTON_BACK = 7;
	public static int BUTTON_START = 8;
	//ANALOG STICK BUTTONS STILL AVAILABLE
	
	private FirstController() {
		firstJoy = new Joystick(0);
	}
	
	public static FirstController getInstance() {
		if (instance == null) {
			instance = new FirstController();
		}

		return instance;
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
