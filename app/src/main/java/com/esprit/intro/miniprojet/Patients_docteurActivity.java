package com.esprit.intro.miniprojet;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import static com.esprit.intro.miniprojet.ActivityLogin.contacts;


public class Patients_docteurActivity extends AppCompatActivity {
    ListView lspatients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_docteur);

        ImageView imgSearch=(ImageView) findViewById(R.id.imgSearch);
        lspatients = (ListView) findViewById(R.id.lst_Patient_docteur);
      /*

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Patients_docteurActivity.this, ProfilPatient_DocteurActivity.class));
            }
        });


        PatientAdapter adapter= new PatientAdapter(getApplicationContext(),R.layout.patients_template__docteur,contacts);

        lspatients.setAdapter(adapter);
*/
    }
}
