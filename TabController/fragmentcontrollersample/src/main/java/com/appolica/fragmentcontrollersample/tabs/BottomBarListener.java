package com.appolica.fragmentcontrollersample.tabs;

import android.support.annotation.IdRes;

import com.appolica.fragmentcontrollersample.R;
import com.roughike.bottombar.OnTabSelectListener;

public class BottomBarListener implements OnTabSelectListener {

    private BottomBarTabListener listener;

    public BottomBarListener(BottomBarTabListener listener) {
        this.listener = listener;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_stack:
                listener.onStackSelected();
                break;
            case R.id.tab_pager:
                listener.onViewPagerSelected();
                break;
        }
    }

    public interface BottomBarTabListener {
        void onStackSelected();
        void onViewPagerSelected();
    }
}
