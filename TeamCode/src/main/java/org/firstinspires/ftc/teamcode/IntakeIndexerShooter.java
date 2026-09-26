package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "IntakeIndexerShooter", group = "testing")
public class IntakeIndexerShooter extends LinearOpMode {

    // ---- Hardware ----
    private DcMotor intake;
    private DcMotor indexer;
    private DcMotorEx shooterTop;
    private DcMotorEx shooterBottom;

    // ---- Tuning constants ----
    private static final double INTAKE_POWER = 1.0;
    private static final double INDEXER_POWER = 0.8;
    private static final double SHOOTER_TOP_VELOCITY = 1500;    // ticks/sec, tune to your flywheel
    private static final double SHOOTER_BOTTOM_VELOCITY = 1500; // ticks/sec, tune independently
    private static final double SHOOTER_VELOCITY_TOLERANCE = 50;
    private static final long INDEXER_FEED_TIME_MS = 400; // time to push one game piece through

    // ---- State machine ----
    private enum State {
        IDLE,
        INTAKING,
        SPINNING_UP_SHOOTER,
        FEEDING,
        RECOVER // brief pause between shots so shooter re-stabilizes
    }

    private State state = State.IDLE;
    private final ElapsedTime stateTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        intake = hardwareMap.get(DcMotor.class, "intake");
        indexer = hardwareMap.get(DcMotor.class, "indexer");
        shooterTop = hardwareMap.get(DcMotorEx.class, "shooterTop");
        shooterBottom = hardwareMap.get(DcMotorEx.class, "shooterBottom");

        // Both shooter motors spin the same direction (stacked top/bottom wheels).
        shooterTop.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterBottom.setDirection(DcMotorSimple.Direction.FORWARD);

        shooterTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        indexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        stateTimer.reset();

        while (opModeIsActive()) {
            Gamepad gp = gamepad1;

            switch (state) {
                case IDLE:
                    stopAll();
                    if (gp.right_trigger > 0.2) {
                        state = State.INTAKING;
                        stateTimer.reset();
                    }
                    break;

                case INTAKING:
                    intake.setPower(INTAKE_POWER);
                    // Shooter can spin up in parallel to save cycle time
                    setShooterVelocity(SHOOTER_TOP_VELOCITY, SHOOTER_BOTTOM_VELOCITY);
                    if (gp.right_bumper) { // driver signals "ready to shoot"
                        state = State.SPINNING_UP_SHOOTER;
                        stateTimer.reset();
                    } else if (gp.right_trigger <= 0.2) {
                        state = State.IDLE;
                    }
                    break;

                case SPINNING_UP_SHOOTER:
                    intake.setPower(0);
                    setShooterVelocity(SHOOTER_TOP_VELOCITY, SHOOTER_BOTTOM_VELOCITY);
                    if (shooterAtSpeed()) {
                        indexer.setPower(INDEXER_POWER);
                        state = State.FEEDING;
                        stateTimer.reset();
                    }
                    break;

                case FEEDING:
                    setShooterVelocity(SHOOTER_TOP_VELOCITY, SHOOTER_BOTTOM_VELOCITY); // keep flywheel loaded under RPM sag
                    indexer.setPower(INDEXER_POWER);
                    if (stateTimer.milliseconds() > INDEXER_FEED_TIME_MS) {
                        indexer.setPower(0);
                        state = State.RECOVER;
                        stateTimer.reset();
                    }
                    break;

                case RECOVER:
                    setShooterVelocity(SHOOTER_TOP_VELOCITY, SHOOTER_BOTTOM_VELOCITY);
                    if (shooterAtSpeed() && stateTimer.milliseconds() > 150) {
                        state = State.IDLE;
                    }
                    break;
            }

            telemetry.addData("State", state);
            telemetry.addData("Shooter Top vel", shooterTop.getVelocity());
            telemetry.addData("Shooter Bottom vel", shooterBottom.getVelocity());
            telemetry.update();
        }
    }

    private void setShooterVelocity(double topVel, double bottomVel) {
        shooterTop.setVelocity(topVel);
        shooterBottom.setVelocity(bottomVel);
    }

    private boolean shooterAtSpeed() {
        return Math.abs(shooterTop.getVelocity() - SHOOTER_TOP_VELOCITY) < SHOOTER_VELOCITY_TOLERANCE
                && Math.abs(shooterBottom.getVelocity() - SHOOTER_BOTTOM_VELOCITY) < SHOOTER_VELOCITY_TOLERANCE;
    }

    private void stopAll() {
        intake.setPower(0);
        indexer.setPower(0);
        shooterTop.setPower(0);
        shooterBottom.setPower(0);
    }
}