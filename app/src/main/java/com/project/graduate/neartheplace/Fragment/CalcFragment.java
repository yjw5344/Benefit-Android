package com.project.graduate.neartheplace.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.graduate.neartheplace.R;

public class CalcFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calc, container, false);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Calc", "CalcBoard");
    }
}
