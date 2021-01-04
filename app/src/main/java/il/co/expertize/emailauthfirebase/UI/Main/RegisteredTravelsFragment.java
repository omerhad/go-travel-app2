package il.co.expertize.emailauthfirebase.UI.Main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import il.co.expertize.emailauthfirebase.Adapters.CustomListAdapter;
import il.co.expertize.emailauthfirebase.Entities.Travel;
import il.co.expertize.emailauthfirebase.R;

public class RegisteredTravelsFragment extends Fragment {


    //private RegisteredTravelsViewModel mViewModel;
    View view;
    Button button;
    CustomListAdapter adapter;
    MainViewModel mViewModel;
    Context context;
    ListView itemsListView;
    RecyclerView recyclerView;
   // public List<Travel> Travels;
   ArrayList<Travel> tmp;

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

        View view= inflater.inflate(R.layout.registered_travels_fragment, container, false);
       // mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel=new ViewModelProvider(getActivity()).get(MainViewModel.class);
        itemsListView  = (ListView)view.findViewById(R.id.list_register);


        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);

                //create adapter object
                CustomListAdapter adapter = new CustomListAdapter(context, tmp);

                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
//        context =RegisteredTravelsFragment.this.getActivity().getBaseContext();
//        recyclerView = (RecyclerView) view.findViewById(R.id.user_travel_recyclerView);
//        DividerItemDecoration itemDecor = new DividerItemDecoration(RegisteredTravelsFragment.this.getActivity(),1);
//        recyclerView.addItemDecoration(itemDecor);
//
//        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
//            @Override
//            public void onChanged(List<Travel> travels) {
//                tmp=new ArrayList<>(travels);
//
//                //Travel[] travelsArr = new Travel[travels.size()];
//                //travels.toArray(travelsArr);
//                adapter = new CustomListAdapter( context,tmp);
////                recyclerView.setHasFixedSize(true);
////                recyclerView.setLayoutManager(new LinearLayoutManager(RegisteredTravelsFragment.this.getActivity()));
//                itemsListView.setAdapter(adapter);
//
//
//            }
//        });


        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        mViewModel=new ViewModelProvider(this).get(MainViewModel.class);
//
//        View view= inflater.inflate(R.layout.registered_travels_fragment, container, false);
//        return view;
//        // Inflate the layout for this fragment
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        itemsListView  = (ListView)view.findViewById(R.id.list_register);
//        //mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
//
//        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
//            @Override
//            public void onChanged(List<Travel> travels) {
//                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);
//
//                //create adapter object
//                CustomListAdapter adapter = new CustomListAdapter(context, tmp);
//
//                //set custom adapter as adapter to our list view
//                itemsListView.setAdapter(adapter);
//            }});
//    }

}