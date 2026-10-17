package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter2 {

    private final DcMotor leftMotor;
    private final DcMotor rightMotor;

    private boolean on = false;
    private static final double SHOOT_POWER = 0.79;

    public Shooter2(HardwareMap hardwareMap) {
        // These names must match your Driver Station configuration exactly
        leftMotor = hardwareMap.get(DcMotor.class, "shooterLeft");
        rightMotor = hardwareMap.get(DcMotor.class, "shooterRight");

        // Motors on opposite sides of a shooter usually spin opposite directions,
        // so reverse one. Swap which one is reversed if the wheels spin the wrong way.
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void toggle() {
        on = !on;
    }

    public void update() {
        double power = on ? SHOOT_POWER : 0;
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }
    public boolean isOn() { return on; }
    public double getLeftPower() { return leftMotor.getPower(); }
    public double getRightPower() { return rightMotor.getPower(); }
}