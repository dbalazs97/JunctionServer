package com.example.aletta.nokiaowerinternet.socketcommunication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.aletta.nokiaowerinternet.model.ActionModel;
import com.example.aletta.nokiaowerinternet.socketcommunication.model.PhoneNumberModel;
import com.example.aletta.nokiaowerinternet.util.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private static final String TAG = EchoWebSocketListener.class.getSimpleName();
    private final SocketMessageParsedListener listener;
    private boolean connecting;

    public EchoWebSocketListener(SocketMessageParsedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(TAG, "onOpen() called with: webSocket = [" + webSocket + "], response = [" + response + "]");
        webSocket.send("sip:244087800006517@ims.mnc008.mcc244.3gppnetwork.org");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        //output("Receiving : " + text);
        if (listener != null) {
            try {
                ActionModel actionModel = new Gson().fromJson(text, ActionModel.class);
                listener.onMessageParsed(actionModel);
            }catch (JsonSyntaxException ex){

            }
        }
        Log.d(TAG, "onMessage() called with: webSocket = [" + webSocket + "], text = [" + text + "]");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        //output("Receiving bytes : " + bytes.hex());
        Log.d(TAG, "onMessage() called with: webSocket = [" + webSocket + "], bytes = [" + bytes + "]" +  bytes.hex() );
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        connecting = true;
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                connecting = false;
                connect();
            }
        }.start();

      //  output("Closing : " + code + " / " + reason);
    }

    private void connect() {
        WebSocket webSocket;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(Constants.BASE_URL).build();
        webSocket = client.newWebSocket(request, this);

        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
     //   output("Error : " + t.getMessage());
        if (connecting) {
            return;
        }
        connecting = true;
        new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                connecting = false;
                connect();
            }
        }.start();
        Log.d(TAG, "onFailure() called with: webSocket = [" + webSocket + "], t = [" + t + "], response = [" + response + "]");
    }

}

