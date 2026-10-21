package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.subsystems.Shooter2;
import org.firstinspires.ftc.teamcode.subsystems.intakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.limelight;
import org.firstinspires.ftc.teamcode.subsystems.servo;
import org.firstinspires.ftc.teamcode.subsystems.shooterGood;

@TeleOp(name = "Systems", group = "testing")
public class Systems extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        BasicDrive basicDrive = new BasicDrive(mecanumDrive);
        intakeIndexer ii = new intakeIndexer(hardwareMap);
        shooterGood shooter = new shooterGood(hardwareMap);
        servo servo1 = new servo(hardwareMap);
        //limelight limelight1 = new limelight(hardwareMap);

        boolean lastTrigger = false;

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            boolean intakeButton = gamepad1.rightBumperWasPressed();
            boolean servoButton = gamepad1.leftBumperWasPressed();
            boolean buttonPressed3 = gamepad1.yWasPressed();
            boolean buttonPressed = gamepad1.rightTriggerWasPressed();



            basicDrive.update();
            basicDrive.drive(forward, strafe, turn);
            shooter.spin(buttonPressed);
            ii.spin(intakeButton);
            ii.outtake(gamepad1.xWasPressed());
            servo1.spinServo(servoButton);
            //limelight1.getAprilTag(buttonPressed3, turn);

            if (gamepad1.dpadDownWasPressed()){
                shooter.decrement();
                shooter.spin(true);
            }

            if (gamepad1.dpadUpWasPressed()){
                shooter.increment();
                shooter.setShooterPower(shooter.getPower());
            }



            telemetry.addData("x", basicDrive.getPose().position.x);
            telemetry.addData("y", basicDrive.getPose().position.y);
            telemetry.addData("shooter speed", shooter.getPower());
            telemetry.update();
        }
    }
}