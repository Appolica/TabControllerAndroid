package com.appolica.sample.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appolica.sample.R;
import com.appolica.sample.TabClickListener;
import com.appolica.sample.Tabs;
import com.appolica.sample.databinding.ActivityTabControllerBinding;
import com.appolica.tabcontroller.AttachDetachHandler;
import com.appolica.tabcontroller.FragmentProvider;
import com.appolica.tabcontroller.TabController;
import com.appolica.tabcontroller.listener.OnFragmentChangeListener;

public class TabControllerActivity
        extends AppCompatActivity
        implements TabClickListener, OnFragmentChangeListener {

    private static final String TAG = "TabControllerActivity";

    private TabController tabController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTabControllerBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_tab_controller);

        binding.setClickListener(this);

        tabController = new TabController(getSupportFragmentManager(), R.id.container, new AttachDetachHandler());
        tabController.setChangeListener(this);

        if (savedInstanceState != null) {
            tabController.restore(savedInstanceState);
        } else {
            tabController.switchTo(Tabs.TAB_1);
        }
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
//        tabController.switchTo(Tabs.TAB_4);
    }

    @Override
    public void onFragmentShown(FragmentProvider provider, Fragment shownFragment) {
        Log.d(TAG, "onFragmentShown: " + provider.getTag());
    }

    @Override
    public void onFragmentAlreadyVisible(FragmentProvider provider, Fragment visibleFragment) {
        Log.d(TAG, "onFragmentAlreadyVisible: " + provider.getTag());
    }

    @Override
    public void onFragmentCreated(FragmentProvider provider, Fragment createdFragment) {
        Log.d(TAG, "onFragmentCreated: " + provider.getTag());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        tabController.save(outState);
    }
}
