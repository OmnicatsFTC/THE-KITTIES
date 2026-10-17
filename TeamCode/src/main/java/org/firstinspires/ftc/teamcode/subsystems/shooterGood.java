package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooterGood {

    private DcMotor shooterLeft;
    private DcMotor shooterRight;

    boolean shooterFlag;

    double motorPower = 0.7;


    public shooterGood(HardwareMap hardwareMap) {
        // "intakeMotor" must match the name in your Driver Station config
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");
        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");

        shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    public double getPower(){
        return motorPower;
    }
    public void spin(boolean buttonPressed) {
        if (buttonPressed) {
            shooterFlag = !shooterFlag;

            if (shooterFlag) {
                setShooterPower(motorPower);
            } else {
                shooterLeft.setPower(0);
                shooterRight.setPower(0);
            }

        }

    }
    public void setShooterPower(double power){
        shooterLeft.setPower(power);
        shooterRight.setPower(power);
    }

    public void increment(){
            motorPower += 0.05;
        }

    public void decrement(){
        motorPower -= 0.05;
    }

    }

