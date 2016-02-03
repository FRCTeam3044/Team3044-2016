package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Utilities {

	public static boolean tolerance(double lowerValue, double actualValue, double upperValue){
		return (lowerValue < actualValue) && (actualValue < upperValue); 
	}
	
	public static double deadband (double Value, double Tolerance){
		if (-Tolerance < Value && Value < Tolerance){
			return 0; 
		} else {
			return Value;
		}
	}
}
