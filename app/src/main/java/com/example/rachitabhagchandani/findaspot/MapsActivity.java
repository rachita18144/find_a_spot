package com.example.rachitabhagchandani.findaspot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

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
    PlaceAutocompleteFragment placeAutoComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        nearby= (Button) findViewById(R.id.nearby);
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("SEARCH");
        Log.d("saumya", String.valueOf(placeAutoComplete.getId()));
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.d("saumya", "Place selected: " + place.getName());
                getLatLongFromAddress((String) place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.d("saumya", "An error occurred: " + status);
            }
        });

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


    }

    @Override
    public void onLocationChanged(Location location)
    {
        mMap.clear();
        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        mp.title("my position");
        mMap.addMarker(mp);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
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

    private void getLatLongFromAddress(String address)
    {
        double lat= 0.0, lng= 0.0;
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0)
            {
              //  Log.d("saumya","we have got it");
             lat = addresses.get(0).getLatitude();
              lng= addresses.get(0).getLongitude();
                for ( Address a : addresses )
                { mMap.addMarker( new MarkerOptions().position( new LatLng( a.getLatitude(), a.getLongitude() ) ).title( "Hello" ).snippet( "Description about me!" ) );}
                Log.d("Latitude", ""+lat);
                Log.d("Longitude", ""+lng);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
}

