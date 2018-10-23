package com.example.aletta.nokiaowerinternet.socketcommunication;

import android.text.TextUtils;
import android.util.Log;

import com.example.aletta.nokiaowerinternet.model.ActionModel;
import com.example.aletta.nokiaowerinternet.socketcommunication.model.PhoneNumberModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.transports.WebSocket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;

import javax.net.ssl.SSLContext;

public class SocketCommunicationsManager implements ISocketCommnication {

    private static final SocketCommunicationsManager ourInstance = new SocketCommunicationsManager();
    private static final String TAG = SocketCommunicationsManager.class.getSimpleName();
    private Socket mSocket;
    private SocketMessageParsedListener socketMessageParsedListener;


    public static SocketCommunicationsManager getInstance() {
        return ourInstance;
    }

    private SocketCommunicationsManager() {
    }

    public void setSocketMessageParsedListener(SocketMessageParsedListener socketMessageParsedListener) {
        this.socketMessageParsedListener = socketMessageParsedListener;
    }

    @Override
    public void connect(String uri) {
        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = true;
            options.reconnectionDelay = 5000;
            options.port = 3000;
            options.timeout = 30000;
            options.transports = new String[]{WebSocket.NAME};

            mSocket = IO.socket(uri, options);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        mSocket.on("connect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call() called with: args = [" + args + "]");
                socketMessageParsedListener.onSocketConnected();

            }
        });

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call() called with: args = [" + args + "]");
                socketMessageParsedListener.onSocketConnected();
                write("0748970967");
            }
        });

        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call() called with: args = [" + args + "]");
              //  socketMessageParsedListener.onSocketConnected();
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call() called with: args = [" + args + "]");
              //  socketMessageParsedListener.onSocketConnected();
            }
        });

        mSocket.connect();
    }

    @Override
    public void disconnect() {
        if (mSocket != null) {
            mSocket.disconnect();
        }

    }

    @Override
    public void subscribeOnEvent(String eventName) {
        mSocket.on(SocketEvenTypes.EVENT_CHANGE,new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                Gson gson = new Gson();
                ActionModel actionModel = gson.fromJson((String) args[0], ActionModel.class);
                if (socketMessageParsedListener != null) {

                    socketMessageParsedListener.onMessageParsed(actionModel);
                }

            }
        });

    }

    @Override
    public void write(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        mSocket.emit("sendPhoneNumber", message);
    }
}
