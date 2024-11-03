package com.dkglabs.notification.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.notification.databinding.FragmentNotificationBinding;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class NotificationFragment extends BaseFragment {
    private FragmentNotificationBinding binding = null;

    public NotificationFragment() {
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar(getString(com.dkglabs.base.R.string.notification));
    }


}
