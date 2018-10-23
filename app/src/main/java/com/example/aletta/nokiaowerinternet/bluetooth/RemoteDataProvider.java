package com.example.aletta.nokiaowerinternet.bluetooth;

public interface RemoteDataProvider {

    void init(String address);

    void disconnect();

    boolean connect();

    void write(String message);

    void read();

}
