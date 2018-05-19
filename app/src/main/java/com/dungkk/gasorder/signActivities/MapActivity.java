package com.dungkk.gasorder.signActivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.dungkk.gasorder.MainActivity;
import com.dungkk.gasorder.R;
import com.dungkk.gasorder.extensions.PlaceAutocompleteAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // returned vars
    private Double lat;
    private Double lng;
    private LatLng pos;
    private String address;
    private String ward;

    // static vars
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final float DETAIL_ZOOM = 17f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(7.520995, 110.581036), new LatLng(23.911473, 100.385724));

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    private Button clear_btn;
    private ImageButton ibtn_submit;

    //vars
    private LatLng HaNoi = new LatLng(21.0084982, 105.8386711);
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private Marker marker;

    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dungkk.gasorder.R.layout.activity_map);
        mSearchText = (AutoCompleteTextView) findViewById(com.dungkk.gasorder.R.id.input_search);
        mGps = (ImageView) findViewById(com.dungkk.gasorder.R.id.ic_gps);
        clear_btn = (Button) findViewById(com.dungkk.gasorder.R.id.clear_btn);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchText.setText("");
                showSoftKeyboard();
            }
        });

        ibtn_submit = (ImageButton) findViewById(com.dungkk.gasorder.R.id.ibtn_submit);
        ibtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);

                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("address", address);
                intent.putExtra("ward", ward);
                Log.e("Passing", "location object");

                startActivity(intent);
            }
        });

        initMap();
        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");

        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            hideSoftKeyboard();

        } else {
            moveCamera(HaNoi, DEFAULT_ZOOM, "Ha Noi");
        }

        initMapOptions();
    }

    private void initMapOptions() {
        Log.d(TAG, "init: initializing");

        // handle with touching on Map: create a new marker
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideSoftKeyboard();
                mMap.clear();
                marker = mMap.addMarker(new MarkerOptions().position(latLng));
                geoLocate(latLng);
                pos = latLng;
                lat = pos.latitude;
                lng = pos.longitude;
                marker.setTitle(address + ", " + ward + " ward");
                marker.showInfoWindow();
                mSearchText.setText(address + ", " + ward + " ward");
            }
        });

        // Place Google API
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                String searchString = mSearchText.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geoLocate(searchString);
                }
                return false;
            }
        });

        // GPS button: get the current position
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        marker = mMap.addMarker(options);

        hideSoftKeyboard();
    }

    private void moveCameraToCurrentPos(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to current position: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        hideSoftKeyboard();
        mMap.clear();

    }

    private void animateCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "animateCamera: animating the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();   // clear Markers

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        marker = mMap.addMarker(options);
        marker.showInfoWindow();
        hideSoftKeyboard();
    }

    // get address using searchString
    private void geoLocate(String searchString) {
        Log.d(TAG, "geoLocate: geolocating by String");
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.getFeatureName() + ", " + address.getThoroughfare() + ", " + address.getLocality());

            animateCamera(new LatLng(address.getLatitude(), address.getLongitude()), DETAIL_ZOOM, address.getFeatureName() + " " + address.getThoroughfare() + ", " + address.getLocality());

        }
    }

    // get address using coordinates
    private void geoLocate(LatLng latLng) {
        Log.d(TAG, "geoLocate: geoLocating by LatLng");
        Geocoder geocoder = new Geocoder(MapActivity.this);

        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (list.size() > 0) {
            Address location = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + location.toString());

            address = location.getFeatureName() + " " + location.getThoroughfare();
            ward = location.getLocality();
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionsGranted) {

                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                Location location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null) {
                    Log.e(TAG, "GPS is on");
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    pos = new LatLng(lat, lng);
                    geoLocate(pos);
                    moveCameraToCurrentPos(pos, DETAIL_ZOOM);
                    mSearchText.setText(address + ", " + ward);

                    MarkerOptions options = new MarkerOptions().position(pos).title("Your location");
                    marker = mMap.addMarker(options);
                    marker.showInfoWindow();

                } else {
                    locationManager.requestLocationUpdates(bestProvider, 100, 0, this);
                }
            } else {
                getLocationPermission();
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;

                }
            }
        }
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    // Google places API autocomplete suggestions

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeID = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeID);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: place query did not complete");
                places.release();
                return;
            }
            final Place place = places.get(0);

            Log.d(TAG, "onResult: place details: " + place.getName());

            address = place.getName().toString();
            pos = place.getLatLng();

            Geocoder geocoder = new Geocoder(MapActivity.this);

            List<Address> list = new ArrayList<>();

            try {
                list = geocoder.getFromLocation(pos.latitude, pos.longitude, 1);

            } catch (IOException e) {
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
            }
            if (list.size() > 0) {
                Address location = list.get(0);
                Log.d(TAG, "geoLocate: found a location: " + address.toString());
                lat = location.getLatitude();
                lng = location.getLatitude();
                ward = location.getLocality();
                Log.d(TAG, "ward: " + ward);
                animateCamera(pos, DETAIL_ZOOM, address + ", " + ward + " ward");
            }

            places.release();
        }
    };

    @Override
    public void onPause() {

        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
