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

public class CabinetAdapter extends ArrayAdapter<Cabinet> {

    Context context;
    List<Cabinet> contacts;
    int resources;
    public CabinetAdapter(@NonNull Context context, int resource, @NonNull List<Cabinet> objects) {
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

        TextView tvNom = (TextView) convertView.findViewById(R.id.nomcabinet);
        TextView tvTel = (TextView) convertView.findViewById(R.id.tel);
        TextView tvamplacement= (TextView) convertView.findViewById(R.id.Emplacement);
        TextView tvinfo= (TextView) convertView.findViewById(R.id.prenommedecin);
        ImageView img=(ImageView)convertView.findViewById(R.id.imgposition);
        ImageView imgmsg=(ImageView)convertView.findViewById(R.id.imgmsg);

        tvNom.setText(contacts.get(position).getNom());
        tvTel.setText(Integer.toString(contacts.get(position).getTel()));
        tvamplacement.setText(contacts.get(position).getEmplacement());
        tvinfo.setText(contacts.get(position).getInfo());



       img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cabinet cabinet=(Cabinet) getItem(position);
                Intent intent = new Intent(context, cabinetmap.class);
                intent.putExtra("lat",cabinet.getLatitude());
                intent.putExtra("log",cabinet.getLongitude());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });


        imgmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cabinet cabinet=(Cabinet) getItem(position);
                Intent intent = new Intent(context, Conversation.class);
                ProfilPatient_PatientActivity.idCabinet=cabinet.getId();
                intent.putExtra("Nomcab",cabinet.getNom());
                intent.putExtra("idcab",cabinet.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });









        return convertView;
    }
}
