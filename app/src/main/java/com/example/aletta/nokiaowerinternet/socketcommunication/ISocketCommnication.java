package com.example.aletta.nokiaowerinternet.socketcommunication;

public interface ISocketCommnication {

    void connect(String uri);

    void disconnect();

    void subscribeOnEvent(String eventName);

    void write(String message);

}
