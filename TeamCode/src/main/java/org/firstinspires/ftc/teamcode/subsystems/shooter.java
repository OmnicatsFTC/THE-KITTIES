package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooter {

        private DcMotor shooter;




        public shooter(HardwareMap hardwareMap) {
            // "intakeMotor" must match the name in your Driver Station config
            shooter = hardwareMap.get(DcMotor.class, "shooter");


            shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        }

        public void shoot(boolean buttonPressed2) {
            if (buttonPressed2) {
                shooter.setPower(1.0);   // full speed; lower it or use -1.0 to reverse

            } else {
                shooter.setPower(0);

            }
        }
    }


