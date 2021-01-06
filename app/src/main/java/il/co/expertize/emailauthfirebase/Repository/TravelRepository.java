package il.co.expertize.emailauthfirebase.Repository;


import android.app.Application;

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


    private MutableLiveData<List<Travel>> mutableLiveData = new MutableLiveData<>();



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
        mutableLiveData.setValue(travelList2);
        return mutableLiveData;
    }


    @Override
    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }

    @Override
    public void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l) {
        notifyToTravelListListenerRepository = l;
    }
}
