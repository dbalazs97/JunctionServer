package com.example.aletta.nokiaowerinternet.devicecommunication;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aletta.nokiaowerinternet.R;
import com.example.aletta.nokiaowerinternet.bluetooth.BluetootDataProvider;
import com.example.aletta.nokiaowerinternet.bluetooth.ConnectionListener;
import com.example.aletta.nokiaowerinternet.model.ActionModel;
import com.example.aletta.nokiaowerinternet.socketcommunication.EchoWebSocketListener;
import com.example.aletta.nokiaowerinternet.socketcommunication.SocketCommunicationsManager;
import com.example.aletta.nokiaowerinternet.socketcommunication.SocketMessageParsedListener;
import com.example.aletta.nokiaowerinternet.socketcommunication.model.PhoneNumberModel;
import com.example.aletta.nokiaowerinternet.util.Constants;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import static com.example.aletta.nokiaowerinternet.util.Constants.BASE_URL;
import static com.example.aletta.nokiaowerinternet.util.Constants.TURN_ON;


public class DeviceCommunicaionFragment extends Fragment implements ConnectionListener, SocketMessageParsedListener {

    @BindView(R.id.sendLight)
    FloatingActionButton sendLight;

    private static final String KEY_ADDRESS = "KEY_ADDRESS";
    private BluetootDataProvider bluetootDataProvider;
    private boolean light;
    private WebSocket ws;

    public static DeviceCommunicaionFragment newInstance(String address) {
        DeviceCommunicaionFragment deviceCommunicaionFragment = new DeviceCommunicaionFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ADDRESS, address);
        deviceCommunicaionFragment.setArguments(args);
        return deviceCommunicaionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_communicaion, container, false);

        ButterKnife.bind(this, view);

        initSocketManager();
        initSendButtonVisibility();
        initBluetoothConnection();
        return view;
    }

    private void initSocketManager() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(Constants.BASE_URL).build();
        EchoWebSocketListener listener = new EchoWebSocketListener(this);
        ws = client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();

        // SocketCommunicationsManager.getInstance().setSocketMessageParsedListener(this);
        // SocketCommunicationsManager.getInstance().connect(BASE_URL);
    }

    private void initSendButtonVisibility() {
        sendLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (light) {
                    //bluetootDataProvider.write("0");
                    ws.send("asdddsaads");
                } else {
                    //bluetootDataProvider.write("1");
                    ws.send("asdddsaads");
                }
                light = !light;
            }
        });
    }

    private void initBluetoothConnection() {

        bluetootDataProvider = new BluetootDataProvider(this);
        bluetootDataProvider.init(getArguments().getString(KEY_ADDRESS));
        bluetootDataProvider.connect();


    }

    @Override
    public void onBluetoothConnected() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendLight.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onMessageParsed(final ActionModel actionModel) {
        // if (actionModel.getCode().equals(String.valueOf(TURN_ON))) {
        bluetootDataProvider.write(actionModel.getAction());
        //}
    }

    @Override
    public void onSocketConnected() {

    }
}
