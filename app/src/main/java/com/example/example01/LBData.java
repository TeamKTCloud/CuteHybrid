package com.example.example01;

import java.util.HashMap;
import java.util.Map;

//데이터 class
class LBData {
    private String State;
    private String Serviceip;
    private String Name;
    private String Zonename;

    public LBData() {

    }

    public LBData(String State, String Name, String Zonename, String Serviceip) {
        this.State = State;
        this.Name = Name;
        this.Zonename = Zonename;
        this.Serviceip = Serviceip;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("State", State);
        result.put("Created", Serviceip);
        result.put("Name", Name);
        result.put("Zonename", Zonename);


        return result;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getServiceip() {
        return Serviceip;
    }

    public void setServiceip(String serviceip) {
        Serviceip = serviceip;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getZonename() {
        return Zonename;
    }

    public void setZonename(String zonename) {
        Zonename = zonename;
    }
}