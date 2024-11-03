package com.dkglabs.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.dashboard.R;
import com.dkglabs.dashboard.databinding.FragmentBlankDashboardBinding;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class BlankDashboardFragment extends BaseFragment implements View.OnClickListener {
    private FragmentBlankDashboardBinding binding = null;

    public BlankDashboardFragment() {
    }

    public static BlankDashboardFragment newInstance() {
        BlankDashboardFragment fragment = new BlankDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlankDashboardBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar(getString(com.dkglabs.base.R.string.dashboard));
        binding.buttonApplyNow.setOnClickListener(this);
        binding.cardFaq.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.dashboardTitle.setText(String.format(getString(R.string.welcome_dashboard_message), PersistentManager.getUserResponse().getFirstName()));
        binding.dashboardMessage.setText(getString(R.string.blank_dashboard_message));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonApplyNow) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_blankDashboardFragment_to_applyLoanActivity);
        } else if (id == R.id.cardFaq) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_blankDashboardFragment_to_faqFragment);
        }
    }
}
