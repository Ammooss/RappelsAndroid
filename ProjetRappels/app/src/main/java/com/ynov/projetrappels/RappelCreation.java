package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RappelCreation extends AppCompatActivity {

    private DatabaseReference firebasedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rappel_creation);

        FloatingActionButton btnRappelCreer = findViewById(R.id.btnRappelCreate);
        EditText etNomCreer = findViewById(R.id.etNomRappel);
        EditText etHeureCreer = findViewById(R.id.etHeureCreate);
        EditText etDateCreer = findViewById(R.id.etDateCreate);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference();

        //Bouton changement d'activity
        btnRappelCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNomCreer = etNomCreer.getText().toString();
                String strHeureCreer = etHeureCreer.getText().toString();
                String strDateCreer = etDateCreer.getText().toString();

                creeRappel(strNomCreer, strHeureCreer, strDateCreer);
            }
        });
    }

    //Firebase ajout Rappel
    private void creeRappel (String nom, String heure, String date) {
        Rappel rappel = new Rappel (nom, heure, date);
        firebasedb.child("rappels").child(nom).setValue(rappel);
        //firebasedb.child("rappels").child(id).child("titre").setValue("f");
    }
}