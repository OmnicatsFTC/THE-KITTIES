package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.Range;
public class shooterPID {



        private DcMotorEx shooterLeft;
        private DcMotorEx shooterRight;
        private VoltageSensor battery;


        boolean shooterFlag = false;
        double targetRPM = 3000;
        double power = 0;


        // TUNING: change these
        public double kV = 0.000175;  // 1 / (RPM at full power)
        public double kP = 0.001;     // raise for faster recovery, lower if it wobbles


        public shooterPID(HardwareMap hardwareMap) {
            shooterLeft = hardwareMap.get(DcMotorEx.class, "shooterLeft");
            shooterRight = hardwareMap.get(DcMotorEx.class, "shooterRight");
            battery = hardwareMap.voltageSensor.iterator().next();


            shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }


        // Toggle on/off (same as before)








        // Call this EVERY loop
        public void spin( boolean buttonPressed) {
            if (buttonPressed){
                shooterFlag = !shooterFlag;
            }
            if (shooterFlag) {
                double error = targetRPM - getRPM();
                power = (kV * targetRPM + kP * error) * (12.0 / battery.getVoltage());
                power = Range.clip(power, 0, 1);
            } else {
                power = 0;
            }
            shooterLeft.setPower(power);
            shooterRight.setPower(power);
        }


        // Flywheel speed (28 ticks per rev for goBILDA/REV motor encoders)
        public double getRPM() {
            return Math.abs(shooterLeft.getVelocity()) / 28.0 * 60.0;
        }


        // True when at speed, use this to decide when to feed
        public boolean isReady() {
            return shooterFlag && Math.abs(targetRPM - getRPM()) < 80;
        }


        public void increment() { targetRPM += 100; }
        public void decrement() { targetRPM -= 100; }
        public void kPUp()      { kP *= 1.25; }
        public void kPDown()    { kP /= 1.25; }


        public double getTargetRPM() { return targetRPM; }
        public double getPower()     { return power; }
    }









