package com.example.android.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {


    /**
     * Create a new {@link SimpleFragmentPagerAdapter} object.
     *
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public SimpleFragmentPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment myFragment = null;
        switch (position) {
            case 0:
                myFragment = new NewsFragment();
                break;
        }
        return myFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
