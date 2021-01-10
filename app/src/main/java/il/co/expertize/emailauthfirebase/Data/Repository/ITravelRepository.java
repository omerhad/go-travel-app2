package il.co.expertize.emailauthfirebase.Data.Repository;



import androidx.lifecycle.MutableLiveData;
import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    MutableLiveData<List<Travel>> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();
    String emailOfUser();
    String phoneOfUser();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}
