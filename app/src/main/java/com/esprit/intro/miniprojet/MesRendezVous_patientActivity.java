package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

import static com.esprit.intro.miniprojet.ActivityLogin.contacts;
import static com.esprit.intro.miniprojet.ActivityLogin.rdvs;

public class MesRendezVous_patientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView lstRDVPatient;
    Button btnAjoutMedicament;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    final String ip = ActivityLogin.ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesrendezvous_patient);

        setNavigationViewListener();
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.Open,R.string.Close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navView =  navigationView.inflateHeaderView(R.layout.header);
        TextView tv = (TextView)navView.findViewById(R.id.headerText);
        CircleImageView imuser=(CircleImageView)navView.findViewById(R.id.userconnecte);
        tv.setText(ActivityLogin.patientConnecté.getNom()+" "+ActivityLogin.patientConnecté.getPrenom());
        Picasso.with(navView.getContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+ActivityLogin.patientConnecté.getUrlImage()).into(imuser);
        navigationView.setNavigationItemSelectedListener(this);
          lstRDVPatient = (ListView) findViewById(R.id.lstRDV_Patient);
        final ArrayList<RendezVous> lp1 = new ArrayList<>();
          final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/rdvpatientconnecte.php?idpt="+ActivityLogin.patientConnecté.getId()+"", new Response.Listener<String>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                  System.out.print("http://"+ActivityLogin.ip+"/medichelper/rdvpatientconnecte.php?idpt="+ActivityLogin.patientConnecté.getId()+"");
                    JSONArray jsonarray = null;
                    System.out.println(response);
                    try {
                        jsonarray = new JSONArray(response);

                        if (jsonarray.length() == 0) {
                            Toast.makeText(getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                        } else {

                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String daten = jsonobject.getString("dateRendezvous");
                                String titre = jsonobject.getString("Nom_Cabinet");
                                RendezVous r = new RendezVous();
                                r.setTitre(titre);
                                r.setDate(daten);
                                lp1.add(r);


                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Adapterpatientrdv adapter = new Adapterpatientrdv(getApplicationContext(), R.layout.itelrdvpatient, lp1);
                    lstRDVPatient.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println("That didn't work!");

                    System.out.print("http://"+ActivityLogin.ip+"/medichelper/rdvpatientconnecte.php?idpt="+ActivityLogin.patientConnecté.getId()+"");

                }
            });

        queue.add(stringRequest);





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
                startActivity(new Intent(MesRendezVous_patientActivity.this, MesRendezVous_patientActivity.class));
                break;
            }
            case R.id.logout: {
                startActivity(new Intent(MesRendezVous_patientActivity.this, ActivityLogin.class));
                break;
            }
            case R.id.parametres: {
                startActivity(new Intent(MesRendezVous_patientActivity.this, parametreprofil.class));
                break;
            }
            case R.id.accueil: {
                startActivity(new Intent(MesRendezVous_patientActivity.this, ProfilPatient_PatientActivity.class));
                break;
            }
        }
        //close navigation drawer

        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
