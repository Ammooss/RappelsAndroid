package com.ynov.projetrappels;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Rappel {
    public String nom;
    public String heure;
    public String date;

    public Rappel () {

    }

    public Rappel (String nom, String heure, String date) {
        this.nom = nom;
        this.heure = heure;
        this.date = date;
    }
}