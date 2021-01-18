package il.co.expertize.emailauthfirebase.Data;

import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import il.co.expertize.emailauthfirebase.Entities.Travel;

public interface ITravelDataSource {
    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    List<Travel> findOpenTravelList();
    MutableLiveData<Boolean> getIsSuccess();

    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(NotifyToTravelListListener l);
}
