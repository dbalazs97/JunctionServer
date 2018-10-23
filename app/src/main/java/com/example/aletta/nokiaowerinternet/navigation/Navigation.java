package com.example.aletta.nokiaowerinternet.navigation;

import android.support.v4.app.FragmentManager;

import com.example.aletta.nokiaowerinternet.MainActivity;
import com.example.aletta.nokiaowerinternet.R;
import com.example.aletta.nokiaowerinternet.devicecommunication.DeviceCommunicaionFragment;
import com.example.aletta.nokiaowerinternet.home.HomeFragment;

public class Navigation {

    private static Navigation instance;
    private final MainActivity activity;
    private final FragmentManager fragmentManager;

    private Navigation(MainActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public static Navigation initialise(MainActivity activity) {
        if (instance == null) {
            instance = new Navigation(activity);
        }

        return instance;
    }

    public static Navigation getNavigation() {
        return instance;
    }

    public void navigateToAction(String address) {
        DeviceCommunicaionFragment homeFragment = DeviceCommunicaionFragment.newInstance(address);

        try {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();
        } catch (IllegalStateException ex) {

        }
    }

}
