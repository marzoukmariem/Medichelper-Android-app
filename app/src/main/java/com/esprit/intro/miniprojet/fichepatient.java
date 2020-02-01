package com.esprit.intro.miniprojet;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

import static com.esprit.intro.miniprojet.App.CHANNEL_1_ID;

public class fichepatient extends AppCompatActivity {

    @Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.fichepatient);
getSupportActionBar().hide();
final tableRensezvous db = new tableRensezvous(this);
db.open();
  final    ListView    lspatients = (ListView) findViewById(R.id.lsrdvs);
  final RequestQueue queue = Volley.newRequestQueue(this);
  final RequestQueue queue1 = Volley.newRequestQueue(this);
  final TextView tnom=findViewById(R.id.nomfichepatient);
        final TextView tpre=findViewById(R.id.prenomfichepatient);
        final TextView tnumtel=findViewById(R.id.telfichepatient);
        final TextView ds=findViewById(R.id.datenaissfichepatient);
        final TextView profession=findViewById(R.id.professionfichepatient);
        final CircleImageView imgpat=(CircleImageView)findViewById(R.id.imgfichepatient);
        final Intent intent = getIntent();
        final Long message = intent.getLongExtra("idcab",3);
        final Long iduser=intent.getLongExtra("iduser",3);
        final Long idrv=intent.getLongExtra("idrv",3);
        final EditText txttitre=findViewById(R.id.txttitre);
        final  EditText txtdetails=findViewById(R.id.txtdetails);
        final TextView titrerdv=findViewById(R.id.titrerdv);
        final  TextView Remarquerdv=findViewById(R.id.Remarquerdv);
        final Button edit=findViewById(R.id.edit);
        edit.setVisibility(View.INVISIBLE);


  StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/fichepatient.php?idCabinet="+message,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length()==0) {Toast.makeText(getApplicationContext(), "vide!"+message,
                                    Toast.LENGTH_LONG).show();

                            }

                            else {

                                 JSONObject jsonobject = jsonarray.getJSONObject(0);
                                 String prenom = jsonobject.getString("Prenom_User");
                                 String nom = jsonobject.getString("Nom_User");
                                 String prof= jsonobject.getString("Cin_Patient");
                                    String img= jsonobject.getString("UrlImage_Patient");
                                    String numtel= jsonobject.getString("NumTel_User");
                                    //  Long idrendezvous=Long.valueOf(jsonobject.getString("Id_Rendezvous"));
                                    //  nompatient=nom;
                                      tnom.setText(nom);
                                      tpre.setText(prenom);
                                      profession.setText(prof);
                                      tnumtel.setText(numtel);
                                    Picasso.with(getApplicationContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+img).into(imgpat);
                                    System.out.print(img);
                                    String dateNaissance = jsonobject.getString("DateNaissance_User");
                                    Date date1=new Date();

                                    try {

                                        date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissance);
                                        System.out.println(date1.toString());
                                      String  datestring=date1.toString();
                                        String day=datestring.substring(0,3);
                                        System.out.println("day="+day);
                                        String month=datestring.substring(8,10);
                                        String day2=datestring.substring(4,7);
                                        String year=datestring.substring(30,34);
                                        String date2=month+"-"+day2+"-"+year;
                                        ds.setText(date2);

                                    } catch (ParseException e) {
                                        e.printStackTrace();

                                    }


                        }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");

                final ArrayList<RendezVous> lp2 = new ArrayList<>();
                System.out.println("That didn't work!");
                Cursor cts = db.getallpatient(idrv);
                while (cts.moveToNext()) {
                    RendezVous ct = new RendezVous();
                    ct.setNompatient(cts.getString(5));
                    ct.setPrenompatient(cts.getString(6));
                    ct.setDateann(cts.getString(9));
                    ct.setNumtelp(cts.getString(11));
                    ct.setProfessp(cts.getString(12));
                    ct.setId(Long.valueOf(cts.getInt(8)));
                    ct.setId_patient(Long.valueOf(cts.getInt(4)));
                    ct.setImg(cts.getString(10));
                    lp2.add(ct);
                }
                tnom.setText(lp2.get(0).getNompatient());
                tpre.setText(lp2.get(0).getPrenompatient());
                tnumtel.setText(lp2.get(0).getNumtelp());
                ds.setText(lp2.get(0).getDateann());
                profession.setText(lp2.get(0).getProfessp());
                Picasso.with(getApplicationContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+lp2.get(0).getImg()).into(imgpat);

                System.out.print(lp2.get(0).getId());

            }
        });

        queue.add(stringRequest);


        final ArrayList<RendezVous> lp1=new ArrayList<>();
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/getallrendezoususer.php?idCabinet="+ActivityLogin.patientConnecté.getCabinet()+"&idUser="+iduser,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length()==0) {Toast.makeText(fichepatient.this, "vide!",Toast.LENGTH_LONG).show();}


                            else {

                                for (int i = 0; i < jsonarray.length(); i++) {

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    Long idrdv=Long.valueOf(jsonobject.getString("Id_Rendezvous"));
                                    String daterdv = jsonobject.getString("dateRendezvous");
                                    String detardv = jsonobject.getString("detail_Consultation");
                                    String titrerdv = jsonobject.getString("Titre_Consultation");
                                    RendezVous rfiche=new RendezVous();
                                    rfiche.setId(idrdv);
                                    rfiche.setTitre(titrerdv);
                                    rfiche.setDate(daterdv);
                                    rfiche.setDetailrdv(detardv);
                                    lp1.add(rfiche);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       Adapterfichepatient adapter=new Adapterfichepatient(fichepatient.this, R.layout.itemfichepatient,lp1);
                        lspatients.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final ArrayList<RendezVous> lp4 = new ArrayList<>();
                System.out.println("That didn't work!");
                Cursor cts = db.getallconsultations(iduser);
                if (cts.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), " Pas de Rendezvous",
                            Toast.LENGTH_LONG).show();


                } else {
                    while (cts.moveToNext()) {
                        RendezVous ct = new RendezVous();
                        ct.setTitre(cts.getString(1));
                        ct.setDate(cts.getString(2));
                        ct.setDetailrdv(cts.getString(7));
                          lp4.add(ct);
                    }
                    cts.close();
                    Adapterfichepatient adapter=new Adapterfichepatient(fichepatient.this, R.layout.itemfichepatient,lp4);
                    lspatients.setAdapter(adapter);
                }


            }
        });

        queue1.add(stringRequest1);



lspatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        final RendezVous m=(RendezVous) parent.getAdapter().getItem(position);
        System.out.println(m.getId());
        titrerdv.setText("titre :");
        Remarquerdv.setText("Remarques :");
        txttitre.setText(m.getTitre());
        txtdetails.setText(m.getDetailrdv());
        edit.setVisibility(View.VISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titrerdv=txttitre.getText().toString().replace(" ","%20");
                String detrdv=txtdetails.getText().toString().replace(" ","%20");

                StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://"+ActivityLogin.ip+"/medichelper/editconsultaion.php?&idrdv="+m.getId()+"&titrerdv="+titrerdv+"&detrdv="+detrdv+"", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("http://"+ActivityLogin.ip+"/medichelper/editconsultaion.php?&idrdv="+m.getId()+"&titrerdv="+titrerdv+"&detrdv="+detrdv+"");
                        Toast.makeText(getApplicationContext(), "Les informations ont bien été mis à jours !",
                                Toast.LENGTH_LONG).show();




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "PROBLEME DE CONEXION"+error.toString(),
                                Toast.LENGTH_LONG).show();
                        System.out.println("That didn't work!");



                    }
                });
                RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);









            }
        });












    }
});








































































    }}
