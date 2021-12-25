package com.allantoledo.remoteinputclient;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GestureController implements SensorEventListener {

    private boolean isActivated = false;
    InputController input;
    ArrayList<Integer> axisXValues;
    ArrayList<Integer> axisZValues;
    int axisXAverage = 0;
    int axisZAverage = 0;
    int coefficientX = 20;
    int coefficientZ = 20;

    public GestureController(InputController input){
        this.input = input;
        axisXValues = new ArrayList<>();
        axisXValues.add(0);
        axisZValues = new ArrayList<>();
        axisZValues.add(0);
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
        input.resetMove(axisXAverage, axisZAverage);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        axisXValues.add(Math.round(event.values[2] * -100 * coefficientX));
        //float axisY = (float) Math.round(event.values[1] * 100);
        axisZValues.add(Math.round(event.values[0] * -100 * coefficientZ));
        if(axisXValues.size() > 10)
            axisXValues.remove(0);

        if(axisZValues.size() > 10)
            axisZValues.remove(0);

        axisXAverage = getAverage(axisXValues);
        Log.v("axisX", String.valueOf(axisXAverage));
        axisZAverage = getAverage(axisZValues);
        Log.v("axisZ", String.valueOf(axisZAverage));

        if(!isActivated)
            return;

        input.moveTouch(axisXAverage, axisZAverage);

    }

    public int getAverage(@NonNull ArrayList<Integer> list){
        return list.stream().mapToInt(integer -> integer).sum() / list.size();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
