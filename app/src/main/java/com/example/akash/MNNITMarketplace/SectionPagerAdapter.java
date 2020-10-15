package com.example.akash.MNNITMarketplace;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                Books booksfragment = new Books();
                return booksfragment;
            case 1:
                Electronics electrofragment = new Electronics();
                return electrofragment;
            case 2:
                Others othersfragment = new Others();
                return othersfragment;
            default:
                   return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch(position){
           case 0:
               return "Books";
           case 1:
               return "Electronics";
           case 2:
               return "Others";
           default:
               return null;
       }
    }
}
