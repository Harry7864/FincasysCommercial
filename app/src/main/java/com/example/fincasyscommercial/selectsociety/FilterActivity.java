package com.example.fincasyscommercial.selectsociety;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fincasyscommercial.PreferenceManager;
import com.example.fincasyscommercial.R;
import com.example.fincasyscommercial.Tools;
import com.example.fincasyscommercial.VariableBag;
import com.example.fincasyscommercial.network.RestCall;
import com.example.fincasyscommercial.network.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FilterActivity extends AppCompatActivity {

    @BindView(R.id.tv_country)
    TextView tv_country;

    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.tv_city)
    TextView tv_city;

    @BindView(R.id.ps_bar)
    ProgressBar ps_bar;

    @BindView(R.id.card_location)
    LinearLayout card_location;

    PreferenceManager preferenceManager;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    RestCall call;

    Tools tools;

    LocationResponce locationRespo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(this);


        call = RestClient.createService(RestCall.class, VariableBag.MAIN_URL, VariableBag.MAIN_KEY);

        tools = new Tools(this);

//        VariableBag.BACKIMG = preferenceManager.getBackBanner();
//        Tools.displayImageBG(this, iv_back, VariableBag.BACKIMG);

        Log.e("@@", "onCreate: " + preferenceManager.getBackBanner());

        preferenceManager.clearPreferences();
        initCode();

    }


    private SpinAdapter adaptercountry;
    private SpinAdapter adapterstate;
    private SpinAdapter adaptercity;

    List<LocationHelper> city = new ArrayList<>();
    List<LocationHelper> country = new ArrayList<>();
    List<LocationHelper> states = new ArrayList<>();
    String countryId = "-1", stateId = "-1", cityId = "-1";

    String TempcountryId = "-1", TempstateId = "-1", TempcityId = "-1";

    String countryName = "", stateName = "", cityName = "";
    String TempcountryName = "", TempstateName = "", TempcityName = "";

    private void initCode() {

        ps_bar.setVisibility(View.VISIBLE);
        card_location.setVisibility(View.GONE);
        final RestCall call = RestClient.createService(RestCall.class, VariableBag.MAIN_URL, VariableBag.MAIN_KEY);

        call.getCountry("getCountries")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LocationResponce>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            if (!FilterActivity.this.isDestroyed()) {
                                ps_bar.setVisibility(View.GONE);
                                tools.stopLoading();
                            }
                        }

                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onNext(final LocationResponce locationResponce) {

                        if (!FilterActivity.this.isDestroyed()) {
                            tools.stopLoading();

                            if (locationResponce.getStatus().equalsIgnoreCase("200")) {

                                locationRespo = locationResponce;
                                ps_bar.setVisibility(View.GONE);
                                card_location.setVisibility(View.VISIBLE);

                                country = new ArrayList<>();

                                country.clear();

                                if (locationResponce.getCountries() != null) {
                                    for (int i = 0; i < locationResponce.getCountries().size(); i++) {

                                        country.add(new LocationHelper(locationResponce.getCountries().get(i).getName(), locationResponce.getCountries().get(i).getCountryId()));

                                    }
                                }
                            } else {
                                Tools.toast(FilterActivity.this, "" + locationResponce.getMessage(), 1);
                            }

                        }
                    }
                });


    }

    @Override
    public void onBackPressed() {

    }


    @SuppressLint("SetTextI18n")
    @OnClick(R.id.lin_country)
    public void lin_country() {

        RecyclerView areaListRv;
        Button doneBtn, cancelBt;
        EditText etSearch;

        final Dialog dialogBuilder = new Dialog(FilterActivity.this);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.select_area_dialog);
        etSearch = dialogBuilder.findViewById(R.id.etSearch);
        areaListRv = dialogBuilder.findViewById(R.id.area_list_rv);
        doneBtn = dialogBuilder.findViewById(R.id.done_btn);
        cancelBt = dialogBuilder.findViewById(R.id.cancel_bt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        areaListRv.setLayoutManager(layoutManager);
        areaListRv.setItemAnimator(new DefaultItemAnimator());
        etSearch.setHint("Search Country");
        dialogBuilder.setCancelable(false);


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TempcountryId.equalsIgnoreCase("-1")) {
                    Tools.toast(FilterActivity.this, "Please Select Country..!", 1);
                } else {
                    dialogBuilder.dismiss();

                    countryId = TempcountryId;
                    countryName = TempcountryName;


                    Tools.hideSoftKeyboard(FilterActivity.this);

                    tv_country.setText("" + countryName);

                    ClearState();
                    ClearCity();


                    if (Tools.vpn()) {

                        new AlertDialog.Builder(FilterActivity.this)
                                .setMessage("Something went wrong.Please try again later.")
                                .setPositiveButton("Retry", (dialog, which) -> FilterActivity.this.reload())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else {

                        tools.showLoading();

                        call.getState("getState", countryId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<LocationResponce>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        tools.stopLoading();

                                    }

                                    @Override
                                    public void onNext(final LocationResponce locationResponce) {
                                        tools.stopLoading();

                                        if (locationResponce.getStatus().equalsIgnoreCase("200")) {

                                            states = new ArrayList<>();
                                            states.clear();

                                            for (int i = 0; i < locationResponce.getStates().size(); i++) {

                                                states.add(new LocationHelper(locationResponce.getStates().get(i).getName(), locationResponce.getStates().get(i).getStateId()));

                                            }


                                        } else {
                                            Tools.toast(FilterActivity.this, "" + locationResponce.getMessage(), 1);
                                        }
                                    }
                                });

                    }


                }


            }
        });

        cancelBt.setOnClickListener(v -> {

            dialogBuilder.dismiss();
        });

        dialogBuilder.show();

        if (country != null) {
            adaptercountry = new SpinAdapter(FilterActivity.this, country, Integer.parseInt(countryId), countryId);

            adaptercountry.setOnItemClickListener((name, id) -> {

                adaptercountry.selectedItem();

                TempcountryId = id;
                TempcountryName = name;

                Tools.hideSoftKeyboard(FilterActivity.this);


            });

            areaListRv.setAdapter(adaptercountry);

        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                if (country != null) {
                    List<LocationHelper> countryFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < country.size(); i++) {

                            if (country.get(i).name.toLowerCase().contains(newText.toLowerCase())) {
                                countryFilter.add(country.get(i));
                            }

                        }
                    } else {
                        countryFilter = country;

                    }
                    adaptercountry.updateSearch(countryFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.lin_state)
    public void lin_state() {

        RecyclerView areaListRv;
        Button doneBtn, cancelBt;
        EditText etSearch;

        final Dialog dialogBuilder = new Dialog(FilterActivity.this);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.select_area_dialog);
        etSearch = dialogBuilder.findViewById(R.id.etSearch);
        areaListRv = dialogBuilder.findViewById(R.id.area_list_rv);
        doneBtn = dialogBuilder.findViewById(R.id.done_btn);
        cancelBt = dialogBuilder.findViewById(R.id.cancel_bt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        areaListRv.setLayoutManager(layoutManager);
        areaListRv.setItemAnimator(new DefaultItemAnimator());
        etSearch.setHint("Search State");

        dialogBuilder.setCancelable(false);

        doneBtn.setOnClickListener(v -> {

            if (!TempstateId.equals("-1")) {

                dialogBuilder.dismiss();

                stateId = TempstateId;
                stateName = TempstateName;

                tv_state.setText("" + stateName);

                ClearCity();

                Tools.hideSoftKeyboard(FilterActivity.this);

                if (Tools.vpn()) {

                    new AlertDialog.Builder(this)
                            .setMessage("Something went wrong.Please try again later.")
                            .setPositiveButton("Retry", (dialog, which) -> reload())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    tools.showLoading();

                    call.getCity("getCity", stateId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<LocationResponce>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    tools.stopLoading();

                                }

                                @Override
                                public void onNext(final LocationResponce locationResponce) {
                                    tools.stopLoading();

                                    if (locationResponce.getStatus().equalsIgnoreCase("200")) {

                                        city = new ArrayList<>();
                                        city.clear();

                                        for (int i = 0; i < locationResponce.getCities().size(); i++) {

                                            city.add(new LocationHelper(locationResponce.getCities().get(i).getName(), locationResponce.getCities().get(i).getCity_id()));

                                        }


                                    } else {
                                        Tools.toast(FilterActivity.this, "" + locationResponce.getMessage(), 1);
                                    }
                                }
                            });
                }

            } else {
                Tools.toast(FilterActivity.this, "Please Select State..!", 1);
            }

        });

        cancelBt.setOnClickListener(v -> {
            dialogBuilder.dismiss();

        });

        dialogBuilder.show();

        if (states != null) {

            adapterstate = new SpinAdapter(FilterActivity.this, states, Integer.parseInt(stateId), stateId);

            adapterstate.setOnItemClickListener((name, id) -> {

                adapterstate.selectedItem();

                TempstateId = id;
                TempstateName = name;

                Tools.hideSoftKeyboard(FilterActivity.this);


            });

            areaListRv.setAdapter(adapterstate);

        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                if (states != null) {
                    List<LocationHelper> stateFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < states.size(); i++) {

                            if (states.get(i).name.toLowerCase().contains(newText.toLowerCase())) {
                                stateFilter.add(states.get(i));
                            }

                        }
                    } else {
                        stateFilter = states;

                    }
                    adapterstate.updateSearch(stateFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @OnClick(R.id.lin_city)
    public void lin_city() {

        RecyclerView areaListRv;
        Button doneBtn, cancelBt;
        EditText etSearch;

        final Dialog dialogBuilder = new Dialog(FilterActivity.this);
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(R.layout.select_area_dialog);
        areaListRv = dialogBuilder.findViewById(R.id.area_list_rv);
        etSearch = dialogBuilder.findViewById(R.id.etSearch);
        doneBtn = dialogBuilder.findViewById(R.id.done_btn);
        cancelBt = dialogBuilder.findViewById(R.id.cancel_bt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
        areaListRv.setLayoutManager(layoutManager);
        areaListRv.setItemAnimator(new DefaultItemAnimator());
        etSearch.setHint("Search City");
        dialogBuilder.setCancelable(false);


        doneBtn.setOnClickListener(v -> {
            if (!Objects.equals(TempcityId, "-1")) {
                dialogBuilder.dismiss();
                cityId = TempcityId;
                cityName = TempcityName;

                tv_city.setText("" + cityName);
            } else {
                Tools.toast(FilterActivity.this, "Please Select City", 1);
            }

        });

        cancelBt.setOnClickListener(v -> {
            dialogBuilder.dismiss();
        });

        dialogBuilder.show();
        if (city != null) {

            adaptercity = new SpinAdapter(FilterActivity.this, city, Integer.parseInt(cityId), cityId);


            adaptercity.setOnItemClickListener((name, id) -> {

                adaptercity.selectedItem();
                TempcityId = id;
                TempcityName = name;
                Tools.hideSoftKeyboard(FilterActivity.this);


            });
            areaListRv.setAdapter(adaptercity);

        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                if (city != null) {
                    List<LocationHelper> cityFilter = new ArrayList<>();

                    if (newText.length() > 0) {
                        for (int i = 0; i < city.size(); i++) {

                            if (city.get(i).name.toLowerCase().contains(newText.toLowerCase())) {
                                cityFilter.add(city.get(i));
                            }

                        }
                    } else {
                        cityFilter = city;

                    }
                    adaptercity.updateSearch(cityFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void ClearState() {

        if (states != null) {
            stateId = "-1";
            TempstateId = "-1";
            stateName = "";
            states.clear();
            tv_state.setText("Select State");

        }

    }

    public void ClearCity() {

        if (city != null) {
            cityId = "-1";
            TempcityId = "-1";
            cityName = "";
            city.clear();
            tv_city.setText("Select City");
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


    @OnClick(R.id.btn_next)
    public void click() {
        if (countryId.equalsIgnoreCase("-1")) {
            Tools.toast(FilterActivity.this, "Select Country", 1);
        } else if (stateId.equalsIgnoreCase("-1")) {
            Tools.toast(FilterActivity.this, "Select State", 1);
        } else if (cityId.equalsIgnoreCase("-1")) {
            Tools.toast(FilterActivity.this, "Select City", 1);
        } else {

            Intent intent = new Intent(FilterActivity.this, SelectSocietyActivity.class);
            intent.putExtra("countryId", countryId);
            intent.putExtra("stateId", stateId);
            intent.putExtra("cityId", cityId);
            startActivity(intent);
        }
    }


}
