package com.example.appcanhbaodotquy.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.appcanhbaodotquy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_device);

        // 🔹 Nhận dữ liệu vị trí từ Intent
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 16.077439);
        longitude = intent.getDoubleExtra("longitude", 108.213008);

        // 🔹 Load Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_container);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 🔹 Kiểm tra nếu có vị trí hợp lệ thì đặt marker
        if (latitude != 0.0 && longitude != 0.0) {
            LatLng location = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location).title("Vị trí của bạn"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 9));
        }
    }
}
