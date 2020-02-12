package com.example.example01;

import java.util.HashMap;
import java.util.Map;

class VMData {
    private String State;
    private String Created;

    public VMData() {

    }

    public VMData(String State, String Created, String CpuSpeed) {
        this.State = State;
        this.Created = Created;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("State", State);
        result.put("Created", Created);

        return result;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public void setCreated(String created) {
        Created = created;
    }



    public String getCreated() {
        return Created;
    }


}