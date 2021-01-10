package il.co.expertize.emailauthfirebase.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.R;
import il.co.expertize.emailauthfirebase.UI.NavigationViewModel;
import il.co.expertize.emailauthfirebase.Util.Gps;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;
    private Location locationdes;
    private Location locationsrc;
    Gps gps;
    private NavigationViewModel viewModel;
    private FragmentActivity viewModelStore;
    private java.text.SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    public HistoryAdapter(Context context, ArrayList<Travel> items, FragmentActivity viewModelStore) {
        this.context = context;
        this.items = items;
        this.viewModelStore=viewModelStore;
        viewModel = ViewModelProviders.of(viewModelStore).get(NavigationViewModel.class);
    }



    @Override
    public int getCount() {
        return items.size(); //returns total item in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns the item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HistoryAdapter.ViewHolder viewHolder;
        gps=new Gps();

        locationdes = new Location(LocationManager.GPS_PROVIDER);
        locationsrc = new Location(LocationManager.GPS_PROVIDER);

        locationdes.setLatitude(items.get(position).getTravelLocation().getLat());
        locationdes.setLongitude(items.get(position).getTravelLocation().getLon());


        locationsrc.setLatitude(items.get(position).getSourceLocation().getLat());
        locationsrc.setLongitude(items.get(position).getSourceLocation().getLon());

      float distance = gps.calculateDistance(items.get(position).getTravelLocation().getLat(),items.get(position).getTravelLocation().getLon(),
                items.get(position).getSourceLocation().getLat(),items.get(position).getSourceLocation().getLon());


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_for_history, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HistoryAdapter.ViewHolder) convertView.getTag();
        }



        Travel currentItem = (Travel) getItem(position);


        String strChooseCompany=" ";
        for (String str:currentItem.getCompany().keySet()) {
            if (currentItem.getCompany().get(str)){
                 strChooseCompany = str;
            }
        }


        viewHolder.companyName.setText("name of company:      "+strChooseCompany);
        viewHolder.numOfKm.setText("distance in KM:      " + distance);

        viewHolder.changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentItem.getRequesType().equals(Travel.RequestType.close)) {
                    currentItem.setRequesType(Travel.RequestType.paid);
                    viewModel.updateTravel(currentItem);
                    Toast.makeText(context, "The status is paid now", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(context, "The status is already paid ", Toast.LENGTH_LONG).show();
            }
        });


        viewHolder.callCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneCompany =viewModel.emailOfUser();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ phoneCompany));

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                viewModelStore.startActivity(callIntent);
            }
        });
        return convertView;
    }



    private class ViewHolder {
        TextView companyName;
        TextView numOfKm;
        Button changeStatus;
        Button callCompany;


        public ViewHolder(View view) {
            companyName = (TextView) view.findViewById(R.id.name_company);
            numOfKm = (TextView) view.findViewById(R.id.num_of_km);
            changeStatus = (Button) view.findViewById(R.id.change_status);
            callCompany = (Button) view.findViewById(R.id.call_company);
        }
    }
}
