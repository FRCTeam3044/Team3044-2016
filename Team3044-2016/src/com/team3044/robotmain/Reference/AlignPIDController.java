package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class AlignPIDController implements PIDOutput {

	double value = 0;
	@Override
	public void pidWrite(double output) {

		this.value = output;
		
	}
	
	public double getSpeed(){
		return -value;
	}



}
