package com.project.graduate.neartheplace.MainFragment;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationForm implements Parcelable{

    private Double latitude;
    private Double longitude;
    private String company;
    private String branch;
    private String address;

    public LocationForm(Double latitude, Double longitude, String company, String branch, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.company = company;
        this.branch = branch;
        this.address = address;
    }

    public LocationForm(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.company = in.readString();
        this.branch = in.readString();
        this.address = in.readString();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeString(this.company);
        parcel.writeString(this.branch);
        parcel.writeString(this.address);
    }

    public static final Creator<LocationForm> CREATOR = new Creator<LocationForm>() {
        @Override
        public LocationForm createFromParcel(Parcel in) {
            return new LocationForm(in);
        }

        @Override
        public LocationForm[] newArray(int size) {
            return new LocationForm[size];
        }
    };
}
