package vn.baonq.mvvmproject.ui.singleTripManage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedFragment;
import vn.baonq.mvvmproject.ui.singleTripManage.STMReceived.STMReceivedFragment;
import vn.baonq.mvvmproject.ui.singleTripManage.offered.OfferedFragment;
import vn.baonq.mvvmproject.ui.singleTripManage.STMTransit.STMTransitFragment;
import vn.baonq.mvvmproject.ui.singleTripManage.offering.OfferingFragment;

public class STMPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    private GetTripByUserResponse.TripItemResponse trip;

    public void setTrip(GetTripByUserResponse.TripItemResponse trip) {
        this.trip = trip;
    }

    public STMPagerAdapter(FragmentManager fragmentManager) {
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
                return OfferedFragment.newInstance(trip);
            case 1:
                return OfferingFragment.newInstance(trip);
            case 2:
                return STMTransitFragment.newInstance(trip.getTripId());
            case 3:
                return STMReceivedFragment.newInstance(trip.getTripId());
            default:
                return OfferedFragment.newInstance(trip);
        }
    }

}