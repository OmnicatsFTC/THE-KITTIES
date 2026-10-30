package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeIndexer;
//import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.ServoIndexer;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(name = "ShooterTest", group = "TeleOp")
public class ShooterTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Shooter shoot = new Shooter(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            boolean ShooterButton = gamepad1.rightTriggerWasPressed();

            shoot.spin(ShooterButton);

            if (gamepad1.dpadDownWasPressed()){
                shoot.decrement();
                shoot.spin(true);
            }

            if (gamepad1.dpadUpWasPressed()){
                shoot.increment();
                shoot.setShooterPower(shoot.getPower());
            }
            telemetry.addData("shooter speed", shoot.getPower());
            telemetry.update();
        }
    }
}