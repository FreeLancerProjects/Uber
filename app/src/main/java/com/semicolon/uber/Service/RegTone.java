package com.semicolon.uber.Service;

import com.semicolon.uber.Models.RegisterModel;

public class RegTone {
    private static RegTone instance=null;
    private RegisterModel registerModel;

    private RegTone() {
    }
    public static synchronized RegTone getInstance()
    {
        if (instance==null)
        {
            instance = new RegTone();
        }
        return instance;
    }
    public interface Listener
    {
        void onSuccess(RegisterModel registerModel);
    }
    public void setModel(RegisterModel registerModel)
    {
        this.registerModel = registerModel;
    }
    public void getModel(Listener listener)
    {
        listener.onSuccess(registerModel);
    }
}
