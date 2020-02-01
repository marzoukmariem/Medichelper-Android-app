package com.esprit.intro.miniprojet;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ActivityLogin extends AppCompatActivity {

    static ArrayList<Patient> contacts = new ArrayList<>();
    static ArrayList<Consultation> consultations = new ArrayList<>();
    static ArrayList<RendezVous> rdvs = new ArrayList<>();
    final static  String ip = "192.168.43.139";
    static Patient1 patientConnecté ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref=getSharedPreferences("Myprefrences", Context.MODE_PRIVATE);
        final   SharedPreferences.Editor editor= pref.edit();
        String  motdepasse=pref.getString("username","");
        String  login=pref.getString("password","");
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Button b = (Button) findViewById(R.id.btnSeConnecter);
        final EditText etLogin=(EditText) findViewById(R.id.editTextLogin);
        final EditText etpassword=(EditText) findViewById(R.id.editTextPassword);
        final TextView btninscription=(TextView)  findViewById(R.id. tvInscription);
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final TextView btninscription1=(TextView)  findViewById(R.id. tvInscription1);

        btninscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, InscriptionPatient.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        btninscription1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, ajoucabinet.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });



        Patient p1=new Patient(1,"Anouar","BenOthman","17/05/1994",98259325,50026882,"10 rues oubai Grombalia");
        Patient p2=new Patient(2,"Meriem","Marzouk","25/03/1994",98259325,50026882,"10 rues oubai Grombalia");
        Patient p3=new Patient(3,"Yassine","Moslah","17/05/1994",98259325,50026882,"10 rues oubai Grombalia");
        Patient p4=new Patient(4,"Amal","Mabrouk","17/05/1994",98259325,50026882,"10 rues oubai Grombalia");
        contacts.add(p1);
        contacts.add(p2);
        contacts.add(p3);
        contacts.add(p4);


        Consultation c1 = new Consultation(1,"consultation1","05/11/2018","detail jfnkjn uqerg jkq erjkre gj nkngrkr bhge gqh hg q bkqg h qhu igque huherhge ii rqq r g rgqugr qreqhugrq",1);
        Consultation c2 = new Consultation(2,"consultation2","05/11/2018","detail jfnkjn uqerg jkq erjkre gj nkngrkr bhge gqh hg q bkqg h qhu igque huherhge ii rqq r g rgqugr qreqhugrq",2);
        consultations.add(c1);
        consultations.add(c2);









        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/login2.php?login="+etLogin.getText().toString()+"&password="+etpassword.getText().toString(),
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(String response) {
                                JSONArray jsonarray = null;
                                System.out.println(response);
                                try {
                                    jsonarray = new JSONArray(response);

                                 if (jsonarray.length()==0) {Toast.makeText(getApplicationContext(), "Vérifier vos informations!",
                                         Toast.LENGTH_LONG).show();}


                                 else{

                                     for (int i = 0; i < jsonarray.length(); i++) {

                                         JSONObject jsonobject = jsonarray.getJSONObject(i);
                                         String role = jsonobject.getString("Role_User");
                                         String app=jsonobject.getString("User_Approuve");
                                         System.out.print("this is app:  "+app);
                                         if (role.equals("medecin")) {
                                             if(app.equals("A") ) {
                                                 System.out.print(app);
                                                 editor.putString("username", etLogin.getText().toString());
                                                 editor.putString("password", etpassword.getText().toString());
                                                 editor.commit();
                                                 Long id = jsonobject.getLong("Id_User");
                                                 String login = jsonobject.getString("Login_User");
                                                 String nom = jsonobject.getString("Nom_User");
                                                 String numTel = jsonobject.getString("NumTel_User");
                                                 String password = jsonobject.getString("Password_User");
                                                 String prenom = jsonobject.getString("Prenom_User");
                                                 String role1 = jsonobject.getString("Role_User");
                                                 String adresse = jsonobject.getString("Adresse_Patient");
                                                 String cin = jsonobject.getString("Cin_Patient");

                                                 int idCabinet = jsonobject.getInt("FK_Cabinet_ID");

                                                 patientConnecté = new Patient1(id, login, password, role1, nom, prenom, numTel, cin, idCabinet);
                                                 startActivity(new Intent(ActivityLogin.this, SwipeMenuDocteur.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Votre compte n'est pas encore approuvé",
                                                         Toast.LENGTH_LONG).show();

                                             }


                                         } else if (role.equals("secretaire")) {

                                             editor.putString("username", etLogin.getText().toString());
                                             editor.putString("password", etpassword.getText().toString());
                                             editor.commit();
                                             Long id = jsonobject.getLong("Id_User");
                                             String login = jsonobject.getString("Login_User");
                                             String nom = jsonobject.getString("Nom_User");
                                             String numTel = jsonobject.getString("NumTel_User");
                                             String password = jsonobject.getString("Password_User");
                                             String prenom = jsonobject.getString("Prenom_User");
                                             String role1 = jsonobject.getString("Role_User");
                                             String adresse = jsonobject.getString("Adresse_Patient");
                                             String cin = jsonobject.getString("Cin_Patient");

                                             int idCabinet = jsonobject.getInt("FK_Cabinet_ID");

                                             patientConnecté = new Patient1(id, login, password, role1, nom, prenom, numTel, cin, idCabinet);

                                             startActivity(new Intent(ActivityLogin.this, secretaoreswipe.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                         }

                                         else if (role.equals("admin")) {
                                             Toast.makeText(getApplicationContext(), "hello admin",
                                                     Toast.LENGTH_LONG).show();



                                             startActivity(new Intent(ActivityLogin.this, adminacceuil.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));



                                         }




                                         else {
                                             editor.putString("username",etLogin.getText().toString());
                                             editor.putString("password",etpassword.getText().toString());
                                             editor.commit();

                                             Long id =jsonobject.getLong("Id_User");
                                             String dateNaissance = jsonobject.getString("DateNaissance_User");
                                             Date date1=new Date();
                                             try {

                                                  date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissance);
                                                 System.out.println(date1.toString());
                                             } catch (ParseException e) {
                                                 e.printStackTrace();

                                             }
                                             String login = jsonobject.getString("Login_User");
                                             String nom = jsonobject.getString("Nom_User");
                                             String numTel = jsonobject.getString("NumTel_User");
                                             String password = jsonobject.getString("Password_User");
                                             String prenom = jsonobject.getString("Prenom_User");
                                             String role1 = jsonobject.getString("Role_User");
                                             String adresse = jsonobject.getString("Adresse_Patient");
                                             String cin = jsonobject.getString("Cin_Patient");
                                             String urlImage = jsonobject.getString("UrlImage_Patient");
                                             int idCabinet = jsonobject.getInt("FK_Cabinet_ID");

                                            patientConnecté=new Patient1( id, login, password,  role1,  nom,  prenom,  date1,  numTel,  adresse,  urlImage,  cin,  idCabinet);

                                             startActivity(new Intent(ActivityLogin.this, ProfilPatient_PatientActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                         }
                                     }
                                 }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                });

                queue.add(stringRequest);

            }
        });













    }

}
