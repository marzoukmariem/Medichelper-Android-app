package com.esprit.intro.miniprojet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import uk.co.senab.photoview.PhotoViewAttacher;

public class adapteradminpt extends ArrayAdapter<Patient1> {
    Context context;
    List<Patient1> contacts;
    int resources;
    public adapteradminpt(@NonNull Context context, int resource, @NonNull List<Patient1> objects) {
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
        TextView tvNom = (TextView) convertView.findViewById(R.id.nomdoc);
        TextView tvdate = (TextView) convertView.findViewById(R.id.prenomdoc);
        TextView tvapp= (TextView) convertView.findViewById(R.id.txtapp);
        TextView tvdiplome = (TextView) convertView.findViewById(R.id.txtdiplome);
        tvNom.setText(contacts.get(position).getNom());
        tvdate.setText(contacts.get(position).getPrenom());
        tvapp.setText("approuver Medecin");
        tvdiplome.setText("Voir Diplôme");
        tvapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient1 rendezVous=(Patient1) getItem(position);
                final RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
              StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ActivityLogin.ip+"/medichelper/approuvermedecinadmin2.php?iduser="+rendezVous.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                System.out.println("http://"+ActivityLogin.ip+"/medichelper/approuvermedecinadmin2.php?iduser="+rendezVous.getId()+"");




                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        System.out.println("http://"+ActivityLogin.ip+"/medichelper/approuvermedecinadmin2.php?iduser="+rendezVous.getId()+"");

                    }
                });

                queue.add(stringRequest);







            }
        });







        return convertView;
    }








}
