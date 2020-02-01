package com.esprit.intro.miniprojet;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.JsonElement;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.esprit.intro.miniprojet.ActivityLogin.rdvs;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccueilDocteur3 extends Fragment implements OnMapReadyCallback ,MapboxMap.OnMapClickListener {



    public AccueilDocteur3() {
        // Required empty public constructor
    }
  Double longitude=2.333;
    Double latitude=2.333;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Marker featureMarker;
    Double lat ;
    Double log ;
    EditText ed_nomcab,ed_adressecab,ed_telcab,ed_nomdoc,ed_prenomdoc,ed_infodoc,ed_login,ed_password;
    Button update;
    ImageView imgajout;
    boolean intituleOk,specialiteOk,adresseOk,telOk,intituleOkd,prenomOk,passwordOk,loginOk;
    boolean containsDigit;
    String lastLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mapbox.getInstance(getContext(), getString(R.string.access_token));
        View view =inflater.inflate(R.layout.fragment_accueil_docteur3, container, false);
        mapView = view.findViewById(R.id.mapView);

        ed_nomcab=view.findViewById(R.id.ednomcab);
        ed_adressecab=view.findViewById(R.id.edadressecab);
        ed_telcab=view.findViewById(R.id.edtel);
        ed_nomdoc=view.findViewById(R.id.ednomdoc);
        ed_prenomdoc=view.findViewById(R.id.edprenomdoc);
        ed_infodoc=view.findViewById(R.id.edinfodoc);
        ed_login =view.findViewById(R.id.edlogin);
        ed_password=view.findViewById(R.id.edpassword);
        update=view.findViewById(R.id.update);


        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this) ;

        final RequestQueue queue = Volley.newRequestQueue(this.getContext());




        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/getelmentdoctoupdate.php?iduser=" + ActivityLogin.patientConnecté.getId(),
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        System.out.println(response);
                        try {
                            jsonarray = new JSONArray(response);

                            if (jsonarray.length() == 0) {
                                Toast.makeText(getContext().getApplicationContext(), "vide!", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                                    ed_nomcab.setText(jsonobject.getString("Nom_Cabinet"));
                                    ed_adressecab.setText(jsonobject.getString("Emplacement_Cabinet"));
                                    ed_telcab.setText(jsonobject.getString("NumTel_Cabinet"));
                                    ed_nomdoc.setText(jsonobject.getString("Nom_User"));
                                    ed_prenomdoc.setText(jsonobject.getString("Prenom_User"));
                                    ed_infodoc.setText(jsonobject.getString("Info_Medecin"));
                                    longitude=jsonobject.getDouble("longitude");
                                    latitude=jsonobject.getDouble("Latitude");
                                    ed_login.setText(jsonobject.getString("Login_User"));
                                    lastLogin=jsonobject.getString("Login_User");
                                    ed_password.setText(jsonobject.getString("Password_User"));


  }


                            }  } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("That didn't work!");

                            }

                    });

        queue.add(stringRequest);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomcabinet=ed_nomcab.getText().toString().replace(" ","%20");
                String numcab=ed_telcab.getText().toString().replace(" ","%20");
                String infotxt=ed_infodoc.getText().toString().replace(" ","%20");
                String nom=ed_nomdoc.getText().toString().replace(" ","%20");
                String prenom=ed_prenomdoc.getText().toString().replace(" ","%20");
                String address=ed_adressecab.getText().toString().replace(" ","%20");
                String login=ed_login.getText().toString().replace(" ","%20");
                        String pw=ed_password.getText().toString().replace(" ","%20");
              if(log==null ||lat==null){log=longitude;
                          lat=latitude;
}


                if (ed_telcab.getText().toString().equals("")) {
                    ed_telcab.setError("enter telephone de cabinet");
                    ed_telcab.requestFocus();
                    telOk = false;
                } else if (!android.text.TextUtils.isDigitsOnly(ed_telcab.getText().toString())) {
                    ed_telcab.setError("telephone doit contenir que des chiffres");
                    ed_telcab.requestFocus();
                    telOk = false;
                } else {
                    telOk = true;
                }

                if (ed_adressecab.getText().toString().equals("")) {
                    ed_adressecab.setError("enter l'adresse de la cabinet");
                    ed_adressecab.requestFocus();
                    adresseOk = false;
                } else {
                    adresseOk = true;
                }

                if (ed_infodoc.getText().toString().equals("")) {
                    ed_infodoc.setError("enter la specialité");
                    ed_infodoc.requestFocus();
                    specialiteOk = false;
                } else if (containsDigit(ed_infodoc.getText().toString()) == true) {
                    ed_infodoc.setError("la specialité ne doit pas contenir de chiffres");
                    ed_infodoc.requestFocus();
                    specialiteOk = false;
                } else {
                    specialiteOk = true;
                }

                if (ed_nomcab.getText().toString().equals("")) {
                    ed_nomcab.setError("enter le nom de votre cabinet");
                    ed_nomcab.requestFocus();
                    intituleOk = false;
                } else if (containsDigit(ed_nomcab.getText().toString()) == true) {
                    ed_nomcab.setError("le nom ne doit pas contenir de chiffres");
                    ed_nomcab.requestFocus();
                    intituleOk = false;
                } else {
                    intituleOk = true;
                }

                if (ed_nomdoc.getText().toString().equals("")) {
                    ed_nomdoc.setError("enter votre nom ");
                    ed_nomdoc.requestFocus();
                    intituleOkd = false;
                } else if (containsDigit(ed_nomdoc.getText().toString()) == true) {
                    ed_nomdoc.setError("le nom ne doit pas contenir de chiffres");
                    ed_nomdoc.requestFocus();
                    intituleOkd = false;
                } else {
                    intituleOkd = true;
                }

                if (ed_prenomdoc.getText().toString().equals("")){
                    ed_prenomdoc.setError("enter votre prenom");
                    ed_prenomdoc.requestFocus();
                    prenomOk=false;
                }
                else if(containsDigit(ed_prenomdoc.getText().toString())==true){
                    ed_prenomdoc.setError("prenom ne doit pas contenir de chiffres");
                    ed_prenomdoc.requestFocus();
                    prenomOk=false;
                }
                else{
                    prenomOk=true;
                }

                if (ed_password.getText().toString().equals("")){
                    ed_password.setError("enter votre mot de passe");
                    ed_password.requestFocus();
                    passwordOk=false;
                }else{
                    passwordOk=true;
                }

                if (ed_login.getText().toString().equals("")){
                    ed_login.setError("enter votre login");
                    ed_login.requestFocus();
                    loginOk=false;
                }
                else if(ed_login.getText().toString().equals(lastLogin)){
                    loginOk=true;
                }
                else{
                    System.out.println("http://"+ActivityLogin.ip+"/medichelper/LoginVerif.php?login="+ed_login.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ActivityLogin.ip+"/medichelper/LoginVerif.php?login="+ed_login.getText().toString(), new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonarray = null;
                            System.out.println(response);
                            try {
                                jsonarray = new JSONArray(response);

                                if (jsonarray.length()==0) {
                                    loginOk=true;
                                }
                                else{
                                    ed_login.setError("login existe deja");
                                    ed_login.requestFocus();
                                    loginOk=false;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That didn't work!");
                            System.out.println(error.toString());
                        }
                    });

                    queue.add(stringRequest);

                    //loginOk=true;
                }

                if (intituleOk&specialiteOk&adresseOk&telOk&intituleOkd&prenomOk&passwordOk&loginOk) {


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/updatedoc.php?idCabinet=" + ActivityLogin.patientConnecté.getCabinet() + "&nomcabinet=" + nomcabinet + "&iddoc=" + ActivityLogin.patientConnecté.getId() + "&logitude=" + log + "&latitude=" + lat + "&nom=" + nom + "&prenom=" + prenom + "&numtel=" + numcab + "&emplacment=" + address + "&info=" + infotxt + "&login=" + login + "&pw=" + pw + "", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("it works");
                            Toast.makeText(getContext().getApplicationContext(), "Les informations ont bien été mis à jours !",
                                    Toast.LENGTH_LONG).show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext().getApplicationContext(), "errur",
                                    Toast.LENGTH_LONG).show();
                            System.out.println("That didn't work!");


                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });



        return view;
    }
