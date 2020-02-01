package com.esprit.intro.miniprojet;

import android.app.Notification;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

import static com.esprit.intro.miniprojet.App.CHANNEL_1_ID;

public class ProfilPatient_PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public static final String PREFS_NAME = "MyPrefsFile";
    static long idCabinet;
    static String nomCabinet;
    private NotificationManagerCompat notificationManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
// Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_patient__patient);
        setNavigationViewListener();
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.Open,R.string.Close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //handleIntent(getIntent());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View navView =  navigationView.inflateHeaderView(R.layout.header);
        TextView tv = (TextView)navView.findViewById(R.id.headerText);

        CircleImageView imuser=(CircleImageView)navView.findViewById(R.id.userconnecte);
        tv.setText(ActivityLogin.patientConnecté.getNom()+" "+ActivityLogin.patientConnecté.getPrenom());

        Picasso.with(navView.getContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+ActivityLogin.patientConnecté.getUrlImage()).into(imuser);
        navigationView.setNavigationItemSelectedListener(this);


        final ArrayList<Cabinet> lp1=new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final   ListView lscab = (ListView) findViewById(R.id.lscab);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {

            //apres l'input de la recherche:

            String query = getIntent().getStringExtra(SearchManager.QUERY);
            System.out.print("http://" + ActivityLogin.ip + "/medichelper/LastRendezVousPatient.php?id=" + ActivityLogin.patientConnecté.getId());
            notificationManager = NotificationManagerCompat.from(this);

            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/LastRendezVousPatient.php?id=" + ActivityLogin.patientConnecté.getId(),
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length() == 0) {
                                    System.out.print("probleme");
                                } else {

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        String idBase = jsonobject.getString("Id_Rendezvous");
                                        String etatBase = jsonobject.getString("approuver_Rendezvous");

                                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                        String idLocal = settings.getString("Id_LastRendezvous", "");
                                        String etatLocal = settings.getString("etat", "");
                                        System.out.println("idBase=" + idBase);
                                        System.out.println("idLocal=" + idLocal);
                                        System.out.println("etatBase=" + etatBase);
                                        if (idBase.equals(idLocal)) {
                                            if (etatBase.equals("false")) {
                                                System.out.println("Dernier rendezVous Pas encore approuvé");

                                                Notification notification = new NotificationCompat.Builder(ProfilPatient_PatientActivity.this, CHANNEL_1_ID)
                                                        .setSmallIcon(R.drawable.logoparamedical)
                                                        .setContentTitle("Etat Dernier RendezVous")
                                                        .setContentText("Dernier rendezVous pas encore approuvé")
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                        .build();
                                                notificationManager.notify(1, notification);

                                            }
                                            if (etatBase.equals("true")) {
                                                System.out.println("Dernier rendezVous approuvé");

                                                Notification notification = new NotificationCompat.Builder(ProfilPatient_PatientActivity.this, CHANNEL_1_ID)
                                                        .setSmallIcon(R.drawable.logoparamedical)
                                                        .setContentTitle("Etat Dernier RendezVous")
                                                        .setContentText("Dernier rendezVous approuvé")
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                        .build();
                                                notificationManager.notify(1, notification);
                                            }


                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("Id_LastRendezvous", idBase);
                                            editor.putString("etat", idBase);

                                            // Commit the edits!
                                            editor.commit();
                                        } else {


                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("Id_LastRendezvous", idBase);
                                            editor.putString("etat", idBase);

                                            // Commit the edits!
                                            editor.commit();
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

            queue.add(stringRequest1);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/gettallcabinetRecherche.php?idpatient=" + ActivityLogin.patientConnecté.getId()+"&searchInput="+query,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length() == 0) {
                                    Toast.makeText(getApplicationContext(), "vide!",
                                            Toast.LENGTH_LONG).show();
                                } else {

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                                        String titre = jsonobject.getString("Nom_Cabinet");
                                        String info = jsonobject.getString("Info_Cabinet");
                                        String emp = jsonobject.getString("Emplacement_Cabinet");
                                        int numtel = Integer.valueOf(jsonobject.getString("NumTel_Cabinet"));
                                        Long idcab = Long.valueOf(jsonobject.getString("Id_Cabinet"));
                                        Double lat = Double.valueOf(jsonobject.getString("Latitude"));
                                        Double log = Double.valueOf(jsonobject.getString("longitude"));
                                        Cabinet p1 = new Cabinet();
                                        p1.setEmplacement(emp);
                                        p1.setInfo(info);
                                        p1.setNom(titre);
                                        p1.setId(idcab);
                                        p1.setTel(numtel);
                                        p1.setLatitude(lat);
                                        p1.setLongitude(log);
                                        lp1.add(p1);


                                    }


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CabinetAdapter adapter = new CabinetAdapter(ProfilPatient_PatientActivity.this, R.layout.itemcabinet, lp1);
                            lscab.setAdapter(adapter);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });

            queue.add(stringRequest);







        }else {
          //sans recherche


            System.out.print("http://" + ActivityLogin.ip + "/medichelper/LastRendezVousPatient.php?id=" + ActivityLogin.patientConnecté.getId());
            notificationManager = NotificationManagerCompat.from(this);

            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/LastRendezVousPatient.php?id=" + ActivityLogin.patientConnecté.getId(),
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length() == 0) {
                                    System.out.print("probleme");

                                } else {

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        String idBase = jsonobject.getString("Id_Rendezvous");
                                        String etatBase = jsonobject.getString("approuver_Rendezvous");

                                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                        String idLocal = settings.getString("Id_LastRendezvous", "");
                                        String etatLocal = settings.getString("etat", "");
                                        System.out.println("idBase=" + idBase);
                                        System.out.println("idLocal=" + idLocal);
                                        System.out.println("etatBase=" + etatBase);
                                        if (idBase.equals(idLocal)) {
                                            if (etatBase.equals("false")) {
                                                System.out.println("Dernier rendezVous Pas encore approuvé");

                                                Notification notification = new NotificationCompat.Builder(ProfilPatient_PatientActivity.this, CHANNEL_1_ID)
                                                        .setSmallIcon(R.drawable.logoparamedical)
                                                        .setContentTitle("Etat Dernier RendezVous")
                                                        .setContentText("Dernier rendezVous pas encore approuvé")
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                        .build();
                                                notificationManager.notify(1, notification);

                                            }
                                            if (etatBase.equals("true")) {
                                                System.out.println("Dernier rendezVous approuvé");

                                                Notification notification = new NotificationCompat.Builder(ProfilPatient_PatientActivity.this, CHANNEL_1_ID)
                                                        .setSmallIcon(R.drawable.logoparamedical)
                                                        .setContentTitle("Etat Dernier RendezVous")
                                                        .setContentText("Dernier rendezVous approuvé")
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                        .build();
                                                notificationManager.notify(1, notification);
                                            }


                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("Id_LastRendezvous", idBase);
                                            editor.putString("etat", idBase);

                                            // Commit the edits!
                                            editor.commit();
                                        } else {


                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("Id_LastRendezvous", idBase);
                                            editor.putString("etat", idBase);

                                            // Commit the edits!
                                            editor.commit();
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

            queue.add(stringRequest1);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/gettallcabinet.php?idpatient=" + ActivityLogin.patientConnecté.getId(),
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length() == 0) {
                                    Toast.makeText(getApplicationContext(), "vide!",
                                            Toast.LENGTH_LONG).show();
                                } else {

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                                        String titre = jsonobject.getString("Nom_Cabinet");
                                        String info = jsonobject.getString("Info_Cabinet");
                                        String emp = jsonobject.getString("Emplacement_Cabinet");
                                        int numtel = Integer.valueOf(jsonobject.getString("NumTel_Cabinet"));
                                        Long idcab = Long.valueOf(jsonobject.getString("Id_Cabinet"));
                                        Double lat = Double.valueOf(jsonobject.getString("Latitude"));
                                        Double log = Double.valueOf(jsonobject.getString("longitude"));
                                        Cabinet p1 = new Cabinet();
                                        p1.setEmplacement(emp);
                                        p1.setInfo(info);
                                        p1.setNom(titre);
                                        p1.setId(idcab);
                                        p1.setTel(numtel);
                                        p1.setLatitude(lat);
                                        p1.setLongitude(log);
                                        lp1.add(p1);


                                    }


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CabinetAdapter adapter = new CabinetAdapter(ProfilPatient_PatientActivity.this, R.layout.itemcabinet, lp1);
                            lscab.setAdapter(adapter);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });

            queue.add(stringRequest);


            lscab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ProfilPatient_PatientActivity.this, PlanificationRendezVous_PatientActivity.class);
                    Cabinet m = (Cabinet) parent.getAdapter().getItem(position);
                    intent.putExtra("idcab", m.getId());
                    nomCabinet = m.getNom();
                    idCabinet = m.getId();
                    startActivity(intent);


                }
            });

        }
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
                startActivity(new Intent(ProfilPatient_PatientActivity.this, MesRendezVous_patientActivity.class));
                break;
            }
            case R.id.logout: {
                startActivity(new Intent(ProfilPatient_PatientActivity.this, ActivityLogin.class));
                break;
            }
            case R.id.parametres: {
                startActivity(new Intent(ProfilPatient_PatientActivity.this, parametreprofil.class));
                break;
            }
            case R.id.accueil: {
                startActivity(new Intent(ProfilPatient_PatientActivity.this, ProfilPatient_PatientActivity.class));
                break;
            }
        }
        //close navigation drawer

        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
        }
    }


}
