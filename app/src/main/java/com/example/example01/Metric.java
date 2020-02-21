package com.example.example01;

import java.util.HashMap;

public class Metric {
    String vmName;

    HashMap<String, Float> data;

    public Metric(String vmName, HashMap<String, Float> data){
        this.vmName = vmName;
        this.data = data;
    }

    public String getVmName() {
        return vmName;
    }

    public HashMap<String, Float> getData() {
        return data;
    }

}
