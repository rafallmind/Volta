package com.example.voltagang.Model;

public class User {

    private String pseudo, sexe;
    private int age;

    public User(String pseudo, String sexe, int age) {
        this.pseudo = pseudo;
        this.sexe = sexe;
        this.age = age;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
