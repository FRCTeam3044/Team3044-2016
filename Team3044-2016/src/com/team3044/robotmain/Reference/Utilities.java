package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Utilities {
//Place any functions in here
	public static boolean tolerance(double lowerValue, double actualValue, double upperValue){
		return (lowerValue < actualValue) && (actualValue < upperValue); 
	}
}
