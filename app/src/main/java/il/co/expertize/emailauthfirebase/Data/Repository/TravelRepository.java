package il.co.expertize.emailauthfirebase.Data.Repository;


import android.app.Application;
import android.location.Location;
import android.location.LocationManager;
import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import androidx.lifecycle.Observer;
import il.co.expertize.emailauthfirebase.Data.HistoryDataSource;
import il.co.expertize.emailauthfirebase.Data.IHistoryDataSource;
import il.co.expertize.emailauthfirebase.Data.ITravelDataSource;
import il.co.expertize.emailauthfirebase.Data.TravelFirebaseDataSource;
import il.co.expertize.emailauthfirebase.Entities.Travel;

/**
 * TravelRepository is a class that takes information from Firebase,
 * and put the information in the Room, and pass for fragments.
 */
public class TravelRepository implements ITravelRepository {
    List<Travel> travelHistory;
    ITravelDataSource travelDataSource;
    FirebaseUser user;
    public FirebaseAuth mAuth;
    private IHistoryDataSource historyDataSource;
    private ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerRepository;
    List<Travel> travelListHis;
    List<Travel> travelList2;


    private MutableLiveData<List<Travel>> mutableLiveDataRegistered = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataCompany = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataHistory = new MutableLiveData<>();



    private static TravelRepository instance;

    /**
     * singleton for repository
     * @param application
     * @return if exist repository, so return old repository.
     * otherwise, create new repository, and return him.
     */
    public static TravelRepository getInstance(Application application) {
        if (instance == null)
            instance = new TravelRepository(application);
        return instance;
    }


    /**
     * constructor.
     * @param application
     */
    private TravelRepository(Application application) {
        travelList2=new LinkedList<Travel>();
        travelHistory=new LinkedList<Travel>();
        travelListHis =new LinkedList<>();
        travelDataSource = TravelFirebaseDataSource.getInstance();
        historyDataSource = new HistoryDataSource(application.getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        ITravelDataSource.NotifyToTravelListListener notifyToTravelListListener = new ITravelDataSource.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                buildRoom();
                findOpenTravelList();
                getAllCloseTravelList();
                getAllTravels();
                if (notifyToTravelListListenerRepository != null)
                    notifyToTravelListListenerRepository.onTravelsChanged();

            }
        };

        travelDataSource.setNotifyToTravelListListener(notifyToTravelListListener);


    }

    /**
     * get travel and add him to our list of travel
     * @param travel
     */
    @Override
    public void addTravel(Travel travel) {
        travelDataSource.addTravel(travel);
    }

    /**
     * get travel and update him in our list of travel
     * @param travel
     */
    @Override
    public void updateTravel(Travel travel) {
        travelDataSource.updateTravel(travel);
    }

    /**
     * get list of travel and return same list but in MutableLiveData for register fragment
     * @return MutableLiveData of travel
     */
    @Override
    public  MutableLiveData<List<Travel>> getAllTravels() {
        travelList2.clear();
        for (Travel travel:travelDataSource.getAllTravels()) {
            if (travel.getClientEmail().equals(user.getEmail()) && !(travel.getRequesType().equals(Travel.RequestType.close))&&
                    !(travel.getRequesType().equals(Travel.RequestType.paid)))
                travelList2.add(travel);
        }

        mutableLiveDataRegistered.setValue(travelList2);
        return mutableLiveDataRegistered;
    }


    /**
     * get list of travel and return same list but in MutableLiveData for company fragment
     * @return MutableLiveData of travel
     */
    @Override
    public MutableLiveData<List<Travel>> findOpenTravelList() {

        LinkedList<Travel> companyTravels = new LinkedList<Travel>();
        for (Travel travel : travelDataSource.findOpenTravelList()) {
            if (travel.getRequesType().equals(Travel.RequestType.sent) || travel.getRequesType().equals(Travel.RequestType.accepted)) {
                    companyTravels.add(travel);
            }
        }
        mutableLiveDataCompany.setValue(companyTravels);
        return mutableLiveDataCompany;
    }

    /**
     * get list of travel and return same list but in MutableLiveData for history fragment
     * @return MutableLiveData of travel
     */
    @Override
    public MutableLiveData<List<Travel>> getAllCloseTravelList() {
        LinkedList<Travel> historyTravels = new LinkedList<Travel>();
            for (Travel travel:travelDataSource.getAllTravels()) {
                if (travel.getRequesType().equals(Travel.RequestType.close)||travel.getRequesType().equals(Travel.RequestType.paid)) {
                    historyTravels.add(travel);
                }
            }
            mutableLiveDataHistory.setValue(historyTravels);
            return mutableLiveDataHistory;

    }

    /**
     * get all travel from Firebase and put in Room
     */
    public void buildRoom(){
        travelListHis=travelDataSource.getAllTravels();
        historyDataSource.clearTable();
        historyDataSource.addTravel(travelListHis);
    }

    @Override
    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }

    /**
     * add to listener
     * @param l
     */
    @Override
    public void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l) {
        notifyToTravelListListenerRepository = l;
    }

    /**
     * give the email of current user
     * @return email of current user
     */
    @Override
    public String emailOfUser(){
        return user.getEmail();
    }

    /**
     * give the phone number of current user
     * @return phone number of current user
     */
    public String phoneOfUser(){ return user.getPhoneNumber(); }
}
