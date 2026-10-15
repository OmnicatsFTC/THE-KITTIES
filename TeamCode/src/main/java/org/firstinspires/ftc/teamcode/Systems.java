package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.shooter;

@TeleOp (name = "Systems", group = "testing")

public class Systems extends LinearOpMode {
    boolean lastTriggerDown = false;

    @Override
   public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        BasicDrive basicDrive = new BasicDrive(mecanumDrive);

       intakeIndexer ii = new intakeIndexer(hardwareMap);

       shooter shoot = new shooter(hardwareMap);

       waitForStart();

       while (opModeIsActive()) {
           basicDrive.update();
           double forward = -gamepad1.left_stick_y;
           double strafe = gamepad1.left_stick_x;
           double turn = gamepad1.right_stick_x;

           boolean buttonPressed = gamepad1.aWasPressed();
           boolean buttonPressed3 = gamepad1.bWasPressed();

           boolean triggerDown = gamepad1.left_trigger > 0.5;
           if (triggerDown && !lastTriggerDown) {
               shoot.toggle();
           }
           lastTriggerDown = triggerDown;

           shoot.update();

           ii.spin(buttonPressed);

           basicDrive.drive(forward, strafe, turn);
           telemetry.addData("x", basicDrive.getPose().position.x);
           telemetry.addData("y", basicDrive.getPose().position.y);
           telemetry.update();

       }

   }


}