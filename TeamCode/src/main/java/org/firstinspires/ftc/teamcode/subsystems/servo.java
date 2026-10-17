package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class servo {

    private CRServo servo;
    boolean servoFlag;

    public servo(HardwareMap hardwareMap) {
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
}