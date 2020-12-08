package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference firebasedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnPlusRappel = findViewById(R.id.btnCreateRappel);

        //Bouton changement d'activity
        btnPlusRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RappelCreation.class);
                startActivityForResult(intent, 48);
            }
        });

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference();

        String id = "02";
        String titre = "DEBOUT";

        creeRappel(id, titre);
    }

    //Firebase ajout Rappel
    public void creeRappel (String id, String titre) {
        Rappel rappel = new Rappel (titre);
        firebasedb.child("rappels").child(id).setValue(rappel);
        //firebasedb.child("rappels").child(id).child("titre").setValue("f");
    }

}