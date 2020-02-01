package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;
import java.util.Map;

public class ajoucabinet extends AppCompatActivity implements OnMapReadyCallback,MapboxMap.OnMapClickListener {
    EditText nom,adresse,info,tel;
    private MapView mapView;
    private MapboxMap mapboxMap;
    Double lat ;
    Double log ;
    private Marker featureMarker;
    boolean intituleOk,specialiteOk,adresseOk,telOk;
    boolean localisationchoisie;
    boolean containsDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));

        setContentView(R.layout.ajoutcab2);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this) ;
        nom=(EditText)findViewById(R.id.nomcab);
        adresse=(EditText)findViewById(R.id.adressecab);
        info=(EditText)findViewById(R.id.prenommedecin);
        tel=(EditText)findViewById(R.id.telcab);
        Button ajouter= (Button) findViewById(R.id.ajoutercabinet);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String nomtxt=nom.getText().toString().replace(" ","%20");
                String adressetxt=adresse.getText().toString().replace(" ","%20");
                 String infotxt=info.getText().toString().replace(" ","%20");
                String teltxt=tel.getText().toString().replace(" ","%20");
                System.out.println("helloooo************");
                System.out.println();

                if (localisationchoisie == false) {
                    Toast.makeText(getApplicationContext(), "veuillez definir la localisation de votre cabinet medical", Toast.LENGTH_LONG).show();
                }

                if (tel.getText().toString().equals("")) {
                    tel.setError("enter telephone de cabinet");
                    tel.requestFocus();
                    telOk = false;
                } else if (!android.text.TextUtils.isDigitsOnly(tel.getText().toString())) {
                    tel.setError("telephone doit contenir que des chiffres");
                    tel.requestFocus();
                    telOk = false;
                } else {
                    telOk = true;
                }

                if (adresse.getText().toString().equals("")) {
                    adresse.setError("enter l'adresse de la cabinet");
                    adresse.requestFocus();
                    adresseOk = false;
                } else {
                    adresseOk = true;
                }

                if (info.getText().toString().equals("")) {
                    info.setError("enter la specialité");
                    info.requestFocus();
                    specialiteOk = false;
                } else if (containsDigit(info.getText().toString()) == true) {
                    info.setError("la specialité ne doit pas contenir de chiffres");
                    info.requestFocus();
                    specialiteOk = false;
                } else {
                    specialiteOk = true;
                }

                if (nom.getText().toString().equals("")) {
                    nom.setError("enter le nom de votre cabinet");
                    nom.requestFocus();
                    intituleOk = false;
                } else if (containsDigit(nom.getText().toString()) == true) {
                    nom.setError("le nom ne doit pas contenir de chiffres");
                    nom.requestFocus();
                    intituleOk = false;
                } else {
                    intituleOk = true;
                }

                if (specialiteOk && telOk && adresseOk && intituleOk && localisationchoisie) {


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ActivityLogin.ip + "/medichelper/ajoutercabinet.php?nom=" + nomtxt + "&adresse=" + adressetxt + "&numtel=" + teltxt + "&info=" + infotxt + "&lat=" + lat + "&log=" + log + "", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("it works");
                            Toast.makeText(getApplicationContext(), "Cabinet ajouté",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ajoucabinet.this, Inscriptionmedecin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "errur",
                                    Toast.LENGTH_LONG).show();
                            System.out.println("That didn't work!");


                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(ajoucabinet.this);
                    requestQueue.add(stringRequest);
                }
            }
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
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
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
       ajoucabinet.this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);

    }


    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (featureMarker != null) {
            mapboxMap.removeMarker(featureMarker);
        }

        final PointF pixel = mapboxMap.getProjection().toScreenLocation(point);
        lat=point.getLatitude();
        log=point.getLongitude();
        Toast.makeText(getApplicationContext(),"cor"+point.getLatitude()+"  "+point.getLongitude(),Toast.LENGTH_LONG).show();
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
                        .title(getString(R.string.draw_marker_options_title))
                        .snippet(stringBuilder.toString())
                );

            } else {
                property = getString(R.string.draw_marker_options_title);
                featureMarker = mapboxMap.addMarker(new MarkerOptions()
                        .position(point)
                        .snippet(property)
                );
            }
        } else {
            featureMarker = mapboxMap.addMarker(new MarkerOptions()
                    .position(point)
                    .snippet(getString(R.string.draw_marker_options_title))
            );
        }
        mapboxMap.selectMarker(featureMarker);
        localisationchoisie=true;
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
