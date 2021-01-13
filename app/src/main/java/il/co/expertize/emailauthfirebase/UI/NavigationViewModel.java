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
    public NavigationViewModel(Application p) {
        super(p);
        repository =  TravelRepository.getInstance(p);
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
        return repository.getAllTravels();
    }
    public MutableLiveData<List<Travel>> findOpenTravelList(){return repository.findOpenTravelList();}
    public MutableLiveData<List<Travel>> getAllCloseTravelList(){return repository.getAllCloseTravelList();}
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }
    public String emailOfUser(){
        return repository.emailOfUser();
    }
    public String phoneOfUser(){return repository.phoneOfUser(); }



}
