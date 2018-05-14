package com.project.graduate.neartheplace.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.project.graduate.neartheplace.Activity.MapsActivity;
import com.project.graduate.neartheplace.R;


public class MainFragment extends Fragment {

    private ImageButton mapBtn;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container ,false);

        mapBtn = (ImageButton) view.findViewById(R.id.mapIcon);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent connectMapActicity = new Intent(getActivity(), MapActivity.class );
//                startActivity(connectMapActicity);
                Intent connectMapActicity = new Intent(getActivity(), MapsActivity.class );
                startActivity(connectMapActicity);
            }
        });

        return view;


    }


}
