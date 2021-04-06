package com.example.testppe.Places;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.testppe.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Details extends AppCompatActivity implements OnMapReadyCallback {
    MapFragment googleMap;
    TextView nameInDetail, vicinityInDetail, ratingInDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");

        setContentView(R.layout.details);
        /*googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                R.id.mapFragment)).getMapAsync(this);*/
        googleMap = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        googleMap.getMapAsync(this);
        nameInDetail = (TextView) findViewById(R.id.nameInDetail);
        vicinityInDetail = (TextView) findViewById(R.id.vicinityInDetail);
        ratingInDetail = (TextView) findViewById(R.id.ratingInDetail);

        double lat = Double.valueOf((getIntent().getStringExtra("lat")));
        double lon = Double.valueOf((getIntent().getStringExtra("lon")));
        String name = getIntent().getStringExtra("name");
        String vicinity = getIntent().getStringExtra("vicinity");
        String userRating = " " + getIntent().getStringExtra("rating") + "/5";

        LatLng latlong = new LatLng(lat, lon);
       // googleMap.addMarker(new MarkerOptions().position(latlong).title(name));
       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));
        nameInDetail.setText(name);
        vicinityInDetail.setText(vicinity);
        ratingInDetail.setText(userRating);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {
//DO WHATEVER YOU WANT WITH GOOGLEMAP
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }
}