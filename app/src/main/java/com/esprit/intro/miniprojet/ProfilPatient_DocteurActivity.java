package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import static com.esprit.intro.miniprojet.ActivityLogin.consultations;
import static com.esprit.intro.miniprojet.ActivityLogin.contacts;

public class ProfilPatient_DocteurActivity extends AppCompatActivity {
    ListView lstconsultation;
    static int idChosenPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_patient__docteur);

        Button btnNouvelleConsultation = (Button) findViewById(R.id.btnNouvelleConsultation);

        lstconsultation = (ListView) findViewById(R.id.lstconsultaion_docteur);

        idChosenPatient =  getIntent().getIntExtra("nom",1);

        btnNouvelleConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilPatient_DocteurActivity.this, AjoutConsultation_DocteurActivity.class));
            }
        });

        ConsultationAdapter adapter = new ConsultationAdapter(this, R.layout.patients_template__docteur, consultations);

        lstconsultation.setAdapter(adapter);

    }
}
