
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;

public class Defense {

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED,
		LA_CALIBRATING, UA_CALIBRATING,UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, 
		UA_CONFLICT, UA_STOPPED, CALIBRATED, UNCALIBRATED, OPEN_UPPER, CLOSE_LOWER, CLOSE_UPPER
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

	public boolean upperArmCalibrated; // TEST IF WE ARE CALIBRATED
	public boolean lowerArmCalibrated;
	public boolean calibrated = false;
	
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

	public double LOWER_ARM_ENCODER_TOLERANCE = 0.3;
	public double UPPER_ARM_ENCODER_TOLERANCE = 0.3;

	public double lowerArmMovingUpSpeed; // MOTOR SPEEDS
	public double lowerArmMovingDownSpeed;
	public double lowerArmStopSpeed;
	public double upperArmMovingUpSpeed;
	public double upperArmMovingDownSpeed;
	public double upperArmStopSpeed;
	public double calibratingSpeed;
	public double calibrationStopSpeed;
	public double calibrationMovingUpSpeed;
	public double calibrationMovingDownSpeed;

	public CANTalon lowerArmMotor; // MOTORS
	public CANTalon upperArmMotor;

	state LOWER_ARM = state.LA_STOPPED; // DEFAULT STARTING STATES AND SWITCH NAMES
	state UPPER_ARM = state.UA_STOPPED;
	state CALIBRATION = state.UNCALIBRATED;

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

