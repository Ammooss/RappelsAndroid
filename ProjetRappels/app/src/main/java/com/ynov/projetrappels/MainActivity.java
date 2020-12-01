package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference firebasedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebasedb = FirebaseDatabase.getInstance().getReference();

        String id = "02";
        String titre = "DEBOUT";

        creeRappel(id, titre);
    }

    public void creeRappel (String id, String titre) {
        Rappel rappel = new Rappel (titre);
        firebasedb.child("rappels").child(id).setValue(rappel);
        //firebasedb.child("rappels").child(id).child("titre").setValue("f");
    }
}