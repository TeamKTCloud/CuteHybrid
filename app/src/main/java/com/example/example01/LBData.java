package com.example.example01;

//데이터 class
class LBData {
    private String Provider;
    private String State;
    private String Serviceip;
    private String Name;
    private String Zonename;

    public LBData() {

    }

    public LBData(String State, String Name, String Zonename, String Serviceip, String Provider) {
        this.State = State;
        this.Name = Name;
        this.Zonename = Zonename;
        this.Serviceip = Serviceip;
        this.Provider = Provider;
    }

    public String getState() {
        return State;
    }

    public String getServiceip() {
        return Serviceip;
    }

    public String getName() {
        return Name;
    }


    public String getZonename() {
        return Zonename;
    }

    public String getProvider() {
        return Provider;
    }
}