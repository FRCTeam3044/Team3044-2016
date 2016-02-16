package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class SecondaryController {
	private static SecondaryController instance = null;
	
	private Joystick secondJoy;
	
	public static int BUTTON_X = 3; 
	public static int BUTTON_Y = 4; 
	public static int BUTTON_B = 2; 
	public static int BUTTON_A = 1; 

	public static int BUTTON_RB = 6;
	public static int BUTTON_LB = 5;
	public static int BUTTON_BACK = 7;
	public static int BUTTON_START = 8;
	
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

	public boolean getTriggerRight() {
		if(Math.abs(secondJoy.getRawAxis(3))>0){
			return true;
		} else {
			return false;
		}
	}

	public boolean getTriggerLeft() {
		if(Math.abs(secondJoy.getRawAxis(2))>0){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDPadLeft(){
		if(secondJoy.getPOV() == 270){ //DEFENSE
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDPadRight(){ //DEFENSE
		if(secondJoy.getPOV() == 90){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDPadUp(){ //DEFENSE
		if(secondJoy.getPOV() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDPadDown(){ //DEFENSE
		if(secondJoy.getPOV() == 180){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDPadOther(){
		if(secondJoy.getPOV() == -1){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getRawButton(int num) {
		if (!DriverStation.getInstance().isAutonomous()) {
			return secondJoy.getRawButton(num);

		} else {
			return false;
		}
	}
}