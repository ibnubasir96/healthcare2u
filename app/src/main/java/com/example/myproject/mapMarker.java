package com.example.myproject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myproject.databinding.ActivityMapMarkerBinding;

import java.util.Vector;

public class mapMarker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapMarkerBinding binding;
    MarkerOptions marker;
    LatLng centerlocation;

    Vector<MarkerOptions> markerOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapMarkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        centerlocation = new LatLng(3.0, 101);

        markerOptions = new Vector<>();
        markerOptions.add(new MarkerOptions().title(" Klinik Shujai")
                .position(new LatLng(3.0436093508995654, 101.42674151180685 ))
                .snippet("Open: 24 Hour")
        );

        markerOptions.add(new MarkerOptions().title(" Klinik Ratna")
                .position(new LatLng(3.043043496233486, 101.42669792305352))
                .snippet("Open: 24 Hour")
        );

        markerOptions.add(new MarkerOptions().title("Klinik Siti")
                .position(new LatLng(3.0420133497908366, 101.4263201540874))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("Poliklinik Sungai Bertek")
                .position(new LatLng(3.0413024030842872, 101.42613126948966))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("KLINIK MEDIVIRON TELUK PULAI")
                .position(new LatLng(3.0415055307625543, 101.42562273403419))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("Poliklinik & Surgeri Dr. Halimah")
                .position(new LatLng(3.0375735524806533, 101.42525949442313))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("KLINIK AURORA SUNGAI UDANG KLANG")
                .position(new LatLng(3.0437610467083798, 101.41717231057))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("KLINIK DAN SURGERI DR.SRI")
                .position(new LatLng(3.040804061312825, 101.4344242785784))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("Klinik Sheela")
                .position(new LatLng(3.04086834368944, 101.43498217800342))
                .snippet("Open: 24 Hour")
        );
        markerOptions.add(new MarkerOptions().title("Poliklinik Zul")
                .position(new LatLng(3.036679267374042, 101.41164198440394))
                .snippet("Open: 24 Hour")
        );


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
        // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);

        }

        enableMyLocation();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation, 10));
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
        String perms[]={"android.permission.ACCESS_FINE_LOCATION"};

        // 2. Otherwise, request location permissions from the user.
        ActivityCompat.requestPermissions(this,perms,200);
    }
}