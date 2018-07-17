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

public class Fragment_Name extends Fragment {
    public static final String TAG ="Fragment_Name";
    private EditText edt_name;
    private String name;
    private RegisterModel registerModel;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_name,container,false);
        registerModel = RegisterModel.getInstance();
        initView(view);
        return view;
    }

    private void initView(View view) {
        edt_name = view.findViewById(R.id.edt_name);
        name = edt_name.getText().toString();
        registerModel.setName(name);
    }

    public static Fragment_Name getInstance()
    {
        Fragment_Name fragment_name = new Fragment_Name();
        return fragment_name;
    }

    public void getName() {
        name = edt_name.getText().toString();
        registerModel.setName(name);
    }
    public void setNameError()
    {
        edt_name.setError("full name require");
        Snackbar.make(view.getRootView(),"Full name required",2000).show();

    }
    public void removeError()
    {
        edt_name.setError(null);

    }
}
