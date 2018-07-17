package com.semicolon.uber.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamudi.phonefield.PhoneInputLayout;
import com.semicolon.uber.Models.RegisterModel;
import com.semicolon.uber.R;

public class Fragment_Phone extends Fragment {
    public static final String TAG ="Fragment_Phone";
    private View view;
    private PhoneInputLayout edt_phone;
    private String phone;
    private RegisterModel registerModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone,container,false);
        registerModel = RegisterModel.getInstance();
        initView(view);
        return view;
    }

    private void initView(View view) {
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_phone.setDefaultCountry("sa");
        edt_phone.getTextInputLayout().getEditText().setHint("phone number");
        edt_phone.getTextInputLayout().getEditText().setTextSize(TypedValue.COMPLEX_UNIT_SP,13f);
        phone = edt_phone.getPhoneNumber();
        registerModel.setPhone(phone);
    }

    public static Fragment_Phone getInstance()
    {
        Fragment_Phone fragment_phone = new Fragment_Phone();
        return fragment_phone;
    }

    public void getPhone()
    {
        phone = edt_phone.getPhoneNumber();

        if (!TextUtils.isEmpty(phone)&&edt_phone.isValid())
        {
            registerModel.setPhone(phone);
        }else
            {
                registerModel.setPhone("");

            }
    }

    public void setPhoneError()
    {
        edt_phone.getTextInputLayout().setErrorEnabled(true);
        edt_phone.getTextInputLayout().setError("Invalid phone number");
        Snackbar.make(view.getRootView(),"Invalid phone number",2000).show();

    }
    public void removeError()
    {
        edt_phone.getTextInputLayout().setError(null);

    }
}
