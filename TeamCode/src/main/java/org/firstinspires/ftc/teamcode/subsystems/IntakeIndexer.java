package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeIndexer {

        private DcMotor indexer;
        private DcMotor intake;

        boolean indexerFlag;
        boolean intakeFlag;



        public IntakeIndexer(HardwareMap hardwareMap) {
            // "intakeMotor" must match the name in your Driver Station config
            intake = hardwareMap.get(DcMotor.class, "intake");
            indexer = hardwareMap.get(DcMotor.class,"indexer");

            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            indexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        }

        public void spin(boolean buttonPressed) {
            if (buttonPressed) {
                indexerFlag =!indexerFlag;
                intakeFlag =!intakeFlag;
                if (indexerFlag && intakeFlag) {
                    intake.setPower(1.0);   // full speed; lower it or use -1.0 to reverse
                    indexer.setPower(-1.0);
                }
                else{
                    intake.setPower(0);
                    indexer.setPower(0);
                }

            }
        }

        public void outtake(boolean outtakeButton){
            if (outtakeButton) {
                indexerFlag =!indexerFlag;
                intakeFlag =!intakeFlag;
                if (indexerFlag && intakeFlag) {
                    intake.setPower(-1.0);   // full speed; lower it or use -1.0 to reverse
                    indexer.setPower(1.0);
                }
                else{
                    intake.setPower(0);
                    indexer.setPower(0);
                }

            }
        }

        public void setiiPower(double power){
            intake.setPower(power);
            indexer.setPower(power);
        }
    }




