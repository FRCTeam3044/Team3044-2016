
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, MAIN_STOPPED, MAIN_MOVING_MANUALLY, MAIN_MOVING_TARGET
	}

	public boolean H1;
	public boolean X1;
	public boolean X2;
	public boolean Y1;
	public boolean Y2;

	public boolean LA_BUTTON_DOWN; // MANUAL BUTTONS
	public boolean LA_BUTTON_UP;
	public boolean UA_BUTTON_DOWN;
	public boolean UA_BUTTON_UP;

	public double lowerArmEnc; // LA ENCODER
	public double upperArmEnc; // UA ENCODER

	public double lowerArmEncTarget; // TARGET ENCODER ANGLE
	public double upperArmEncTarget;

	public boolean CMD_LA_UP; // COMMANDS TO GET TO THE NEXT LA STATE
	public boolean CMD_LA_DOWN;
	public boolean CMD_LA_UP_TARGET;
	public boolean CMD_LA_DOWN_TARGET;
	public boolean CMD_LA_STOP;

	public boolean CMD_UA_UP; // COMMANDS TO GET TO THE NEXT UA STATE
	public boolean CMD_UA_DOWN;
	public boolean CMD_UA_UP_TARGET;
	public boolean CMD_UA_DOWN_TARGET;
	public boolean CMD_UA_STOP;

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

	public boolean LA_LS_HOME; // LOWER ARM LIMIT SWITCH HOME
	public boolean LA_LS_TOO_FAR; // LOWER ARM LIMIT SWITCH TOO FAR
	public boolean UA_LS_HOME; // UPPER ARM LIMIT SWITCH HOME
	public boolean UA_LS_TOO_FAR; // UPPER ARM LIMIT SWITCH TOO FAR
	public boolean LS_CONFLICT; // LIMIT SWITCH CONFLICT

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		LA_LS_HOME = Components.getInstance().upperArm.isFwdLimitSwitchClosed();
		LA_LS_TOO_FAR = Components.getInstance().upperArm.isRevLimitSwitchClosed();
		UA_LS_HOME = Components.getInstance().lowerArm.isFwdLimitSwitchClosed();
		UA_LS_TOO_FAR = Components.getInstance().lowerArm.isRevLimitSwitchClosed();
		LS_CONFLICT = Components.getInstance().conflict.get();

		lowerArmEnc = Components.getInstance().lowerArm.getEncPosition();
		upperArmEnc = Components.getInstance().upperArm.getEncPosition();

		X1 = CommonArea.dPadUp;
		X2 = CommonArea.dPadDown;
		Y1 = CommonArea.dPadLeft;
		Y2 = CommonArea.dPadRight;
		H1 = CommonArea.homeArm;

		switch (MAIN) {

		default:
			MAIN = state.MAIN_STOPPED;
			CMD_UA_STOP = true;
			CMD_LA_STOP = true;
			break;

		case MAIN_MOVING_MANUALLY:
			if (UPPER_ARM == state.UA_STOPPED && LOWER_ARM == state.LA_STOPPED) {
				MAIN = state.MAIN_STOPPED;
			} else if (LA_BUTTON_DOWN) {
				LOWER_ARM = state.LA_MOVING_DOWN;
			} else if (LA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (UA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (!UA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			}
			break;

		case MAIN_MOVING_TARGET:
			if (UPPER_ARM == state.UA_STOPPED && LOWER_ARM == state.LA_STOPPED) {
				MAIN = state.MAIN_STOPPED;
			} else if (LA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (LA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (UA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (UA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			}
			break;

		case MAIN_STOPPED:
			if (X1) {
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (X2) {
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (Y1) {
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (Y2) {
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (H1) {
				MAIN = state.MAIN_MOVING_TARGET;
			} else if (LA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (LA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (UA_BUTTON_DOWN) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			} else if (UA_BUTTON_UP) {
				MAIN = state.MAIN_MOVING_MANUALLY;
			}
		}
		
		switch (LOWER_ARM) {

		default:
			LOWER_ARM = state.LA_STOPPED;
			break;

		case LA_STOPPED:
			if (CMD_LA_UP = true && !LA_LS_TOO_FAR) {
				LOWER_ARM = state.LA_MOVING_UP;
				Components.getInstance().lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (CMD_LA_UP_TARGET = true && !LA_LS_TOO_FAR) {
				LOWER_ARM = state.LA_MOVING_UP_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (CMD_LA_DOWN_TARGET = true && !LA_LS_HOME) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			} else if (CMD_LA_DOWN = true && !LA_LS_HOME) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_CONFLICT:
			if (CMD_LA_UP = true && !LA_LS_TOO_FAR) {
				LOWER_ARM = state.LA_MOVING_UP;
				Components.getInstance().lowerArm.set(LA_MOVING_UP_SPEED);
			} else if (CMD_LA_DOWN = true && !LA_LS_HOME) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_MOVING_UP:
			if (LA_LS_TOO_FAR) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (LS_CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (CMD_LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			}
			break;

		case LA_MOVING_DOWN:
			if (LA_LS_HOME) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (LS_CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (CMD_LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			}
			break;

		case LA_MOVING_UP_TARGET:
			if (LS_CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (LA_LS_TOO_FAR) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmEnc >= lowerArmEncTarget) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (CMD_LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (CMD_LA_DOWN_TARGET && !LA_LS_HOME && lowerArmEnc <= lowerArmEncTarget) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;

		case LA_MOVING_DOWN_TARGET:
			if (LS_CONFLICT) {
				LOWER_ARM = state.LA_CONFLICT;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (LA_LS_HOME) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (lowerArmEnc <= lowerArmEncTarget) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (CMD_LA_STOP) {
				LOWER_ARM = state.LA_STOPPED;
				Components.getInstance().lowerArm.set(LA_SPEED_STOP);
			} else if (CMD_LA_UP_TARGET && !LA_LS_TOO_FAR && lowerArmEnc >= lowerArmEncTarget) {
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.getInstance().lowerArm.set(LA_MOVING_DOWN_SPEED);

			}
			break;

		}

		switch (UPPER_ARM) {

		default:
			UPPER_ARM = state.UA_STOPPED;
			break;

		case UA_STOPPED:
			if (CMD_UA_UP = true && !UA_LS_TOO_FAR) {
				UPPER_ARM = state.UA_MOVING_UP;
				Components.getInstance().upperArm.set(UA_MOVING_UP_SPEED);
			} else if (CMD_UA_UP_TARGET = true && !UA_LS_TOO_FAR) {
				UPPER_ARM = state.UA_MOVING_UP_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_UP_SPEED);
			} else if (CMD_UA_DOWN_TARGET = true && !UA_LS_HOME) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (CMD_UA_DOWN = true && !UA_LS_HOME) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			}
			break;

		case UA_CONFLICT:
			if (CMD_UA_UP = true && !UA_LS_TOO_FAR) {
				UPPER_ARM = state.UA_MOVING_UP;
				Components.getInstance().upperArm.set(UA_MOVING_UP_SPEED);
			} else if (CMD_UA_DOWN = true && !UA_LS_HOME) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			}
			break;

		case UA_MOVING_UP:
			if (UA_LS_TOO_FAR) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (LS_CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (CMD_UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_DOWN:
			if (UA_LS_HOME) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (LS_CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (CMD_UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_UP_TARGET:
			if (upperArmEnc >= upperArmEncTarget) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (UA_LS_TOO_FAR) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (CMD_UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (CMD_UA_DOWN_TARGET && !UA_LS_HOME && upperArmEnc <= upperArmEncTarget) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (LS_CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;

		case UA_MOVING_DOWN_TARGET:
			if (upperArmEnc <= upperArmEncTarget) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (UA_LS_HOME) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (CMD_UA_STOP) {
				UPPER_ARM = state.UA_STOPPED;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			} else if (CMD_UA_UP_TARGET && !UA_LS_TOO_FAR && upperArmEnc >= upperArmEncTarget) {
				UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
				Components.getInstance().upperArm.set(UA_MOVING_DOWN_SPEED);
			} else if (LS_CONFLICT) {
				UPPER_ARM = state.UA_CONFLICT;
				Components.getInstance().upperArm.set(UA_SPEED_STOP);
			}
			break;
		}

	}
}