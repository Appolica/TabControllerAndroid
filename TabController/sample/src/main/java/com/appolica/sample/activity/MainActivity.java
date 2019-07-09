package com.appolica.sample.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appolica.sample.R;
import com.appolica.sample.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewActivities.setHasFixedSize(true);

        ActivitiesAdapter adapter = new ActivitiesAdapter();
        binding.recyclerViewActivities.setAdapter(adapter);

        final ArrayList<Class<? extends Activity>> activityClasses = new ArrayList<>();
        activityClasses.add(TabControllerActivity.class);
        activityClasses.add(TabControllerFragmentActivity.class);

        adapter.addData(activityClasses);


    }
}
