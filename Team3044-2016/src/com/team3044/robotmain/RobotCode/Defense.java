
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, 
		UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, 
		MAIN_STOPPED, MAIN_MOVING_MANUALLY, MAIN_MOVING_TARGET, ENCODER_ZEROING_LOWER_ARM,ENCODER_ZEROING_UPPER_ARM
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
	
	public boolean encoderCalibrated = false;
	
	public double targetLowerArm;
	public double targetUpperArm;
	

	public boolean laButtonDown; // MANUAL BUTTONS
	public boolean laButtonUp;
	public boolean uaButtonDown;
	public boolean uaButtonUp;
	
	public boolean H1; // TARGET BUTTONS
	public boolean H2;
	public boolean X1;
	public boolean X2;
	public boolean Y1;
	public boolean Y2;

	public double lowerArmEnc; // ENCODERS
	public double upperArmEnc; 
	public double lowerArmEncTarget; 
	public double upperArmEncTarget;

	public boolean commandLowerArmUp; // COMMANDS TO GET TO THE NEXT STATE
	public boolean commandLowerArmDown;
	public boolean commandLowerArmStop;
	public boolean commandLowerArmTarget;
	public boolean commandUpperArmUp; 
	public boolean commandUpperArmDown;
	public boolean commandUpperArmStop;
	public boolean commandUpperArmTarget;

	public double LA_MOVING; // MOTORS
	public double LA_MOVING_UP_SPEED;
	public double LA_MOVING_DOWN_SPEED;
	public double LA_SPEED_STOP;
	public double UA_MOVING;
	public double UA_MOVING_UP_SPEED;
	public double UA_MOVING_DOWN_SPEED;
	public double UA_SPEED_STOP;

	state LOWER_ARM = state.LA_STOPPED; // DEFAULT STARTING STATES AND SWITCH NAMES
	state MAIN = state.MAIN_STOPPED;
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

		lowerArmEnc = Components.getInstance().lowerArm.getEncPosition();
		upperArmEnc = Components.getInstance().upperArm.getEncPosition();

		X1 = CommonArea.dPadUp;
		X2 = CommonArea.dPadDown;
		Y1 = CommonArea.dPadLeft;
		Y2 = CommonArea.dPadRight;
		H1 = CommonArea.homeArmStart;
		H2 = CommonArea.homeArmEnd;


		switch (MAIN) {

		default:
			MAIN = state.MAIN_STOPPED;
			commandUpperArmStop = true;
			commandLowerArmStop = true;
			break;

		case MAIN_MOVING_MANUALLY:
			if (UPPER_ARM == state.UA_STOPPED && LOWER_ARM == state.LA_STOPPED) {
				commandLowerArmUp = false;
				commandUpperArmDown = false;
				commandLowerArmUp = false;
				commandLowerArmDown = false;
				MAIN = state.MAIN_STOPPED;
			} else if (!laButtonDown && !laButtonUp && !uaButtonDown && !uaButtonDown) {
				commandLowerArmStop = true;
				commandUpperArmStop = true;
				commandLowerArmUp = false;
				commandUpperArmDown = false;
				commandLowerArmUp = false;
				commandLowerArmDown = false;
				MAIN = state.MAIN_STOPPED;				
			}
			break;

		case MAIN_MOVING_TARGET:
			if (UPPER_ARM == state.UA_STOPPED && LOWER_ARM == state.LA_STOPPED) {			
				MAIN = state.MAIN_STOPPED;
			} else if (laButtonDown) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (laButtonUp) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (uaButtonDown) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (uaButtonUp) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			}
			break;

		case MAIN_STOPPED:
			commandLowerArmStop = false;
			commandUpperArmStop = false;
			
			if (X1) {
				targetUpperArm = TARGET_UA_X1;
				targetLowerArm = TARGET_LA_X1;
				commandLowerArmTarget = true;
				commandUpperArmTarget = true;
				MAIN = state.MAIN_MOVING_TARGET;				
			} else if (X2) {
				targetUpperArm = TARGET_UA_X2;
				targetLowerArm = TARGET_LA_X2;
				commandLowerArmTarget = true;
				commandUpperArmTarget = true;
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (Y1) {
				targetUpperArm = TARGET_UA_Y1;
				targetLowerArm = TARGET_LA_Y1;
				commandLowerArmTarget = true;
				commandUpperArmTarget = true;
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (Y2) {
				targetUpperArm = TARGET_UA_Y2;
				targetLowerArm = TARGET_LA_Y2;
				commandLowerArmTarget = true;
				commandUpperArmTarget = true;
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (H1) {
				targetUpperArm = TARGET_UA_H1;
				targetLowerArm = TARGET_LA_H1;
				commandLowerArmTarget = true;
				commandUpperArmTarget = true;
				MAIN = state.MAIN_MOVING_TARGET;
			}  else if (H2) {
				targetUpperArm = TARGET_UA_H2;
				targetLowerArm = TARGET_LA_H2;
				commandLowerArmTarget = true;
				commandUpperArmTarget = true;
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (laButtonDown) {
				commandLowerArmDown = true;
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (laButtonUp) {
				commandLowerArmUp = true;
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (uaButtonDown) {
				commandUpperArmDown = true;
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (uaButtonUp) {
				commandLowerArmUp = true;
				MAIN = state.MAIN_MOVING_MANUALLY;
			} 
		}
		
		switch (LOWER_ARM) {

		default:
			LOWER_ARM = state.LA_STOPPED;
			break;
			
		case ENCODER_ZEROING_LOWER_ARM:
			if (lowerArmLimitSwitchHome){
				Components.getInstance().lowerArm.set(0);
				Components.getInstance().lowerArm.setPosition(0);
				encoderCalibrated = true;
				LOWER_ARM = state.LA_STOPPED;
			}
			break;

		case LA_STOPPED:
			if (commandLowerArmUp = true && !lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_MOVING_UP;
				Components.getInstance().lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (commandLowerArmUp = true && !lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_MOVING_UP_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (commandLowerArmDown = true && !lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			} else if (commandLowerArmDown = true && !lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_CONFLICT:
			if (commandLowerArmUp = true && !lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_MOVING_UP;
				Components.getInstance().lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (commandLowerArmDown = true && !lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_MOVING_UP:
			if (lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (commandLowerArmStop) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			}
			break;

		case LA_MOVING_DOWN:
			if (lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (commandLowerArmStop) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			}
			break;

		case LA_MOVING_UP_TARGET:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmEnc >= lowerArmEncTarget) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (commandLowerArmStop) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (commandLowerArmDown && !lowerArmLimitSwitchHome && lowerArmEnc <= lowerArmEncTarget) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_MOVING_DOWN_TARGET:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmEnc <= lowerArmEncTarget) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (commandLowerArmStop) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (commandLowerArmUp && !lowerArmLimitSwitchTooFar && lowerArmEnc >= lowerArmEncTarget) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);

			}
			break;

		}

		switch (UPPER_ARM) {

		default:
			UPPER_ARM = state.UA_STOPPED;
			break;
			
		case ENCODER_ZEROING_UPPER_ARM:
			if (upperArmLimitSwitchHome){
				Components.getInstance().upperArm.set(0);
				Components.getInstance().upperArm.setPosition(0);
				encoderCalibrated = true;
				UPPER_ARM = state.UA_STOPPED;
			}
			break;

		case UA_STOPPED:
			if (commandLowerArmUp = true && !upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_MOVING_UP;
				Components.getInstance().upperArm.set(UA_MOVING_UP_SPEED);
			} else if (commandLowerArmUp = true && !upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_MOVING_UP_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_UP_SPEED);
			} else if (commandUpperArmDown = true && !upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (commandUpperArmDown = true && !upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			}
			break;

		case UA_CONFLICT:
			if (commandLowerArmUp = true && !upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_MOVING_UP;
				Components.getInstance().upperArm.set(UA_MOVING_UP_SPEED);
			} else if (commandUpperArmDown = true && !upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			}
			break;

		case UA_MOVING_UP:
			if (upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (commandUpperArmStop) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_DOWN:
			if (upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (commandUpperArmStop) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_UP_TARGET:
			if (upperArmEnc >= upperArmEncTarget) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (commandUpperArmStop) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (commandUpperArmDown && !upperArmLimitSwitchHome && upperArmEnc <= upperArmEncTarget) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_DOWN_TARGET:
			if (upperArmEnc <= upperArmEncTarget) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (commandUpperArmStop) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (commandLowerArmUp && !upperArmLimitSwitchTooFar && upperArmEnc >= upperArmEncTarget) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;
		}

	}
}
