package com.appolica.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.sample.databinding.FragmentTabBinding;

public class SampleTab extends Fragment {

    private static final String BUNDLE_NUMBER = "bundle-tab-num";
    private FragmentTabBinding binding;

    public static SampleTab getInstance(int tabNum) {
        final SampleTab tab = new SampleTab();

        final Bundle arguments = new Bundle();
        arguments.putInt(BUNDLE_NUMBER, tabNum);

        tab.setArguments(arguments);

        return tab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int tabNum = getArguments().getInt(BUNDLE_NUMBER);
        binding.setTabNum("Tab " + tabNum);
    }
}
