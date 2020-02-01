package com.esprit.intro.miniprojet;


import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

import static com.esprit.intro.miniprojet.ActivityLogin.contacts;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccueilDocteur2 extends Fragment {
    ListView lspatients;
    public AccueilDocteur2() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil_docteur2, container, false);
        final tableRensezvous db = new tableRensezvous(this.getContext());
        Button btnRecherche = (Button) view.findViewById(R.id.btnRecherche);
        EditText etRecherche = (EditText) view.findViewById(R.id.textViewRecherche);
        db.open();
        final RequestQueue queue = Volley.newRequestQueue(this.getContext());
        lspatients = (ListView) view.findViewById(R.id.lst_Patient_docteur);
        final ArrayList<RendezVous> lp1 = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/gettallredsmedecin.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet(),
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
                                                        r.setImg(img);
                                                        r.setId(idrendezvous);
                                                        r.setId_patient(idpatient);
                                                        r.setNumtelp(ntel);
                                                        r.setProfessp(prof);
                                                        r.setTitre(titre);
                                                        r.setDetailrdv(detail);
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
                                    db.insertcontact(r);

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AdaptermedecinRdv adapter = new AdaptermedecinRdv(getContext().getApplicationContext(), R.layout.itemrdvm, lp1);
                        lspatients.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final ArrayList<RendezVous> lp2 = new ArrayList<>();
                System.out.println("That didn't work!");
                Cursor cts = db.getallContacts();
                if (cts.getCount() == 0) {
                    Toast.makeText(getActivity(), " Pas de Rendezvous",
                            Toast.LENGTH_LONG).show();


                } else {
                    while (cts.moveToNext()) {
                        RendezVous ct = new RendezVous();
                        ct.setNompatient(cts.getString(5));
                        ct.setPrenompatient(cts.getString(6));
                        ct.setDate(cts.getString(2));
                        ct.setId(Long.valueOf(cts.getInt(0)));
                        ct.setId_patient(Long.valueOf(cts.getInt(4)));
                        lp2.add(ct);
                    }
                    cts.close();
                    AdaptermedecinRdv adapter = new AdaptermedecinRdv(getContext().getApplicationContext(), R.layout.itemrdvm, lp2);
                    lspatients.setAdapter(adapter);


                }
            }
        });
        queue.add(stringRequest);

        btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp1.clear();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/gettallredsmedecinRecherche.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet()+"&searchInput="+etRecherche.getText(),
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
                                            r.setImg(img);
                                            r.setId(idrendezvous);
                                            r.setId_patient(idpatient);
                                            r.setNumtelp(ntel);
                                            r.setProfessp(prof);
                                            r.setTitre(titre);
                                            r.setDetailrdv(detail);
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
                                            db.insertcontact(r);

                                        }

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                AdaptermedecinRdv adapter = new AdaptermedecinRdv(getContext().getApplicationContext(), R.layout.itemrdvm, lp1);
                                lspatients.setAdapter(adapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        final ArrayList<RendezVous> lp2 = new ArrayList<>();
                        System.out.println("That didn't work!");
                        Cursor cts = db.getallContacts();
                        if (cts.getCount() == 0) {
                            Toast.makeText(getActivity(), " Pas de Rendezvous",
                                    Toast.LENGTH_LONG).show();


                        } else {
                            while (cts.moveToNext()) {
                                RendezVous ct = new RendezVous();
                                ct.setNompatient(cts.getString(5));
                                ct.setPrenompatient(cts.getString(6));
                                ct.setDate(cts.getString(2));
                                ct.setId(Long.valueOf(cts.getInt(0)));
                                ct.setId_patient(Long.valueOf(cts.getInt(4)));
                                lp2.add(ct);
                            }
                            cts.close();
                            AdaptermedecinRdv adapter = new AdaptermedecinRdv(getContext().getApplicationContext(), R.layout.itemrdvm, lp2);
                            lspatients.setAdapter(adapter);


                        }
                    }
                });
                queue.add(stringRequest);
            }
        });



        return view;

    }


}