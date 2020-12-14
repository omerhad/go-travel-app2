package il.co.expertize.emailauthfirebase.UI.Main;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.Data.Repository.ITravelRepository;
import il.co.expertize.emailauthfirebase.Data.Repository.TravelRepository;

public class MainViewModel extends AndroidViewModel {
    ITravelRepository repository;
    public MainViewModel(Application p) {
        super(p);
        repository =  TravelRepository.getInstance(p);
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
        return (MutableLiveData<List<Travel>>)repository.getAllTravels();
    }
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }
}
