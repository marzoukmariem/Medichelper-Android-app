package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Accueil_DocteurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil__docteur);

        Button btnRecette = (Button) findViewById(R.id.btnRecette);
        Button btnPatients = (Button) findViewById(R.id.btnPatients);
        Button btnRendezVous = (Button) findViewById(R.id.btnRendezVous);

        btnRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(Accueil_DocteurActivity.this, Statistique_DocteurActivity.class));
            }
        });

        btnPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accueil_DocteurActivity.this, Patients_docteurActivity.class));
            }
        });
        btnRendezVous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accueil_DocteurActivity.this, ListeRendezVous_DocteurActivity.class));
            }
        });



    }
}
