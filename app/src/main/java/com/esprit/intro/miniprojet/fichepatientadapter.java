package com.esprit.intro.miniprojet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class fichepatientadapter  extends ArrayAdapter<Consultation> {


    public Context context;
    List<Consultation> contacts;
    int resources;



    public fichepatientadapter(@NonNull Context context, int resource, @NonNull List<Consultation> objects) {
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
        TextView Nom= (TextView) convertView.findViewById(R.id.titrecon);
        TextView dateconsultation = (TextView) convertView.findViewById(R.id.dateconsultation);
        TextView details = (TextView) convertView.findViewById(R.id.detailscon);
        Nom.setText(contacts.get(position).getTitre());
        dateconsultation.setText("27/11/2018");
        details.setText(contacts.get(position).getDetails());

        return convertView;
    }





}
