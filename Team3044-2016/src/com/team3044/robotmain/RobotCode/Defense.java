
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {

	SecondaryController secondJoy = SecondaryController.getInstance();

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, MAIN_STOPPED, MAIN_MOVING_MANUALLY, MAIN_MOVING_TARGET
	}

	public boolean H1;
	public boolean X1;
	public boolean X2;
	public boolean Y1;
	public boolean Y2;
	public boolean LA_BUTTON_DOWN;
	public boolean LA_BUTTON_UP;
	public boolean UA_BUTTON_DOWN;
	public boolean UA_BUTTON_UP;

	public double lowerArmEnc;  // LA ENCODER
	public double upperArmEnc; // UA ENCODER
																		

	public double lowerArmEncTarget; // TARGET ENCODER ANGLE
	public double upperArmEncTarget;

	public boolean LA_UP; // COMMANDS TO GET TO THE NEXT LA STATE
	public boolean LA_DOWN;
	public boolean LA_UP_TARGET;
	public boolean LA_DOWN_TARGET;
	public boolean LA_STOP;

	public boolean UA_UP; // COMMANDS TO GET TO THE NEXT UA STATE
	public boolean UA_DOWN;
	public boolean UA_UP_TARGET;
	public boolean UA_DOWN_TARGET;
	public boolean UA_STOP;

	public double LA_MOVING; // MOTORS
	public double LA_MOVING_UP_SPEED;
	public double LA_MOVING_DOWN_SPEED;
	public double LA_SPEED_STOP;
	public double UA_MOVING;
	public double UA_MOVING_UP_SPEED;
	public double UA_MOVING_DOWN_SPEED;
	public double UA_SPEED_STOP;

	state LOWER_ARM = state.LA_STOPPED; // DEFAULT STARTING STATES AND SWITCH
										// STATEMENT NAMES
	state MAIN = state.MAIN_STOPPED;
	state UPPER_ARM = state.UA_STOPPED;

	public boolean LA_HOME; // LOWER ARM LIMIT SWITCH HOME
	public boolean LA_TOO_FAR; //LOWER ARM LIMIT SWITCH TOO FAR
	public boolean UA_HOME; //UPPER ARM LIMIT SWITCH HOME
	public boolean UA_TOO_FAR; //UPPER ARM LIMIT SWITCH TOO FAR
	public boolean CONFLICT; //LIMIT SWITCH CONFLICT

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		LA_HOME = Components.upperArm.isFwdLimitSwitchClosed();
		LA_TOO_FAR = Components.upperArm.isRevLimitSwitchClosed();
		UA_HOME = Components.lowerArm.isFwdLimitSwitchClosed();
		UA_TOO_FAR = Components.lowerArm.isRevLimitSwitchClosed();
		CONFLICT = Components.conflict.get();

		
		lowerArmEnc = Components.lowerArm.getEncPosition();
		upperArmEnc = Components.upperArm.getEncPosition();
		
		X1 = secondJoy.getDPadUp();
		X2 = secondJoy.getDPadDown();
		Y1 = secondJoy.getDPadLeft();
		Y2 = secondJoy.getDPadRight();
		H1 = secondJoy.getRawButton(secondJoy.BUTTON_START);
				
				
		switch (MAIN) {

		default:
			MAIN = state.MAIN_STOPPED;
			UA_STOP = true;
			LA_STOP = true;
			break;

		case MAIN_MOVING_MANUALLY:
			if (UPPER_ARM == state.UA_STOPPED && LOWER_ARM == state.LA_STOPPED) {
				MAIN = state.MAIN_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (!LA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (!LA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (!UA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (!UA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			}
			break;

		case MAIN_MOVING_TARGET:
			if (UPPER_ARM == state.UA_STOPPED && LOWER_ARM == state.LA_STOPPED) {
				MAIN = state.MAIN_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (LA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (UA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (UA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			}

			break;
		case MAIN_STOPPED:
			if (secondJoy.getRawButton(1)) {
				MAIN = state.MAIN_MOVING_TARGET;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (secondJoy.getRawButton(2)) {
				MAIN = state.MAIN_MOVING_TARGET;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (secondJoy.getRawButton(3)) {
				MAIN = state.MAIN_MOVING_TARGET;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (secondJoy.getRawButton(4)) {
				MAIN = state.MAIN_MOVING_TARGET;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (secondJoy.getRawButton(8)) {
				MAIN = state.MAIN_MOVING_TARGET;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (LA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (LA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (UA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			} else if (UA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
				Components.upperArm.set(UA_MOVING);
				Components.lowerArm.set(LA_MOVING);
			}
		}
		switch (LOWER_ARM) {

		default:
			LOWER_ARM = state.LA_STOPPED;
			break;

		case LA_STOPPED:
			if (LA_UP = true && !LA_TOO_FAR) {
				LOWER_ARM = state.LA_MOVING_UP;
				Components.lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (LA_UP_TARGET = true && !LA_TOO_FAR) {
				LOWER_ARM = state.LA_MOVING_UP_TARGET;
				Components.lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (LA_DOWN_TARGET = true && !LA_HOME) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.lowerArm.set(LA_MOVING_DOWN_SPEED);
			} else if (LA_DOWN = true && !LA_HOME) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_CONFLICT: // show the commands better
			if (LA_UP = true && !LA_TOO_FAR) {
				LOWER_ARM = state.LA_MOVING_UP;
				Components.lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (LA_DOWN = true && !LA_HOME) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_MOVING_UP:
			if (LA_TOO_FAR) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			}
			break;

		case LA_MOVING_DOWN:
			if (LA_HOME) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			}
			break;

		case LA_MOVING_UP_TARGET:
			if (CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_TOO_FAR) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmEnc >= lowerArmEncTarget) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_DOWN_TARGET && !LA_HOME && lowerArmEnc <= lowerArmEncTarget) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_MOVING_DOWN_TARGET:
			if (CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_HOME) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmEnc <= lowerArmEncTarget) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.lowerArm.set(LA_SPEED_STOP);
			} else if (LA_UP_TARGET && !LA_TOO_FAR && lowerArmEnc >= lowerArmEncTarget) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.lowerArm.set(LA_MOVING_DOWN_SPEED);

			}
			break;

		}

		switch (UPPER_ARM) {

		default:
			UPPER_ARM = state.UA_STOPPED;
			break;

		case UA_STOPPED:
			if (UA_UP = true && !UA_TOO_FAR) {
				UPPER_ARM = state.UA_MOVING_UP;
				Components.upperArm.set(UA_MOVING_UP_SPEED);
			} else if (UA_UP_TARGET = true && !UA_TOO_FAR) {
				UPPER_ARM = state.UA_MOVING_UP_TARGET;
				Components.upperArm.set(UA_MOVING_UP_SPEED);
			} else if (UA_DOWN_TARGET = true && !UA_HOME) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (UA_DOWN = true && !UA_HOME) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				Components.upperArm.set(UA_MOVING_DOWN_SPEED);
			}
			break;

		case UA_CONFLICT:
			if (UA_UP = true && !UA_TOO_FAR) {
				UPPER_ARM = state.UA_MOVING_UP;
				Components.upperArm.set(UA_MOVING_UP_SPEED);
			} else if (UA_DOWN = true && !UA_HOME) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				Components.upperArm.set(UA_MOVING_DOWN_SPEED);
			}
			break;

		case UA_MOVING_UP:
			if (UA_TOO_FAR) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} 
			break;

		case UA_MOVING_DOWN:
			if (UA_HOME) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} 
			break;

		case UA_MOVING_UP_TARGET:
			if (upperArmEnc >= upperArmEncTarget) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_TOO_FAR) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_DOWN_TARGET && !UA_HOME && upperArmEnc <= upperArmEncTarget) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_DOWN_TARGET:
			if (upperArmEnc <= upperArmEncTarget) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_HOME) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.upperArm.set(UA_SPEED_STOP);
			} else if (UA_UP_TARGET && !UA_TOO_FAR && upperArmEnc >= upperArmEncTarget) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.upperArm.set(UA_SPEED_STOP);
			}
			break;
		}

	}
}
