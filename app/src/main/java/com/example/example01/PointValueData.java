package com.example.example01;

//데이터 class
public class PointValueData {
    String Time;
    String Average;
    String Provider;
    String Name;
    public PointValueData() {

    }

    public PointValueData(String Time, String Average, String Provider, String Name) {
        this.Time =Time;
        this.Average = Average;
        this.Provider = Provider;
        this.Name = Name;

    }

    public String getTime() {
        return Time;
    }

    public String getAverage() {
        return Average;
    }

    public String getName() {
        return Name;
    }

    public String getProvider() {
        return Provider;
    }
}