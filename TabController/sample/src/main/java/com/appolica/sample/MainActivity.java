package com.appolica.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appolica.sample.databinding.ActivityMainBinding;
import com.appolica.tabcontroller.FragmentProvider;
import com.appolica.tabcontroller.OnFragmentChangeListener;
import com.appolica.tabcontroller.TabController;

public class MainActivity extends AppCompatActivity implements TabClickListener, OnFragmentChangeListener {

    private static final String TAG = "MainActivity";

    private TabController tabController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setClickListener(this);
        tabController = new TabController(getSupportFragmentManager(), R.id.holder);

        tabController.setChangeListener(this);
        tabController.switchTo(Tabs.TAB_1);
    }

    @Override
    public void onTab1Click() {
        tabController.switchTo(Tabs.TAB_1);
    }

    @Override
    public void onTab2Click() {
        tabController.switchTo(Tabs.TAB_2);
    }

    @Override
    public void onTab3Click() {
        tabController.switchTo(Tabs.TAB_3);
    }

    @Override
    public void onTab4Click() {
        tabController.switchTo(Tabs.TAB_4);
    }

    @Override
    public void onFragmentShown(FragmentProvider fragmentType, Fragment shownFragment) {
        Log.d(TAG, "onFragmentShown: " + fragmentType.getTag());
    }

    @Override
    public void onFragmentAlreadyVisible(FragmentProvider fragmentType, Fragment visibleFragment) {
        Log.d(TAG, "onFragmentAlreadyVisible: " + fragmentType.getTag());
    }

    @Override
    public void onFragmentCreated(FragmentProvider fragmentType, Fragment addedFragment) {
        Log.d(TAG, "onFragmentCreated: " + fragmentType.getTag());
    }
}
