package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class SecondaryController {
	private static SecondaryController instance = null;
	
	private Joystick secondJoy;
	
	public static int BUTTON_X = 3; //defense 
	public static int BUTTON_Y = 4;
	public static int BUTTON_B = 2;
	public static int BUTTON_A = 1; //defense

	public static int BUTTON_RT = 6;
	public static int BUTTON_LT = 5;
	public static int BUTTON_BACK = 7;
	public static int BUTTON_START = 8; //defense
	//ANALOG STICK BUTTONS STILL AVAILABLE
	
	private SecondaryController() {
		secondJoy = new Joystick(1);
	}
	
	public static SecondaryController getInstance() {
		if (instance == null) {
			instance = new SecondaryController();
		}

		return instance;
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