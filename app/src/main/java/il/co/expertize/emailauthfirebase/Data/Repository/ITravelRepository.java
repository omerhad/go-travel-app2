package il.co.expertize.emailauthfirebase.Data.Repository;



import androidx.lifecycle.MutableLiveData;
import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
   List<Travel> findOpenTravelList();
   List<Travel> getAllCloseTravelList();
    MutableLiveData<Boolean> getIsSuccess();
    String emailOfUser();
    String phoneOfUser();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListenerReg(ITravelRepository.NotifyToTravelListListener l);
    void setNotifyToTravelListListenerComp(ITravelRepository.NotifyToTravelListListener l);
    void setNotifyToTravelListListenerHis(ITravelRepository.NotifyToTravelListListener l);
}
