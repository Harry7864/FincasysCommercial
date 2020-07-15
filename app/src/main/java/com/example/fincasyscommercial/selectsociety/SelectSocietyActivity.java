package com.example.fincasyscommercial.selectsociety;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fincasyscommercial.PreferenceManager;
import com.example.fincasyscommercial.R;
import com.example.fincasyscommercial.Tools;
import com.example.fincasyscommercial.VariableBag;
import com.example.fincasyscommercial.WebViewMainActivity;
import com.example.fincasyscommercial.network.RestCall;
import com.example.fincasyscommercial.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SelectSocietyActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.recy_society_list)
    RecyclerView recyclerView;

    @BindView(R.id.btn_continue)
    Button btn_continue;

    @BindView(R.id.sv_society)
    SearchView searchView;

    @BindView(R.id.rel_nodata)
    RelativeLayout rel_nodata;

    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    List<SocietyResponce.Society> societyResponce1;
    SocietyAdapter adepter;

    @BindView(R.id.ps_bar)
    ProgressBar ps_bar;

    String societyId,sName;
    String countryId="0",stateId="0",cityId="0";

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_society);
        ButterKnife.bind(this);
        Tools.setSystemBarColor(SelectSocietyActivity.this);

        searchView.setInputType(InputType.TYPE_NULL);


        preferenceManager = new PreferenceManager(SelectSocietyActivity.this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){

            countryId = bundle.getString("countryId");
            stateId = bundle.getString("stateId");
            cityId = bundle.getString("cityId");
            societyResponce1  = new ArrayList<>();
            initCode();

        }

    }

    private void initCode() {

        ps_bar.setVisibility(View.VISIBLE);
        rel_nodata.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        btn_continue.setVisibility(View.GONE);

        RestCall call = RestClient.createService(RestCall.class, VariableBag.MAIN_URL, VariableBag.MAIN_KEY);
        RequestBody tag = RequestBody.create(MediaType.parse("multipart/form-data"), "getSociety");
        RequestBody ci = RequestBody.create(MediaType.parse("multipart/form-data"), countryId);
        RequestBody si = RequestBody.create(MediaType.parse("multipart/form-data"), stateId);
        RequestBody cti = RequestBody.create(MediaType.parse("multipart/form-data"), cityId);

            call.getSocietyList(tag, ci, si, cti)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<SocietyResponce>() {
                        @Override
                        public void onCompleted() {

                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onError(final Throwable e) {

                            runOnUiThread(() -> {
                                searchView.setActivated(true);
                                searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                                recyclerView.setVisibility(View.GONE);
                                rel_nodata.setVisibility(View.VISIBLE);
                                tv_no_data.setText("No Society Found");
                                Toast.makeText(SelectSocietyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onNext(final SocietyResponce societyResponce) {

                            runOnUiThread(() -> {

                                searchView.setActivated(true);
                                searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                                if (societyResponce.getStatus().equalsIgnoreCase("200")) {

                                    societyResponce1.clear();
                                    societyResponce1.addAll(societyResponce.getSociety());

                                    ps_bar.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    rel_nodata.setVisibility(View.VISIBLE);
                                    tv_no_data.setText("Search Your Society");

                                    adepter = new SocietyAdapter(SelectSocietyActivity.this, societyResponce1);

                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(SelectSocietyActivity.this));
                                    recyclerView.setAdapter(adepter);

                                    adepter.setUpInterface((data, sname, pos, baseurl, apikey, society,isClick) -> {
                                        if (isClick){
                                            societyId = data;
                                            sName = sname;
                                            preferenceManager.setSocietyId(societyId);
                                            preferenceManager.setBaseUrl(baseurl);
                                            preferenceManager.setApikey(apikey);
                                            preferenceManager.setSocietyName(society.getSocietyName());
                                            btn_continue.setEnabled(true);
                                            btn_continue.setBackground(ContextCompat.getDrawable(SelectSocietyActivity.this, R.drawable.btn_rounded_primary));
                                            adepter.update();
                                        }else {
                                            btn_continue.setBackground(ContextCompat.getDrawable(SelectSocietyActivity.this, R.drawable.btn_rounded_disable));
                                            btn_continue.setEnabled(false);
                                            adepter.update();

                                        }
                                    });


                                } else {

                                    recyclerView.setVisibility(View.GONE);
                                    rel_nodata.setVisibility(View.VISIBLE);
                                    ps_bar.setVisibility(View.GONE);
                                    tv_no_data.setText("Search Your Society");


                                }

                            });

                        }
                    });

            searchView.setOnQueryTextListener(this);

    }


    @OnClick(R.id.btn_continue)
    public void btn_continue(){

        preferenceManager.setKeyValueString("stateId",stateId);
        preferenceManager.setKeyValueString("cityId",cityId);
        preferenceManager.setKeyValueString("countryId",countryId);
        VariableBag.SOCIETY_NAME = sName;

        Intent intent = new Intent(SelectSocietyActivity.this, WebViewMainActivity.class);
        intent.putExtra("societyId",societyId);
        intent.putExtra("countryId",countryId);
        intent.putExtra("stateId",stateId);
        intent.putExtra("cityId",cityId);
        intent.putExtra("sName",sName);
        VariableBag.SOCIETY_NAME=sName;
        startActivity(intent);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onQueryTextChange(String s) {
        List<SocietyResponce.Society> responceList= new ArrayList<>();
        boolean flag=false;
        if (adepter != null && societyResponce1 != null) {
            if (s.trim().length() > 2 ) {
                for (int i = 0; i < societyResponce1.size(); i++) {
                    if (societyResponce1.get(i).getSocietyName().trim().toLowerCase().contains(s.trim().toLowerCase())) {
                        responceList.add(societyResponce1.get(i));
                        flag = true;
                    }
                }
                if (flag) {
                    adepter.Update(responceList);
                    recyclerView.setVisibility(View.VISIBLE);
                    rel_nodata.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    rel_nodata.setVisibility(View.VISIBLE);
                    tv_no_data.setText("No Society Found");
                    btn_continue.setVisibility(View.GONE);

                }
            } else {
                set();

                btn_continue.setEnabled(false);
                btn_continue.setBackground(ContextCompat.getDrawable(SelectSocietyActivity.this, R.drawable.btn_rounded_disable));
                adepter.Update(societyResponce1);
                recyclerView.setVisibility(View.GONE);
                rel_nodata.setVisibility(View.VISIBLE);
                tv_no_data.setText("Search Your Society");
                btn_continue.setVisibility(View.GONE);

            }
        }
        return false;
    }

    private void set(){
        btn_continue.setVisibility(View.GONE);
        btn_continue.setEnabled(false);
        btn_continue.setTextColor(ContextCompat.getColor(SelectSocietyActivity.this, R.color.grey_10));
        btn_continue.setBackground(ContextCompat.getDrawable(SelectSocietyActivity.this, R.drawable.btn_rounded_grey_10));


        for(SocietyResponce.Society st :  societyResponce1){
            st.setClicked(false);
        }
    }


    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
