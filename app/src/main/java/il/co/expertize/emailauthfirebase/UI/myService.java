package il.co.expertize.emailauthfirebase.UI;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class myService extends Service {
    Integer sum = 0;
    boolean isThreadOn = false;
    public final String TAG = "myService";

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"onCreate", Toast.LENGTH_LONG).show();
        Log.d(TAG," onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isThreadOn)
        {
            isThreadOn = true;
            SumCalc sumCalc = new SumCalc();
            sumCalc.start();
        }
        Toast.makeText(this,"onStartCommand.+ startId:="+startId+ " sum is: " + sum, Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isThreadOn)
            Toast.makeText(this,"onDestroy. sum is:" + sum, Toast.LENGTH_LONG).show();
        sum=0;
        Log.d(TAG," onDestroy");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public class SumCalc extends Thread {

        public void run() {
            sum = 0;
            for(Integer idx = 0; idx< 10099; idx ++)
            {
                sum++;
            }
            isThreadOn = false;
        }
    }
}
