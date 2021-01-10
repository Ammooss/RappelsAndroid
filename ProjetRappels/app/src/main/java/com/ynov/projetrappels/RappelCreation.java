package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RappelCreation extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public DatabaseReference firebasedb;

    public String strNomCreer;
    public String strHeureCreer;
    public String strDateCreer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rappel_creation);
        
        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference();

        //Récupération des éléments xml
            //EditText rappel_creation
            EditText etNomRappel = findViewById(R.id.etNomRappel);

            //TextView rappel_creation
            TextView tvHeureSelected = findViewById(R.id.tvHeureSelected);
            TextView tvDateSelected = findViewById(R.id.tvDateSelected);

            //Button rappel_creation
            Button btnHeureSelect = findViewById(R.id.btnHeureSelect);
            Button btnDateSelect = findViewById(R.id.btnDateSelect);

            //Button rappel_creation Validation du formulaire
            FloatingActionButton btnRappelCreer = findViewById(R.id.btnRappelCreate);

        //Button Selection Heure et Date
        btnHeureSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        btnDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        //Button Creation du rappel
        btnRappelCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNomCreer = etNomRappel.getText().toString();
                strHeureCreer = tvHeureSelected.getText().toString();
                strDateCreer = tvDateSelected.getText().toString();

                if(TextUtils.isEmpty(strNomCreer) || strHeureCreer.equals("Heure") || strDateCreer.equals("Date")){
                    Toast.makeText(RappelCreation.this, "Un des champs à remplir est vide", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RappelCreation.this, "Rappel Créé !", Toast.LENGTH_SHORT).show();
                    btnRappelCreer.setVisibility(View.INVISIBLE);

                    //Appel a la fonction qui envoie les données a Firebase
                    creeRappel(strNomCreer, strHeureCreer, strDateCreer);
                }
            }
        });
    }

    //TimePicker
    @Override
    public void onTimeSet(android.widget.TimePicker view, int heure, int minute) {
        TextView tvHeureSelected = findViewById(R.id.tvHeureSelected);
        tvHeureSelected.setText(heure + ":" + minute);
    }

    //DatePicker
    @Override
    public void onDateSet(android.widget.DatePicker view, int annee, int mois, int jour) {
        TextView tvDateSelected = findViewById(R.id.tvDateSelected);
        tvDateSelected.setText(jour + "/" + mois + "/" + annee);
    }

    //Firebase Ajout du Rappel
    private void creeRappel (String nom, String heure, String date) {
        Rappel rappel = new Rappel (nom, heure, date);
        firebasedb.child("rappels").child(nom).setValue(rappel);
    }
}
