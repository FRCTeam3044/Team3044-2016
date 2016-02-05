
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {

	SecondaryController secondJoy = SecondaryController.getInstance();

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, STOPPED, MOVING_MANUALLY, MOVING_TARGET
	}

	// Button 8 HOME

	final int X = 11; // This will be the angle of the POT for the LongArm
	final int Y = 12; // This will be the angle of the POT for the ShortArm
	state LOWER_ARM = state.LA_STOPPED;
	state MAIN = state.STOPPED;
	state UPPER_ARM = state.UA_STOPPED;
	public boolean LA_HOME = Components.shortArm.isFwdLimitSwitchClosed();
	public boolean LA_TOO_FAR = Components.shortArm.isRevLimitSwitchClosed();
	public boolean UA_HOME = Components.longArm.isFwdLimitSwitchClosed();
	public boolean UA_TOO_FAR = Components.longArm.isRevLimitSwitchClosed();
	

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		switch (LOWER_ARM) {
		default:
			LOWER_ARM = state.LA_STOPPED;
			break;

		case LA_STOPPED:
			Components.conflict.get();
			break;
		case LA_CONFLICT:
			break;
		case LA_MOVING_UP:
			break;
		case LA_MOVING_DOWN:
			break;
		case LA_MOVING_UP_TARGET:
			break;
		case LA_MOVING_DOWN_TARGET:
			break;

		}
		switch (UPPER_ARM){
		default:
			UPPER_ARM = state.UA_STOPPED;
			break;

		case UA_STOPPED:
			break;
		case UA_CONFLICT:
			break;
		case UA_MOVING_UP:
			break;
		case UA_MOVING_DOWN:
			break;
		case UA_MOVING_UP_TARGET:
			break;
		case UA_MOVING_DOWN_TARGET:
			break;
		}

	}
}
