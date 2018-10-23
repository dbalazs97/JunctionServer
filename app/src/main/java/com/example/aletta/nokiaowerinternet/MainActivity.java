package com.example.aletta.nokiaowerinternet;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aletta.nokiaowerinternet.bluetooth.ConnectionViewModel;
import com.example.aletta.nokiaowerinternet.home.HomeFragment;
import com.example.aletta.nokiaowerinternet.navigation.Navigation;
import com.example.aletta.nokiaowerinternet.socketcommunication.EchoWebSocketListener;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private ConnectionViewModel connectionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   initViewModel();

        Navigation.initialise(this);

        setContentView(R.layout.activity_main);

        initFragment();


    }

    private void initFragment() {
        HomeFragment homeFragment = HomeFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment).commit();
    }
}
