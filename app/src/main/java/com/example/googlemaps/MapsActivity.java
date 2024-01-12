package com.example.googlemaps;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.models.ResponseSearch;
import com.example.models.RetrofitClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback  {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CROSS_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_CODE = 1234;
    private static final float DEFAULT_ZOOM = 12.0f;
    public static Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
//    private EditText address ;
    private ImageView gps;
    String searc ;
    MarkerOptions place1  ;

    private Handler handler;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private AutoCompleteTextView edit1 ;
    private AutoSuggestAdapter autoSuggestAdapter ;

    ArrayList<ResponseSearch> responseSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        address = findViewById(R.id.adress);
        gps = findViewById(R.id.gps);

        getlocationPermission();
        initMap();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        SearchPlace();


    }

    private void SearchPlace() {
        edit1 = findViewById(R.id.edit_1);

        autoSuggestAdapter = new AutoSuggestAdapter(this, R.layout.list_item_search_auto, R.id.text1, responseSearch);
        final AutoCompleteTextView input = new AutoCompleteTextView(this);
        input.setTextColor(getResources().getColor(R.color.white));
        edit1.setThreshold(1);
        edit1.setAdapter(autoSuggestAdapter);

        edit1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }

        });

        edit1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GLocate(position);


            }

        });

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 2) {

                    handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                    handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                if (msg.what == TRIGGER_AUTO_COMPLETE) {

                    if (!TextUtils.isEmpty(edit1.getText())) {

                        SearchLocation(edit1.getText().toString());

                    }

                }
                return false;
            }

        });
    }

    private void SearchLocation(CharSequence text) {

        Call<List<ResponseSearch>> call = RetrofitClient.getInstance().getModel().getSearch(
                "application/x-www-form-urlencoded", text,
                "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94", "en-us", "value"
        );

        call.enqueue(new Callback<List<ResponseSearch>>() {
            @Override
            public void onResponse(Call<List<ResponseSearch>> call, Response<List<ResponseSearch>> response) {

                responseSearch = new ArrayList<>();
                List<ResponseSearch> search = response.body();

                if (search != null) {

                    responseSearch.addAll(search);
                    autoSuggestAdapter.setData(responseSearch);
                    autoSuggestAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<List<ResponseSearch>> call, Throwable t) {

            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.multiple_maps, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nonemap) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
        if (item.getItemId() == R.id.normalmap) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (item.getItemId() == R.id.satelite) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (item.getItemId() == R.id.maphybrid) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        if (item.getItemId() == R.id.terrain) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }

        return super.onOptionsItemSelected(item);
    }



    private void initMap() {
        gps.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        /*address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    GLocate();

                }
                return false;
            }
        });*/

    }

    private void GLocate(int position) {
        searc = responseSearch.get(position).getLocalizedName() ;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searc, 1);


        } catch (Exception e) {

        }
        if (list.size() > 0) {
            Address address1 = list.get(0);
            MoveCamera(new LatLng(address1.getLatitude(), address1.getLongitude()), DEFAULT_ZOOM, address1.getAddressLine(0));

        }
    }


    private void getlocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();

                getDeviceLocation();

            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_CODE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;

                            break;

                        }
                    }
                    mLocationPermissionGranted = true;

                }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng sydney = new LatLng(0, 0);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Now Gps is enable", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Denied Gps enable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Location lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                mMap.setMyLocationEnabled(true);
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);


                            }
                        } else {
                            Toast.makeText(MapsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    private void MoveCamera(LatLng latLng, float zoom, String title) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        place1 = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(place1);

        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


}
