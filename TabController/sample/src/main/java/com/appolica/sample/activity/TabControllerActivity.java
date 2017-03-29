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
import com.appolica.sample.tabs.BottomBarListener;
import com.appolica.tabcontroller.AttachDetachHandler;
import com.appolica.tabcontroller.FragmentProvider;
import com.appolica.tabcontroller.ShowHideFrHandler;
import com.appolica.tabcontroller.TabController;
import com.appolica.tabcontroller.listener.OnFragmentChangeListener;

public class TabControllerActivity
        extends AppCompatActivity
        implements OnFragmentChangeListener, BottomBarListener.BottomBarTabListener {

    private static final String BUNDLE_BOTTOM_BAR = TabControllerFragmentActivity.class.getName() + ":BottomBar";
    private static final String TAG = "TabControllerActivity";

    private TabController tabController;
    private ActivityTabControllerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_controller);

        tabController = new TabController(getSupportFragmentManager(), R.id.container, new AttachDetachHandler());
        tabController.setChangeListener(this);

        if (savedInstanceState != null) {
            binding.bottomBar.onRestoreInstanceState(savedInstanceState.getParcelable(BUNDLE_BOTTOM_BAR));
            tabController.restore(savedInstanceState);
        }

        binding.bottomBar.setOnTabSelectListener(new BottomBarListener(this), true);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tabController.save(outState);
    }

    @Override
    public void onStackSelected() {
        tabController.switchTo(Tabs.TAB_1);
    }

    @Override
    public void onViewPagerSelected() {
        tabController.switchTo(Tabs.TAB_2);
    }

    @Override
    public void onFlatSelected() {
        tabController.switchTo(Tabs.TAB_3);
    }
}
