package com.example.aletta.nokiaowerinternet.home.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aletta.nokiaowerinternet.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {

    private final ItemSelectionAction listener;
    private ArrayList<String> dataSet =new ArrayList<>();

    public DeviceListAdapter(ItemSelectionAction listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DeviceViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_scan, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceViewHolder deviceViewHolder, int position) {
        deviceViewHolder.deviceName.setText(dataSet.get(position));
        deviceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String address = dataSet.get(deviceViewHolder.getAdapterPosition());
                    listener.onItemSelected(address);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void add(String data) {
        dataSet.add(data);
        notifyItemInserted(dataSet.size()-1);
    }

    public interface ItemSelectionAction{

        void onItemSelected(String macAdress);

    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.deviceName)
        TextView deviceName;

        DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
