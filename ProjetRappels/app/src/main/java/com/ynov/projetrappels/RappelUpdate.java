package com.ynov.projetrappels;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RappelUpdate extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public DatabaseReference firebasedb;

    public String strNomUpdate;
    public String strHeureUpdate;
    public String strDateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rappel_update);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference("rappels");

        //Récupération des éléments xml
            //EditText rappel_update
            EditText etNomRappel = findViewById(R.id.etNomRappel);

            //TextView rappel_update
            TextView tvHeureSelected = findViewById(R.id.tvHeureSelected);
            TextView tvDateSelected = findViewById(R.id.tvDateSelected);

            //Button rappel_update
            Button btnRetrieveData = findViewById(R.id.btnRetrieveData);
            Button btnHeureSelect = findViewById(R.id.btnHeureSelect);
            Button btnDateSelect = findViewById(R.id.btnDateSelect);

            //Button rappel_update Validation du formulaire
            FloatingActionButton btnRappelUpdate = findViewById(R.id.btnRappelUpdate);

        //Button Récupération du rappel
        btnRetrieveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strNomUpdate = etNomRappel.getText().toString();

                if(TextUtils.isEmpty(strNomUpdate)){
                    Toast.makeText(RappelUpdate.this, "Un champ à remplir est vide", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RappelUpdate.this, "Information Récupérée !", Toast.LENGTH_SHORT).show();
                    btnRetrieveData.setVisibility(View.INVISIBLE);
                    firebasedb = FirebaseDatabase.getInstance().getReference("rappels").child(strNomUpdate);
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
            }
        });

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

        //Button Modification du rappel
        btnRappelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNomUpdate = etNomRappel.getText().toString();
                strHeureUpdate = tvHeureSelected.getText().toString();
                strDateUpdate = tvDateSelected.getText().toString();

                if(TextUtils.isEmpty(strNomUpdate) || strHeureUpdate.equals("Heure") || strDateUpdate.equals("Date")){
                    Toast.makeText(RappelUpdate.this, "Un des champs à remplir est vide", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RappelUpdate.this, "Rappel Modifié !", Toast.LENGTH_SHORT).show();
                    btnRappelUpdate.setVisibility(View.INVISIBLE);

                    //Appel a la fonction qui envoie les données a Firebase
                    updateRappel(strNomUpdate);
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
        firebasedb.child("heure").setValue(strHeureUpdate);
        firebasedb.child("date").setValue(strDateUpdate);
    }
}