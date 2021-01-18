package il.co.expertize.emailauthfirebase.UI;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.Data.Repository.ITravelRepository;
import il.co.expertize.emailauthfirebase.Data.Repository.TravelRepository;

public class NavigationViewModel extends AndroidViewModel {
    ITravelRepository repository;
    private MutableLiveData<List<Travel>> mutableLiveDataReg = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataHis = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataComp = new MutableLiveData<>();

    public NavigationViewModel(Application p) {
        super(p);

        repository = TravelRepository.getInstance(p);


        ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerReg = new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.getAllTravels();
                mutableLiveDataReg.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListenerReg(notifyToTravelListListenerReg);


        ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerCom = new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.findOpenTravelList();
                mutableLiveDataComp.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListenerComp(notifyToTravelListListenerCom);

        ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerHis = new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.getAllCloseTravelList();
                mutableLiveDataHis.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListenerHis(notifyToTravelListListenerHis);
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
        return mutableLiveDataReg;
    }
    public MutableLiveData<List<Travel>> findOpenTravelList(){return mutableLiveDataComp;}
    public MutableLiveData<List<Travel>> getAllCloseTravelList(){return mutableLiveDataHis;}
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }
    public String emailOfUser(){
        return repository.emailOfUser();
    }
    public String phoneOfUser(){return repository.phoneOfUser(); }



}
