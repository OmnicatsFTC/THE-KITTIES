package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Shooter2;
import org.firstinspires.ftc.teamcode.subsystems.intakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.servo;
import org.firstinspires.ftc.teamcode.subsystems.shooter;

@TeleOp (name = "Systems", group = "testing")

public class Systems extends LinearOpMode {
    boolean lastTriggerDown = false;

    @Override
   public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        BasicDrive basicDrive = new BasicDrive(mecanumDrive);

       intakeIndexer ii = new intakeIndexer(hardwareMap);



       waitForStart();

       while (opModeIsActive()) {
           basicDrive.update();
           double forward = -gamepad1.left_stick_y;
           double strafe = gamepad1.left_stick_x;
           double turn = gamepad1.right_stick_x;

           boolean buttonPressed = gamepad1.rightBumperWasPressed();
           boolean buttonPressed3 = gamepad1.bWasPressed();
           boolean buttonServo = gamepad1.leftBumperWasPressed();
           Shooter2 shooter = new Shooter2(hardwareMap);

           boolean lastA = false;

           waitForStart();

           while (opModeIsActive()) {
               // Toggle only on the moment A is pressed, not while it's held
               boolean a = gamepad1.right_trigger > 0.5;
               if (a && !lastA) {
                   shooter.toggle();
               }
               lastA = a;

               shooter.update();

               telemetry.addData("Shooter", "running");
               telemetry.update();

           ii.spin(buttonPressed);

           basicDrive.drive(forward, strafe, turn);
           telemetry.addData("x", basicDrive.getPose().position.x);
           telemetry.addData("y", basicDrive.getPose().position.y);
           telemetry.update();

           servo servo1 = new servo(hardwareMap);

           servo1.spinServo(buttonServo);

       }

   }


}
}