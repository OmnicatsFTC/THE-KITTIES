package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.subsystems.intakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.shooter;

@TeleOp (name = "normal", group = "testing")

public class normal extends LinearOpMode {
    boolean lastTriggerDown = false;

    @Override
   public void runOpMode() {
       BasicDrive drive = new BasicDrive(hardwareMap);

       intakeIndexer ii = new intakeIndexer(hardwareMap);

       shooter shoot = new shooter(hardwareMap);

       waitForStart();

       while (opModeIsActive()) {
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

           drive.drive(forward, strafe, turn);


       }

   }


}