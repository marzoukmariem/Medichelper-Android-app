package com.esprit.intro.miniprojet;

import
        android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AcceuilSecretaire extends AppCompatActivity {
    EditText login, nom, prenom, password, cin, tel;
    final String ip = "192.168.43.139";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_secretaire);

        Button inscrire = (Button) findViewById(R.id.inscrire);
        login = (EditText) findViewById(R.id.login);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenommedecin);
        cin = (EditText) findViewById(R.id.info);
        password = (EditText)findViewById(R.id.password);
        tel = (EditText) findViewById(R.id.tel);
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        inscrire.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String logintxt = login.getText().toString();
                final String passwordtxt = password.getText().toString();
                final String nomtxt = nom.getText().toString();
                final String prenomtxt = prenom.getText().toString();
                final String infotxt = cin.getText().toString();
                final String numteltxt = tel.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + ":18080/Projet-android-web/rest/Patient/ajouter/" + logintxt + "/" + passwordtxt + "/" + nomtxt + "/" + prenomtxt + "/" + infotxt + "/" + numteltxt,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                System.out.println("it works");
                                Toast.makeText(getApplicationContext(), "Patient bien ajoutée",
                                        Toast.LENGTH_LONG).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");

                        System.out.println("hello " + nomtxt);

                    }
                });

                queue.add(stringRequest);


            }
        });




    }
}
