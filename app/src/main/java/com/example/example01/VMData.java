package com.example.example01;

//데이터 class
class VMData {
    private String Provider;
    private String State;
    private String Created;
    private String Name;
    private String Zonename;
    private String Templatename;

    public VMData() {

    }

    public VMData(String Provider, String State, String Created, String Name, String Templatename, String Zonename) {
        this.Provider = Provider;
        this.State = State;
        this.Created = Created;
        this.Name = Name;
        this.Templatename = Templatename;
        this.Zonename = Zonename;
    }

    public String getState() {
        return State;
    }

    public String getCreated() {
        return Created;
    }

    public String getName() {
        return Name;
    }

    public String getProvider() {
        return Provider;
    }

    public String getZonename() {
        return Zonename;
    }

    public String getTemplatename() {
        return Templatename;
    }
}