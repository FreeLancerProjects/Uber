package com.semicolon.uber.Service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {
    private static  NetworkConnection instance=null;
    private NetworkConnection() {
    }

    public static synchronized NetworkConnection getInstance()
    {
        if (instance==null)
        {
            instance = new NetworkConnection();
        }
        return instance;
    }

    private boolean getConnection(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (manager!=null)
        {
            if (info.isAvailable()&&info.isConnected())
            {
                return true;
            }else
                {
                    return false;
                }
        }
        return false;
    }

}
