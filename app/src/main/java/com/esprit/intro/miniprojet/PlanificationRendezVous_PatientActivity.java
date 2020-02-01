package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

import static com.esprit.intro.miniprojet.ActivityLogin.patientConnecté;
import static com.esprit.intro.miniprojet.ActivityLogin.rdvs;

public class PlanificationRendezVous_PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    static String wa="";
    static Date dateslect;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static int d,m,y;
    String app="false";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planification_rendez_vous__patient);
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

        Button btnConfirmerRDV = (Button) findViewById(R.id.btnConfirmerRDV);

        CalendarView calendarViewPlanificationRDV= (CalendarView)findViewById(R.id.calendarViewPlanificationRDV);
        calendarViewPlanificationRDV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
              //  String myFormat = "yyyy-MM-dd"; //In which you need put here
              //  SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                wa="";

                d = dayOfMonth;
                String curDate =String.valueOf(d);
                m = month+1;
                String curMonth =String.valueOf(m);
                y = year;
                String curYear =String.valueOf(y);
                System.out.println(wa);
                wa=curYear+"-"+curMonth+"-"+curDate;
                System.out.println("annee est"+ year);
                Calendar c1 = Calendar.getInstance();
                dateslect = new Date();
                System.out.println("anne du calendrier"+c1.getTime().getYear());

                 dateslect.setDate(dayOfMonth);
                dateslect.setMonth(month);
                String yar=""+y;
                String yy=yar.substring(2,4);
                int yyy=Integer.valueOf(yy);
                int yyyy=100+yyy;
                dateslect.setYear(yyyy);
                dateslect.setHours(c1.getTime().getHours());
                dateslect.setMinutes(c1.getTime().getMinutes());
                dateslect.setSeconds(c1.getTime().getSeconds());
                System.out.println("daate selectionner est "+ dateslect);
                System.out.println("daate ann est "+ yyyy);
            }
        });
        final Intent intent = getIntent();
       final Long message = intent.getLongExtra("idcab",3);

        btnConfirmerRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Date date1 = new Date();
                date1.setDate(c.getTime().getDate());


                System.out.println("day "+ c.getTime().getDate());
                date1.setMonth(c.getTime().getMonth());
                date1.setYear(c.getTime().getYear());


                System.out.println("daate1 "+ date1);
                if (wa.equals("")||dateslect.compareTo(date1)<=0)

                {   Toast.makeText(getApplicationContext(), "Il faut selectionner une date valide  " ,
                        Toast.LENGTH_LONG).show();

                }




                else {

                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ActivityLogin.ip + "/medichelper/insererRendezVous2.php?date=" + wa + "&approuver_Rendezvous=" + app + "&FK_Patient_ID=" + patientConnecté.getId() + "&cabinet_ID=" + message,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println("it works");
                                    Toast.makeText(getApplicationContext(), "Rendez-vous est pris le  " + wa,
                                            Toast.LENGTH_LONG).show();

                                    System.out.println("http://" + ActivityLogin.ip + "/medichelper/insererRendezVous2.php?date=" + wa + "&approuver_Rendezvous=" + app + "&FK_Patient_ID=" + patientConnecté.getId() + "&cabinet_ID=" + message);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getMessage());
                        }
                    });
                    queue.add(stringRequest);
                    startActivity(new Intent(PlanificationRendezVous_PatientActivity.this, ProfilPatient_PatientActivity.class));
                }
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
                startActivity(new Intent(PlanificationRendezVous_PatientActivity.this, MesRendezVous_patientActivity.class));
                break;
            }
            case R.id.logout: {
                startActivity(new Intent(PlanificationRendezVous_PatientActivity.this, ActivityLogin.class));
                break;
            }
            case R.id.parametres: {
                startActivity(new Intent(PlanificationRendezVous_PatientActivity.this, parametreprofil.class));
                break;
            }
            case R.id.accueil: {
                startActivity(new Intent(PlanificationRendezVous_PatientActivity.this, ProfilPatient_PatientActivity.class));
                break;
            }
        }
        //close navigation drawer

        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
