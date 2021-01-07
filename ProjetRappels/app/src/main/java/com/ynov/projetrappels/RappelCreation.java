package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        //Récupération des éléments xml
            //EditText rappel_creation
            EditText etNomCreer = findViewById(R.id.etNomRappel);

            //TextView rappel_creation
            TextView tvHeureSelected = findViewById(R.id.tvHeureSelected);
            TextView tvDateSelected = findViewById(R.id.tvDateSelected);
            TextView tvRappelCreate = findViewById(R.id.tvRappelCreate);
            TextView tvChampsRemplir = findViewById(R.id.tvChampsRemplir);

            //Button rappel_creation
            Button btnHeureSelect = findViewById(R.id.btnHeureSelect);
            Button btnDateSelect = findViewById(R.id.btnDateSelect);

            //Button rappel_creation Validation du formulaire
            FloatingActionButton btnRappelCreer = findViewById(R.id.btnRappelCreate);

            //TextView rappel_creation Affichage si Ok ou Erreur
            tvRappelCreate.setVisibility(View.INVISIBLE);
            tvChampsRemplir.setVisibility(View.INVISIBLE);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference();

        //Button Select Heure et Date
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

        //Bouton creation du rappel
        btnRappelCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNomCreer = etNomCreer.getText().toString();
                strHeureCreer = tvHeureSelected.getText().toString();
                strDateCreer = tvDateSelected.getText().toString();

                if(strNomCreer.equals("") || strHeureCreer.equals("Heure") || strDateCreer.equals("Date")){
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

    //Firebase ajout Rappel
    private void creeRappel (String nom, String heure, String date) {
        Rappel rappel = new Rappel (nom, heure, date);
        firebasedb.child("rappels").child(nom).setValue(rappel);

        //firebasedb.child("rappels").child(id).child("titre").setValue("Ce qui doit être modifier");
    }
}
