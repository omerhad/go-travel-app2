package il.co.expertize.emailauthfirebase.UI;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import il.co.expertize.emailauthfirebase.Data.ITravelDataSource;
import il.co.expertize.emailauthfirebase.Data.TravelFirebaseDataSource;
import il.co.expertize.emailauthfirebase.Entities.Travel;

public class myService extends Service {
    Integer sum = 0;
    static Integer numOfTravel = 0;
    Integer countOfTravel = 0;
    ITravelDataSource travelDataSource;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference travels = firebaseDatabase.getReference("ExistingTravels");
    boolean isThreadOn = false;
    public final String TAG = "myService";

    @Override
    public void onCreate() {
        super.onCreate();
        travelDataSource = TravelFirebaseDataSource.getInstance();
        Toast.makeText(this,"onCreate", Toast.LENGTH_LONG).show();
        Log.d(TAG," onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countOfTravel=0;
                Intent intent1=new Intent("Add_travel");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        countOfTravel++;
                    }
                    if (countOfTravel>numOfTravel){
                        intent1.setAction("com.javacodegeeks.android.A_NEW_TRAVEL");
                        sendBroadcast(intent1);
                        numOfTravel=countOfTravel;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if(!isThreadOn)
//        {
//            isThreadOn = true;
//            SumCalc sumCalc = new SumCalc();
//            sumCalc.start();
//        }
//        Toast.makeText(this,"onStartCommand.+ startId:="+startId+ " sum is: " + sum, Toast.LENGTH_LONG).show();
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
   public void printMassage(){
        Toast.makeText(this,"Added a new travel", Toast.LENGTH_LONG).show();
    }
}
