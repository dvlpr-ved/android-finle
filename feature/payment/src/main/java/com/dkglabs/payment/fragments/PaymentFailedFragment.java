package com.dkglabs.payment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.payment.R;
import com.dkglabs.payment.databinding.FragmentPaymentFailedBinding;
import com.dkglabs.payment.databinding.FragmentProcessingPaymentBinding;
import com.dkglabs.payment.utils.PaymentConst;
import com.dkglabs.payment.view_model.PaymentViewModel;

public class PaymentFailedFragment extends BaseFragment implements View.OnClickListener {

    private FragmentPaymentFailedBinding binding;
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
        binding = FragmentPaymentFailedBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonCancel.setOnClickListener(this);
        binding.buttonTryAgain.setOnClickListener(this);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonTryAgain) {
            PaymentViewModel paymentViewModel = new PaymentViewModel();
            paymentViewModel.setPaymentAction(PaymentConst.PAYMENT_RETRY);
            viewModel.setSelectedItem(paymentViewModel);
        } else if (id == R.id.buttonCancel) {
            PaymentViewModel paymentViewModel = new PaymentViewModel();
            paymentViewModel.setPaymentAction(PaymentConst.PAYMENT_CANCEL);
            viewModel.setSelectedItem(paymentViewModel);
        }
    }
}