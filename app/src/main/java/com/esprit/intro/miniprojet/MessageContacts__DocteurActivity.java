package com.esprit.intro.miniprojet;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

public class MessageContacts__DocteurActivity extends AppCompatActivity {
    ListView lspatients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_contacts___docteur);
        lspatients = (ListView) findViewById(R.id.LVContacts);
        final ArrayList<Contact> lp1=new ArrayList<>();
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/getallpatientsHavingMessages.php?Userid="+ActivityLogin.patientConnecté.getId(),
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length()==0) {Toast.makeText(getApplicationContext(), "vide!",Toast.LENGTH_LONG).show();}


                            else {

                                for (int i = 0; i < jsonarray.length(); i++) {

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String prenom = jsonobject.getString("Prenom_User");
                                    String nom = jsonobject.getString("Nom_User");
                                    Long idPatient=Long.valueOf(jsonobject.getString("Id_User"));

                                    Contact r=new Contact();
                                    r.setIdPatient(idPatient);
                                    r.setNompatient(nom);
                                    r.setPrenompatient(prenom);

                                    lp1.add(r);

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AdapterContact adapter=new AdapterContact(getApplicationContext(), R.layout.itemcontact,lp1);
                        lspatients.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }
}
