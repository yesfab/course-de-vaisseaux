/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.codingdojo.students;


import fr.rphstudio.codingdojo.game.Pod;
import fr.rphstudio.codingdojo.game.PodPlugIn;

/**
 *
 * @author Romuald GRIGNON
 */
public class Student13 extends PodPlugIn {
    public Student13(Pod p){
        super(p);
    }
    
    //-------------------------------------------------------
    // DECLARE YOUR OWN VARIABLES AND FUNCTIONS HERE
    boolean charging = false;
    boolean useboost = false;

    float acc = 0.53f;
    float minBat = 21;
    float maxBat = 93;

    //float frein = -1.0f;

    float maxBoost = 100;

    public boolean updateChargingMode2(float minBat, float maxBat){

        float battery = getShipBatteryLevel();
        if(getShipBatteryLevel() <= minBat && !charging){
            charging = true;
        }
        if(getShipBatteryLevel() >= maxBat && charging){
            charging = false;
        }

        return charging;
    }

    public boolean activateBoost(float maxBoost){

        float boost = getShipBoostLevel();
        if(getShipBoostLevel() == maxBoost) {
            useboost = true;
            useBoost();
        }
        return useboost;
    }

    public int getNextChargingCheckPointIndex(){
        for(int checkPointIndex = 0; checkPointIndex < getNbRaceCheckPoints(); checkPointIndex++){
            if(isCheckPointCharging(checkPointIndex)){
                return checkPointIndex;
            }
        }
        return 0;
    }

    public int getNextCheckPointIndex(boolean needCharging){
        if(needCharging){
            return getNextChargingCheckPointIndex();
        }else{
            return getNextCheckPointIndex();
        }
    }

    public void turnToAngle2(float angle2){
        float shipAngleX = getShipAngle();
        float angle = getRelativeAngleDifference(shipAngleX, angle2);

        turn(angle);
    }

    public void turnTowardPosition2(float x, float y){

        float shipX = getShipPositionX();
        float shipY = getShipPositionY();

        float angle = getAbsoluteAngleFromPositions(shipX, shipY, x, y);

        turnToAngle2(angle);
    }


    public void moveAndRecharge2(float acc, float minBat, float maxBat){
        boolean needCharging = updateChargingMode2(minBat, maxBat);
        int nextCheckPointIndex = getNextCheckPointIndex(needCharging);

        float x = getCheckPointX(nextCheckPointIndex);
        float y = getCheckPointY(nextCheckPointIndex);

        turnTowardPosition2(x,y);
        accelerateOrBrake(acc);
    }



    @Override
    public float getAbsoluteAngleFromPositions(float x1, float y1, float x2, float y2){

        return super.getAbsoluteAngleFromPositions(x1, y1, x2, y2);


    }

    //niveau de code 6
    void moveAndRecharge2(){
        float charge = getShipBatteryLevel();
        float x1 = getCheckPointX(getNextCheckPointIndex());
        float y1 = getCheckPointY(getNextCheckPointIndex());
        float x2 = getCheckPointX(getFirstChargingCheckPointIndex());
        float y2 = getCheckPointY(getFirstChargingCheckPointIndex());
        float x3 = getShipPositionX();
        float y3 = getShipPositionY();

        if (charge<37){
            //go to charging CP
            turnTowardPosition(x2,y2);
            if(x2==x3 && y2==y3 && charge<60){
                accelerateOrBrake(-1f);
            }else {
                accelerateOrBrake(0.50f);
            }
        }else{
            //continue the race
            turnTowardPosition(x1,y1);
            accelerateOrBrake(0.70f);
        }
    }
    
    // END OF VARIABLES/FUNCTIONS AREA
    //-------------------------------------------------------
    
    @Override
    public void process(int delta)
    {   
        //-------------------------------------------------------
        // WRITE YOUR OWN CODE HERE
        
        setPlayerName("13");
        selectShip(18);
        setPlayerColor(255,0,0,220);

        moveAndRecharge2(acc, minBat, maxBat);
        updateChargingMode2(minBat, maxBat);
        activateBoost(maxBoost);


        // END OF CODE AREA
        //-------------------------------------------------------
    }
    
}
