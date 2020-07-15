package com.example.fincasyscommercial.selectsociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fincasyscommercial.R;
import com.example.fincasyscommercial.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SocietyAdapter extends RecyclerView.Adapter<SocietyAdapter.MySocietyHolder> {

    private Context ctx;
    private List<SocietyResponce.Society> societyResponce;
    private SocietyInterface societyInterface;

    public SocietyAdapter(Context ctx, List<SocietyResponce.Society> societyResponce) {
        this.ctx = ctx;
        this.societyResponce = societyResponce;
    }

    public void update() {
        notifyDataSetChanged();
    }

    private void set(){
        for(SocietyResponce.Society st :  societyResponce){
            st.setClicked(false);
        }
    }

    public interface SocietyInterface {
        void click(String data, String sname, int pos, String baseurl, String apikey, SocietyResponce.Society society, boolean isClick);
    }

    public void setUpInterface(SocietyInterface upInterface) {
        this.societyInterface = upInterface;
    }


    @NonNull
    @Override
    public MySocietyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_society, parent, false);
        return new MySocietyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySocietyHolder h, @SuppressLint("RecyclerView") final int i) {

        h.tv_society_title.setText(societyResponce.get(i).getSocietyName());
        h.tv_society_address.setText(societyResponce.get(i).getSocietyAddress());
        if (societyResponce.get(i).isClicked()) {

            h.lin_root.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark));
            h.tv_society_title.setTextColor(ContextCompat.getColor(ctx, R.color.white));
            h.tv_society_address.setTextColor(ContextCompat.getColor(ctx, R.color.white));
            h.itemView.setOnClickListener(v -> {
                set();
                societyInterface.click(societyResponce.get(i).getSocietyId(), societyResponce.get(i).getSocietyName(), i, societyResponce.get(i).getSubDomain(), societyResponce.get(i).getApiKey(), societyResponce.get(i),
                        societyResponce.get(i).isClicked());    });

        } else {


            h.lin_root.setBackgroundColor(ContextCompat.getColor(ctx, R.color.white));
            h.tv_society_title.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            h.tv_society_address.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            h.itemView.setOnClickListener(v -> {
                set();
                societyResponce.get(i).setClicked(true);
                societyInterface.click(societyResponce.get(i).getSocietyId(), societyResponce.get(i).getSocietyName(), i, societyResponce.get(i).getSubDomain(), societyResponce.get(i).getApiKey(), societyResponce.get(i),
                        societyResponce.get(i).isClicked());
            });

        }

        try {
            Log.e("@@", "society logo: " + societyResponce.get(i).getSocieatyLogo());

            Tools.displayImage(ctx, h.iv_society_logo, societyResponce.get(i).getSocieatyLogo());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Update(List<SocietyResponce.Society> societyResponce1) {
        societyResponce = societyResponce1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return societyResponce.size();
    }

    class MySocietyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_society_logo)
        CircularImageView iv_society_logo;
        @BindView(R.id.iv_society_logo1)
        ImageView iv_society_logo1;

        @BindView(R.id.tv_society_title)
        TextView tv_society_title;

        @BindView(R.id.tv_society_address)
        TextView tv_society_address;

        @BindView(R.id.lin_root)
        RelativeLayout lin_root;

        MySocietyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
