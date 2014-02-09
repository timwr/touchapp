package com.headsup.remote;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class BluetoothFragment extends DialogFragment {


    private ListView listView;
    private List<String> bluetoothList = new ArrayList<String>();
    private ArrayAdapter<String> listAdapter;

    public BluetoothFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview_bluetooth);
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, bluetoothList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Object listPairedName = arg0.getItemAtPosition(arg2);
                String selectedPairedName = listPairedName.toString();
            }
        });

        return rootView;
    }

}

