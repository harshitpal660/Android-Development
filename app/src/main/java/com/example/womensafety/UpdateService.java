package com.example.womensafety;


import android.app.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class UpdateService extends Service {
    @Override
    public void onCreate(){
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new PowerHookReceiver();
        registerReceiver(mReceiver,filter);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart (Intent intent, int startID) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
//        ArrayList<String> contactList = (ArrayList<String>) intent.getSerializableExtra("key");
        if (!screenOn) {
//            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                if (contactList.size() != 0) {
//                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//                    String message = "Please Help me..!";
//                    SmsManager sms = SmsManager.getDefault();
//                    for (int i = 0; i < contactList.size(); i++)
//                        sms.sendTextMessage(contactList.get(i), null, message, null, null);
//                    Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                } else {

                }
//            }
//
//        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
