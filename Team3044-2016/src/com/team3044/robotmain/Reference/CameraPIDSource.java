package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraPIDSource implements PIDSource {

	double angle = 0;
	private boolean isManual = false;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	public void step(double val) {
		if(this.isManual == false){
			this.isManual = true;
		}
		this.angle = val;
	}

	public void step() {
		this.angle = SmartDashboard.getNumber("ANGLE", 0);
	}

	@Override
	public double pidGet() {
		if (this.isManual) {
		} else {
			angle = SmartDashboard.getNumber("ANGLE", 0);
		}
		return angle;
	}

}
