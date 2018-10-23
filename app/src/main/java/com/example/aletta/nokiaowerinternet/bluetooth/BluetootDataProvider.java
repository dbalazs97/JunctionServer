package com.example.aletta.nokiaowerinternet.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.example.aletta.nokiaowerinternet.base.BaseApplication;

import java.util.UUID;

public class BluetootDataProvider implements RemoteDataProvider{

    private static final String TAG = BluetootDataProvider.class.getSimpleName();
    private final ConnectionListener connectionListener;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String address;
    private BluetoothGatt mBluetoothGatt;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    private final IBinder mBinder = new LocalBinder();

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";

    public static String RATE = "19b10000-e8f2-537e-4f6c-d104768a1214";

    private int mConnectionState = STATE_DISCONNECTED;
    private BluetoothGattCharacteristic lightCharacteristic;

    public BluetootDataProvider(ConnectionListener listener) {
        mBluetoothAdapter = BluetoothAdapterProvider.getBuetoothAdapter();
        this.connectionListener = listener;
    }

    @Override
    public void init(String address) {
        this.address = address;
    }

    @Override
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    @Override
    public boolean connect() {

        if (mBluetoothAdapter == null || TextUtils.isEmpty(address)) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (address != null && address.equals(address)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(BaseApplication.getContext(), false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    @Override
    public void write(String message) {

        if (lightCharacteristic == null) {
            return;
        }

        //fixme
        byte[] bytes = new byte[1];
        if (TextUtils.equals(message,"0")) {
            bytes[0] = (byte) 0x0000;
        }else{
            bytes[0] = (byte) 0x0001;
        }

        lightCharacteristic.setValue(bytes);
        mBluetoothGatt.writeCharacteristic(lightCharacteristic);
    }

    @Override
    public void read() {

    }

    public class LocalBinder extends Binder {
        BluetootDataProvider getService() {
            return BluetootDataProvider.this;
        }
    }

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
             //   broadcastUpdate(intentAction);
                connectionListener.onBluetoothConnected();
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());


            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
            //    broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                configureCharacteristic(mBluetoothGatt);
                //        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
               // broadcastUpdate(ACTION_DATA_AVAILABLE, lightCharacteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
      //      broadcastUpdate(ACTION_DATA_AVAILABLE, lightCharacteristic);
        }
    };

    private void configureCharacteristic(BluetoothGatt gatt) {
        BluetoothGattService service = gatt.getService(UUID.fromString(RATE));
        if (service == null) {
            return;
        }

        for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
            lightCharacteristic = characteristic;
        }

    }
}
