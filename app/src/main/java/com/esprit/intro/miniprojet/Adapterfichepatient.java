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

public class Adapterfichepatient  extends ArrayAdapter<RendezVous> {

    Context context;
    List<RendezVous> contacts;
    int resources;

    public Adapterfichepatient (@NonNull Context context, int resource, @NonNull List<RendezVous> objects) {
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

        TextView tvdate = (TextView) convertView.findViewById(R.id.datefiche);

        Button app = (Button) convertView.findViewById(R.id.affichermedecinpatient);

        tvdate.setText(contacts.get(position).getDate().substring(0,16));







        return convertView;
    }
}
