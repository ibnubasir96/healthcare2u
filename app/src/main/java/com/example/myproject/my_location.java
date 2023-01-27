package com.example.myproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.LocationRequest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.text.DateFormat;
import java.util.Calendar;


import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class my_location extends AppCompatActivity {
    TextView tvcoords, tvaddress;
    private LocationCallback locationCallback;
    LocationRequest locationRequest;
    Button location_button;
    Button map_button;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Toast.makeText(this,"Home Menu",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(this,SecondActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_aboutus:
                Toast.makeText(this,"About Us Menu",Toast.LENGTH_SHORT).show();
                Intent intent2 =new Intent(this,menu_aboutus.class);
                startActivity(intent2);
                return true;
            case R.id.signout:
                Toast.makeText(this,"Signed Out",Toast.LENGTH_SHORT).show();
                Intent intent3 =new Intent(this,MainActivity.class);
                startActivity(intent3);
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        finish();
                        startActivity(new Intent(my_location.this,MainActivity.class));
                    }
                });

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    String[] perms = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.INTERNET"
            , "android.permission.ACCESS_NETWORK_STATE"};

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.timedate2);
        textViewDate.setText(currentDate);

        map_button=(Button) findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),mapMarker.class);
                startActivity(intent);

            }
        });

        location_button = (Button) findViewById(R.id.location_button);
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),my_location.class);
                startActivity(intent);

            }
        });

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HealthCare2U");

        tvcoords = (TextView) findViewById(R.id.tvcoords);
        tvaddress = (TextView) findViewById(R.id.tvaddress);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }


        locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(2000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(getApplicationContext(), "Unable to detect location", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    tvcoords.setText("" + lat + "," + lon);

                }
            }
        };


        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                tvcoords.setText("" + lat + "," + lon);

                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> addressList= null;
                try {
                    addressList = geocoder.getFromLocation(lat,lon,1);

                    Address address=addressList.get(0);
                    String line=address.getAddressLine(0);
                    String area=address.getAdminArea();
                    String locality=address.getLocality();
                    String country=address.getCountryName();
                    String postcode=address.getPostalCode();

                    tvaddress.setText(line+"\n"+area+"\n"+locality+"\n"+postcode+"\n"+country);
                } catch (IOException e) {
                    e.printStackTrace();
                }








            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        startLocationUpdates();

    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}