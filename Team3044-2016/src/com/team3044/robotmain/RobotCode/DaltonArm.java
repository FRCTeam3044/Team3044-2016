package com.team3044.robotmain.RobotCode;
import com.team3044.robotmain.Reference.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DaltonArm {

	public enum state {
		STOPPED, MOVING_UP, MOVING_DOWN, DALTON_ARM
	}

	public boolean armButtonUp; // BUTTONS
	public boolean armButtonDown;

	public final double rightArmMovingUpSpeed = -0.15; // MOTOR SPEEDS
	public final double rightArmMovingDownSpeed = 0.05;
	public final double rightArmStoppedSpeed = 0;
	public final double leftArmMovingUpSpeed = -0.15;
	public final double leftArmMovingDownSpeed = 0.05;
	public final double leftArmStoppedSpeed = 0;

	public CANTalon leftArmMotor; // MOTORS
	public CANTalon rightArmMotor;

	state DALTON_ARM = state.STOPPED; // DEFAULT STARTING STATE AND SWITCH

	public boolean armLimitSwitchUp; // LIMIT SWITCHES
	public boolean armLimitSwitchDown;

	public void defenseInit() {

		leftArmMotor = Components.getInstance().leftArm;
		rightArmMotor = Components.getInstance().rightArm;
		leftArmMotor.set(leftArmStoppedSpeed);
		rightArmMotor.set(rightArmStoppedSpeed);

		DALTON_ARM = state.STOPPED;

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {

		SmartDashboard.putString("DB/String 1", String.valueOf(DALTON_ARM)); // DEFENSE
																				// TEST

		armLimitSwitchUp = leftArmMotor.isFwdLimitSwitchClosed(); // NEED TO
																	// FIGURE
																	// OUT LIMIT
																	// SWITCHES
																	// WHICH
																	// MOTOR
																	// THEY GO
																	// THROUGH
		armLimitSwitchDown = leftArmMotor.isRevLimitSwitchClosed();

		armButtonUp = CommonArea.UP;
		armButtonDown = CommonArea.DOWN;

		switch (DALTON_ARM) {

		default:
			DALTON_ARM = state.STOPPED;
			leftArmMotor.set(leftArmStoppedSpeed);
			rightArmMotor.set(rightArmStoppedSpeed);
			break;

		case STOPPED:
			if (!armLimitSwitchDown && armButtonDown) {
				DALTON_ARM = state.MOVING_DOWN;
			} else if (!armLimitSwitchUp && armButtonUp) {
				DALTON_ARM = state.MOVING_UP;
			}
		case MOVING_UP:
			if (armLimitSwitchUp || !armButtonUp) {
				DALTON_ARM = state.STOPPED;
			}
		case MOVING_DOWN:
			if (armLimitSwitchDown || !armButtonDown) {
				DALTON_ARM = state.STOPPED;
			}
		}
	}
}