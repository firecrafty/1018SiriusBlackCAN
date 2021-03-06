package org.usfirst.frc.team1018.robot.commands.gearholder;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1018.robot.subsystems.GearRotator;

/**
 * @author Ryan Blue
 *
 * Overrides the automatic gear rotation
 *
 * @see GearRotator
 * @see org.usfirst.frc.team1018.robot.subsystems.GearStateMachine
 */
public class RotateGearOverrideCommand extends Command {
    private GearRotator gearRotator = GearRotator.getInstance();

    public RotateGearOverrideCommand() {
        requires(gearRotator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        gearRotator.setOverride(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished() returns true
    protected void end() {
        gearRotator.setOverride(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

}
