package com.esprit.intro.miniprojet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;
import uk.co.senab.photoview.PhotoViewAttacher;

public class adminacceuil extends AppCompatActivity {
   ListView lstRDVPatient ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminacceuil);
        setTitle("");
    lstRDVPatient = (ListView) findViewById(R.id.lstadmin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ArrayList<Patient1> lp1 = new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/approuveradmin.php", new Response.Listener<String>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {

                System.out.println("http://"+ActivityLogin.ip+"/medichelper/approuveradmin.php");
                JSONArray jsonarray = null;
                System.out.println(response);
                try {
                    jsonarray = new JSONArray(response);

                    if (jsonarray.length() == 0) {
                        Toast.makeText(getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                    } else {

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String daten = jsonobject.getString("Prenom_User");
                            String titre = jsonobject.getString("Nom_User");
                            String urlimagediplome=jsonobject.getString("UrlImage_diplome");
                            String urlimagepatient=jsonobject.getString("UrlImage_Patient");
                            Long iduser=Long.valueOf(jsonobject.getString("Id_User"));
                            Patient1 r = new Patient1();
                            r.setPrenom(daten);
                            r.setNom(titre);
                            r.setId(iduser);
                            System.out.print(urlimagepatient);
                            r.setUrlImage(urlimagepatient);
                            r.setUrrldiplome(urlimagediplome);
                            lp1.add(r);


                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapteradminpt adapter = new adapteradminpt(getApplicationContext(), R.layout.itemadminmedecin, lp1);
                lstRDVPatient.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("That didn't work!");
                System.out.println("http://"+ActivityLogin.ip+"/medichelper/approuveradmin.php");

            }
        });

        queue.add(stringRequest);

          lstRDVPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  try {
                      Patient1 m = (Patient1) parent.getAdapter().getItem(position);
                      Toast.makeText(getApplicationContext(), "hello!",
                              Toast.LENGTH_LONG).show();
                      AlertDialog.Builder alertadd = new AlertDialog.Builder(adminacceuil.this);
                      LayoutInflater factory = LayoutInflater.from(adminacceuil.this);
                      final View v = factory.inflate(R.layout.dialogue, null);
                      alertadd.setView(v);
                      ImageView img = (ImageView) v.findViewById(R.id.image);
                      Picasso.with(v.getContext()).load("http://" + ActivityLogin.ip + "/medichelper/" + m.getUrrldiplome()).into(img);
                      PhotoViewAttacher photoView = new PhotoViewAttacher(img);
                      photoView.update();


                      alertadd.setNeutralButton("", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dlg, int sumthin) {

                          }
                      });

                      alertadd.show();


                  }catch(Exception ex){

                      Toast.makeText(getApplicationContext(), "hello!"+ex.getMessage(),
                              Toast.LENGTH_LONG).show();

                  }


              }
          });





    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.decon: startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deconexion, menu);

        return true;
    }
}
