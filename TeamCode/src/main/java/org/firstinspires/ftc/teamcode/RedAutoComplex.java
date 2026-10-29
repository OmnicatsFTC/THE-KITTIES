
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "BlueAutoComplex")
public class RedAutoComplex extends LinearOpMode {

    static final double START_X = -24;        // where you place the robot along the wall
    static final double START_Y = -63;        // wall at -72 + half your robot length (measure yours)


    //static final double MIDPOINT_X = -12;       // short strafe left, stops before the flower
    //static final double MIDPOINT_Y = 24;// drive forward to here, then cut left

    static final double MIDPOINT_X1 = -12;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y1 = -48;

    static final double MIDPOINT_X2 = -63;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y2 = -24;

    static final double MIDPOINT_X25 = -48;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y25 = 48;

    static final double MIDPOINT_X3 = -12;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y3 = 48;

    static final double MIDPOINT_X4 = -24;       // short strafe left, stops before the flower
    static final double MIDPOINT_Y4 = -63;

    static final double PARK_X = -63;         // robot center; the left wall is at -72
    static final double PARK_Y = 36;          // middle of the red loading zone

    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(START_X, START_Y, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        //Shooter shooter = new Shooter(hardwareMap);
        //IntakeIndexer ii = new IntakeIndexer(hardwareMap);
        //ServoIndexer s = new ServoIndexer(hardwareMap);


        Action path = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(MIDPOINT_X1, MIDPOINT_Y1))// diagonal to the midpoint
                //.stopAndAdd(new InstantAction(() -> shooter.setShooterPower(0.7)))
                .waitSeconds(2)
                //.stopAndAdd(new InstantAction(() -> ii.setiiPower(1)))
                //.waitSeconds(1)
                //.stopAndAdd(new InstantAction(() -> ii.setiiPower(0)))
                //.stopAndAdd(new InstantAction(() -> s.setServoPower(1)))
                //.waitSeconds(1)
                //.stopAndAdd(new InstantAction(() -> s.setServoPower(0)))
                //.stopAndAdd(new InstantAction(() -> shooter.setShooterPower(0)))
                .turn(Math.toRadians(90))
                //flower mechanism
                .strafeTo(new Vector2d(MIDPOINT_X2, MIDPOINT_Y2))
                //flower tickler up
                // intake
                .waitSeconds(2)
                .strafeTo(new Vector2d(MIDPOINT_X25, MIDPOINT_Y25))
                .strafeTo(new Vector2d(MIDPOINT_X3, MIDPOINT_Y3))
                .turn(Math.toRadians(90))
                //shoot
                .waitSeconds(2)
                .turn(Math.toRadians(180))
                //flower down
                .strafeTo(new Vector2d(MIDPOINT_X4, MIDPOINT_Y4))
                //flower tickler up
                //intake
                .waitSeconds(2)
                .strafeTo(new Vector2d(PARK_X, PARK_Y))            // diagonal into the loading zone
                .build();

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(path);
    }

}

