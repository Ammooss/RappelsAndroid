package com.ynov.projetrappels;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Rappel {
    public String titre;

    public Rappel () {

    }
    public Rappel (String titre) {
        this.titre = titre;
    }
}