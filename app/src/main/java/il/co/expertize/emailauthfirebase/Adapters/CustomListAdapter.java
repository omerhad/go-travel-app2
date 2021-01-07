package il.co.expertize.emailauthfirebase.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.ContactsContract;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.R;
import il.co.expertize.emailauthfirebase.UI.Main.NavigationViewModel;


/**
 * Custom list adapter, implementing BaseAdapter
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;
    private Location location;
    private NavigationViewModel viewModel;
    private int numStatus;
//   private FragmentActivity viewModelStore;
    private java.text.SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    public CustomListAdapter(Context context, ArrayList<Travel> items,FragmentActivity viewModelStore) {
        this.context = context;
        this.items = items;
        //this.viewModelStore=viewModelStore;
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

/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                inflate(R.layout.layout_list_view_row_items, parent, false);
        }

        // get current item to be displayed
        Item currentItem = (Item) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
            convertView.findViewById(R.id.text_view_item_name);
        TextView textViewItemDescription = (TextView)
            convertView.findViewById(R.id.text_view_item_description);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getItemName());
        textViewItemDescription.setText(currentItem.getItemDescription());

        // returns the view for the current row
        return convertView;
    }

*/




    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(items.get(position).getTravelLocation().getLat());
        location.setLongitude(items.get(position).getTravelLocation().getLon());

        String strLocation="";
        strLocation =getPlace(location);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_for_register, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        Travel currentItem = (Travel) getItem(position);
        viewHolder.clientName.setText(currentItem.getClientName());
        viewHolder.clientDestination.setText("destination:      " + strLocation);
        viewHolder.exp.setText("your current status is:      " + currentItem.getRequesType().toStr(Travel.RequestType.getTypeInt(currentItem.getRequesType())));
        viewHolder.clientDate.setText("start Date:      "+format.format(currentItem.getTravelDate()));
        viewHolder.clientDate2.setText("Arrivad Date:      "+format.format(currentItem.getArrivalDate()));
        ArrayAdapter<String> adapter= new ArrayAdapter<> (context ,android.R.layout.simple_spinner_item, new ArrayList<String>( currentItem.getCompany().keySet()) )  ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (currentItem.getRequesType().getCode()!= 3)
        viewHolder.clientStatus.setSelection(currentItem.getRequesType().getCode());

        viewHolder.company.setAdapter(adapter);

        viewHolder.clientStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                viewHolder.exp.setText("your current status is:    " + currentItem.getRequesType().toStr(position));
               numStatus=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Boolean> company=new HashMap<>();
                company = currentItem.getCompany();
                if(!company.get(viewHolder.company.getSelectedItem().toString())) {
                    company.put(viewHolder.company.getSelectedItem().toString(), true);
                    currentItem.setCompany(company);
                    currentItem.setCompany(company);
                    Toast.makeText(context, "Thank you very much, the " +viewHolder.company.getSelectedItem().toString() +" company will contact you", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(context, "company is already approved", Toast.LENGTH_LONG).show();


                currentItem.setRequesType(Travel.RequestType.getType(numStatus));
                viewModel.updateTravel(currentItem);
                viewHolder.exp.clearComposingText();
                viewHolder.exp.setText("your current status is:    " + currentItem.getRequesType().toStr(numStatus));
                Toast.makeText(context, "The status saved", Toast.LENGTH_LONG).show();

            }
        });



        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView clientName;
        TextView clientDestination;
        TextView clientDate;
        TextView clientDate2;
        TextView exp;
        Spinner clientStatus;
        Spinner company;
        Button button;


        public ViewHolder(View view) {
            clientName = (TextView)view.findViewById(R.id.name);
            clientDestination = (TextView) view.findViewById(R.id.dest);
            clientDate = (TextView) view.findViewById(R.id.date);
            clientDate2 = (TextView) view.findViewById(R.id.date2);
            clientStatus = (Spinner)view.findViewById(R.id.status);
            exp = (TextView) view.findViewById(R.id.explain_in_status);
            company = (Spinner)view.findViewById(R.id.company);
            button=(Button) view.findViewById(R.id.save_change);
            Travel.RequestType[] enumR;
            enumR=new Travel.RequestType[]{Travel.RequestType.accepted, Travel.RequestType.run, Travel.RequestType.close};

            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa = new ArrayAdapter<Travel.RequestType>(context,android.R.layout.simple_spinner_item,enumR);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            clientStatus.setAdapter(aa);

        }
    }
    public String getPlace(Location location) {
        String cityName="" ;
        String stateName="";
        String countryName="";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            if (addresses.size() > 0) {
                cityName = addresses.get(0).getAddressLine(0);
                if (addresses.size() > 1)
                    stateName = addresses.get(0).getAddressLine(1);
                if (addresses.size() > 2)
                    countryName = addresses.get(0).getAddressLine(2);
                return stateName + " " + cityName + " " + countryName;
            }

            return "no place: \n ("+location.getLongitude()+" , "+location.getLatitude()+")";
        }
        catch(
                IOException e)

        {
            e.printStackTrace();
        }
        return "IOException ...";
    }
}
