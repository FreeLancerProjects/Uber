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

public class Fragment_Email extends Fragment {
    public static final String TAG ="Fragment_Email";
    private View view;
    private EditText edt_email;
    private RegisterModel registerModel;
    private String email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_email,container,false);
        registerModel = RegisterModel.getInstance();
        initView(view);
        return view;
    }

    private void initView(View view) {
        edt_email = view.findViewById(R.id.edt_email);
        email = edt_email.getText().toString();
        registerModel.setEmail(email);
    }

    public static Fragment_Email getInstance()
    {
        Fragment_Email fragment_email = new Fragment_Email();
        return fragment_email;
    }

    public void getEmail()
    {
        email = edt_email.getText().toString();
        registerModel.setEmail(email);
    }
    public void setEmailEmpty()
    {
        edt_email.setError("Email required");
        Snackbar.make(view.getRootView(),"Email required",5000).show();


    }
    public void setEmailInvalid()
    {
        edt_email.setError("Invalid email");
        Snackbar.make(view.getRootView(),"Invalid email",2000).show();

    }
    public void removeError()
    {
        edt_email.setError(null);

    }
}
