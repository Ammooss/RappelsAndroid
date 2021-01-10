package com.ynov.projetrappels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public DatabaseReference firebasedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference("rappels");

        //Récupération des éléments xml
            //ListView activity_main
            ListView lvRappel = findViewById(R.id.lvRappel);

            //Button activity_main
            FloatingActionButton btnCreateRappel = findViewById(R.id.btnCreateRappel);
            FloatingActionButton btnUpdateRappel = findViewById(R.id.btnUpdateRappel);
            FloatingActionButton btnDeleteRappel = findViewById(R.id.btnDeleteRappel);

            //Array pour récupérer les données de firebase
            ArrayList<String> arrayListRappel = new ArrayList<>();
            ArrayAdapter<String> arrayAdapterRappel;
            arrayAdapterRappel = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListRappel);

            //List View affiche les données de firebase
            lvRappel.setAdapter(arrayAdapterRappel);

        //Affichage des données dans la ListView
        firebasedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(Rappel.class).toString();
                arrayListRappel.add(value);
                arrayAdapterRappel.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Button Créer Rappel
        btnCreateRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RappelCreation.class);
                startActivity(intent);
            }
        });

        //Button Update Rappel
        btnUpdateRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RappelUpdate.class);
                startActivity(intent);
            }
        });

        //Button Delete Rappel
        btnDeleteRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RappelDelete.class);
                startActivity(intent);
            }
        });
    }
}