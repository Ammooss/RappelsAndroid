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

    public ListView lvRappel;

    public DatabaseReference firebasedb;

    ArrayList<String> arrayListRappel = new ArrayList<>();

    ArrayAdapter<String> arrayAdapterRappel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupération des éléments xml
        //ListView activity_main
        lvRappel = findViewById(R.id.lvRappel);

        //Button activity_main
        FloatingActionButton btnPlusRappel = findViewById(R.id.btnCreateRappel);

        arrayAdapterRappel = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListRappel);

        lvRappel.setAdapter(arrayAdapterRappel);

        //Gestion base de donnée Firebase
        firebasedb = FirebaseDatabase.getInstance().getReference("rappels");
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

        //Bouton changement d'activity
        btnPlusRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RappelCreation.class);
                startActivity(intent);
            }
        });
    }
}