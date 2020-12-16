package il.co.expertize.emailauthfirebase.UI.Main;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import il.co.expertize.emailauthfirebase.R;

public class HistoryTravelsFragment extends Fragment {

    //private HistoryTravelsViewModel HistoryViewModel;
    View view;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.history_travels_fragment, container, false);
        button= view.findViewById(R.id.btm_1);
        button.setOnClickListener((v) ->{
            Toast.makeText(getActivity(),"History Travel",Toast.LENGTH_LONG).show();

        });
        return  view;
    }
}
