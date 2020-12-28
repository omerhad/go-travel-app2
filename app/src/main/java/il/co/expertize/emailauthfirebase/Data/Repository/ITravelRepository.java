package il.co.expertize.emailauthfirebase.Data.Repository;



import androidx.lifecycle.MutableLiveData;
import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}
