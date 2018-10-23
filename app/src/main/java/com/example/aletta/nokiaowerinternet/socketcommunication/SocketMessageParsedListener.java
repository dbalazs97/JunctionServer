package com.example.aletta.nokiaowerinternet.socketcommunication;

import com.example.aletta.nokiaowerinternet.model.ActionModel;

public interface SocketMessageParsedListener {

    void onMessageParsed(ActionModel actionModel);

    void onSocketConnected();
}
