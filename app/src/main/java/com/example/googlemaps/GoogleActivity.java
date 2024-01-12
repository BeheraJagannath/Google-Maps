package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.models.ResponseSearch;
import com.example.models.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleActivity extends AppCompatActivity  {
    private Handler handler;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private AutoCompleteTextView edit1 ;
    private AutoSuggestAdapter autoSuggestAdapter ;

    ArrayList<ResponseSearch> responseSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
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


}