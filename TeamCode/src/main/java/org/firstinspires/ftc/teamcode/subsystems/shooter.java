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

    private boolean on = false;   // add this near your other fields at the top of the class

    public void toggle() {
        on = !on;
    }

    public void update() {
        if (on) {
            shooter.setPower(-0.79);
        } else {
            shooter.setPower(0);
        }
    }
    }


