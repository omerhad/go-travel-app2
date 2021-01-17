package il.co.expertize.emailauthfirebase.UI;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
import java.util.LinkedList;
import java.util.List;

import il.co.expertize.emailauthfirebase.Adapters.CompanyAdapter;
import il.co.expertize.emailauthfirebase.Adapters.CustomListAdapter;
import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.R;
import il.co.expertize.emailauthfirebase.Util.Gps;

public class CompanyTravelsFragment extends Fragment implements LocationListener{


    NavigationViewModel mViewModel;
    Context context;
    ListView itemsListView;
    LocationManager locationManager;
    double lat=0;
    double lon=0;

    public static RegisteredTravelsFragment newInstance() {
        return new RegisteredTravelsFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_travels_fragment, container, false);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        }

        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
//        location=locationManager.getCurrentLocation();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsListView = (ListView) view.findViewById(R.id.list_company);

        mViewModel = ViewModelProviders.of(getActivity()).get(NavigationViewModel.class);





        mViewModel.findOpenTravelList().observe(this, new Observer<List<Travel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);
                ArrayList<Travel> tmp2  = findOpenTravelList(31.7650581, 35.191158099999996,100000,tmp);
                //create adapter object
                CompanyAdapter adapter = new CompanyAdapter(context, tmp2, requireActivity());

                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }
        });

    }


    public ArrayList<Travel> findOpenTravelList(double lat, double lon, int maxDes,ArrayList<Travel> tmper) {

        ArrayList<Travel> companyTravels = new ArrayList<Travel>();
        for (Travel travel : tmper) {
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(travel.getSourceLocation().getLat());
                temp.setLongitude(travel.getSourceLocation().getLon());

                Location temp1 = new Location(LocationManager.GPS_PROVIDER);
                temp1.setLatitude(lat);
                temp1.setLongitude(lon);

                double  distance= temp.distanceTo(temp1);
                //     Toast.makeText(this.application.getApplicationContext(), " dis is :" + distance, Toast.LENGTH_LONG).show();
                if(distance<maxDes)
                    companyTravels.add(travel);

            }
        return companyTravels;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();

    }
}
