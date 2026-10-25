package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BasicDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeIndexer;
//import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.ServoIndexer;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(name = "Systems", group = "TeleOp")
public class Systems extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        BasicDrive basicDrive = new BasicDrive(mecanumDrive);
        IntakeIndexer ii = new IntakeIndexer(hardwareMap);
        Shooter shoot = new Shooter(hardwareMap);
        ServoIndexer servo = new ServoIndexer(hardwareMap);
        //Limelight ll = new Limelight(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            boolean IntakeButton = gamepad1.rightBumperWasPressed();
            boolean ServoButton = gamepad1.leftBumperWasPressed();
            boolean AlignButton = gamepad1.yWasPressed();
            boolean ShooterButton = gamepad1.rightTriggerWasPressed();

            basicDrive.update();
            basicDrive.drive(forward, strafe, turn);
            shoot.spin(ShooterButton);
            ii.spin(IntakeButton);
            ii.outtake(gamepad1.xWasPressed());
            servo.spinServo(ServoButton);
            //ll.getAprilTag(AlignButton, turn);

            if (gamepad1.dpadDownWasPressed()){
                shoot.decrement();
                shoot.spin(true);
                }

            if (gamepad1.dpadUpWasPressed()){
                shoot.increment();
                shoot.setShooterPower(shoot.getPower());
                }

            if (gamepad1.aWasPressed()) {
                ii.setiiPower(0);
                shoot.setShooterPower(0.6);
                sleep(1750);
                servo.setServoPower(1);
                }

            if (gamepad1.bWasPressed()){
                ii.setiiPower(0);
                shoot.setShooterPower(0);
                servo.setServoPower(0);
                }

            }

            telemetry.addData("x", basicDrive.getPose().position.x);
            telemetry.addData("y", basicDrive.getPose().position.y);
            telemetry.addData("shooter speed", shoot.getPower());
            telemetry.update();
        }
    }