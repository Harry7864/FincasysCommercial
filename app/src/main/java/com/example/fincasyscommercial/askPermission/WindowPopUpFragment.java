package com.example.fincasyscommercial.askPermission;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fincasyscommercial.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class WindowPopUpFragment extends BottomSheetDialogFragment {


    private DismissCheck dismissCheck;

    public WindowPopUpFragment() {
        // Required empty public constructor
    }

    public void setUpInterface(DismissCheck upInterface) {
        this.dismissCheck = upInterface;
    }

    public interface DismissCheck {
        void dissmiss(boolean b);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_window_pop_up, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.tvOpenSetting)
    void tvOpenSetting() {

        if (dismissCheck != null) {
            dismissCheck.dissmiss(true);
        }
        dismiss();

    }

    @OnClick(R.id.tvLater)
    void tvLater() {
        if (dismissCheck != null) {
            dismissCheck.dissmiss(false);
        }

        dismiss();
    }

    @OnClick(R.id.iv_img_per1)
    void iv_img_per1() {

        ImageViewFragment imageViewFragment = new ImageViewFragment(getResources().getDrawable(R.drawable.app_pop_up_img));

        imageViewFragment.show(getChildFragmentManager(), "dialogPop");
    }

    @OnClick(R.id.iv_img_per2)
    void iv_img_per2() {
        ImageViewFragment imageViewFragment = new ImageViewFragment(getResources().getDrawable(R.drawable.app_pop_up_allow));

        imageViewFragment.show(getChildFragmentManager(), "dialogPop");

    }


}
