package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.subsystems.Shooter2;
import org.firstinspires.ftc.teamcode.subsystems.intakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.servo;

@TeleOp(name = "Systems", group = "testing")
public class Systems extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        BasicDrive basicDrive = new BasicDrive(mecanumDrive);
        intakeIndexer ii = new intakeIndexer(hardwareMap);
        Shooter2 shooter = new Shooter2(hardwareMap);
        servo servo1 = new servo(hardwareMap);

        boolean lastTrigger = false;

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            boolean intakeButton = gamepad1.rightBumperWasPressed();
            boolean servoButton = gamepad1.leftBumperWasPressed();

            boolean trigger = gamepad1.right_trigger > 0.5;
            if (trigger && !lastTrigger) {
                shooter.toggle();
            }
            lastTrigger = trigger;

            basicDrive.update();
            basicDrive.drive(forward, strafe, turn);
            shooter.update();
            ii.spin(intakeButton);
            servo1.spinServo(servoButton);

            telemetry.addData("Shooter", "running");
            telemetry.addData("x", basicDrive.getPose().position.x);
            telemetry.addData("y", basicDrive.getPose().position.y);
            telemetry.update();
        }
    }
}