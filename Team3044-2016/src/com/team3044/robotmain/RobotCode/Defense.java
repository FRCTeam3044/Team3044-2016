
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;

public class Defense {

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, 
		UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, 
		CALIBRATING
	}

	public final double TARGET_LA_X1 = 0.9; // DEFENSE ENCODER POSITIONS
	public final double TARGET_LA_X2 = 0.5;
	public final double TARGET_LA_Y1 = 0.9;
	public final double TARGET_LA_Y2 = 0.9;
	public final double TARGET_LA_H1 = 0.0;
	public final double TARGET_LA_H2 = 0.0;
	public final double TARGET_UA_X1 = 0.5;
	public final double TARGET_UA_X2 = 0.5;
	public final double TARGET_UA_Y1 = 0.5;
	public final double TARGET_UA_Y2 = 0.9;
	public final double TARGET_UA_H1 = 0.2;
	public final double TARGET_UA_H2 = 0.0;

	public double targetLowerArm; // TARGET ENCODER POSITIONS
	public double targetUpperArm;
	
	public boolean calibrated; // TEST IF WE ARE CALIBRATED

	public boolean lowerArmButtonDown; // MANUAL BUTTONS
	public boolean lowerArmButtonUp;
	public boolean upperArmButtonDown;
	public boolean upperArmButtonUp;
	public boolean calibrationButton;
	public boolean stopTargeting;

	public boolean H1; // TARGET BUTTONS
	public boolean H2;
	public boolean X1;
	public boolean X2;
	public boolean Y1;
	public boolean Y2;

	public double lowerArmEncoder; // ENCODERS
	public double upperArmEncoder;
	public double lowerArmEncoderTarget;
	public double upperArmEncoderTarget;

	public double lowerArmMovingUpSpeed; // MOTOR SPEEDS
	public double lowerArmMovingDownSpeed;
	public double lowerArmStopSpeed;
	public double upperArmMovingUpSpeed;
	public double upperArmMovingDownSpeed;
	public double upperArmStopSpeed;
	public double calibratingSpeed;

	public CANTalon lowerArmMotor; // MOTORS
	public CANTalon upperArmMotor;

	state LOWER_ARM = state.LA_STOPPED; // DEFAULT STARTING STATES AND SWITCH
										// NAMES
	state UPPER_ARM = state.UA_STOPPED;

	public boolean lowerArmLimitSwitchHome; // LIMIT SWITCHES
	public boolean lowerArmLimitSwitchTooFar;
	public boolean upperArmLimitSwitchHome;
	public boolean upperArmLimitSwitchTooFar;
	public boolean limitSwitchConflict;

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		lowerArmLimitSwitchHome = Components.getInstance().upperArm.isFwdLimitSwitchClosed();
		lowerArmLimitSwitchTooFar = Components.getInstance().upperArm.isRevLimitSwitchClosed();
		upperArmLimitSwitchHome = Components.getInstance().lowerArm.isFwdLimitSwitchClosed();
		upperArmLimitSwitchTooFar = Components.getInstance().lowerArm.isRevLimitSwitchClosed();
		limitSwitchConflict = Components.getInstance().conflict.get();

		lowerArmMotor = Components.getInstance().lowerArm;
		upperArmMotor = Components.getInstance().upperArm;

		lowerArmEncoder = Components.getInstance().lowerArm.getEncPosition();
		upperArmEncoder = Components.getInstance().upperArm.getEncPosition();

		X1 = CommonArea.dPadUp;
		X2 = CommonArea.dPadDown;
		Y1 = CommonArea.dPadLeft;
		Y2 = CommonArea.dPadRight;
		H1 = CommonArea.homeArmStart;
		H2 = CommonArea.homeArmEnd;

		switch (LOWER_ARM) {

		default:
			LOWER_ARM = state.LA_STOPPED;
			lowerArmMotor.set(lowerArmStopSpeed);
			break;

		case LA_STOPPED:
			if(limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if(lowerArmButtonUp && !lowerArmLimitSwitchTooFar){
				LOWER_ARM = state.LA_MOVING_UP;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if(lowerArmButtonDown && !lowerArmLimitSwitchHome){
				LOWER_ARM = state.LA_MOVING_DOWN;
				lowerArmMotor.set(lowerArmMovingDownSpeed);
			} else if(calibrated){
				if(X1){
					if(lowerArmEncoder < TARGET_LA_X1){
						if(!lowerArmLimitSwitchTooFar){
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}
			} else if(calibrationButton && !limitSwitchConflict){
				LOWER_ARM = state.CALIBRATING;
				lowerArmMotor.set(calibratingSpeed); 
			}
			
			
			
			switch (UPPER_ARM) {

			default:
				LOWER_ARM = state.LA_STOPPED;
				break;

			}
		}
	}
}
