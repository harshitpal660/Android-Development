package com.example.womensafety;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerHookReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, UpdateService.class);
        context.startService(i);
    }
}
