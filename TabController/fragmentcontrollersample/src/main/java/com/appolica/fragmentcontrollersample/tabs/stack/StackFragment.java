package com.appolica.fragmentcontrollersample.tabs.stack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.fragmentcontroller.FragmentController;
import com.appolica.fragmentcontrollersample.R;
import com.appolica.fragmentcontrollersample.databinding.StackFragmentsBinding;
import com.appolica.fragmentcontrollersample.tabs.FragmentsType;

public class StackFragment extends Fragment implements StackFragmentClickListener {

    private static final String CONTROLLER_TAG = "FRAGMENT_CONTAINER";
    private StackFragmentsBinding binding;
    private FragmentController fragmentController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_tab_stack, container, false);

        fragmentController = FragmentController.instance(FragmentsType.NESTED);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentController, CONTROLLER_TAG)
                .commit();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setClickListener(this);
    }

    @Override
    public void onPopClick() {
        popFragment();
    }

    @Override
    public void onPushClick() {
        pushFragment();
    }

    private void pushFragment() {
        final int stackCount = fragmentController.getChildFragmentManager().getBackStackEntryCount();

        fragmentController.pushBody()
                .addToBackStack(true)
                .withAnimation(true)
                .fragment(FragmentsType.NESTED.withIndex(stackCount))
                .push();
    }

    private void popFragment() {
        fragmentController.pop(true);
    }
}
