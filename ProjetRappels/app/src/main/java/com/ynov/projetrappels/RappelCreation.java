package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RappelCreation extends AppCompatActivity {

    public DatabaseReference firebasedb;

    public String strNomCreer;
    public String strHeureCreer;
    public String strDateCreer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rappel_creation);

        //EditText rappel_creation
        EditText etNomCreer = findViewById(R.id.etNomRappel);
        EditText etHeureCreer = findViewById(R.id.etHeureCreate);
        EditText etDateCreer = findViewById(R.id.etDateCreate);

        //TextView rappel_creation
        TextView tvRappelCreate = findViewById(R.id.tvRappelCreate);
        TextView tvChampsRemplir = findViewById(R.id.tvChampsRemplir);

        //Button rappel_creation Validation du formulaire
        FloatingActionButton btnRappelCreer = findViewById(R.id.btnRappelCreate);

        //TextView rappel_creation Affichage si erreur ou Ok
        tvRappelCreate.setVisibility(View.INVISIBLE);
        tvChampsRemplir.setVisibility(View.INVISIBLE);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference();

        //Bouton creation du rappel
        btnRappelCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNomCreer = etNomCreer.getText().toString();
                strHeureCreer = etHeureCreer.getText().toString();
                strDateCreer = etDateCreer.getText().toString();

                if(strNomCreer.equals("") || strHeureCreer.equals("") || strDateCreer.equals("")){
                    tvChampsRemplir.setVisibility(View.VISIBLE);
                } else {
                    //Appel a la fonction qui envoie les données a Firebase
                    creeRappel(strNomCreer, strHeureCreer, strDateCreer);

                    btnRappelCreer.setVisibility(View.INVISIBLE);
                    tvChampsRemplir.setVisibility(View.INVISIBLE);
                    tvRappelCreate.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Firebase ajout Rappel
    private void creeRappel (String nom, String heure, String date) {
        Rappel rappel = new Rappel (nom, heure, date);
        firebasedb.child("rappels").child(nom).setValue(rappel);

        //firebasedb.child("rappels").child(id).child("titre").setValue("Ce qui doit être modifier");
    }
}
