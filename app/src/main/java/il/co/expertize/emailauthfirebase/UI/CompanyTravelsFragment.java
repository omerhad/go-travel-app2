package il.co.expertize.emailauthfirebase.UI;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import il.co.expertize.emailauthfirebase.Adapters.CompanyAdapter;
import il.co.expertize.emailauthfirebase.Adapters.CustomListAdapter;
import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.R;

public class CompanyTravelsFragment extends Fragment {
    //private RegisteredTravelsViewModel mViewModel;
    View view;
    Button button;
    CustomListAdapter adapter;
    NavigationViewModel mViewModel;
    Context context;
    ListView itemsListView;
    RecyclerView recyclerView;
    // public List<Travel> Travels;
    ArrayList<Travel> tmp;
    LocationManager locationManager ;
    LocationListener locationListener;
    double lat;
    double lon;

    public static RegisteredTravelsFragment newInstance() {
        return new RegisteredTravelsFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.company_travels_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsListView = (ListView) view.findViewById(R.id.list_company);

        mViewModel = ViewModelProviders.of(getActivity()).get(NavigationViewModel.class);

        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);


        mViewModel.findOpenTravelList(1,2,100).observe(this, new Observer<List<Travel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);

                //create adapter object
                CompanyAdapter adapter = new CompanyAdapter(context, tmp, requireActivity());

                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }
        });
    }
}