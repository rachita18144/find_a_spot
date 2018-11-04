package com.example.rachitabhagchandani.findaspot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    boolean l_enabled = false;
    String mode;
    LocationManager locationManager;
    Location location;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 0;
    Button nearby;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    boolean network_enabled,gps_enabled;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        nearby= (Button) findViewById(R.id.nearby);
        final Intent intent = new Intent(this,NearbyPlaces.class);
        //Handling click on NearBy Button
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(intent);
            }
        });
        mode = Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        Log.d("saumya", mode);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
         network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(network_enabled==false && gps_enabled == false)
        {
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
        else
            {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (gps_enabled) {
            Log.d("saumya", "lm not null");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("saumya", "permission granted");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d("saumya", " gloc not null" + latitude + " " + longitude);
                    LatLng current = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(current).title("Marker in curr loc"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            Log.d("saumya", "clicked");
                            MarkerOptions mp = new MarkerOptions();
                            mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
                            mp.title("my position");
                            mMap.addMarker(mp);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                            return false;
                        }
                    });

                } else if (location == null) {
                    Log.d("saumya", "gloc null");
                }

            } else {
                Log.d("saumya", "permission not there");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }

        }

        /*if (network_enabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            Log.d("saumya", "Network enabled");
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("saumya", "network loc is not null " + latitude + " " + longitude);
            } else {
                Log.d("saumya", "network loc is  null ");
            }

        }*/
    }

    @Override
    public void onLocationChanged(Location location)
    {
       // Log.d("saumya","on  loc changed");
        mMap.clear();
        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        mp.title("my position");
        mMap.addMarker(mp);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {
   Log.d("saumya","provider enabled method called");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("saumya","provider disabled method called");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("saumya","USER ACCEPTS PERMISSIONS");
                }
                else
                    { Log.d("saumya","USER DENIES PERMISSIONS");
                        Toast t = Toast.makeText(getApplicationContext(),"USER DENIED PERMISSIONS",Toast.LENGTH_LONG);
                    }
                return;
                }

        }
    }
}

