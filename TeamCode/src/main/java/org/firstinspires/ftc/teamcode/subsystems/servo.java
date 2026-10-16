package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class servo {

    private CRServo servo;
    boolean servoFlag;

    public servo(HardwareMap hardwareMap) {
        // These names must match your Driver Station configuration exactly
        servo = hardwareMap.get(CRServo.class, "servo");


    }

    public void spinServo(boolean buttonServo) {
        if (buttonServo) {
            servoFlag = !servoFlag;

            if (servoFlag) {
                servo.setPower(1.0);   // full speed; lower it or use -1.0 to reverse

            } else {
                servo.setPower(0);

            }

        }
    }
}