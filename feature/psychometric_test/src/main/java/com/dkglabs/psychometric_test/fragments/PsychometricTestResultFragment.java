package com.dkglabs.psychometric_test.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.model.response.PsyResultResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.psychometric_test.R;
import com.dkglabs.psychometric_test.databinding.FragmentPsychometricStartTestBinding;
import com.dkglabs.psychometric_test.databinding.FragmentPsychometricTestResultBinding;

public class PsychometricTestResultFragment extends BaseFragment implements View.OnClickListener {

    private PsyResultResponse response;
    ;
    private FragmentPsychometricTestResultBinding binding = null;

    public PsychometricTestResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setEnabled(false);
                //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricTestResultFragment_pop_including_psychometricStartTestFragment);
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPsychometricTestResultBinding.inflate(getLayoutInflater(), container, false);
        //requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        //moving to new step.
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setPsyTestDone(1);
        viewModel.setSelectedItem(applyLoanModel);

        response = (PsyResultResponse) bundle.getSerializable("result");
//        binding.tvScore.setText(response.getTotalCorrect() * 10 > 0 ? String.valueOf(response.getTotalCorrect() * 10) : "00");
//        binding.circleProgressBar.setProgress(response.getTotalCorrect() * 10);
//        binding.tvTotal.setText(String.valueOf(response.getTotalQuestion()));
//        binding.tvCorrect.setText(String.valueOf(response.getTotalCorrect()));
//        binding.tvWrong.setText(String.valueOf(response.getTotalInCorrect()));
//        binding.resultMessage.setText(String.format(getResources().getString(R.string.psychometric_test_result_message), PersistentManager.getUserResponse().getFirstName()));

//        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
//        applyLoanModel.setPsyTestDone(1);
//        viewModel.setSelectedItem(applyLoanModel);
    }

    private void initializeView() {
        binding.buttonDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonDone) {
            ApplyLoanModel applyLoanModel = new ApplyLoanModel();
            applyLoanModel.setPsyTestDone(1);
            viewModel.setSelectedItem(applyLoanModel);
        }
    }
}