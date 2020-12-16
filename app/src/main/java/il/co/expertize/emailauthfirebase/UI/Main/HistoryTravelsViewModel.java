package il.co.expertize.emailauthfirebase.UI.Main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryTravelsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public HistoryTravelsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is HistoryTravels fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}