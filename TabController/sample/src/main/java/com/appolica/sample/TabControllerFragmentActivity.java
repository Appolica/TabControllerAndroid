package com.appolica.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appolica.sample.databinding.ActivityTabFragmentBinding;
import com.appolica.tabcontroller.FragmentProvider;
import com.appolica.tabcontroller.TabController;
import com.appolica.tabcontroller.fragment.TabControllerFragment;
import com.appolica.tabcontroller.listener.OnFragmentChangeListener;

public class TabControllerFragmentActivity
        extends AppCompatActivity
        implements TabClickListener, OnFragmentChangeListener {

    private static final String TAG = "TabControllerActivity";

    private TabController tabController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityTabFragmentBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_fragment_tab_controller);

        binding.setClickListener(this);

        final TabControllerFragment tabControllerFragment =
                (TabControllerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        tabController = tabControllerFragment.getTabController();
        tabController.setChangeListener(this);

        if (savedInstanceState == null) {
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
        tabController.switchTo(Tabs.TAB_4);
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

}
