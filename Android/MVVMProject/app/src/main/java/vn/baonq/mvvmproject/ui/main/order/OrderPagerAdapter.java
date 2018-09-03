package vn.baonq.mvvmproject.ui.main.order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryFragment;
import vn.baonq.mvvmproject.ui.main.order.Received.ReceivedFragment;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedFragment;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitFragment;

public class OrderPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public OrderPagerAdapter(FragmentManager fragmentManager) {
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
                return RequestedFragment.newInstance();
            case 1:
                return TransitFragment.newInstance();
            case 2:
                return ReceivedFragment.newInstance();
            default:
                return null;
        }
    }
}
