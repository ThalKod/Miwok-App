package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Thal Marc on 1/16/2017.
 */

public class CustomFragmentAdapter extends FragmentPagerAdapter {

    private String titre[] = {"NUMBER","FAMILY","COLOR","PHRASES",};

    public CustomFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return new NumbersFragment();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titre[position];
    }
}
