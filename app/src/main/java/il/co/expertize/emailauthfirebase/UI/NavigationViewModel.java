package il.co.expertize.emailauthfirebase.UI;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.Date;
import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.Data.Repository.ITravelRepository;
import il.co.expertize.emailauthfirebase.Data.Repository.TravelRepository;

public class NavigationViewModel extends AndroidViewModel {
    ITravelRepository repository;
    private MutableLiveData<List<Travel>> mutableLiveData = new MutableLiveData<>();
    public NavigationViewModel(Application p) {
        super(p);

        repository =  TravelRepository.getInstance(p);

        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener = new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList =repository.getAllTravels().getValue();
                mutableLiveData.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListener(notifyToTravelListListener);
    }
    void addTravel(Travel travel)
    {
        repository.addTravel(travel);
    }
    public void updateTravel(Travel travel)
    {
        repository.updateTravel(travel);
    }
    public MutableLiveData<List<Travel>> getAllTravels()
    {
        return mutableLiveData;
    }
    public MutableLiveData<List<Travel>> findOpenTravelList(double lat,double lon,int maxDes){return mutableLiveData;}
    public MutableLiveData<List<Travel>> getAllCloseTravelList(Date start, Date end){return mutableLiveData;}
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }
    public String emailOfUser(){
        return repository.emailOfUser();
    }
    public String phoneOfUser(){return repository.phoneOfUser(); }



}
