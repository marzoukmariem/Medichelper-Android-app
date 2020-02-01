package com.esprit.intro.miniprojet;
import android.content.Context;
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
public class Adapterpatientrdv extends ArrayAdapter<RendezVous> {
    Context context;
    List<RendezVous> contacts;
    int resources;
    public Adapterpatientrdv(@NonNull Context context, int resource, @NonNull List<RendezVous> objects) {
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
        TextView tvNom = (TextView) convertView.findViewById(R.id.cabinetnom);
        TextView tvdate = (TextView) convertView.findViewById(R.id.datetrdvpecin);
        tvNom.setText(contacts.get(position).getTitre());
        String d=contacts.get(position).getDate();
        String day=d.substring(0,16);
        tvdate.setText(day);
        return convertView;
}}
