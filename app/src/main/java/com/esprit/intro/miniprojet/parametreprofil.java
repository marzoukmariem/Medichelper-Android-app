package com.esprit.intro.miniprojet;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

public class parametreprofil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mdrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametreprofil);


        setNavigationViewListener();
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.Open,R.string.Close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         ImageView mod=(ImageView)findViewById(R.id.mod);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navView =  navigationView.inflateHeaderView(R.layout.header);

        TextView tv = (TextView)navView.findViewById(R.id.headerText);
        CircleImageView imuser=(CircleImageView)navView.findViewById(R.id.userconnecte);
        tv.setText(ActivityLogin.patientConnecté.getNom());
        Picasso.with(navView.getContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+ActivityLogin.patientConnecté.getUrlImage()).into(imuser);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvNom = (TextView) findViewById(R.id.tvNom);
        tvNom.setText(ActivityLogin.patientConnecté.getNom()+" "+ActivityLogin.patientConnecté.getPrenom());

        String d=ActivityLogin.patientConnecté.getDateNaissance().toString();

        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        TextView tvProfession = (TextView) findViewById(R.id.tvProfession);


        TextView tvAdresse = (TextView) findViewById(R.id.tvAdresse);


        TextView tvPhone = (TextView) findViewById(R.id.tvPhone);


        final RequestQueue queue = Volley.newRequestQueue(this);
        CircleImageView img=(CircleImageView)findViewById(R.id.userconnecte);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/getInnfopatient.php?idpt=" + ActivityLogin.patientConnecté.getId(),
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length() == 0) {
                                Toast.makeText(getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);


                                    tvPhone.setText(jsonobject.getString("NumTel_User"));

                                    tvNom.setText(jsonobject.getString("Nom_User")+" "+jsonobject.getString("Prenom_User"));
                                   tvProfession.setText(jsonobject.getString("Cin_Patient"));
                                    tvAdresse.setText(jsonobject.getString("Adresse_Patient"));
                                   tvDate.setText(jsonobject.getString("DateNaissance_User").substring(0,10));

                                    String imageurl=jsonobject.getString("UrlImage_Patient");
                                    ActivityLogin.patientConnecté.setUrlImage(imageurl);
                                    ActivityLogin.patientConnecté.setNom(jsonobject.getString("Nom_User"));
                                    ActivityLogin.patientConnecté.setPrenom(jsonobject.getString("Prenom_User"));
                                   ActivityLogin.patientConnecté.setNumTel(jsonobject.getString("Cin_Patient"));

                                  Picasso.with(getApplicationContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+imageurl).into(img);
                                    tv.setText(ActivityLogin.patientConnecté.getNom()+" "+ActivityLogin.patientConnecté.getPrenom());
                                    Picasso.with(navView.getContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+ActivityLogin.patientConnecté.getUrlImage()).into(imuser);

                                }


                            }  } catch (JSONException e) {
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


        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(parametreprofil.this, ActivityUpdateProfil__PatientActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.historyRdv: {
                startActivity(new Intent(parametreprofil.this, MesRendezVous_patientActivity.class));
                break;
            }
            case R.id.logout: {
                startActivity(new Intent(parametreprofil.this, ActivityLogin.class));
                break;
            }
            case R.id.parametres: {
                startActivity(new Intent(parametreprofil.this, parametreprofil.class));
                break;
            }
            case R.id.accueil: {
                startActivity(new Intent(parametreprofil.this, ProfilPatient_PatientActivity.class));
                break;
            }
        }
        //close navigation drawer

        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
