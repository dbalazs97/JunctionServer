package com.example.aletta.nokiaowerinternet.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.example.aletta.nokiaowerinternet.base.BaseApplication;

public class BluetoothAdapterProvider {

    private static BluetoothAdapter adapter;

    public static BluetoothAdapter getBuetoothAdapter() {
        if (adapter == null) {
            final BluetoothManager bluetoothManager =
                    (BluetoothManager) BaseApplication.getContext().getSystemService(Context.BLUETOOTH_SERVICE);
            adapter = bluetoothManager.getAdapter();
        }
        return adapter;
    }

}
