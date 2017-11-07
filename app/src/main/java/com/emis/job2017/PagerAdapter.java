package com.emis.job2017;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jo5 on 17/10/17.
 * Implementing the adapter for ViewPager which controls the order of the tabs, the titles and their associated content.
 *
 */

public class PagerAdapter extends FragmentPagerAdapter {

    //Test for git
    final static int PAGE_COUNT = 5;
    private Context context;

    public PagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ExhibitorsPage();
            case 1:
                return new NewsPage();
            case 2:
                return new QRCodeReader();
            case 3:
                return new CalendarPage();
            case 4:
//                return new ExhibitorsPage();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return null;
    }
}
