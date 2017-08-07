package com.appolica.fragmentcontrollersample.tabs.pager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.fragmentcontrollersample.R;
import com.appolica.fragmentcontrollersample.databinding.FragmentTabViewPagerBinding;


public class ViewPagerFragment extends Fragment {

    private FragmentTabViewPagerBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_view_pager, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());

        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.viewPager.setAdapter(pagerAdapter);
    }
}
