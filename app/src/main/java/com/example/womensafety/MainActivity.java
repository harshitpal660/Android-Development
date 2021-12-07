package com.example.womensafety;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.app.AlertDialog;

import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.telephony.SmsManager;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SensorEventListener, Serializable {


    boolean isAccelerometerSensorAvailable, notfirstTime = false;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private float lastX;
    private float lastY;
    private float lastZ;
    private Vibrator vibrator;
    Button buttton;
    ArrayList<String> contactList = new ArrayList<>();
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getApplicationContext().startService(new Intent(this, UpdateService.class));
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Log.d("Women Safety", "Hello");
        buttton = findViewById(R.id.button);

        buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                 else{
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                 }
            }
        });

        final LottieAnimationView add_animation = findViewById(R.id.add_animation);
        add_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_animation.setSpeed(1);
                add_animation.playAnimation();
            }
        });
//
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        } else {
            isAccelerometerSensorAvailable = false;
        }
    }


    public void getLocation() {
        final String[] message = new String[1];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        message[0] = "Help Help Help I am in Danger \nMy Location ->" +"\nhttp://maps.google.com/maps?saddr="+addresses.get(0).getLatitude() +","+ addresses.get(0).getLongitude();

                        Action(message[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void addNumber(View view){
        Log.d("Women Safety","Adding Number");
        EditText editText;
        Toast.makeText(this, "Adding Number", Toast.LENGTH_SHORT).show();
        editText = findViewById(R.id.editTextPhone2);
        String num = editText.getText().toString();
        contactList.add(num);

    }
    public void removeNumber(View view){
        EditText editText;
        Toast.makeText(this, "Removing Number", Toast.LENGTH_SHORT).show();
        editText = findViewById(R.id.editTextPhone2);
        String num = editText.getText().toString();
        contactList.remove(num);
    }
    public void openList(View view){
        ArrayAdapter<String> adapter;
        TextView close;
        ListView li;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        builder.setTitle("Emergency Contacts");
        View row = getLayoutInflater().inflate(R.layout.activity_list,null);
        li = row.findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        li.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        alertDialog.setView(row);
        AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.list_bg);
        dialog.show();
        close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> dialog.dismiss());
    }
    public void Action(String msg){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (contactList.size() != 0) {
                SmsManager sms = SmsManager.getDefault();
                for (int i = 0; i < contactList.size(); i++)
                    sms.sendTextMessage(contactList.get(i), null, msg , null, null);
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Add Contacts", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float currentX = sensorEvent.values[0];
        float currentY = sensorEvent.values[1];
        float currentZ = sensorEvent.values[2];
        if (notfirstTime){
            float xdifference = Math.abs(lastX - currentX);
            float ydifference = Math.abs(lastY - currentY);
            float zdifference = Math.abs(lastZ - currentZ);
            float shakeThreshold = 5f;
            if ((xdifference > shakeThreshold && ydifference > shakeThreshold) ||
            (xdifference > shakeThreshold && zdifference > shakeThreshold) || (ydifference > shakeThreshold && zdifference > shakeThreshold)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getLocation();
                }
            }

        }
        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        notfirstTime = true;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume(){
        super.onResume();
        if (isAccelerometerSensorAvailable)
            sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (isAccelerometerSensorAvailable)
            sensorManager.unregisterListener(this);
    }

}