package com.esprit.intro.miniprojet;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TimePicker;
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

import static com.esprit.intro.miniprojet.ActivityLogin.patientConnecté;
import static com.esprit.intro.miniprojet.App.CHANNEL_1_ID;

public class SecretaireRendezvous extends Fragment {
    private NotificationManagerCompat notificationManager;
    public  SecretaireRendezvous(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.activity_secretaire_rendezvous, container, false);
        super.onCreate(savedInstanceState);


        final RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        final ArrayList<RendezVous> lp1=new ArrayList<>();
        final ListView listres = (ListView) view.findViewById(R.id.listRendez);
        System.out.println(ActivityLogin.patientConnecté.getCabinet());

        notificationManager = NotificationManagerCompat.from(getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/getallrendezvous.php?idCabinet="+ActivityLogin.patientConnecté.getCabinet(),
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length()==0) {Toast.makeText(getContext().getApplicationContext(), "vide!"+ActivityLogin.patientConnecté.getCabinet(),
                                    Toast.LENGTH_LONG).show();

                            }

                            else {

                                for (int i = 0; i < jsonarray.length(); i++) {

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String prenom = jsonobject.getString("Prenom_User");
                                    String nom = jsonobject.getString("Nom_User");
                                    String date = jsonobject.getString("dateRendezvous");



                                    Long idrendezvous=Long.valueOf(jsonobject.getString("Id_Rendezvous"));
                                    RendezVous r=new RendezVous();
                                    r.setNompatient(nom);
                                    r.setPrenompatient(prenom);
                                    r.setDate(date);
                                    r.setId(idrendezvous);
                                    lp1.add(r);
                                }
                                Notification notification = new NotificationCompat.Builder(getContext().getApplicationContext(),CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.logoparamedical)
                                        .setContentTitle("Infos RendesVous")
                                        .setContentText("Nouveaux demandes RendezVous Disponibles")
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();
                                notificationManager.notify(1,notification);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AdapterRendez adapter=new AdapterRendez(getContext().getApplicationContext(), R.layout.itemrendezvous,lp1);
                        listres.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        queue.add(stringRequest);


        return view;

    }
}
