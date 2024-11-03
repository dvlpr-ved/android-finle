package com.dkglabs.base.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Himanshu Srivastava on 9/15/2022.
 */
public class ItemViewModel<T> extends ViewModel {

    private final MutableLiveData<T> selectedItem = new MutableLiveData<T>();

    public LiveData<T> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T item) {
        selectedItem.setValue(item);
    }
}
