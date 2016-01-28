
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {
	Components components = new Components();
	SecondaryController secondJoy = SecondaryController.getInstance();

	final int MOVING = 1;
	final int HOME = 2;
	final int SALLY_PORT1 = 3;
	final int SALLY_PORT2 = 4;
	final int DRAW_BRIDGE1 = 5;
	final int DRAW_BRIDGE2 = 6;
	final int OTHER = 7;
	final int MOVING_AWAY_FROM_HOME = 8;
	final int MOVING_TOWARD_HOME = 9;
	final int NOT_HOME = 10;

	final int X = 11; // This will be the angle of the POT for the LongArm
	final int Y = 12; // This will be the angle of the POT for the ShortArm
	int DEFENSE_STATE = 13;

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		switch (DEFENSE_STATE) {
		case HOME:
			if (secondJoy.getRawButton(1)) {
				if (!components.longArm.isFwdLimitSwitchClosed()) {
					components.shortArm.set(1);
					components.longArm.set(1);
					DEFENSE_STATE = MOVING_AWAY_FROM_HOME;
				}
			}

			else if (secondJoy.getRawButton(3)) {
				if (!components.longArm.isFwdLimitSwitchClosed()) {
					components.shortArm.set(1);
					components.longArm.set(1);
					DEFENSE_STATE = MOVING_AWAY_FROM_HOME;
				}
			}
			break;
		case MOVING_AWAY_FROM_HOME:
			if (components.longArm.getAnalogInPosition() == X && components.shortArm.getAnalogInPosition() == Y) {
				components.shortArm.set(1);
				components.longArm.set(1);
				DEFENSE_STATE = MOVING;
			} else if (!secondJoy.getRawButton(1)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = NOT_HOME;
			} else if (!secondJoy.getRawButton(3)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = NOT_HOME;
				break;
			}
		case NOT_HOME:
			if (secondJoy.getRawButton(1)) {
				components.shortArm.set(1);
				components.longArm.set(1);
				DEFENSE_STATE = MOVING_AWAY_FROM_HOME;
			} else if (!secondJoy.getRawButton(3)) {
				components.shortArm.set(1);
				components.longArm.set(1);
				DEFENSE_STATE = MOVING_AWAY_FROM_HOME;
			} else if (!secondJoy.getRawButton(8)) {
				components.shortArm.set(1);
				components.longArm.set(1);
				DEFENSE_STATE = MOVING_TOWARD_HOME;
				break;
			}
		case MOVING_TOWARD_HOME:
			if (components.longArm.getAnalogInPosition() == X && components.shortArm.getAnalogInPosition() == Y) {
				components.shortArm.set(0);
				components.longArm.set(0);
			} else if (!secondJoy.getRawButton(1)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = NOT_HOME;
			} else if (!secondJoy.getRawButton(3)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = NOT_HOME;
				break;
			}
		case MOVING:
			if (components.longArm.getAnalogInPosition() == X && components.shortArm.getAnalogInPosition() == Y) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = SALLY_PORT1;
			} else if (components.longArm.getAnalogInPosition() == X
					&& components.shortArm.getAnalogInPosition() == Y) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = DRAW_BRIDGE1;
			} else if (components.longArm.getAnalogInPosition() == X
					&& components.shortArm.getAnalogInPosition() == Y) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = SALLY_PORT2;
			} else if (components.longArm.getAnalogInPosition() == X
					&& components.shortArm.getAnalogInPosition() == Y) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = DRAW_BRIDGE2;
			} else if (secondJoy.getRawButton(8)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = HOME;
			} else if (secondJoy.getRawButton(8)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = HOME;
			} else if (!secondJoy.getRawButton(1)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = OTHER;
			} else if (!secondJoy.getRawButton(3)) {
				components.shortArm.set(0);
				components.longArm.set(0);
				DEFENSE_STATE = OTHER;
				break;
			}
		case SALLY_PORT1:
			if (secondJoy.getRawButton(8)) {
				components.shortArm.set(1);
				components.longArm.set(1);
				DEFENSE_STATE = MOVING;
			} else if (secondJoy.getRawButton(1)) {
				components.shortArm.set(1);
				components.longArm.set(1);
				DEFENSE_STATE = MOVING;
			} //this is a test
		}
	}
}
