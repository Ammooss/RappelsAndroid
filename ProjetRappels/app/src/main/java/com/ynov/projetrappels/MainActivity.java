package com.ynov.projetrappels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ynov.projetrappels.adapter.RappelAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnPlusRappel = findViewById(R.id.btnCreateRappel);

        //liste de rappels
        List<Rappel> tableauRappel = new ArrayList<>();
        tableauRappel.add(new Rappel("test", "15:20", "20/12/2020"));
        tableauRappel.add(new Rappel("test2", "10:00", "25/12/2020"));

        //get list view
        ListView list = (ListView)findViewById(R.id.ListView);
        list.setAdapter(new RappelAdapter(this,tableauRappel));

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