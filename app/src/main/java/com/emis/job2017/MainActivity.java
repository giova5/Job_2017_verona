package com.emis.job2017;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //TODO: change images.
    private int[] imageResId = {
            R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_notifications_black_24dp,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher_round};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), MainActivity.this));

        // Give the TabLayout the ViewPager.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

    }

}
