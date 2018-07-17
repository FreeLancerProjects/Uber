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

public class Fragment_Username_Password extends Fragment {
    public static final String TAG ="Fragment_Username_Password";
    private View view;
    private EditText edt_user_name,edt_password;
    private RegisterModel registerModel;
    private String user_name="",password="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_username_password_layout,container,false);
        registerModel = RegisterModel.getInstance();
        initView(view);
        return view;
    }

    private void initView(View view) {
        edt_user_name = view.findViewById(R.id.edt_user_name);
        edt_password = view.findViewById(R.id.edt_password);
        user_name = edt_user_name.getText().toString();
        password = edt_password.getText().toString();
        registerModel.setUser_name(user_name);
        registerModel.setPassword(password);
    }

    public static Fragment_Username_Password getInstance()
    {
        Fragment_Username_Password fragment_username_password = new Fragment_Username_Password();
        return fragment_username_password;
    }

    public void getUserName()
    {
        user_name = edt_user_name.getText().toString();
        registerModel.setUser_name(user_name);
    }
    public void getPassword()
    {
        password = edt_password.getText().toString();
        registerModel.setPassword(password);
    }
    public void setUserNameError()
    {
        Snackbar.make(view.getRootView(),"User name require",2000).show();
        edt_user_name.setError("User name require");

    }
    public void setPasswordError()
    {
        Snackbar.make(view.getRootView(),"Password require",2000).show();
        edt_password.setError("Password require");
    }
    public void setPasswordShort()
    {
        Snackbar.make(view.getRootView(),"Password is too short",2000).show();
        edt_password.setError("Password is too short");
    }
    public void removeUserNameError()
    {
        edt_user_name.setError(null);

    }
    public void removePasswordError()
    {
        edt_password.setError(null);

    }
}
