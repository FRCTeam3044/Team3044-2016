
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class Defense {

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, LA_CALIBRATING, UA_CALIBRATING, UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, CALIBRATED, UNCALIBRATED, UNCALIBRATED_BLOCKED, OPEN_UPPER, CLOSE_LOWER, CLOSE_UPPER
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
	public final double UPPER_ARM_RELATIVE = 0.1;

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

	public final double LOWER_ARM_ENCODER_TOLERANCE = 0.03;
	public final double UPPER_ARM_ENCODER_TOLERANCE = 0.03;

	public final double lowerArmMovingUpSpeed = 0.5; // MOTOR SPEEDS
	public final double lowerArmMovingDownSpeed = 0.5;
	public final double lowerArmStopSpeed = 0.5;
	public final double upperArmMovingUpSpeed = 0.5;
	public final double upperArmMovingDownSpeed = 0.5;
	public final double upperArmStopSpeed = 0.5;
	public final double calibratingSpeed = 0.5;
	public final double calibrationStopSpeed = 0.5;
	public final double calibrationMovingUpSpeed = 0.5;
	public final double calibrationMovingDownSpeed = 0.5;

	public CANTalon lowerArmMotor; // MOTORS
	public CANTalon upperArmMotor;

	public DigitalInput conflictDigitalIO; // CONFLICT LIMIT SWITCH

	state LOWER_ARM = state.LA_STOPPED; // DEFAULT STARTING STATES AND SWITCH
										// NAMES
	state UPPER_ARM = state.UA_STOPPED;
	state CALIBRATION = state.UNCALIBRATED;

	public boolean lowerArmLimitSwitchHome; // LIMIT SWITCHES
	public boolean lowerArmLimitSwitchTooFar;
	public boolean upperArmLimitSwitchHome;
	public boolean upperArmLimitSwitchTooFar;
	public boolean limitSwitchConflict;

	public void defenseInit() {

		lowerArmMotor = Components.getInstance().lowerArm;
		upperArmMotor = Components.getInstance().upperArm;

		conflictDigitalIO = Components.getInstance().conflict;

		LOWER_ARM = state.LA_STOPPED;
		UPPER_ARM = state.UA_STOPPED;
		CALIBRATION = state.UNCALIBRATED;

		upperArmCalibrated = false;
		lowerArmCalibrated = false;
		calibrated = false;

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		lowerArmLimitSwitchHome = upperArmMotor.isFwdLimitSwitchClosed();
		lowerArmLimitSwitchTooFar = upperArmMotor.isRevLimitSwitchClosed();
		upperArmLimitSwitchHome = lowerArmMotor.isFwdLimitSwitchClosed();
		upperArmLimitSwitchTooFar = lowerArmMotor.isRevLimitSwitchClosed();

		limitSwitchConflict = conflictDigitalIO.get();

		lowerArmEncoder = lowerArmMotor.getEncPosition();
		upperArmEncoder = upperArmMotor.getEncPosition();

		X1 = CommonArea.X1;
		X2 = CommonArea.X2;
		Y1 = CommonArea.Y1;
		Y2 = CommonArea.Y2;
		H1 = CommonArea.H1;
		H2 = CommonArea.H2;

		lowerArmButtonDown = CommonArea.LA_Down;
		lowerArmButtonUp = CommonArea.LA_Up;
		upperArmButtonDown = CommonArea.UA_Down;
		upperArmButtonUp = CommonArea.UA_Up;
		calibrationButton = CommonArea.CAL;
		stopTargeting = CommonArea.STOP_TARGETING;

		switch (CALIBRATION) {

		default:
			CALIBRATION = state.UNCALIBRATED;
			lowerArmMotor.set(calibrationStopSpeed);
			upperArmMotor.set(calibrationStopSpeed);
			calibrated = false;
			break;

		case CALIBRATED:
			if (calibrationButton) {

				if (lowerArmLimitSwitchHome && upperArmLimitSwitchHome) {
					calibrated = true;
				} else if (LOWER_ARM == state.LA_STOPPED && UPPER_ARM == state.UA_STOPPED && !limitSwitchConflict
						&& !stopTargeting) {
					calibrated = false;
					CALIBRATION = state.UNCALIBRATED;
				}
			}
			break;

		case UNCALIBRATED:
			if (calibrationButton) {
				if (lowerArmLimitSwitchHome && upperArmLimitSwitchHome) {
					calibrated = true;
					CALIBRATION = state.CALIBRATED;
				} else if (!stopTargeting && !limitSwitchConflict) {
					if (!lowerArmLimitSwitchHome && !upperArmLimitSwitchHome && LOWER_ARM == state.LA_STOPPED
							&& UPPER_ARM == state.UA_STOPPED) {
						upperArmEncoderTarget = upperArmEncoder + UPPER_ARM_RELATIVE;
						upperArmMotor.set(calibrationMovingUpSpeed);
						CALIBRATION = state.OPEN_UPPER;
					} else if (!lowerArmLimitSwitchHome && upperArmLimitSwitchHome) {
						lowerArmMotor.set(calibrationMovingDownSpeed);
						CALIBRATION = state.CLOSE_LOWER;
					} else if (lowerArmLimitSwitchHome && !upperArmLimitSwitchHome) {
						upperArmMotor.set(calibrationMovingDownSpeed);
						CALIBRATION = state.CLOSE_UPPER;
					}
				}
			}
			break;

		case OPEN_UPPER:
			if (lowerArmLimitSwitchTooFar || upperArmEncoder > upperArmEncoderTarget) {
				upperArmMotor.set(calibrationStopSpeed);
				lowerArmMotor.set(calibrationMovingDownSpeed);
				CALIBRATION = state.CLOSE_LOWER;
			} else if (limitSwitchConflict || stopTargeting) {
				upperArmMotor.set(calibrationStopSpeed);
				CALIBRATION = state.UNCALIBRATED_BLOCKED;
			} else if (!calibrationButton) {
				upperArmMotor.set(calibrationStopSpeed);
				CALIBRATION = state.UNCALIBRATED;
			}
			break;

		case CLOSE_LOWER:
			if (limitSwitchConflict || stopTargeting) {
				lowerArmMotor.set(calibrationStopSpeed);
				CALIBRATION = state.UNCALIBRATED_BLOCKED;
			} else if (lowerArmLimitSwitchHome) {
				lowerArmMotor.set(calibrationStopSpeed);
				if (upperArmLimitSwitchHome) {
					calibrated = true;
					CALIBRATION = state.CALIBRATED;
				} else {
					upperArmMotor.set(calibrationMovingDownSpeed);
					CALIBRATION = state.CLOSE_UPPER;
				}
			} else if (!calibrationButton) {
				lowerArmMotor.set(calibrationStopSpeed);
				CALIBRATION = state.UNCALIBRATED_BLOCKED;
			}
			break;

		case CLOSE_UPPER:
			if (upperArmLimitSwitchHome) {
				upperArmMotor.set(calibrationStopSpeed);
				calibrated = true;
				CALIBRATION = state.CALIBRATED;
			} else if (!calibrationButton) {
				upperArmMotor.set(calibrationStopSpeed);
				CALIBRATION = state.UNCALIBRATED;
			} else if (limitSwitchConflict || stopTargeting) {
				upperArmMotor.set(calibrationStopSpeed);
				CALIBRATION = state.UNCALIBRATED_BLOCKED;
			}
			break;

		case UNCALIBRATED_BLOCKED:
			if (!calibrationButton) {
				CALIBRATION = state.UNCALIBRATED;
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
			} else if (lowerArmButtonUp && !lowerArmLimitSwitchTooFar) {
				lowerArmMotor.set(lowerArmMovingUpSpeed);
				LOWER_ARM = state.LA_MOVING_UP;
			} else if (lowerArmButtonDown && !lowerArmLimitSwitchHome) {
				lowerArmMotor.set(lowerArmMovingDownSpeed);
				LOWER_ARM = state.LA_MOVING_DOWN;
			} else if (calibrationButton && !limitSwitchConflict) {
				LOWER_ARM = state.LA_CALIBRATING;
			} else if (lowerArmCalibrated) {
				if (X1) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_X1) {
						if (!lowerArmLimitSwitchTooFar) {
							lowerArmMotor.set(lowerArmMovingUpSpeed);
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
						}
					}
				}

				if (X2) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_X2) {
						if (!lowerArmLimitSwitchTooFar) {
							lowerArmMotor.set(lowerArmMovingUpSpeed);
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
						}
					}
				}
				if (Y1) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_Y1) {
						if (!lowerArmLimitSwitchTooFar) {
							lowerArmMotor.set(lowerArmMovingUpSpeed);
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
						}
					}
				}
				if (Y2) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_Y2) {
						if (!lowerArmLimitSwitchTooFar) {
							lowerArmMotor.set(lowerArmMovingUpSpeed);
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
						}
					}
				}
				if (H1) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_H1) {
						if (!lowerArmLimitSwitchTooFar) {
							lowerArmMotor.set(lowerArmMovingUpSpeed);
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
						}
					}
				}
				if (H2) {
					if (lowerArmEncoder + LOWER_ARM_ENCODER_TOLERANCE < TARGET_LA_H2) {
						if (!lowerArmLimitSwitchTooFar) {
							lowerArmMotor.set(lowerArmMovingUpSpeed);
							LOWER_ARM = state.LA_MOVING_UP_TARGET;
						}
					}
				}
			} else if (lowerArmCalibrated) {
				if (X1) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_X1) {
						if (!lowerArmLimitSwitchHome) {
							lowerArmMotor.set(lowerArmMovingDownSpeed);
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
						}
					}
				}

				if (X2) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_X2) {
						if (!lowerArmLimitSwitchHome) {
							lowerArmMotor.set(lowerArmMovingDownSpeed);
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (Y1) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_Y1) {
						if (!lowerArmLimitSwitchHome) {
							lowerArmMotor.set(lowerArmMovingDownSpeed);
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (Y2) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_Y2) {
						if (!lowerArmLimitSwitchHome) {
							lowerArmMotor.set(lowerArmMovingDownSpeed);
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (H1) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_H1) {
						if (!lowerArmLimitSwitchHome) {
							lowerArmMotor.set(lowerArmMovingDownSpeed);
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (H2) {
					if (lowerArmEncoder - LOWER_ARM_ENCODER_TOLERANCE > TARGET_LA_H2) {
						if (!lowerArmLimitSwitchHome) {
							lowerArmMotor.set(lowerArmMovingDownSpeed);
							LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
						}
					}
				}
			}
			break;

		case LA_MOVING_UP:
			if (limitSwitchConflict) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_CONFLICT;
			} else if (lowerArmLimitSwitchTooFar) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			} else if (!lowerArmButtonUp) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			}
			break;

		case LA_CONFLICT:
			if (!lowerArmLimitSwitchTooFar && lowerArmButtonUp) {
				lowerArmMotor.set(lowerArmMovingUpSpeed);
				LOWER_ARM = state.LA_MOVING_UP;
			} else if (!lowerArmLimitSwitchHome && lowerArmButtonDown) {
				lowerArmMotor.set(lowerArmMovingDownSpeed);
				LOWER_ARM = state.LA_MOVING_DOWN;
			}
			break;

		case LA_MOVING_DOWN:
			if (limitSwitchConflict) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_CONFLICT;
			} else if (lowerArmLimitSwitchHome) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			} else if (!lowerArmButtonDown) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			}
			break;

		case LA_CALIBRATING:
			if (limitSwitchConflict) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_CONFLICT;
			} else if (!calibrationButton || stopTargeting) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			}
			break;

		case LA_MOVING_DOWN_TARGET:
			if (limitSwitchConflict) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_CONFLICT;
			} else if (lowerArmLimitSwitchHome) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			} else if (stopTargeting) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			} else if (lowerArmEncoder <= lowerArmEncoderTarget) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			}
			break;

		case LA_MOVING_UP_TARGET:
			if (limitSwitchConflict) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_CONFLICT;
			} else if (lowerArmLimitSwitchTooFar) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			} else if (stopTargeting) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
			} else if (lowerArmEncoder >= lowerArmEncoderTarget) {
				lowerArmMotor.set(lowerArmStopSpeed);
				LOWER_ARM = state.LA_STOPPED;
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
			} else if (upperArmButtonUp && !upperArmLimitSwitchTooFar) {
				upperArmMotor.set(upperArmMovingUpSpeed);
				UPPER_ARM = state.UA_MOVING_UP;
			} else if (upperArmButtonDown && !upperArmLimitSwitchHome) {
				upperArmMotor.set(upperArmMovingDownSpeed);
				UPPER_ARM = state.UA_MOVING_DOWN;
			} else if (calibrationButton && !limitSwitchConflict) {
				UPPER_ARM = state.UA_CALIBRATING;
			} else if (upperArmCalibrated) {
				if (X1) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_X1) {
						if (!upperArmLimitSwitchTooFar) {
							upperArmMotor.set(upperArmMovingUpSpeed);
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
						}
					}
				}

				if (X2) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_X2) {
						if (!upperArmLimitSwitchTooFar) {
							upperArmMotor.set(upperArmMovingUpSpeed);
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
						}
					}
				}
				if (Y1) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_Y1) {
						if (!upperArmLimitSwitchTooFar) {
							upperArmMotor.set(upperArmMovingUpSpeed);
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
						}
					}
				}
				if (Y2) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_Y2) {
						if (!upperArmLimitSwitchTooFar) {
							upperArmMotor.set(upperArmMovingUpSpeed);
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
						}
					}
				}
				if (H1) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_H1) {
						if (!upperArmLimitSwitchTooFar) {
							upperArmMotor.set(upperArmMovingUpSpeed);
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
						}
					}
				}
				if (H2) {
					if (upperArmEncoder + UPPER_ARM_ENCODER_TOLERANCE < TARGET_UA_H2) {
						if (!upperArmLimitSwitchTooFar) {
							upperArmMotor.set(upperArmMovingUpSpeed);
							UPPER_ARM = state.UA_MOVING_UP_TARGET;
						}
					}
				}
			} else if (upperArmCalibrated) {
				if (X1) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_X1) {
						if (!upperArmLimitSwitchHome) {
							upperArmMotor.set(upperArmMovingDownSpeed);
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
						}
					}
				}

				if (X2) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_X2) {
						if (!upperArmLimitSwitchHome) {
							upperArmMotor.set(upperArmMovingDownSpeed);
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (Y1) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_Y1) {
						if (!upperArmLimitSwitchHome) {
							upperArmMotor.set(upperArmMovingDownSpeed);
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (Y2) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_Y2) {
						if (!upperArmLimitSwitchHome) {
							upperArmMotor.set(upperArmMovingDownSpeed);
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (H1) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_H1) {
						if (!upperArmLimitSwitchHome) {
							upperArmMotor.set(upperArmMovingDownSpeed);
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
						}
					}
				}
				if (H2) {
					if (upperArmEncoder - UPPER_ARM_ENCODER_TOLERANCE > TARGET_UA_H2) {
						if (!upperArmLimitSwitchHome) {
							upperArmMotor.set(upperArmMovingDownSpeed);
							UPPER_ARM = state.UA_MOVING_DOWN_TARGET;
						}
					}
				}
			}
			break;

		case UA_MOVING_UP:
			if (limitSwitchConflict) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_CONFLICT;
			} else if (upperArmLimitSwitchTooFar) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			} else if (!upperArmButtonUp) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			}
			break;

		case UA_CONFLICT:
			if (!upperArmLimitSwitchTooFar && upperArmButtonUp) {
				upperArmMotor.set(upperArmMovingUpSpeed);
				UPPER_ARM = state.UA_MOVING_UP;
			} else if (!upperArmLimitSwitchHome && upperArmButtonDown) {
				upperArmMotor.set(upperArmMovingDownSpeed);
				UPPER_ARM = state.UA_MOVING_DOWN;
			}
			break;

		case UA_MOVING_DOWN:
			if (limitSwitchConflict) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_CONFLICT;
			} else if (upperArmLimitSwitchHome) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			} else if (!upperArmButtonDown) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			}
			break;

		case UA_CALIBRATING:
			if (limitSwitchConflict) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_CONFLICT;
			} else if (!calibrationButton || stopTargeting) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			}
			break;

		case UA_MOVING_DOWN_TARGET:
			if (limitSwitchConflict) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_CONFLICT;
			} else if (upperArmLimitSwitchHome) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			} else if (stopTargeting) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			} else if (upperArmEncoder <= upperArmEncoderTarget) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			}
			break;

		case UA_MOVING_UP_TARGET:
			if (limitSwitchConflict) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_CONFLICT;
			} else if (upperArmLimitSwitchTooFar) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			} else if (stopTargeting) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			} else if (upperArmEncoder >= upperArmEncoderTarget) {
				upperArmMotor.set(upperArmStopSpeed);
				UPPER_ARM = state.UA_STOPPED;
			}
			break;

		}
	}
}
