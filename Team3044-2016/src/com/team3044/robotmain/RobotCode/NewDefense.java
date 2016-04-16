// GOODBYE FOREVER

package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NewDefense {

	// RE-MAKE VALUES
	
	public final int TARGET_LA_X1 = 256; // DEFENSE ENCODER POSITIONS LOWER ARM
	public final int TARGET_LA_X2 = 300;
	public final int TARGET_LA_Y1 = 256;
	public final int TARGET_LA_Y2 = 400;
	public final int TARGET_LA_H1 = 256;
	public final int TARGET_LA_H2 = 512;

	public final int TARGET_UA_X1 = 767; // DEFENSE ENCODER POSITIONS UPPER ARM
	public final int TARGET_UA_X2 = 896;
	public final int TARGET_UA_Y1 = 612;
	public final int TARGET_UA_Y2 = 767;
	public final int TARGET_UA_H1 = 512;
	public final int TARGET_UA_H2 = 128;
	
	public final int TARGET_INCREMENT = 60;

	public boolean H1; // TARGET BUTTONS
	public boolean H2;
	public boolean X1;
	public boolean X2;
	public boolean Y1;
	public boolean Y2;
	
	public boolean lowerArmButtonDown; // MANUAL BUTTONS
	public boolean lowerArmButtonUp;
	public boolean upperArmButtonDown;
	public boolean upperArmButtonUp;

	public int lowerArmEncoder; // ENCODERS
	public int upperArmEncoder;
	public int lowerArmEncoderTarget;
	public int upperArmEncoderTarget;

	public final double margin = 50; // TOLERANCE

	public final double lowerArmMovingUpSpeed = -0.15; // MOTOR SPEEDS
	public final double lowerArmMovingDownSpeed = 0.05;
	public final double lowerArmStopSpeed = 0;
	public final double upperArmMovingUpSpeed = -0.1;
	public final double upperArmMovingDownSpeed = 0.1;
	public final double upperArmStopSpeed = 0;

	public CANTalon lowerArmMotor; // MOTORS
	public CANTalon upperArmMotor;

	public boolean lowerArmLimitSwitchHome; // LIMIT SWITCHES
	public boolean lowerArmLimitSwitchTooFar;
	public boolean upperArmLimitSwitchHome;
	public boolean upperArmLimitSwitchTooFar;
	public boolean limitSwitchConflict;

	public void defenseInit() {

		//lowerArmMotor = Components.getInstance().lowerArm;
		//upperArmMotor = Components.getInstance().upperArm;
		lowerArmMotor.set(lowerArmStopSpeed);
		upperArmMotor.set(upperArmStopSpeed);

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {

		upperArmLimitSwitchHome = upperArmMotor.isFwdLimitSwitchClosed();
		upperArmLimitSwitchTooFar = upperArmMotor.isRevLimitSwitchClosed();
		lowerArmLimitSwitchHome = lowerArmMotor.isFwdLimitSwitchClosed();
		lowerArmLimitSwitchTooFar = lowerArmMotor.isRevLimitSwitchClosed();

		lowerArmEncoder = 1023 - lowerArmMotor.getAnalogInRaw();
		upperArmEncoder = 1023 - upperArmMotor.getAnalogInRaw();

		X1 = CommonArea.X1;
		X2 = CommonArea.X2;
		Y1 = CommonArea.Y1;
		Y2 = CommonArea.Y2;
		H1 = CommonArea.H1;
		H2 = CommonArea.H2;

		// LOWER ARM
		
		// UPPER PART OF FLOW CHART
		if(lowerArmButtonDown){
			if(lowerArmMotor.getAnalogInVelocity() != lowerArmMovingDownSpeed){
				lowerArmEncoderTarget = lowerArmEncoderTarget - TARGET_INCREMENT;
			}
		} else if(lowerArmButtonUp){
			if(lowerArmMotor.getAnalogInVelocity() != lowerArmMovingUpSpeed){
				lowerArmEncoderTarget = lowerArmEncoderTarget + TARGET_INCREMENT;
			}
		} else if(X1){
			lowerArmEncoderTarget = TARGET_LA_X1;
		} else if(X2){
			lowerArmEncoderTarget = TARGET_LA_X2;
		} else if(Y1){
			lowerArmEncoderTarget = TARGET_LA_Y1;
		} else if(Y2){
			lowerArmEncoderTarget = TARGET_LA_Y2;
		} else if(H1){
			lowerArmEncoderTarget = TARGET_LA_H1;
		} else if(H2){
			lowerArmEncoderTarget = TARGET_LA_H2;
		}
		
		
		
		// BOTTOM PART OF FLOW CHART
		if (lowerArmEncoder > lowerArmEncoderTarget + margin) {
			if (!lowerArmLimitSwitchHome) {
				if (lowerArmMotor.getAnalogInVelocity() != lowerArmMovingDownSpeed) {
					lowerArmMotor.set(lowerArmMovingDownSpeed);
				}
			} else if (lowerArmMotor.getAnalogInVelocity() != lowerArmStopSpeed) {
				lowerArmMotor.set(lowerArmStopSpeed);
			}
		} else if (lowerArmEncoder < lowerArmEncoderTarget - margin) {
			if (!lowerArmLimitSwitchTooFar) {
				if (lowerArmMotor.getAnalogInVelocity() != lowerArmMovingUpSpeed) {
					lowerArmMotor.set(lowerArmMovingUpSpeed);
				}
			} else if (lowerArmMotor.getAnalogInVelocity() != lowerArmStopSpeed) {
				lowerArmMotor.set(lowerArmStopSpeed);
			}
		} else if (lowerArmMotor.getAnalogInVelocity() != lowerArmStopSpeed) {
			lowerArmMotor.set(lowerArmStopSpeed);
		}

	}
}