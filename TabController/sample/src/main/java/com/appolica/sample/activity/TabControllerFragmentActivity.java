package com.appolica.sample.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appolica.sample.R;
import com.appolica.sample.Tabs;
import com.appolica.sample.databinding.ActivityTabFragmentBinding;
import com.appolica.sample.tabs.BottomBarListener;
import com.appolica.tabcontroller.FragmentProvider;
import com.appolica.tabcontroller.TabController;
import com.appolica.tabcontroller.fragment.TabControllerFragment;
import com.appolica.tabcontroller.listener.OnFragmentChangeListener;

public class TabControllerFragmentActivity
        extends AppCompatActivity
        implements OnFragmentChangeListener,
        BottomBarListener.BottomBarTabListener {

    private static final String TAG = "TabControllerActivity";
    private static final String BUNDLE_BOTTOM_BAR = TabControllerFragmentActivity.class.getName() + ":BottomBar";

    private TabController tabController;
    private ActivityTabFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment_tab_controller);

        final TabControllerFragment tabControllerFragment =
                (TabControllerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        tabController = tabControllerFragment.getTabController();
        tabController.setChangeListener(this);

        if (savedInstanceState != null) {
            binding.bottomBar.onRestoreInstanceState(savedInstanceState.getParcelable(BUNDLE_BOTTOM_BAR));
        }

        binding.bottomBar.setOnTabSelectListener(new BottomBarListener(this), true);
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        final Parcelable bottomBarParcelable = binding.bottomBar.onSaveInstanceState();

        outState.putParcelable(BUNDLE_BOTTOM_BAR, bottomBarParcelable);
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
