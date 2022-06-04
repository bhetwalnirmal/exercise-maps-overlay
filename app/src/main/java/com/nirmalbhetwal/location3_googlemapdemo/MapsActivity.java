package com.nirmalbhetwal.location3_googlemapdemo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nirmalbhetwal.location3_googlemapdemo.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng toronto = new LatLng(43.65, -79.38);
        LatLng brampton = new LatLng(43.7315, -79.7624);
        LatLng mississauga = new LatLng(43.5890, -79.6441);
        LatLng vaughan = new LatLng(43.8563, -79.5085);
        mMap.addMarker(new MarkerOptions().position(toronto).title("Marker in Toronto").snippet("A"));
        mMap.addMarker(new MarkerOptions().position(brampton).title("Marker in Brampton").snippet("B"));
        mMap.addMarker(new MarkerOptions().position(mississauga).title("Marker in Mississauga").snippet("C"));
        mMap.addMarker(new MarkerOptions().position(vaughan).title("Marker in Vaughan").snippet("D"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brampton));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mississauga));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vaughan));

        LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();

        ArrayList<LatLng> markers = new ArrayList<LatLng>();
        markers.add(toronto);
        markers.add(brampton);
        markers.add(mississauga);
        markers.add(vaughan);

        for (LatLng marker : markers) {
            boundBuilder.include(marker);
        }
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10);
        LatLngBounds bounds = boundBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,  width, height, padding);
        mMap.animateCamera(cameraUpdate);

        PolylineOptions line1 = new PolylineOptions().add(toronto, mississauga)
                .width(5)
                .color(Color.RED);
        PolylineOptions line2 = new PolylineOptions().add(mississauga, brampton)
                .width(5)
                .color(Color.RED);
        PolylineOptions line3 = new PolylineOptions().add(brampton, vaughan)
                .width(5)
                .color(Color.RED);
        PolylineOptions line4 = new PolylineOptions().add(vaughan, toronto)
                .width(5)
                .color(Color.RED);

        line1.clickable(true);
        line2.clickable(true);
        line3.clickable(true);
        line4.clickable(true);
        mMap.addPolyline(line1);
        mMap.addPolyline(line2);
        mMap.addPolyline(line3);
        mMap.addPolyline(line4);

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(@NonNull Polyline polyline) {
                List<LatLng> points = polyline.getPoints();
                LatLng point1 = points.get(0);
                LatLng point2 = points.get(1);

                float[] results = {0};
                Location.distanceBetween(point1.longitude, point2.latitude, point2.latitude, point2.longitude, results);
            }
        });
    }
}