/*
    @Override
    public void onMapReady(MapboxMap mapboxMap) {

       // LatLng ll=new LatLng(latitude,longitude);
        AccueilDocteur3.this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        /*
        mapboxMap.addMarker(new MarkerOptions()
                .position(ll)
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet)));

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(ll)) // Sets the new camera position
                .zoom(2) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);


    }

*/
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }



    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        AccueilDocteur3.this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);
        LatLng ll=new LatLng(latitude,longitude);

        mapboxMap.addMarker(new MarkerOptions()
                .position(ll)
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet)));

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(ll)) // Sets the new camera position
                .zoom(2) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);



    }


    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (featureMarker != null) {
            mapboxMap.removeMarker(featureMarker);
        }

        final PointF pixel = mapboxMap.getProjection().toScreenLocation(point);
        lat=point.getLatitude();
        log=point.getLongitude();
        Toast.makeText(getContext().getApplicationContext(),"cor"+point.getLatitude()+"  "+point.getLongitude(),Toast.LENGTH_LONG).show();
        List<Feature> features = mapboxMap.queryRenderedFeatures(pixel);

        if (features.size() > 0) {
            Feature feature = features.get(0);

            String property;

            StringBuilder stringBuilder = new StringBuilder();
            if (feature.properties() != null) {
                for (Map.Entry<String, JsonElement> entry : feature.properties().entrySet()) {
                    stringBuilder.append(String.format("%s - %s", entry.getKey(), entry.getValue()));
                    stringBuilder.append(System.getProperty("line.separator"));
                }

                featureMarker = mapboxMap.addMarker(new MarkerOptions()
                        .position(point)
                        .title(getString(R.string.draw_marker_options_title_mis_a_jours))
                        .snippet(stringBuilder.toString())
                );

            } else {
                property = getString(R.string.draw_marker_options_title_mis_a_jours);
                featureMarker = mapboxMap.addMarker(new MarkerOptions()
                        .position(point)
                        .snippet(property)
                );
            }
        } else {
            featureMarker = mapboxMap.addMarker(new MarkerOptions()
                    .position(point)
                    .snippet(getString(R.string.draw_marker_options_title_mis_a_jours))
            );
        }
        mapboxMap.selectMarker(featureMarker);
    }


    public final boolean containsDigit(String s) {
        containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }
}
