package com.example.aletta.nokiaowerinternet.bluetooth;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ConnectionViewModel extends ViewModel {

    private MutableLiveData<Integer> connectionState;

    public MutableLiveData<Integer> getConnectionState() {
        if (connectionState == null) {
            connectionState = new MutableLiveData<Integer>();
        }
        return connectionState;
    }

}

