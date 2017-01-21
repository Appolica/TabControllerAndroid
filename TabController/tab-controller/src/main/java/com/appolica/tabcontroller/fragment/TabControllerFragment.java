package com.appolica.tabcontroller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.tabcontroller.R;
import com.appolica.tabcontroller.TabController;


public class TabControllerFragment extends Fragment {

    private TabController tabController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tab_controller, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabController = new TabController(getChildFragmentManager(), R.id.container);
    }

    public TabController getTabController() {
        return tabController;
    }
}
