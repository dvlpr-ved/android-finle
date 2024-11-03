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
import com.dkglabs.apply_loan.databinding.FragmentLoanEvScoreBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.EvScoreResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.EvScoreManager;
import com.google.android.material.snackbar.Snackbar;

public class LoanEvScoreFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private FragmentLoanEvScoreBinding binding = null;
    private Snackbar snackbar = null;

    public LoanEvScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (snackbar != null) {
                    UIUtils.dismissSnackbar(snackbar);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "title");
                    bundle.putString("message", "message");
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanEvScoreBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(7);
        viewModel.setSelectedItem(applyLoanModel);
        calculateEVScore();
    }

    private void initializeView() {
        binding.buttonDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonDone) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanEvScoreFragment_to_loanPreviewFragment);
        }
    }

    private void calculateEVScore() {
        UIUtils.showView(binding.evScoreProgress);
        binding.evScore.setText("");
        binding.circleProgressBar.setProgress(0);
        EvScoreManager.calculateEvScore(1001, PersistentManager.getUserResponse().getUserId(), LoanPersistentManager.getLoanId(),  this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        EvScoreResponse evScoreResponse = (EvScoreResponse) response.getResponseData();
        binding.evScore.setText(evScoreResponse.getEvScore());
        binding.circleProgressBar.setProgress(Float.parseFloat(evScoreResponse.getEvScore()));
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        snackbar = UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message, "Calculate Now", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEVScore();
            }
        });
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.hideViewGone(binding.evScoreProgress);
    }
}