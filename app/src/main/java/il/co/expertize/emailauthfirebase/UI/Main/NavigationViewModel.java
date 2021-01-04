package il.co.expertize.emailauthfirebase.UI.Main;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


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
                List<Travel> travelList =repository.getAllTravels();
                mutableLiveData.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListener(notifyToTravelListListener);
    }
    void addTravel(Travel travel)
    {
        repository.addTravel(travel);
    }
    void updateTravel(Travel travel)
    {
        repository.updateTravel(travel);
    }
    MutableLiveData<List<Travel>> getAllTravels()
    {
        return mutableLiveData;
    }
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }

//    void addTravel(Travel travel)
//    {
//        repository.addTravel(travel);
//    }
//    void updateTravel(Travel travel)
//    {
//        repository.updateTravel(travel);
//    }
//    MutableLiveData<List<Travel>> getAllTravels()
//    {
//        return (MutableLiveData<List<Travel>>)repository.getAllTravels();
//    }
//    MutableLiveData<Boolean> getIsSuccess()
//    {
//        return repository.getIsSuccess();
//    }
}
