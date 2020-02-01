package com.esprit.intro.miniprojet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by esprit on 10/8/2018.
 */

public class ConsultationAdapter extends ArrayAdapter<Consultation>{
    Context context;
    List<Consultation> contacts;
    int resources;
    public ConsultationAdapter(@NonNull Context context, int resource, @NonNull List<Consultation> objects) {
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

        TextView tvNom = (TextView) convertView.findViewById(R.id.tvNom__template);
        TextView tvTel = (TextView) convertView.findViewById(R.id.tvCIN__template);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgPatient__template);
        tvNom.setText(contacts.get(position).getTitre());
        tvTel.setText(contacts.get(position).getDate());
        img.setImageResource(R.drawable.history);

        tvNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,DetailConsultationDunPatient_DocteurActivity.class );
                context.startActivity(intent);

            }
        });


        return convertView;
    }
}