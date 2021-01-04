package il.co.expertize.emailauthfirebase.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.R;


/**
 * Custom list adapter, implementing BaseAdapter
 */
public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;

    public CustomListAdapter(Context context, ArrayList<Travel> items) {
        this.context = context;
        this.items = items;
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




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_for_register, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Travel.RequestType[] enumR;
        enumR=new Travel.RequestType[]{Travel.RequestType.accepted, Travel.RequestType.close, Travel.RequestType.paid, Travel.RequestType.run, Travel.RequestType.sent};

        Travel currentItem = (Travel) getItem(position);
        viewHolder.clientName.setText(currentItem.getClientName());
        viewHolder.clientDestination.setText(currentItem.getTravelLocation().toString());
        viewHolder.clientDate.setText(currentItem.getArrivalDate().toString());
        viewHolder.clientStatus.setAdapter(new ArrayAdapter<Travel.RequestType>(this.context,R.layout.registered_travels_fragment,enumR));
        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView clientName;
        TextView clientDestination;
        TextView clientDate;
        Spinner clientStatus;
        Spinner company;



        public ViewHolder(View view) {
            clientName = (TextView)view.findViewById(R.id.name);
            clientDestination = (TextView) view.findViewById(R.id.dest);
            clientDate = (TextView) view.findViewById(R.id.date);
            clientStatus = (Spinner)view.findViewById(R.id.status);
            company = (Spinner)view.findViewById(R.id.company);
            company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    //Toast.makeText(context, Travel.RequestType.getTypeInt(position), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa = new ArrayAdapter<Travel.RequestType>(context,android.R.layout.simple_spinner_item,Travel.RequestType.values());
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            company.setAdapter(aa);
        }
    }
}
