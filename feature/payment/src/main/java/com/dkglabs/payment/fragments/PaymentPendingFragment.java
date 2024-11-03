package com.dkglabs.payment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.payment.databinding.FragmentProcessingPaymentBinding;

public class PaymentPendingFragment extends BaseFragment {

    private FragmentProcessingPaymentBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProcessingPaymentBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}