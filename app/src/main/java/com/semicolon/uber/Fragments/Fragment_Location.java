package com.semicolon.uber.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.semicolon.uber.Models.RegisterModel;
import com.semicolon.uber.R;

public class Fragment_Location extends Fragment {
    public static final String TAG ="Fragment_Location";
    private View view;
    private EditText edt_country,edt_city;
    private RegisterModel registerModel;
    private String country="",city="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location,container,false);
        registerModel = RegisterModel.getInstance();
        initView(view);

        return view;
    }

    private void initView(View view) {
        edt_country = view.findViewById(R.id.edt_country);
        edt_city = view.findViewById(R.id.edt_city);
        country = edt_country.getText().toString();
        city = edt_city.getText().toString();
        registerModel.setCountry(country);
        registerModel.setCity(city);

    }

    public static Fragment_Location getInstance()
    {
        Fragment_Location fragment_location = new Fragment_Location();
        return fragment_location;
    }
    public void getCountry()
    {
        country = edt_country.getText().toString();
        registerModel.setCountry(country);
    }
    public void getCity()
    {
        city = edt_city.getText().toString();
        registerModel.setCity(city);
    }

    public void setCountryError()
    {
        Snackbar.make(view.getRootView(),"Country required",2000).show();
        edt_country.setError("Country required");

    }

    public void setCityError()
    {
        Snackbar.make(view.getRootView(),"City required",2000).show();
        edt_city.setError("City required");
    }

    public void removeCountryError()
    {
        edt_country.setError(null);

    }
    public void removecityError()
    {
        edt_city.setError(null);

    }

}
