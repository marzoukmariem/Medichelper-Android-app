package com.esprit.intro.miniprojet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import static com.esprit.intro.miniprojet.ActivityLogin.patientConnecté;

public class AdapterRendez  extends ArrayAdapter<RendezVous> {

    Context context;
    List<RendezVous> contacts;
    int resources;
    public AdapterRendez(@NonNull Context context, int resource, @NonNull List<RendezVous> objects) {
        super(context, resource, objects);
        this.context=context;
        this.contacts = objects;
        this.resources=resource;


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= LayoutInflater.from(context);
        convertView = inflater.inflate(resources, null);
        final RequestQueue queue1 = Volley.newRequestQueue(getContext().getApplicationContext());
        TimePicker timePicker1;
        timePicker1 = (TimePicker) convertView.findViewById(R.id.timePicker1);
        timePicker1.setIs24HourView(true);

        TextView tvNom = (TextView) convertView.findViewById(R.id.nompatientrendez);
        TextView tvpreNom = (TextView) convertView.findViewById(R.id.prenompatienrendez);
        TextView tvdate = (TextView) convertView.findViewById(R.id.daterendez);
        final Button app = (Button) convertView.findViewById(R.id.approuver);
        tvNom.setText(contacts.get(position).getNompatient());
        tvpreNom.setText(contacts.get(position).getPrenompatient());
        String d=contacts.get(position).getDate();
        String day=d.substring(0,10);

        tvdate.setText(day);

        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 RendezVous rendezVous=(RendezVous) getItem(position);
                final RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
                final RequestQueue queue1 = Volley.newRequestQueue(getContext().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ActivityLogin.ip+"/medichelper/approuverRDV.php?Rendezvous_ID="+rendezVous.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                System.out.println("it works");

                                app.setBackgroundColor(R.drawable.buttonleightgradient);
                                    app.setText("approuvé");


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());


                    }
                });

                queue.add(stringRequest);
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://"+ActivityLogin.ip+"/medichelper/updatetimesec.php?daterdv="+rendezVous.getDate().substring(0,10)+"%20" +timePicker1.getCurrentHour() +":"+timePicker1.getCurrentMinute()+":00&idrdv="+rendezVous.getId()+"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                System.out.println("http://"+ActivityLogin.ip+"/medichelper/updatetimesec.php?daterdv="+rendezVous.getDate().substring(0,10)+" " +timePicker1.getCurrentHour() +":"+timePicker1.getCurrentMinute()+":00&idrdv="+rendezVous.getId()+"");




                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());


                    }
                });


                queue1.add(stringRequest1);
                Toast.makeText(getContext().getApplicationContext(), "RDV approuvé!",
                        Toast.LENGTH_LONG).show();




            }
        });
        return convertView;
    }
















}
