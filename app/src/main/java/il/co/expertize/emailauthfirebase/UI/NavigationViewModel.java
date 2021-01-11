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
    private MutableLiveData<List<Travel>> mutableLiveDataReg = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataComp = new MutableLiveData<>();
    private MutableLiveData<List<Travel>> mutableLiveDataHis = new MutableLiveData<>();
    public NavigationViewModel(Application p) {
        super(p);

        repository =  TravelRepository.getInstance(p);

//        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener = new ITravelRepository.NotifyToTravelListListener() {
//            @Override
//            public void onTravelsChanged() {
//                mutableLiveDataReg.setValue(repository.getAllTravels().getValue());
////                List<Travel> travelListComp =repository.findOpenTravelList().getValue();
//                mutableLiveDataComp.setValue(repository.findOpenTravelList().getValue());
////                List<Travel> travelListHis =repository.getAllCloseTravelList().getValue();
//                mutableLiveDataHis.setValue(repository.getAllCloseTravelList().getValue());
//            }
//        };
//        repository.setNotifyToTravelListListener(notifyToTravelListListener);
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
        return repository.getAllTravels();//mutableLiveDataReg;
    }
    public MutableLiveData<List<Travel>> findOpenTravelList(){return repository.findOpenTravelList();}//mutableLiveDataComp;}
    public MutableLiveData<List<Travel>> getAllCloseTravelList(){return repository.getAllCloseTravelList();}//mutableLiveDataHis;}
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }
    public String emailOfUser(){
        return repository.emailOfUser();
    }
    public String phoneOfUser(){return repository.phoneOfUser(); }



}
