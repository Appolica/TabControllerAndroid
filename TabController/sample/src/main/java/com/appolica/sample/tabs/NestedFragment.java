package com.appolica.sample.tabs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.sample.R;
import com.appolica.sample.databinding.FragmentStackNestedBinding;


public class NestedFragment extends Fragment {

    public static final String KEY_FRAGMENT_INDEX = NestedFragment.class.getName() + ":KEY_FRAGMENT_INDEX";
    public static final String TAG = NestedFragment.class.getSimpleName();
    private FragmentStackNestedBinding binding;

    public static NestedFragment getInstance(int index) {
        final Bundle args = new Bundle();
        final NestedFragment fragment = new NestedFragment();

        args.putInt(KEY_FRAGMENT_INDEX, index);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stack_nested, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int fragmentIndex = getArguments().getInt(KEY_FRAGMENT_INDEX);
        binding.setText(getString(R.string.stack_fragment_index, fragmentIndex));
    }
}
