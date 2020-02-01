package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.esprit.intro.miniprojet.ActivityLogin.consultations;

public class AjoutConsultation_DocteurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_consultation__docteur);

        Button btnConfirmer = (Button) findViewById(R.id.btnConfirmer);
        final EditText editTextTitre=(EditText) findViewById(R.id.editTextTitre);
        final EditText editTextDate=(EditText) findViewById(R.id.editTextDate);
        final EditText editTextNotes=(EditText) findViewById(R.id.editTextNotes);


        btnConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AjoutConsultation_DocteurActivity.this, ProfilPatient_DocteurActivity.class));
                Consultation c =new Consultation(1,editTextTitre.getText().toString(),editTextDate.getText().toString(),editTextNotes.getText().toString(),1);
                consultations.add(c);
            }
        });


    }
}
