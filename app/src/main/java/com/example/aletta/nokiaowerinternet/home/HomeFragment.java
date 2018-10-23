package com.example.aletta.nokiaowerinternet.home;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aletta.nokiaowerinternet.R;
import com.example.aletta.nokiaowerinternet.ResourceProvider;
import com.example.aletta.nokiaowerinternet.base.BaseFragment;
import com.example.aletta.nokiaowerinternet.bluetooth.BluetoothAdapterProvider;
import com.example.aletta.nokiaowerinternet.generadialog.GeneralDialogFactory;
import com.example.aletta.nokiaowerinternet.home.adapter.DeviceListAdapter;
import com.example.aletta.nokiaowerinternet.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment<HomeContract.HomePresenter> implements HomeContract.HomeView, DeviceListAdapter.ItemSelectionAction {

    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.idConnect)
    FloatingActionButton fabConnect;

    @BindView(R.id.parentHome)
    ConstraintLayout parentHome;

    @BindView(R.id.searchTextView)
    TextView searchTextView;

    private HomePresenter presenter;
    private DeviceListAdapter adapter;
    private Dialog dialog;
    private boolean dialogIsShowing;
    private long countdownTime = 15000;
    private boolean discoveryIsInProgress;
    private CountDownTimer countDownTimer;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        initScanButton();

        return view;
    }

    private BroadcastReceiver fundActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent.getExtras() + "]");
            final BluetoothDevice device = (BluetoothDevice) intent.getExtras().get("android.bluetooth.device.extra.DEVICE");
            String name = (String) intent.getExtras().get("android.bluetooth.device.extra.NAME");

            //fixme
            if (device.toString().toLowerCase().contains("98:4f:ee:0f:3b:08")) {
                dialog = GeneralDialogFactory.createGeneralDialog(getContext(),
                        ResourceProvider.getInstance().getString(R.string.connect_to_bluetooth),
                        new GeneralDialogFactory.DialogPositiveListener() {
                            @Override
                            public void onPositiveResponse() {
                                dialogIsShowing = false;
                                BluetoothAdapterProvider.getBuetoothAdapter().cancelDiscovery();
                                Navigation.getNavigation().navigateToAction(device.toString());
                            }

                            @Override
                            public void onNegativeResponse() {
                                dialogIsShowing = false;
                                resetFab();
                            }
                        });

                if (!dialogIsShowing) {
                    dialogIsShowing = true;

                    dialog.show();
                }
            }
        }
    };

    private void initScanButton() {
        if (getActivity() != null) {
            IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            getActivity().registerReceiver(fundActionReceiver, intentFilter);
        }

        fabConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discoveryIsInProgress) {
                    return;
                }
                startDiscoveryCountdown();

                BluetoothAdapterProvider.getBuetoothAdapter().startDiscovery();
                moveFab();
            }
        });
    }

    private void startDiscoveryCountdown() {
        discoveryIsInProgress = true;
        countDownTimer = new CountDownTimer(countdownTime, countdownTime) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                resetFab();

            }
        }.start();
    }

    private void resetFab() {
        BluetoothAdapterProvider.getBuetoothAdapter().cancelDiscovery();
        discoveryIsInProgress=false;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parentHome);

        constraintSet.setHorizontalBias(fabConnect.getId(),1f);
        constraintSet.setVerticalBias(fabConnect.getId(),1f);

        TransitionManager.beginDelayedTransition(parentHome);

        constraintSet.applyTo(parentHome);
        searchTextView.setVisibility(View.GONE);
    }

    private void moveFab() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parentHome);

        constraintSet.setHorizontalBias(fabConnect.getId(),0.5f);
        constraintSet.setVerticalBias(fabConnect.getId(),0.5f);

        TransitionManager.beginDelayedTransition(parentHome);

        constraintSet.applyTo(parentHome);
        searchTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(fundActionReceiver);
        BluetoothAdapterProvider.getBuetoothAdapter().cancelDiscovery();
    }

    @Override
    public void onItemSelected(String macAdress) {
        Navigation.getNavigation().navigateToAction(macAdress);
    }

}
