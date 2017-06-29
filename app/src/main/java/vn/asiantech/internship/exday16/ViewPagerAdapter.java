package vn.asiantech.internship.exday16;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mImages;

    ViewPagerAdapter(FragmentManager fm, List<String> images) {
        super(fm);
        mImages = images;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new PageBFragment();
        }
        return ItemImageFragment.newInstance(mImages, position);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int pos = position + 1;
        return "Picture " + pos;
    }
}
