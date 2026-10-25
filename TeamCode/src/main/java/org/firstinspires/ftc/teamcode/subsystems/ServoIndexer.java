package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ServoIndexer {

    private CRServo servo;
    boolean servoFlag;

    public ServoIndexer(HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, "servo");


    }

    public void spinServo(boolean buttonServo) {
        if (buttonServo) {
            servoFlag = !servoFlag;

            if (servoFlag) {
                servo.setPower(1.0);

            } else {
                servo.setPower(0);

            }

        }
    }

    public void setServoPower(double power){
        servo.setPower(power);

    }
}