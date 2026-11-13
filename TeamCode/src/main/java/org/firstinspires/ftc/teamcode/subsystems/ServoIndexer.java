package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoIndexer {

    private CRServo servo;
    private Servo servo2;

    private Servo servo1;

    boolean CRservoFlag;
    boolean servoFlag;



    public ServoIndexer(HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, "servo");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo1 = hardwareMap.get(Servo.class, "servo1");

        servo1.setDirection(Servo.Direction.REVERSE);
    }


    public void spinServo(boolean buttonCRServo) {
        if (buttonCRServo) {
            CRservoFlag = !CRservoFlag;

            if (CRservoFlag) {
                servo.setPower(1.0);

            } else {
                servo.setPower(-1.0);

            }

        }
    }


    public void rotateServo(boolean buttonServo) {
        if (buttonServo) {
            servoFlag = !servoFlag;

            if (servoFlag) {
                servo2.setPosition(0.95);
                servo1.setPosition(0.95);

            } else {
                servo2.setPosition(0.45);
                servo1.setPosition(0.45);

            }

        }
    }

    public void rotateServo(double angle){
        servo1.setPosition(angle);
        servo2.setPosition(angle);
    }



    public void setServoPower(double power) {
        servo.setPower(power);

    }

    public void setServoAngle(double angle) {
        servo2.setPosition(angle);
        servo1.setPosition(angle);

    }
}

