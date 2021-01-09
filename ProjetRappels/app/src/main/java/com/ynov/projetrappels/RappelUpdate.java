package com.ynov.projetrappels;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RappelUpdate extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public DatabaseReference firebasedb;

    public String strNomRappel;
    public String strNomUpdate;
    public String strHeureUpdate;
    public String strDateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rappel_update);

        //Récupération des éléments xml
            //EditText rappel_update
            EditText etNomRappel = findViewById(R.id.etNomRappel);

            //TextView rappel_update
            TextView tvHeureSelected = findViewById(R.id.tvHeureSelected);
            TextView tvDateSelected = findViewById(R.id.tvDateSelected);
            TextView tvRappelUpdate = findViewById(R.id.tvRappelUpdate);
            TextView tvChampsRemplir = findViewById(R.id.tvChampsRemplir);

            //Button rappel_update
            Button btnRetrieveData = findViewById(R.id.btnRetrieveData);
            Button btnHeureSelect = findViewById(R.id.btnHeureSelect);
            Button btnDateSelect = findViewById(R.id.btnDateSelect);

            //Button rappel_update Validation du formulaire
            FloatingActionButton btnRappelUpdate = findViewById(R.id.btnRappelUpdate);

            //TextView rappel_update Affichage si Ok ou Erreur
            tvRappelUpdate.setVisibility(View.INVISIBLE);
            tvChampsRemplir.setVisibility(View.INVISIBLE);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference("rappels");

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

        //Button Récupération du rappel
        btnRetrieveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strNomRappel = etNomRappel.getText().toString();
                firebasedb = FirebaseDatabase.getInstance().getReference().child("rappels/"+strNomRappel);
                firebasedb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Rappel rappel = snapshot.getValue(Rappel.class);
                        strHeureUpdate = rappel.heure;
                        strDateUpdate = rappel.date;

                        tvHeureSelected.setText(strHeureUpdate);
                        tvDateSelected.setText(strDateUpdate);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //Button Modification du rappel
        btnRappelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNomUpdate = etNomRappel.getText().toString();
                strHeureUpdate = tvHeureSelected.getText().toString();
                strDateUpdate = tvDateSelected.getText().toString();

                if(strNomUpdate.equals("") || strHeureUpdate.equals("Heure") || strDateUpdate.equals("Date")){
                    tvChampsRemplir.setVisibility(View.VISIBLE);
                } else {
                    //Appel a la fonction qui envoie les données a Firebase
                    updateRappel(strNomUpdate);

                    btnRappelUpdate.setVisibility(View.INVISIBLE);
                    tvChampsRemplir.setVisibility(View.INVISIBLE);
                    tvRappelUpdate.setVisibility(View.VISIBLE);
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

    //Firebase Modification du Rappel
    private void updateRappel (String nom) {
        firebasedb.child(nom).child("heure").setValue(strHeureUpdate);
        firebasedb.child(nom).child("date").setValue(strDateUpdate);
    }
}