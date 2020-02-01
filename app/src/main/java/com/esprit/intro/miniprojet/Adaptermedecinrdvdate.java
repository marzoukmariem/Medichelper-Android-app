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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

public class Adaptermedecinrdvdate extends ArrayAdapter<RendezVous> {
    Context context;
    List<RendezVous> contacts;
    int resources;

    public  Adaptermedecinrdvdate(@NonNull Context context, int resource, @NonNull List<RendezVous> objects) {
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

        CircleImageView img=(CircleImageView)convertView.findViewById(R.id.imgmedecinpaient);

        tvNom.setText(contacts.get(position).getNompatient());
        tvpreNom.setText(contacts.get(position).getPrenompatient());
        tvdate.setText(contacts.get(position).getDate().substring(0,16));
        Picasso.with(getContext().getApplicationContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+contacts.get(position).getImg()).into(img);






        return convertView;
    }
}
