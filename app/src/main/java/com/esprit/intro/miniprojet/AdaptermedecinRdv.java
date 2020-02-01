package com.esprit.intro.miniprojet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.List;

public class AdaptermedecinRdv extends ArrayAdapter<RendezVous> {

    Context context;
    List<RendezVous> contacts;
    int resources;

    public AdaptermedecinRdv(@NonNull Context context, int resource, @NonNull List<RendezVous> objects) {
        super(context, resource, objects);
        this.context = context;
        this.contacts = objects;
        this.resources = resource;


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
       convertView = inflater.inflate(resources, null);
        final RequestQueue queue1 = Volley.newRequestQueue(getContext().getApplicationContext());

        TextView tvNom = (TextView) convertView.findViewById(R.id.nommedecinrendez);
        TextView tvpreNom = (TextView) convertView.findViewById(R.id.prenommedecinrendez);
        TextView tvdate = (TextView) convertView.findViewById(R.id.daterendezmedecin);
        Button app = (Button) convertView.findViewById(R.id.affichermedecinpatient);

                ImageView img=(ImageView)convertView.findViewById(R.id.imgmedecinpaient);

        tvNom.setText(contacts.get(position).getNompatient());
        tvpreNom.setText(contacts.get(position).getPrenompatient());
        tvdate.setText(contacts.get(position).getDate().substring(0,16));



        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RendezVous rendezVous=(RendezVous) getItem(position);
               // Intent intent=new Intent(context.getApplicationContext(),testactivity.class);
            //    intent.putExtra("idcab",rendezVous.getId());
               // Toast.makeText(context,"helllo from adapter",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, fichepatient.class);
                intent.putExtra("idcab",rendezVous.getId());
                intent.putExtra("iduser",rendezVous.getId_patient());
                intent.putExtra("idrv",rendezVous.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
               // context.getApplicationContext().startActivity(intent);

                }
                });




        return convertView;
    }

}







