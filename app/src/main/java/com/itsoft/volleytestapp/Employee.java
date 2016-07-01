package com.itsoft.volleytestapp;

import java.io.Serializable;

/**
 * Created by rohit on 23/6/16.
 */
public class Employee implements Serializable{


    private String name,city,phoneNumber,compName;


    public Employee(String Name, String City, String Number, String CompanyName) {
        this.name = Name;
        this.city = City;
        this.phoneNumber = Number;
        this.compName = CompanyName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

}
