package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.Components;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Defense defense = new Defense();
	Drive drive = new Drive();
	Shooter shooter = new Shooter();
	Components component = new Components();
	Gate gate = new Gate();

	public void robotInit() {
		component.init();
		drive.driveInit();
		defense.defenseInit();
		shooter.shooterInit();
		gate.gateInit();
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		drive.driveAutoPeriodic();
		defense.defenseAutoPeriodic();
		shooter.shooterAutoPeriodic();
		gate.gateAutoPeriodic();
	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {
		drive.driveTeleopPeriodic();
		defense.defenseTeleopPeriodic();
		shooter.shooterTeleopPeriodic();
		gate.gateTeleopPeriodic();
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
	}

	public void testPeriodic() {
	}
}
