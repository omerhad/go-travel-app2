package il.co.expertize.emailauthfirebase.UI.Main;

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
import android.widget.Toast;

import il.co.expertize.emailauthfirebase.R;

public class RegisteredTravelsFragment extends Fragment {

    //private RegisteredTravelsViewModel mViewModel;
    View view;
    Button button;
    public static RegisteredTravelsFragment newInstance() {
        return new RegisteredTravelsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.company_travels_fragment, container, false);
        button= view.findViewById(R.id.btm_1);
        button.setOnClickListener((v) ->{
            Toast.makeText(getActivity(),"Registered Travel",Toast.LENGTH_LONG).show();

        });
        return  view;
    }

}