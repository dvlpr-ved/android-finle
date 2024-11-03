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
import com.dkglabs.payment.databinding.FragmentPaymentSuccessBinding;
import com.dkglabs.payment.utils.PaymentConst;
import com.dkglabs.payment.view_model.PaymentViewModel;

public class PaymentSuccessFragment extends BaseFragment implements View.OnClickListener {

    private FragmentPaymentSuccessBinding binding;

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
        binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();

    }

    private void initializeView() {
        binding.buttonDone.setOnClickListener(this);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null)
            binding = null;
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.buttonDone){
           PaymentViewModel paymentViewModel = new PaymentViewModel();
           paymentViewModel.setPaymentAction(PaymentConst.PAYMENT_DONE);
           viewModel.setSelectedItem(paymentViewModel);
       }
    }
}