package com.snoozieapp.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.snoozieapp.app.ui.Adapter;

public class HomePage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        Adapter adapter = new Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new Alarms(), "ALARMS");
        adapter.addFragment(new Track(), "TRACK");
        adapter.addFragment(new Audio(), "AUDIO");
        adapter.addFragment(new Settings(), "SETTINGS");
        viewPager.setAdapter(adapter);

        if (savedInstanceState == null)
        {
            BluetoothPage page = new BluetoothPage();
        }
    }

}
