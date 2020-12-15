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

import il.co.expertize.emailauthfirebase.R;

public class RegisteredTravelsFragment extends Fragment {

    private RegisteredTravelsViewModel mViewModel;

    public static RegisteredTravelsFragment newInstance() {
        return new RegisteredTravelsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registered_travels_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(RegisteredTravelsViewModel.class);
        // TODO: Use the ViewModel
    }

}