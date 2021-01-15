package il.co.expertize.emailauthfirebase.UI;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static com.google.android.material.snackbar.BaseTransientBottomBar.*;

public class myBroadcastReciever extends BroadcastReceiver {
    private pushInterface callback1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // Extract data included in the Intent
        CharSequence intentData = intent.getCharSequenceExtra("message");
        Toast.makeText(context, "Javacodegeeks received the Intent's message: " + intentData, Toast.LENGTH_LONG).show();

        if (intent.getAction().matches("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context, "BOOT_COMPLETED", Toast.LENGTH_LONG).show();
            // pass content text to show in SnackBar
            if (callback1 != null) {
                callback1.passText("BOOT_COMPLETED");
            } else {
                Log.e("log", "callback from UI is not registered yet..");
            }

        } else if (intent.getAction().matches("com.javacodegeeks.android.A_CUSTOM_INTENT")) {
            Toast.makeText(context, "A_CUSTOM_INTENT", Toast.LENGTH_LONG).show();
            if (callback1 != null) {
                callback1.passText("A_CUSTOM_INTENT");
            } else {
                Log.e("log", "callback from UI is not registered yet..");
            }

        } else if (intent.getAction().matches("android.intent.action.TIME_SET")) {
            Toast.makeText(context, "TIME_SET", Toast.LENGTH_LONG).show();
            if (callback1 != null) {
                callback1.passText("TIME_SET");
            } else {
                Log.e("log", "callback from UI is not registered yet..");
            }

        } else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Toast.makeText(context, "SMS_RECEIVED", Toast.LENGTH_LONG).show();
            if (callback1 != null) {
                callback1.passText("TIME_SET");
            } else {
                Log.e("log", "callback from UI is not registered yet..");
            }

        }

    }

    public void registerReceiver(pushInterface receiver) {
        this.callback1 = receiver;
    }

    public interface pushInterface {
        public void passText(String text);
    }

}

