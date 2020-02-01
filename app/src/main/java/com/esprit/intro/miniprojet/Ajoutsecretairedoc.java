package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;

public class Ajoutsecretairedoc extends AppCompatActivity {
    EditText nom,prenom,cin,tel,login,password;
    boolean loginOk,nomOk,prenomOk,passwordOk,telOk,cinOk,adresseOk,dateOk;
    boolean containsDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_ajoutsecretairedoc);
        nom=(EditText)findViewById(R.id.nomsec);
        prenom  =(EditText)findViewById(R.id.prenomsec);
        cin=(EditText)findViewById(R.id.infosec);
        tel=(EditText)findViewById(R.id.telsec);
        login  =(EditText)findViewById(R.id.loginsec);
        password  =(EditText)findViewById(R.id.passwordsec);
        Button ajouter= (Button) findViewById(R.id.ajoutersec);





        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomtxt=nom.getText().toString().replace(" ","%20");
                String prenomtxt=prenom.getText().toString().replace(" ","%20");
                String infotxt= cin.getText().toString().replace(" ","%20");
                String teltxt=tel.getText().toString().replace(" ","%20");
                String logintxt=login.getText().toString().replace(" ","%20");
                String paswordtxt=password.getText().toString().replace(" ","%20");


                if (password.getText().toString().equals("")){
                    password.setError("enter mot de passe de secretaire");
                    password.requestFocus();
                    passwordOk=false;
                }else{
                    passwordOk=true;
                }


                final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                if (login.getText().toString().equals("")){
                    login.setError("enter login secretaire");
                    login.requestFocus();
                    loginOk=false;
                }else{
                    System.out.println("http://"+ActivityLogin.ip+"/medichelper/LoginVerif.php?login="+login.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/LoginVerif.php?login="+login.getText().toString(), new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length()==0) {
                                    loginOk=true;
                                }
                                else{
                                    login.setError("login existe deja");
                                    login.requestFocus();
                                    loginOk=false;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That didn't work!");
                            System.out.println(error.toString());
                        }
                    });

                    queue.add(stringRequest);

                    //loginOk=true;
                }


                if (tel.getText().toString().equals("")){
                    tel.setError("enter numero secretaire");
                    tel.requestFocus();
                    telOk=false;
                }
                else if (!android.text.TextUtils.isDigitsOnly(tel.getText().toString())){
                    tel.setError("le numero doit contenir que des chiffres");
                    tel.requestFocus();
                    telOk=false;
                }
                else{
                    telOk=true;
                }

                if (cin.getText().toString().equals("")){
                    cin.setError("enter infos secretaire ");
                    cin.requestFocus();
                    cinOk=false;
                }else{
                    cinOk=true;
                }

                if (prenom.getText().toString().equals("")){
                    prenom.setError("enter prenom secretaire");
                    prenom.requestFocus();
                    prenomOk=false;
                }
                else if(containsDigit(prenom.getText().toString())==true){
                    prenom.setError("nom ne doit pas contenir de chiffres");
                    prenom.requestFocus();
                    prenomOk=false;
                }
                else{
                    prenomOk=true;
                }


                if (nom.getText().toString().equals("")){
                    nom.setError("enter nom secretaire");
                    nom.requestFocus();
                    nomOk=false;
                }
                else if(containsDigit(nom.getText().toString())==true){
                    nom.setError("nom ne doit pas contenir de chiffres");
                    nom.requestFocus();
                    nomOk=false;
                }
                else{
                    nomOk=true;
                }

                if (nomOk&prenomOk&cinOk&telOk&loginOk&passwordOk) {


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/inserersecretaire.php?login=" + logintxt + "&password=" + paswordtxt + "&nom=" + nomtxt + "&numtel=" + teltxt + "&prenom=" + prenomtxt + "&info=" + infotxt + "&cab=" + ActivityLogin.patientConnecté.getCabinet() + "", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("it works");
                            System.out.print("http://" + ActivityLogin.ip + "/medichelper/inserersecretaire.php?login=" + logintxt + "&password=" + paswordtxt + "&nom=" + nomtxt + "&numtel=" + teltxt + "&prenom=" + prenomtxt + "&info=" + infotxt + "&cab=" + ActivityLogin.patientConnecté.getCabinet() + "");

                            Toast.makeText(getApplicationContext(), "Secretaire a été bien ajouté",
                                    Toast.LENGTH_LONG).show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "errur",
                                    Toast.LENGTH_LONG).show();
                            System.out.println("That didn't work!");


                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(Ajoutsecretairedoc.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    public final boolean containsDigit(String s) {
        containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }
}
