package org.usfirst.frc.team1018.robot.commands.paddles;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1018.robot.subsystems.Paddles;

/**
 * @author Ryan Blue
 * <p>
 * Flips up the gear paddles
 * @see Paddles
 */
public class PaddlesInCommand extends Command {
    private Paddles paddles = Paddles.getInstance();

    public PaddlesInCommand() {
        requires(paddles);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        paddles.paddlesIn();
    }

    // Called repeatedly when this Command is scheduled to run

    protected void execute() {}
    // Called once after isFinished() returns true

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

}
