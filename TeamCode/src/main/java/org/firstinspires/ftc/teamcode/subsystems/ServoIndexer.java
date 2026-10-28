package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoIndexer {

    private CRServo servo;
    private Servo servo2;

    boolean CRservoFlag;
    boolean servoFlag;

    public ServoIndexer(HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, "servo");
        servo2 = hardwareMap.get(Servo.class,"servo2");


    }



    public void spinServo(boolean buttonCRServo) {
        if (buttonCRServo) {
            CRservoFlag = !CRservoFlag;

            if (CRservoFlag) {
                servo.setPower(1.0);

            } else {
                servo.setPower(0);

            }

        }
    }


    public void rotateServo(boolean buttonServo) {
        if (buttonServo) {
            servoFlag = !servoFlag;

            if (servoFlag) {
                servo2.setPosition(0.5);

            } else {
                servo.setPower(0);

            }

        }
    }

    public void setServoPower(double power){
        servo.setPower(power);

    }
}