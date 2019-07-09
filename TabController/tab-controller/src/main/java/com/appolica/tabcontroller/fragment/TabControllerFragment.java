package com.appolica.tabcontroller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appolica.tabcontroller.R;
import com.appolica.tabcontroller.TabController;

/**
 * An encapsulation for the fragments that are managed through the {@link TabController}.
 * All of these fragments are switched between within this fragment using child fragment manager.
 * <br>
 * We encourage you to use this fragment in order to restrict the access of the
 * {@link TabController} to any other fragments, that aren't related to it and vice versa.
 */
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

        if (savedInstanceState != null) {
            tabController.restore(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tabController.save(outState);
    }

    /**
     * Obtain the {@link TabController}. Its instance is created in
     * {@link Fragment#onViewCreated(View, Bundle)}.
     *
     * @return The {@link TabController}'s instance or null if hasn't been instantiated yet.
     */
    public TabController getTabController() {
        return tabController;
    }
}
