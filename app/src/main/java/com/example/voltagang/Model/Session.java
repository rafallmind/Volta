package com.example.voltagang.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Session {

    private Date date;
    private float temps;
    private int ressenti;

    private List<Session> session = new ArrayList<>();


    public Session(Date date, float temps, int ressenti) {
        this.date = date;
        this.temps = temps;
        this.ressenti = ressenti;
    }


    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session = session;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTemps() {
        return temps;
    }

    public void setTemps(float temps) {
        this.temps = temps;
    }

    public int getRessenti() {
        return ressenti;
    }

    public void setRessenti(int ressenti) {
        this.ressenti = ressenti;
    }
}
