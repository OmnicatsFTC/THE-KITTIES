package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "BasicAuto")
public class BasicAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(12, -60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Action path = drive.actionBuilder(startPose)
                .lineToY(-36)                                   // drive forward
                .strafeTo(new Vector2d(36, -36))                // strafe, heading unchanged
                .splineTo(new Vector2d(48, 0), Math.toRadians(90)) // smooth curve
                .turn(Math.toRadians(90))
                .waitSeconds(1)
                .build();

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(path);
    }
}
