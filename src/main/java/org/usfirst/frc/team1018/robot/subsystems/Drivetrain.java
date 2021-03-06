package org.usfirst.frc.team1018.robot.subsystems;

import com.ctre.MotorControl.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1018.robot.RobotConfig;
import org.usfirst.frc.team1018.robot.commands.drivetrain.DrivetrainBrakesCommand;

/**
 * @author Ryan Blue
 */
public class Drivetrain extends Subsystem implements PIDOutput {
    private static Drivetrain instance;
    public RobotConfig.DrivetrainConfig CONFIG = RobotConfig.DRIVETRAIN_CONFIG;

    private CANTalon rearRight = new CANTalon(CONFIG.MOTOR_REAR_RIGHT_CAN);
    private CANTalon rearLeft = new CANTalon(CONFIG.MOTOR_REAR_LEFT_CAN);
    private CANTalon frontRight = new CANTalon(CONFIG.MOTOR_FRONT_RIGHT_CAN);
    private CANTalon frontLeft = new CANTalon(CONFIG.MOTOR_FRONT_LEFT_CAN);

    private Encoder rightEncoder = new Encoder(CONFIG.ENCODER_RIGHT_A_DIO, CONFIG.ENCODER_RIGHT_B_DIO, CONFIG.RIGHT_ENCODER_REVERSE_CFG);
    private Encoder leftEncoder = new Encoder(CONFIG.ENCODER_LEFT_A_DIO, CONFIG.ENCODER_LEFT_B_DIO, CONFIG.LEFT_ENCODER_REVERSE_CFG);
    private AHRS navX = new AHRS(CONFIG.NAVX_SPI);

    private RobotDrive driveHelper = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);

    private boolean fieldOriented = CONFIG.FIELD_ORIENTED_CFG;

    private PIDController angleController = new PIDController(0, 0, 0, navX, this);

    private Drivetrain() {
        frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        rearRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        rearLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        frontRight.enableBrakeMode(false);
        frontLeft.enableBrakeMode(false);
        rearRight.enableBrakeMode(false);
        rearLeft.enableBrakeMode(false);
        frontRight.set(0);
        frontLeft.set(0);
        rearRight.set(0);
        rearLeft.set(0);
        driveHelper.setSafetyEnabled(false);
        rearRight.setInverted(true);
        frontRight.setInverted(true);
        rightEncoder.reset();
        leftEncoder.reset();
    }

    public static Drivetrain getInstance() {
        if(instance == null) instance = new Drivetrain();
        return instance;
    }

    public void enableBrakeMode(boolean brake) {
        frontRight.enableBrakeMode(brake);
        frontLeft.enableBrakeMode(brake);
        rearRight.enableBrakeMode(brake);
        frontRight.enableBrakeMode(brake);
    }

    public void mecanumDrive(double x, double y, double turn) {
        driveHelper.mecanumDrive_Cartesian(x, y, turn, fieldOriented ? navX.getYaw() : 0);
    }

    public void driveSimpleArcade(double power, double turn) {
        driveTank(power - turn, power + turn);
    }

    public void driveTank(double left, double right) {
        rearLeft.set(left);
        frontLeft.set(left);
        rearRight.set(right);
        frontRight.set(right);
    }

    public void stop() {
        rearLeft.set(0);
        frontLeft.set(0);
        rearRight.set(0);
        frontRight.set(0);
    }

    public int getLeftEncoderTicks() {
        return leftEncoder.get();
    }

    public int getRightEncoderTicks() {
        return rightEncoder.get();
    }

    private void resetLeftEncoder() {
        leftEncoder.reset();
    }

    private void resetRightEncoder() {
        rightEncoder.reset();
    }

    public void resetEncoders() {
        resetLeftEncoder();
        resetRightEncoder();
    }

    public double getYaw() {
        return navX.getYaw();
    }

    public void resetGyro() {
        navX.reset();
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("Front Left Motor: ", frontLeft.get());
        SmartDashboard.putNumber("Front Right Motor: ", frontRight.get());
        SmartDashboard.putNumber("Rear Left Motor: ", rearLeft.get());
        SmartDashboard.putNumber("Rear Right Motor: ", rearRight.get());
        SmartDashboard.putNumber("Left encoder count: ", getLeftEncoderTicks());
        SmartDashboard.putNumber("Right encoder count: ", getRightEncoderTicks());
        SmartDashboard.putNumber("Gyro Yaw: ", getYaw());
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainBrakesCommand(false));
    }

    @Override
    public void pidWrite(double output) {

    }
}
