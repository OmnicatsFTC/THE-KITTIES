
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.ServoIndexer;
import org.firstinspires.ftc.teamcode.subsystems.shooterPID;


@Autonomous(name = "BlueAutoLimelight")
public class BlueAutoLimelight extends LinearOpMode {

    static final double START_X = 12;        // where you place the robot along the wall
    static final double START_Y = 63;        // wall at -72 + half your robot length (measure yours)
    static final double MIDPOINT_X1 = 12;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y1 = 63;
    static final double PARK_X = 63;         // robot center; the left wall is at -72
    static final double PARK_Y = -36;          // middle of the red loading zone

    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(START_X, START_Y, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        shooterPID shooter = new shooterPID(hardwareMap);
        IntakeIndexer ii = new IntakeIndexer(hardwareMap);
        ServoIndexer s = new ServoIndexer(hardwareMap);
        boolean[] spinning = {false};
        boolean[] finished = {false};

        Action spinShooter = packet -> {
            if (spinning[0]) {
                shooter.autonSpin(true);
                packet.put("Shooter RPM", shooter.getRPM());
            }
            return !finished[0];
        };

        Action conditional1 = drive.actionBuilder(drive.localizer.getPose())
                .turn(Math.toRadians(90))
                .stopAndAdd(new InstantAction(() -> s.rotateServo(0.95)))
                .strafeTo(new Vector2d(63, 24))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(1)))
                .waitSeconds(2)
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                .strafeToLinearHeading(
                        new Vector2d(12,63),
                        Math.toRadians(270)
                )
                .stopAndAdd(new InstantAction(() -> s.setServoPower(1)))
                .stopAndAdd(new InstantAction(() -> spinning[0] = true))
                .waitSeconds(2)
                .stopAndAdd(new InstantAction(() -> s.setServoPower(0)))
                .stopAndAdd(new InstantAction(() -> {
                    spinning[0] = false;
                    shooter.autonSpin(false);
                }))
                .strafeTo(new Vector2d(48, -24))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(1)))
                .strafeTo(new Vector2d(24, -63))
                .stopAndAdd(new InstantAction(() -> s.setServoPower(1)))
                .stopAndAdd(new InstantAction(() -> spinning[0] = true))
                .waitSeconds(2)
                .stopAndAdd(new InstantAction(() -> s.setServoPower(0)))
                .stopAndAdd(new InstantAction(() -> {
                    spinning[0] = false;
                    shooter.autonSpin(false);
                }))
                .strafeTo(new Vector2d(63, -36))
                .build();


        Action conditional2 = drive.actionBuilder(drive.localizer.getPose())
                .build()




        Action path = drive.actionBuilder(startPose)
                .stopAndAdd(new InstantAction(() -> spinning[0] = true))//.stopAndAdd(new InstantAction(() -> shooter.autonSpin(true)))
                .waitSeconds(3)
                .stopAndAdd(new InstantAction(() -> {
                    s.setServoPower(1);
                    telemetry.addLine("SERVO STARTED");
                    telemetry.update();
                }))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(-1)))
                .waitSeconds(3)
                .stopAndAdd(new InstantAction(() -> {
                    spinning[0] = false;
                    shooter.autonSpin(false);
                }))
                .stopAndAdd(new InstantAction(() -> s.setServoPower(0)))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                .strafeTo(new Vector2d(36, 48))
                .strafeTo(new Vector2d(PARK_X, PARK_Y))            // diagonal into the loading zone
                .build();

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                path,
                                new InstantAction(() -> finished[0] = true)
                        ),
                        spinShooter
                )
        );

    }

}

