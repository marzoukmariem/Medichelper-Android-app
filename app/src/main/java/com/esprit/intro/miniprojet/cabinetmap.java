
package com.esprit.intro.miniprojet;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
public class cabinetmap extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));
        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_cabinetmap);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
       mapView.getMapAsync(this) ;



    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        final Intent intent = getIntent();
        final Double lat = intent.getDoubleExtra("lat",48.866667);
        final  Double log = intent.getDoubleExtra("log",2.333333);
        LatLng ll=new LatLng(lat,log);
        cabinetmap.this.mapboxMap = mapboxMap;
        mapboxMap.addMarker(new MarkerOptions()
                .position(ll)
                .title(getString(R.string.draw_marker_options_title))
                .snippet(getString(R.string.draw_marker_options_snippet)));

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(ll)) // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);


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
}