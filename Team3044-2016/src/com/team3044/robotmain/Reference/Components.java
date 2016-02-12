package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class Components {

	private static Components instance = null;

	private Components() {

	}
 
	public static Components getInstance() {
		if (instance == null) {
			instance = new Components();
		}
		return instance;
	}

	// Drive System
	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;

	public static double leftDriveEncoder;
	public static double rightDriveEncoder;

	// Shooter
	public CANTalon topShooter;
	public CANTalon botShooter;

	// Defense
	public CANTalon lowerArm;
	public CANTalon upperArm;

	// Pick Up
	public CANTalon gateTalon;
	public CANTalon shooterTrack;
	public CANTalon Pickup3;

	// Digital IOs
	public DigitalInput conflict = new DigitalInput(0);
	public DigitalInput topTacho = new DigitalInput(5);
	public DigitalInput botTacho = new DigitalInput(4);

	public DigitalInput GateUpLimit = new DigitalInput(1);
	public DigitalInput GateDownLimit = new DigitalInput(2);
	public DigitalInput BallInLimit = new DigitalInput(3);

	public Counter topTachoCounter = new Counter(topTacho);
	public Counter botTachoCounter = new Counter(botTacho);

	public void init() {
		leftFrontDrive = new CANTalon(1);
		leftBackDrive = new CANTalon(2);
		rightFrontDrive = new CANTalon(3);
		rightBackDrive = new CANTalon(4);

		leftFrontDrive.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
		rightFrontDrive.setFeedbackDevice(FeedbackDevice.AnalogEncoder);

		topShooter = new CANTalon(5);
		botShooter = new CANTalon(6);

		lowerArm = new CANTalon(7);
		upperArm = new CANTalon(8);

		gateTalon = new CANTalon(9);
		shooterTrack = new CANTalon(10);
		Pickup3 = new CANTalon(11);
	}
}
