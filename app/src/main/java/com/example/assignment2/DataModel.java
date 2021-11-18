package com.example.assignment2;

public class DataModel {

    //create variables for each property
    private Integer id;
    private String Address;
    private Float Latitude;
    private Float Longitude;

    //create constructor for all properties
    public DataModel(Integer id, String address, Float latitude, Float longitude) {
        this.id = id;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
    }

    //not sure if this will be used later
    public DataModel() {
    }

    //create a toString for easy conversion
    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", Address='" + Address + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }



    //create getters and setters for each property
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }


}
