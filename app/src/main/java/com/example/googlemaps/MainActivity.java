package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.PDFActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech t1;
    EditText ed1;
    Button b1;

    private static final  String Tag = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001 ;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=findViewById(R.id.editText);
        b1=findViewById(R.id.text_button);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PDFActivity.class);

                startActivity(intent);
            }
        });


        if (isServicesOk()){
            init();
        }
    }


    public void init(){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);


    }
    public boolean isServicesOk(){
        Log.d(Tag , "isServicesOk: Checking google service version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available== ConnectionResult.SUCCESS){
            Log.d(Tag , "isServicesOk: Google Play Services is working");
            return true ;


        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(Tag , "isServicesOk: an error occured but we can fix");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog .show();


        }else {
            Toast.makeText(this, "You can make map request", Toast.LENGTH_SHORT).show();
        }
        return false ;
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }



}