		X1 = CommonArea.X1;
		X2 = CommonArea.X2;
		Y1 = CommonArea.Y1;
		Y2 = CommonArea.Y2;
		H1 = CommonArea.H1;
		H2 = CommonArea.H2;
		switch(CALIBRATION){
		
		case CALIBRATED:
			if(LOWER_ARM == state.LA_STOPPED && UPPER_ARM == state.UA_STOPPED && !limitSwitchConflict && !stopTargeting){
				CALIBRATION = state.UNCALIBRATED;
				lowerArmMotor.set(calibrationStopSpeed);
				upperArmMotor.set(calibrationStopSpeed);
				calibrated = false;
			} else if(calibrationButton && lowerArmLimitSwitchHome && upperArmLimitSwitchHome){
				CALIBRATION = state.CALIBRATED;
				lowerArmMotor.set(calibrationStopSpeed);
				upperArmMotor.set(calibrationStopSpeed);
				calibrated = true;
			}
		
		
		
		}
		switch (LOWER_ARM) {

		default:
			LOWER_ARM = state.LA_STOPPED;
			lowerArmMotor.set(lowerArmStopSpeed);
			break;

		case LA_STOPPED:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmButtonUp && !lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_MOVING_UP;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmButtonDown && !lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				lowerArmMotor.set(lowerArmMovingDownSpeed);
			} else if (lowerArmCalibrated) {
				if (X1) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_X1) {
						if (!lowerArmLimitSwitchTooFar) {
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}

				if (X2) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_X2) {
						if (!lowerArmLimitSwitchTooFar) {
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}
				if (Y1) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_Y1) {
						if (!lowerArmLimitSwitchTooFar) {
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}
				if (Y2) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_Y2) {
						if (!lowerArmLimitSwitchTooFar) {
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}
				if (H1) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_H1) {
						if (!lowerArmLimitSwitchTooFar) {
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}
				if (H2) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_H2) {
						if (!lowerArmLimitSwitchTooFar) {
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
							lowerArmMotor.set(lowerArmMovingUpSpeed);
						}
					}
				}
			} else if (lowerArmCalibrated) {
				if (X1) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_X1) {
						if (!lowerArmLimitSwitchHome) {
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
							lowerArmMotor.set(lowerArmMovingDownSpeed);
						}
					}
				}

				if (X2) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_X2) {
						if (!lowerArmLimitSwitchHome) {
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
							lowerArmMotor.set(lowerArmMovingDownSpeed);
						}
					}
				}
				if (Y1) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_Y1) {
						if (!lowerArmLimitSwitchHome) {
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
							lowerArmMotor.set(lowerArmMovingDownSpeed);
						}
					}
				}
				if (Y2) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_Y2) {
						if (!lowerArmLimitSwitchHome) {
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
							lowerArmMotor.set(lowerArmMovingDownSpeed);
						}
					}
				}
				if (H1) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_H1) {
						if (!lowerArmLimitSwitchHome) {
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
							lowerArmMotor.set(lowerArmMovingDownSpeed);
						}
					}
				}
				if (H2) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_H2) {
						if (!lowerArmLimitSwitchHome) {
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
							lowerArmMotor.set(lowerArmMovingDownSpeed);
						}
					}
				}
			}
			break;

		case LA_MOVING_UP:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (!lowerArmButtonUp) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			}
			break;

		case LA_CONFLICT:
			if (!lowerArmLimitSwitchTooFar && lowerArmButtonUp) {
				LOWER_ARM = state.LA_MOVING_UP;
				lowerArmMotor.set(lowerArmMovingUpSpeed);
			} else if (!lowerArmLimitSwitchHome && lowerArmButtonDown) {
				LOWER_ARM = state.LA_MOVING_DOWN;
				lowerArmMotor.set(lowerArmMovingDownSpeed);
			}
			break;

		case LA_MOVING_DOWN:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (!lowerArmButtonDown) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			}
			break;

		case LA_CALIBRATING:
			if(limitSwitchConflict){
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if(!calibrationButton && stopTargeting){
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			}
			break;

		case LA_MOVING_DOWN_TARGET:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmLimitSwitchHome) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (stopTargeting) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmEncoder <= lowerArmEncoderTarget) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			}
			break;

		case LA_MOVING_UP_TARGET:
			if (limitSwitchConflict) {
				LOWER_ARM = state.LA_CONFLICT;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmLimitSwitchTooFar) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (stopTargeting) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			} else if (lowerArmEncoder >= lowerArmEncoderTarget) {
				LOWER_ARM = state.LA_STOPPED;
				lowerArmMotor.set(lowerArmStopSpeed);
			}
			break;

		}

		switch (UPPER_ARM) {

		default:
			UPPER_ARM = state.UA_STOPPED;
			upperArmMotor.set(upperArmStopSpeed);
			break;

		case UA_STOPPED:
			if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmButtonUp && !upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_MOVING_UP;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmButtonDown && !upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				upperArmMotor.set(upperArmMovingDownSpeed);
			} else if (upperArmCalibrated) {
				if (X1) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_X1) {
						if (!upperArmLimitSwitchTooFar) {
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
							upperArmMotor.set(upperArmMovingUpSpeed);
						}
					}
				}

				if (X2) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_X2) {
						if (!upperArmLimitSwitchTooFar) {
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
							upperArmMotor.set(upperArmMovingUpSpeed);
						}
					}
				}
				if (Y1) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_Y1) {
						if (!upperArmLimitSwitchTooFar) {
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
							upperArmMotor.set(upperArmMovingUpSpeed);
						}
					}
				}
				if (Y2) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_Y2) {
						if (!upperArmLimitSwitchTooFar) {
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
							upperArmMotor.set(upperArmMovingUpSpeed);
						}
					}
				}
				if (H1) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_H1) {
						if (!upperArmLimitSwitchTooFar) {
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
							upperArmMotor.set(upperArmMovingUpSpeed);
						}
					}
				}
				if (H2) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_H2) {
						if (!upperArmLimitSwitchTooFar) {
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
							upperArmMotor.set(upperArmMovingUpSpeed);
						}
					}
				}
			} else if (upperArmCalibrated) {
				if (X1) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_X1) {
						if (!upperArmLimitSwitchHome) {
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
							upperArmMotor.set(upperArmMovingDownSpeed);
						}
					}
				}

				if (X2) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_X2) {
						if (!upperArmLimitSwitchHome) {
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
							upperArmMotor.set(upperArmMovingDownSpeed);
						}
					}
				}
				if (Y1) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_Y1) {
						if (!upperArmLimitSwitchHome) {
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
							upperArmMotor.set(upperArmMovingDownSpeed);
						}
					}
				}
				if (Y2) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_Y2) {
						if (!upperArmLimitSwitchHome) {
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
							upperArmMotor.set(upperArmMovingDownSpeed);
						}
					}
				}
				if (H1) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_H1) {
						if (!upperArmLimitSwitchHome) {
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
							upperArmMotor.set(upperArmMovingDownSpeed);
						}
					}
				}
				if (H2) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_H2) {
						if (!upperArmLimitSwitchHome) {
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
							upperArmMotor.set(upperArmMovingDownSpeed);
						}
					}
				}
			}
			break;

		case UA_MOVING_UP:
			if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (!upperArmButtonUp) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			}
			break;

		case UA_CONFLICT:
			if (!upperArmLimitSwitchTooFar && upperArmButtonUp) {
				UPPER_ARM = state.UA_MOVING_UP;
				upperArmMotor.set(upperArmMovingUpSpeed);
			} else if (!upperArmLimitSwitchHome && upperArmButtonDown) {
				UPPER_ARM = state.UA_MOVING_DOWN;
				upperArmMotor.set(upperArmMovingDownSpeed);
			}
			break;

		case UA_MOVING_DOWN:
			if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (!upperArmButtonDown) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			}
			break;

		case UA_CALIBRATING:
			if(limitSwitchConflict){
				UPPER_ARM = state.UA_CONFLICT;
				upperArmMotor.set(upperArmStopSpeed);
			} else if(!calibrationButton && stopTargeting){
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			}
			break;

		case UA_MOVING_DOWN_TARGET:
			if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmLimitSwitchHome) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (stopTargeting) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmEncoder <= upperArmEncoderTarget) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			}
			break;

		case UA_MOVING_UP_TARGET:
			if (limitSwitchConflict) {
				UPPER_ARM = state.UA_CONFLICT;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmLimitSwitchTooFar) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (stopTargeting) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			} else if (upperArmEncoder >= upperArmEncoderTarget) {
				UPPER_ARM = state.UA_STOPPED;
				upperArmMotor.set(upperArmStopSpeed);
			}
			break;

		}
	}
}
