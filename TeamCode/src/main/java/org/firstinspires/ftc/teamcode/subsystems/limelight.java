

package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.util.List;


// This code is written assuming that the right-side motors need to be reversed for the robot to drive forward.
// When you first test your robot, if it moves backward when you push the left stick forward, then you must flip the direction of all 4 motors (see code below).
// Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
// Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list

public class limelight {

    // Declare OpMode members for each of the 4 motors.


    private Limelight3A limelight;
    private static final int DESIRED_TAG_ID = -1;
    final double TURN_GAIN   =  0.01  ;
    final double MAX_AUTO_TURN  = 0.3;


    public limelight(HardwareMap hardwareMap) {



        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);
        limelight.start();


    }
    public void getAprilTag(boolean buttonPressed3, double turn){
        boolean targetFound = false;

        LLResult result = limelight.getLatestResult();

        LLResultTypes.FiducialResult desiredTag = null;

        if (result != null && result.isValid()) {

            List<LLResultTypes.FiducialResult> fiducials =
                    result.getFiducialResults();

            for (LLResultTypes.FiducialResult tag : fiducials) {

                if (DESIRED_TAG_ID == -1 ||
                        tag.getFiducialId() == DESIRED_TAG_ID) {

                    desiredTag = tag;
                    targetFound = true;
                    break;
                }
            }
        }

        // Tell the driver what we see, and what to do.
        if (targetFound) {
            telemetry.addData("\n>","HOLD Left-Bumper to Align to Target\n");
            telemetry.addData("Found Tag ID", desiredTag.getFiducialId());
            telemetry.addData("Target X (tx)", desiredTag.getTargetXDegrees());
            telemetry.addData("Target Y (ty)", desiredTag.getTargetYDegrees());
        } else {
            telemetry.addData("\n>","Drive using joysticks to find valid target\n");
        }

        // If Left Bumper is being pressed, AND we have found the desired target, turn to align to it automatically.
        if (buttonPressed3 && targetFound) {

            // tx is how many degrees off-center (left/right) the tag is in the camera's view.
            // Positive tx = tag is to the right. moveRobot() treats positive yaw as
            // counter-clockwise (turn left), so we negate tx to turn right toward the tag.
            // If the robot turns the wrong way when you test this, flip this sign.
            double tx = desiredTag.getTargetXDegrees();

            turn = Range.clip(
                    -tx * TURN_GAIN,
                    -MAX_AUTO_TURN,
                    MAX_AUTO_TURN
            );
    }

        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.




        // run until the end


            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.


            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.



        }





            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.

            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */

            // Send calculated power to wheels

        }

