package il.co.expertize.emailauthfirebase.Data.Repository;


import android.app.Application;
import android.location.Location;
import android.location.LocationManager;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

import il.co.expertize.emailauthfirebase.Data.HistoryDataSource;
import il.co.expertize.emailauthfirebase.Data.IHistoryDataSource;
import il.co.expertize.emailauthfirebase.Data.ITravelDataSource;
import il.co.expertize.emailauthfirebase.Data.TravelFirebaseDataSource;
import il.co.expertize.emailauthfirebase.Entities.Travel;

public class TravelRepository implements ITravelRepository {
    ITravelDataSource travelDataSource;
    FirebaseUser user;
    public FirebaseAuth mAuth;
    private IHistoryDataSource historyDataSource;
    private ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerRepository;
    List<Travel> travelList;
    List<Travel> travelList2=new LinkedList<Travel>();


    private MutableLiveData<List<Travel>> mutableLiveDataRegistered = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataCompany = new MutableLiveData<>();



    private static TravelRepository instance;
    public static TravelRepository getInstance(Application application) {
        if (instance == null)
            instance = new TravelRepository(application);
        return instance;
    }

    private TravelRepository(Application application) {
        travelDataSource = TravelFirebaseDataSource.getInstance();
        historyDataSource = new HistoryDataSource(application.getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ITravelDataSource.NotifyToTravelListListener notifyToTravelListListener = new ITravelDataSource.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
               // travelList.clear();
                travelList = travelDataSource.getAllTravels();

                if (notifyToTravelListListenerRepository != null)
                    notifyToTravelListListenerRepository.onTravelsChanged();

            }
        };

        travelDataSource.setNotifyToTravelListListener(notifyToTravelListListener);
    }

    @Override
    public void addTravel(Travel travel) {
        travelDataSource.addTravel(travel);
    }

    @Override
    public void updateTravel(Travel travel) {
        travelDataSource.updateTravel(travel);
    }

    @Override
    public  MutableLiveData<List<Travel>> getAllTravels() {
        travelList2.clear();
       // String strEmail=user.getEmail();
        for (Travel travel:travelList) {
            if (travel.getClientEmail().equals(user.getEmail()) && !(travel.getRequesType().equals(Travel.RequestType.close)))
                travelList2.add(travel);
        }
        //travelList=travelList2;
        mutableLiveDataRegistered.setValue(travelList2);
        return mutableLiveDataRegistered;
    }



    public MutableLiveData<List<Travel>> findOpenTravelList(double lat,double lon,int maxDes) {

        LinkedList<Travel> companyTravels = new LinkedList<Travel>();
        for (Travel travel : travelList) {
            if (travel.getRequesType().toString().equals(Travel.RequestType.sent) || travel.getRequesType().toString().equals(Travel.RequestType.accepted)) {
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(lat);
                temp.setLongitude(lon);

                Location temp1 = new Location(LocationManager.GPS_PROVIDER);
                temp1.setLatitude(travel.getSourceLocation().getLat());
                temp1.setLongitude(travel.getSourceLocation().getLon());

                double  distance= temp.distanceTo(temp1);
                //     Toast.makeText(this.application.getApplicationContext(), " dis is :" + distance, Toast.LENGTH_LONG).show();
                if(distance<maxDes)
                    companyTravels.add(travel);

            }
        }
        mutableLiveDataCompany.setValue(companyTravels);
        return mutableLiveDataCompany;
    }





    @Override
    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }

    @Override
    public void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l) {
        notifyToTravelListListenerRepository = l;
    }
    @Override
    public String emailOfUser(){
        return user.getEmail();
    }
}
