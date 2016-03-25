package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.PIDOutput;

public class AlignPIDController implements PIDOutput {

	double value = 0;

	@Override
	public void pidWrite(double output) {

		this.value = output;

	}

	public double getSpeed() {
		if (value < -.3) {
			value = -.3;
		} else if (value > .3) {
			value = .3;
		}
		return -value;
	}

}
