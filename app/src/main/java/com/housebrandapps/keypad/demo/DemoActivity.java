package com.housebrandapps.keypad.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

import com.housebrandapps.keypad.demo.pages.DemoAllAttrFragment;
import com.housebrandapps.keypad.demo.pages.DemoBasicFragment;

public class DemoActivity extends AppCompatActivity {

    private static final int DEMO_TOTAL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Button nightSwitch = findViewById(R.id.switch_nightmode);
        nightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("KEY_PAD_DEMO", Context.MODE_PRIVATE);
                boolean isNightMode = prefs.getBoolean("KEY_PAD_NIGHT_MODE", false);
                prefs.edit().putBoolean("KEY_PAD_NIGHT_MODE", !isNightMode).apply();
                v.setOnClickListener(null);
                recreate();
            }
        });

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private boolean isNightMode() {
        SharedPreferences prefs = this.getSharedPreferences("KEY_PAD_DEMO", Context.MODE_PRIVATE);
        return prefs.getBoolean("KEY_PAD_NIGHT_MODE", false);
    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                default:
                    return DemoBasicFragment.newInstance();
                case 1:
                    return DemoAllAttrFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return DEMO_TOTAL;
        }
    }
}
