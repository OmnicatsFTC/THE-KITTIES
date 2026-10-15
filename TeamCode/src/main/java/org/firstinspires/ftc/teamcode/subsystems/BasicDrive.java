package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class BasicDrive {
    private final MecanumDrive drive;

    public BasicDrive(MecanumDrive drive) {
        this.drive = drive;
    }

    // forward: + is forward, strafe: + is right, turn: + is clockwise
    public void drive(double forward, double strafe, double turn) {
        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(forward, -strafe), -turn));
    }

    public void update() {
        drive.updatePoseEstimate();
    }

    public Pose2d getPose() {
        return drive.localizer.getPose();
    }
}