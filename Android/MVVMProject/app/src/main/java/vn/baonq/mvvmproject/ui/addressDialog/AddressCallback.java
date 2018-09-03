package vn.baonq.mvvmproject.ui.addressDialog;

import vn.baonq.mvvmproject.data.model.db.City;

public interface AddressCallback {
    void doSubmit(String address, City city, String feild);
}
