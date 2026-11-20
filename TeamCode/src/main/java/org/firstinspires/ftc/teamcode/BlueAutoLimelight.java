
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.NullAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.ServoIndexer;
import org.firstinspires.ftc.teamcode.subsystems.shooterPID;

import java.util.Set;
import java.util.function.Supplier;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;



@Autonomous(name = "BlueAutoLimelight")
public class BlueAutoLimelight extends LinearOpMode {

    static final double START_X = 12;        // where you place the robot along the wall
    static final double START_Y = 63;        // wall at -72 + half your robot length (measure yours)
    static final double MIDPOINT_X1 = 12;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y1 = 63;
    static final double PARK_X = 63;         // robot center; the left wall is at -72
    static final double PARK_Y = -36;
    static final Set<Integer> PATH1_TAGS = Set.of(45, 44, 43,42);// middle of the red loading zone


    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(START_X, START_Y, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        shooterPID shooter = new shooterPID(hardwareMap);
        IntakeIndexer ii = new IntakeIndexer(hardwareMap);
        ServoIndexer s = new ServoIndexer(hardwareMap);
        Limelight l = new Limelight(hardwareMap);
        boolean[] spinning = {false};
        boolean[] finished = {false};



        Action spinShooter = packet -> {
            if (spinning[0]) {
                shooter.autonSpin(true);
                packet.put("Shooter RPM", shooter.getRPM());
            }
            return !finished[0];
        };
        Supplier<Action> shoot= () -> new SequentialAction(
                new InstantAction(() -> spinning[0] = true),
                new SleepAction(3.5),
                new InstantAction(() -> ii.setiiPower(-1)),
                new InstantAction(() -> s.setServoPower(1)),
                new SleepAction(4),
                new InstantAction(() -> s.setServoPower(0)),
                new InstantAction(() -> ii.setiiPower(0)),
                new InstantAction(() -> {
                    spinning[0] = false;
                    shooter.autonSpin(false);

        }));

        Action conditional1 = drive.actionBuilder(drive.localizer.getPose())
                .turn(Math.toRadians(180))
                .stopAndAdd(new InstantAction(() -> s.rotateServo(0.95)))
                .strafeTo(new Vector2d(63, 24))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(-1)))
                .waitSeconds(2)
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                .strafeToLinearHeading(
                        new Vector2d(12,63),
                        Math.toRadians(270)
                )
                .stopAndAdd(shoot.get())
                .strafeTo(new Vector2d(48, -24))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(-1)))
                .strafeTo(new Vector2d(24, -63))
                .stopAndAdd(shoot.get())
                .strafeTo(new Vector2d(63, -36))
                .build();


        Action conditional2 = drive.actionBuilder(startPose)
                .turn(Math.toRadians(180))
                .stopAndAdd(new InstantAction(() -> s.rotateServo(0.95)))
                .strafeTo(new Vector2d(63, 24))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(-1)))
                .waitSeconds(2)
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                .strafeToLinearHeading(
                        new Vector2d(12,-63),
                        Math.toRadians(90)
                )
                .stopAndAdd(shoot.get())
                .stopAndAdd(new InstantAction(() -> s.rotateServo(0.95)))
                .splineTo(new Vector2d(24,-63),Math.toRadians(270))
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(-1)))
                .waitSeconds(2)
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                .splineTo(new Vector2d(12,-63), Math.toRadians(90))
                .stopAndAdd(shoot.get())
                .splineTo(new Vector2d(63,-36), Math.toRadians(180))
                .build();

        Action chooseByTag = new Action() {
            Action chosen = null;

            @Override
            public boolean run(TelemetryPacket packet) {
                if (chosen == null) {
                    boolean sawTag = l.seesAny(PATH1_TAGS);
                    packet.put("Saw tag", sawTag);
                    chosen = sawTag ? conditional1 : conditional2;
                }
                return chosen.run(packet);
            }
        };

        Action path = drive.actionBuilder(startPose)
                .stopAndAdd(new InstantAction(() -> s.setServoPower(-1)))
                .waitSeconds(2)
                .stopAndAdd(shoot.get())
                .stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                .stopAndAdd(chooseByTag)



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

