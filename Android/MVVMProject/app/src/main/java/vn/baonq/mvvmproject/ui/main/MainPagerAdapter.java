package vn.baonq.mvvmproject.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.baonq.mvvmproject.ui.main.home.HomeFragment;
import vn.baonq.mvvmproject.ui.main.home_buyer.HomeBuyerFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return HomeBuyerFragment.newInstance();
            default:
                return null;
        }
    }
}