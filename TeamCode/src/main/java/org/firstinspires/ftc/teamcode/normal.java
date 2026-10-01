package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;

@TeleOp (name = "normal", group = "testing")

public class normal extends LinearOpMode {


    @Override
   public void runOpMode() {
       BasicDrive drive = new BasicDrive(hardwareMap);

       waitForStart();

       while (opModeIsActive()) {
           double forward = -gamepad1.left_stick_y;
           double strafe = gamepad1.left_stick_x;
           double turn = gamepad1.right_stick_x;




           drive.drive(forward, strafe, turn);
       }

   }


}
