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

public class AdapterContact extends ArrayAdapter<Contact> {

    Context context;
    List<Contact> contacts;
    int resources;

    public AdapterContact(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
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

        TextView tvNom = (TextView) convertView.findViewById(R.id.nomPatient);
        TextView tvpreNom = (TextView) convertView.findViewById(R.id.prenonPatient);
        TextView tvdate = (TextView) convertView.findViewById(R.id.daterendezmedecin);
        Button app = (Button) convertView.findViewById(R.id.afficherConversation);

        CircleImageView img=(CircleImageView)convertView.findViewById(R.id.imgmedecinpaient);
        Picasso.with(getContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+contacts.get(position).getImg()).into(img);


        tvNom.setText(contacts.get(position).getNompatient());
        tvpreNom.setText(contacts.get(position).getPrenompatient());


        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c=(Contact) getItem(position);
               // Intent intent=new Intent(context.getApplicationContext(),testactivity.class);
            //    intent.putExtra("idcab",rendezVous.getId());
               // Toast.makeText(context,"helllo from adapter",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, ConversationDocteur.class);
                intent.putExtra("idPatient",c.getIdPatient());
                intent.putExtra("nomPatient",c.getNompatient());
                intent.putExtra("prenomPatient",c.getPrenompatient());
                intent.putExtra("urlimg",c.getImg());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+c.getIdPatient()+c.getNompatient()+c.getPrenompatient());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
               // context.getApplicationContext().startActivity(intent);

                }
                });




        return convertView;
    }

}







