package com.semicolon.uber.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.semicolon.uber.Adapters.PlaceAutocompleteAdapter;
import com.semicolon.uber.R;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private final int DISPLACEMENT = 10;
    private final String FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int ERROR_DIALOG = 9001;
    private final int PERMISSION_REQ = 121;
    private Location myLocation;
    private Marker myMarker;
    private String country_code="sa";
    private final float zoom=13f;
    private boolean isGranted = false;
    private AutoCompleteTextView search;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private AutocompleteFilter filter=null;
    private GeoDataClient geoDataClient;
    private LatLngBounds latLngBounds = new LatLngBounds(
            new LatLng(-33.880490, 151.184363),
            new LatLng(-33.858754, 151.229596));

    private String destination_name="";
    private LatLng destinationlatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        if (isServiceOk()) {
            CheckPermission();
            if (isGranted==false)
            {
                initMap();

            }
        }


    }


    private void initView() {

        /////////////////////////////////////////////////////////////////
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /////////////////////////////////////////////////////////////////
        geoDataClient = Places.getGeoDataClient(this,null);
        search = findViewById(R.id.search);
        filter = new AutocompleteFilter.Builder().setCountry(country_code).build();
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(HomeActivity.this,geoDataClient,latLngBounds,filter);
        search.setAdapter(placeAutocompleteAdapter);
        search.setOnItemClickListener(itemClickListener);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE
                        ||i==EditorInfo.IME_ACTION_SEARCH
                        ||keyEvent.getAction()==KeyEvent.ACTION_DOWN
                        ||keyEvent.getAction()==KeyEvent.KEYCODE_ENTER)

                {

                }
                return false;
            }
        });

    }

    private void initMap() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.uber_style_map));
            buildGoogleApiClient();
            Log.e("initMap","map");

        }
    }

    private boolean isServiceOk() {
        int availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (availability == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availability)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeActivity.this, availability, ERROR_DIALOG);
            dialog.show();
        } else {
            return false;
        }
        return false;
    }

    private void CheckPermission() {
        String[] permissions = {FINE_LOC};
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), FINE_LOC) == PackageManager.PERMISSION_DENIED)
        {
            isGranted = true;
            initMap();
        } else {
            isGranted = false;
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQ);
            Log.e("2","2");



        }
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isGranted = false;
        if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_DENIED) {
                    Log.e("3","3");
                    isGranted = false;
                    return;
            }
            Log.e("4","4");
                isGranted=true;
                initMap();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {

        } else if (id == R.id.register_drive) {

        } else if (id == R.id.about_app) {

        } else if (id == R.id.contactUs) {

        } else if (id == R.id.send) {

        } else if (id == R.id.rule) {

        }
        else if (id == R.id.share) {

        }
        else if (id == R.id.logout) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        Log.e("myloc1",myLocation.getLatitude()+"  _  "+myLocation.getLatitude());

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationRequest();
        startLocationUpdate();
        Log.e("connected","connected");

    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation!=null)
        {
            myLocation = lastLocation;
            myMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(myLocation.getLatitude(),myLocation.getLongitude())));

                    AddMyMarker(myLocation);
            Log.e("myloc1",myLocation.getLatitude()+"  _  "+myLocation.getLatitude());


        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);

    }

    private void AddMyMarker(Location myLocation) {
        if (myMarker!=null)
        {
            myMarker.remove();

            Geocoder geocoder = new Geocoder(HomeActivity.this);
            try {
                List<Address> addressList = geocoder.getFromLocation(myLocation.getLatitude(),myLocation.getLongitude(),1);
                if (addressList.size()>0)
                {
                    try {
                        Address address = addressList.get(0);
                        if (address!=null)
                        {
                            String loc = address.getLocality();
                            country_code = address.getCountryCode();
                            filter = new AutocompleteFilter.Builder()
                                    .setCountry(country_code)
                                    .build();
                            placeAutocompleteAdapter = new PlaceAutocompleteAdapter(HomeActivity.this,geoDataClient,latLngBounds,filter);
                            search.setAdapter(placeAutocompleteAdapter);
                            if (loc!=null|| TextUtils.isEmpty(loc))
                            {
                                myMarker = mMap.addMarker(new MarkerOptions()
                                        .title(loc)
                                        .position(new LatLng(myLocation.getLatitude(),myLocation.getLongitude()))
                                );

                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),myLocation.getLongitude()),zoom));

                            }else
                                {
                                    myMarker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(myLocation.getLatitude(),myLocation.getLongitude()))
                                    );
                                }
                        }else
                            {
                                myMarker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(myLocation.getLatitude(),myLocation.getLongitude()))
                                );
                            }
                    }
                    catch (NullPointerException e){}
                    catch (Exception e){}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient!=null)
        {
            googleApiClient.connect();
        }
    }




    private void HideKeyBoard()
    {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(search.getWindowToken(),0);
    }
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AutocompletePrediction item = placeAutocompleteAdapter.getItem(i);
            String place_id = item.getPlaceId();
            PendingResult<PlaceBuffer> bufferPendingResult = Places.GeoDataApi.getPlaceById(googleApiClient,place_id);
            bufferPendingResult.setResultCallback(resultCallback);

        }
    };

    private ResultCallback<PlaceBuffer> resultCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            try {
                if (!places.getStatus().isSuccess())
                {
                    places.release();
                    return;
                }
                Place place = places.get(0);
                try
                {
                    HideKeyBoard();
                    destinationlatLng = place.getLatLng();
                    destination_name = place.getName()+","+place.getAddress();



//                    getDirection(mylatLng,latLng);
                    //dist = distance(mylatLng.latitude,mylatLng.longitude,latLng.latitude,latLng.longitude);


                }
                catch (NullPointerException e) {
                }


                places.release();
            }catch (NullPointerException e)
            {

            }

        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleApiClient!=null)
        {
            if (locationRequest!=null)
            {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);

            }
            googleApiClient.disconnect();
        }


    }
}
