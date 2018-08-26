package com.project.graduate.neartheplace.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.graduate.neartheplace.MainFragment.LocationForm;
import com.project.graduate.neartheplace.R;

import java.util.ArrayList;

import static com.project.graduate.neartheplace.Fragment.MainFragment.REQUEST_CODE_PERMISSIONS;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    private GoogleMap mMap;
    private Double latitude;
    private Double longitude;
    private ArrayList<LocationForm> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");

        locationList = new ArrayList<LocationForm>();
        locationList = getIntent().getParcelableArrayListExtra("location");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"GPS 권한이 없습니다",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }else{
            mMap.setMyLocationEnabled(true);
        }

        LatLng myLocation = new LatLng(latitude, longitude);
        MarkerOptions makerOptions = new MarkerOptions();

        for(int i = 0 ; i < locationList.size() ; i++){
            makerOptions
                    .position(new LatLng(locationList.get(i).getLatitude(), locationList.get(i).getLongitude()))
                    .title(locationList.get(i).getCompany() + " (" + locationList.get(i).getBranch() + ") ")
                    .snippet(locationList.get(i).getAddress());
            mMap.addMarker(makerOptions);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }else{
                    mMap.setMyLocationEnabled(true);
                }
        }
    }

}
