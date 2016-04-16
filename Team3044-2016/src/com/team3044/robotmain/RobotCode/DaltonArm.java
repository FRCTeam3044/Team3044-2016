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

	public final double daltonArmMovingUpSpeed = -0.15; // MOTOR SPEEDS
	public final double daltonArmMovingDownSpeed = 0.05;
	public final double daltonArmStoppedSpeed = 0;

	public CANTalon daltonArmMotor; // MOTORS

	state DALTON_ARM = state.STOPPED; // DEFAULT STARTING STATE AND SWITCH

	public boolean daltonArmLimitSwitchUp; // LIMIT SWITCHES
	public boolean daltonArmLimitSwitchDown;

	public void defenseInit() {

		daltonArmMotor = Components.getInstance().daltonArm;
		daltonArmMotor.set(daltonArmStoppedSpeed);

		DALTON_ARM = state.STOPPED;

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {

		SmartDashboard.putString("DB/String 1", String.valueOf(DALTON_ARM)); // DEFENSE TEST

		daltonArmLimitSwitchUp = daltonArmMotor.isFwdLimitSwitchClosed(); // LIMIT SWITCHES
		daltonArmLimitSwitchDown = daltonArmMotor.isRevLimitSwitchClosed();

		armButtonUp = CommonArea.UP;
		armButtonDown = CommonArea.DOWN;

		switch (DALTON_ARM) {

		default:
			DALTON_ARM = state.STOPPED;
			daltonArmMotor.set(daltonArmStoppedSpeed);
			break;

		case STOPPED:
			if (!daltonArmLimitSwitchDown && armButtonDown) {
				DALTON_ARM = state.MOVING_DOWN;
			} else if (!daltonArmLimitSwitchUp && armButtonUp) {
				DALTON_ARM = state.MOVING_UP;
			}
		case MOVING_UP:
			if (daltonArmLimitSwitchUp || !armButtonUp) {
				DALTON_ARM = state.STOPPED;
			}
		case MOVING_DOWN:
			if (daltonArmLimitSwitchDown || !armButtonDown) {
				DALTON_ARM = state.STOPPED;
			}
		}
	}
}