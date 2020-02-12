package com.example.example01;

import java.util.HashMap;
import java.util.Map;

class VMData {
    private String State;
    private String Created;
    private String Name;
    private String Zonename;
    private String Templatename;

    public VMData() {

    }

    public VMData(String State, String Created, String Name, String Templatename, String Zonename) {
        this.State = State;
        this.Created = Created;
        this.Name = Name;
        this.Templatename = Templatename;
        this.Zonename = Zonename;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("State", State);
        result.put("Created", Created);
        result.put("Name", Name);
        result.put("Templatename", Templatename);
        result.put("Zonename", Zonename);


        return result;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
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

    public String getTemplatename() {
        return Templatename;
    }

    public void setTemplatename(String templatename) {
        Templatename = templatename;
    }
}