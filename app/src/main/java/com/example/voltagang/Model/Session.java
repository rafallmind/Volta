package com.example.voltagang.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Session {

    private String date;
    private String temps;
    private String ressenti;

    private List<Session> session = new ArrayList<>();


    public Session(String date, String temps, String ressenti) {
        this.date = date;
        this.temps = temps;
        this.ressenti = ressenti;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getRessenti() {
        return ressenti;
    }

    public void setRessenti(String ressenti) {
        this.ressenti = ressenti;
    }
}
