package com.example.fincasyscommercial.selectsociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fincasyscommercial.R;

import java.util.List;

public class SpinAdapter extends RecyclerView.Adapter<SpinAdapter.AreaListViewHolder>{
    private Context context;
    private List<LocationHelper> locationHelpers;

    private static  AreaSingleClickListener sClickListener;
    private   int sSelected = -1;
    private String RecordId;

    public void updateSearch(List<LocationHelper> filterlocationHelpers){
        locationHelpers=filterlocationHelpers;
        notifyDataSetChanged();
    }

    public SpinAdapter(Context context, List<LocationHelper> locationHelpers, int sSelected,String RecordId) {
        this.context = context;
        this.locationHelpers = locationHelpers;
        this.sSelected = sSelected;
        this.RecordId=RecordId;
    }

    @NonNull
    @Override
    public AreaListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.area_list_raw, parent, false);
        return new AreaListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaListViewHolder holder, int position) {

        holder.selectAreaRb.setChecked(false);
        holder.selectAreaRb.setText(locationHelpers.get(position).name);
        holder.selectAreaRb.setChecked(RecordId.equalsIgnoreCase(locationHelpers.get(position).id));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sSelected = position;
                RecordId=locationHelpers.get(position).id;

                if (sClickListener!=null) {
                    sClickListener.onItemClickListener(locationHelpers.get(position).name, locationHelpers.get(position).id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationHelpers.size();
    }

    public class AreaListViewHolder extends RecyclerView.ViewHolder {
        RadioButton selectAreaRb;

        AreaListViewHolder(View itemView) {
            super(itemView);
            selectAreaRb = itemView.findViewById(R.id.select_area_rb);
        }


    }

    public  void setOnItemClickListener(AreaSingleClickListener clickListener) {
        sClickListener = clickListener;
    }
    public interface AreaSingleClickListener {
        void onItemClickListener(String name, String id);
    }
    public void selectedItem() {
        notifyDataSetChanged();
    }

}