package com.appolica.sample.tabs.stack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.appolica.sample.R;
import com.appolica.sample.databinding.StackFragmentsBinding;
import com.appolica.sample.tabs.NestedFragment;

public class StackFragment extends Fragment implements StackFragmentClickListener {

    private StackFragmentsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_tab_stack, container, false);

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
        final FragmentManager childFragmentManager = getChildFragmentManager();
        final int stackCount = childFragmentManager.getBackStackEntryCount();
        final NestedFragment fragment = NestedFragment.getInstance(stackCount);

        childFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(String.format("%s:%d", NestedFragment.TAG, stackCount))
                .commit();

        childFragmentManager.executePendingTransactions();
    }

    private void popFragment() {
        getChildFragmentManager().popBackStack();
    }
}
