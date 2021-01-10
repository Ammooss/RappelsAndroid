package com.ynov.projetrappels;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RappelDelete extends AppCompatActivity {

    public DatabaseReference firebasedb;

    public String strNomDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rappel_delete);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference("rappels");

        //Récupération des éléments xml
            //EditText rappel_delete
            EditText etNomRappel = findViewById(R.id.etNomRappel);

            FloatingActionButton btnRappelDelete = findViewById(R.id.btnRappelDelete);

        //Button Suppression du rappel
        btnRappelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strNomDelete = etNomRappel.getText().toString();
                firebasedb = FirebaseDatabase.getInstance().getReference("rappels").child(strNomDelete);

                if(TextUtils.isEmpty(strNomDelete)){
                    Toast.makeText(RappelDelete.this, "Un champ à remplir est vide", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RappelDelete.this, "Rappel Supprimé !", Toast.LENGTH_SHORT).show();
                    btnRappelDelete.setVisibility(View.INVISIBLE);

                    firebasedb.removeValue();
                }
            }
        });
    }
}