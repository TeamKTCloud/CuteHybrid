package com.example.example01;

//데이터 class
public class PointValueData {
    float Time;
    float Average;

    public PointValueData() {

    }

    public PointValueData(float Time, float Average) {
        this.Time =Time;
        this.Average = Average;

    }

    public float getTime() {
        return Time;
    }

    public float getAverage() {
        return Average;
    }
}