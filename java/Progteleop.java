 package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="Basic: Omni Linear OpMode", group="Linear Opmode")

public class Progteleop extends LinearOpMode {

    
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor garra; 
    private CRServo servomotor;
    
    @Override
    public void runOpMode() {

        
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        garra = hardwareMap.get(DcMotor.class, "garra");
        servomotor = hardwareMap.get(CRServo.class, "servomotor");
       
        servomotor.setDirection(DcMotorSimple.Direction.REVERSE);
        garra.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

       
        while (opModeIsActive()) {
            double max;

            
            double axial   = gamepad1.right_stick_x;  
            double lateral =  -gamepad1.left_stick_x;
            double yaw     =  gamepad1.left_stick_y;

            
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;
            

            
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

           
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);
            
            if (gamepad1.a) {
          garra.setPower(1);
        } else {
          garra.setPower(0);
        }
        if (gamepad1.b) {
          garra.setPower(-1);
        } else {
          garra.setPower(0);
        }
        
         if (gamepad1.y) {
        servomotor.setPower(1);
      } else {
        servomotor.setPower(0);
      }
      if (gamepad1.x) {
        servomotor.setPower(-1);
      } else {
        servomotor.setPower(0);
      }
        
        

          
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.update();
        }
    }}
