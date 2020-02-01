package com.esprit.intro.miniprojet;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccueilDocteur1 extends Fragment {
    static int d,m,y;
    static String wa;

    public AccueilDocteur1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     final View view= inflater.inflate(R.layout.fragment_accueil_docteur1, container, false);
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final RequestQueue queue1 = Volley.newRequestQueue(getContext());
        final ArrayList<RendezVous> lp1 = new ArrayList<>();
        CalendarView calendarViewPlanificationRDV= (CalendarView)view.findViewById(R.id.calendarView);
        ListView lsrv= (ListView)view.findViewById(R.id.lstdatedoc);

        calendarViewPlanificationRDV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

          @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                lp1.clear();
                //  String myFormat = "yyyy-MM-dd"; //In which you need put here
                //  SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                d = dayOfMonth;
                String curDate =String.valueOf(d);
                if(d==1||d==2||d==3||d==4||d==5||d==6||d==7||d==8||d==9)
                {curDate="0"+curDate;}
                m = month+1;
                String curMonth =String.valueOf(m);
                if(m==1||m==2||m==3||m==4||m==5||m==6||m==7||m==8||m==9)
                {curMonth="0"+curMonth;}
                y = year;
                String curYear =String.valueOf(y);
                System.out.println(wa);
                wa=curYear+"-"+curMonth+"-"+curDate;



                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/getrdvpardate.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet()+"&daterdv="+wa+"",
                                    new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(String response) {
                                    System.out.print("http://" + ActivityLogin.ip + "/medichelper/getrdvpardate.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet()+"&daterdv="+wa+"");
                                JSONArray jsonarray = null;
                                System.out.println(response);
                                try {
                                    jsonarray = new JSONArray(response);

                                    if (jsonarray.length() == 0) {
                                        Toast.makeText(getContext().getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                                    } else {

                                        for (int i = 0; i < jsonarray.length(); i++) {

                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String prenom = jsonobject.getString("Prenom_User");
                                            String nom = jsonobject.getString("Nom_User");
                                            String date = jsonobject.getString("dateRendezvous");
                                            Long idrendezvous = Long.valueOf(jsonobject.getString("Id_Rendezvous"));
                                            Long idpatient = Long.valueOf(jsonobject.getString("Id_User"));
                                            String img = jsonobject.getString("UrlImage_Patient");
                                            String ntel = jsonobject.getString("NumTel_User");
                                            String prof = jsonobject.getString("Cin_Patient");
                                            String daten = jsonobject.getString("DateNaissance_User");
                                            String titre = jsonobject.getString("Titre_Consultation");
                                            String detail = jsonobject.getString("detail_Consultation");
                                            RendezVous r = new RendezVous();
                                            r.setNompatient(nom);
                                            r.setPrenompatient(prenom);
                                            r.setDate(date);
                                            r.setId(idrendezvous);
                                            r.setId_patient(idpatient);
                                            r.setNumtelp(ntel);
                                            r.setProfessp(prof);
                                            r.setTitre(titre);
                                            r.setDetailrdv(detail);
                                            r.setImg(img);
                                            Date date1 = new Date();


                                            try {

                                                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(daten);
                                                System.out.println(date1.toString());
                                                String datestring = date1.toString();
                                                String day = datestring.substring(0, 3);
                                                //System.out.println("day="+day);
                                                String month = datestring.substring(8, 10);
                                                String day2 = datestring.substring(4, 7);
                                                String year = datestring.substring(30, 34);
                                                String date2 = month + "-" + day2 + "-" + year;
                                                r.setDateann(date2);


                                            } catch (Exception e) {
                                                Log.d("ImageManager", "Error: " + e.toString());
                                            }

                                            lp1.add(r);


                                        }

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                    Adaptermedecinrdvdate adapter = new Adaptermedecinrdvdate(getContext().getApplicationContext(), R.layout.itemrdvdate, lp1);
                                lsrv.setAdapter(adapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("http://" + ActivityLogin.ip + "/medichelper/getrdvpardate.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet()+"&daterdv="+wa+"");

                        System.out.println("That didn't work!");


                    }
                });

                queue.add(stringRequest);



            }
        });







        Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        String currentdatee= ss.format(date);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/getrdvpardate.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet()+"&daterdv="+currentdatee+"",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length() == 0) {
                                Toast.makeText(getContext().getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                                System.out.print("http://" + ActivityLogin.ip + "/medichelper/getrdvpardate.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet()+"&daterdv="+currentdatee+"" );
                            } else {

                                for (int i = 0; i < jsonarray.length(); i++) {

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String prenom = jsonobject.getString("Prenom_User");
                                    String nom = jsonobject.getString("Nom_User");
                                    String date = jsonobject.getString("dateRendezvous");
                                    Long idrendezvous = Long.valueOf(jsonobject.getString("Id_Rendezvous"));
                                    Long idpatient = Long.valueOf(jsonobject.getString("Id_User"));
                                    String img = jsonobject.getString("UrlImage_Patient");
                                    String ntel = jsonobject.getString("NumTel_User");
                                    String prof = jsonobject.getString("Cin_Patient");
                                    String daten = jsonobject.getString("DateNaissance_User");
                                    String titre = jsonobject.getString("Titre_Consultation");
                                    String detail = jsonobject.getString("detail_Consultation");
                                    RendezVous r = new RendezVous();
                                    r.setNompatient(nom);
                                    r.setPrenompatient(prenom);
                                    r.setDate(date);
                                    r.setId(idrendezvous);
                                    r.setId_patient(idpatient);
                                    r.setNumtelp(ntel);
                                    r.setProfessp(prof);
                                    r.setTitre(titre);
                                    r.setDetailrdv(detail);
                                    r.setImg(img);
                                    Date date1 = new Date();


                                    try {

                                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(daten);
                                        System.out.println(date1.toString());
                                        String datestring = date1.toString();
                                        String day = datestring.substring(0, 3);
                                        //System.out.println("day="+day);
                                        String month = datestring.substring(8, 10);
                                        String day2 = datestring.substring(4, 7);
                                        String year = datestring.substring(30, 34);
                                        String date2 = month + "-" + day2 + "-" + year;
                                        r.setDateann(date2);


                                    } catch (Exception e) {
                                        Log.d("ImageManager", "Error: " + e.toString());
                                    }

                                    lp1.add(r);


                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Adaptermedecinrdvdate adapter = new Adaptermedecinrdvdate(getContext().getApplicationContext(), R.layout.itemrdvdate, lp1);
                        lsrv.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("");

                System.out.println("That didn't work!");


            }
        });

        queue1.add(stringRequest1);












        return view;

    }

}
