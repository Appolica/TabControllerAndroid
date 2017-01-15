package com.appolica.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appolica.sample.databinding.ActivityMainBinding;
import com.appolica.tabcontroller.TabController;

public class MainActivity extends AppCompatActivity implements TabClickListener {

    private TabController tabController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setClickListener(this);
        tabController = new TabController(getSupportFragmentManager(), R.id.holder);

        tabController.switchTo(Tabs.TAB_1);
    }

    @Override
    public void onTab1Click() {
        tabController.switchTo(Tabs.TAB_1);
    }

    @Override
    public void onTab2Click() {
        tabController.switchTo(Tabs.TAB_2);
    }

    @Override
    public void onTab3Click() {
        tabController.switchTo(Tabs.TAB_3);
    }

    @Override
    public void onTab4Click() {
        tabController.switchTo(Tabs.TAB_4);
    }
}
