package com.example.batteryvisualization;

public class StatHolder {
    long screenTime;
    float batteryLevel;
    public StatHolder(long screenTime, float batteryLevel){
        screenTime = this.screenTime;
        batteryLevel = this.batteryLevel;


    }

    public long getTime(StatHolder s){
        return s.screenTime;
    }

    public float getBatteryLevel(StatHolder s){
        return s.batteryLevel;
    }

    public void aggregateTime(StatHolder s,long timeInMS){
        s.screenTime += timeInMS;
    }

    public void setBatteryLevel(StatHolder s, float batteryLvl){
        s.batteryLevel = batteryLvl;
    }

}
