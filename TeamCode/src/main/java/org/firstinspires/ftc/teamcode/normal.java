package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;

public class normal extends LinearOpMode {
   public void runOpMode() {
       BasicDrive drive = new BasicDrive(hardwareMap);

       while (opModeIsActive()) {
           double forward = -gamepad1.left_stick_y;
           double strafe = gamepad1.left_stick_x;
           double turn = gamepad1.right_stick_x;




           drive.drive(forward, strafe, turn);
       }

   }


}
