package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentPsychometricStatusBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PsyResultResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.PsychometricManager;

public class PsychometricStatusFragment extends BaseFragment implements View.OnClickListener, ResponseListener {


    private FragmentPsychometricStatusBinding binding = null;

    public PsychometricStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(7);
        viewModel.setSelectedItem(applyLoanModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPsychometricStatusBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.messagePsychometricTest.setText(String.format(getResources().getString(com.dkglabs.psychometric_test.R.string.message_psychometric_test), PersistentManager.getUserResponse().getFirstName()));
        binding.buttonStartTest.setOnClickListener(this);
        binding.buttonSkip.setOnClickListener(this);
        binding.buttonTryAgain.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        PsychometricManager.getPsyTestResult(1001, PersistentManager.getUserResponse().getUserId(),  this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonStartTest) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricStatusFragment_to_navTest);
        } else if (id == R.id.buttonTryAgain) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricStatusFragment_to_navTest);
        } else if (id == R.id.buttonSkip) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricStatusFragment_to_userEvScoreFragment);
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        PsyResultResponse psyResultResponse = (PsyResultResponse) response.getResponseData();
        updateUi(psyResultResponse);
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {

    }

    private void updateUi(PsyResultResponse psyResultResponse) {
        if (psyResultResponse != null) {
            if (psyResultResponse.getTestStatusFlag().equals("N")) {
                UIUtils.showView(binding.buttonStartTest);
                UIUtils.hideViewGone(binding.buttonSkip);
                UIUtils.hideViewGone(binding.buttonTryAgain);
            } else {
                UIUtils.hideViewGone(binding.buttonStartTest);
                UIUtils.showView(binding.buttonSkip);
                UIUtils.showView(binding.buttonTryAgain);
            }

            binding.tvScore.setText(psyResultResponse.getTotalCorrect() * 10 > 0 ? String.valueOf(psyResultResponse.getTotalCorrect() * 10) : "00");
            binding.circleProgressBar.setProgress(psyResultResponse.getTotalCorrect() * 10);
        } else {
            UIUtils.showView(binding.buttonStartTest);
            UIUtils.hideViewGone(binding.buttonSkip);
            UIUtils.hideViewGone(binding.buttonTryAgain);

        }
    }
}

