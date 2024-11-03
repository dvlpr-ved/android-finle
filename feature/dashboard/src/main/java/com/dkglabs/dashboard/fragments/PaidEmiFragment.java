package com.dkglabs.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.dashboard.adapters.PaidEmiAdapter;
import com.dkglabs.dashboard.databinding.FragmentPaidEmiBinding;
import com.dkglabs.model.response.LoanEmiDetailResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 24 August, 2023.
 * Project e_savari
 */
public class PaidEmiFragment extends BaseFragment {
    private List<LoanEmiDetailResponse> loanEmiList;
    private PaidEmiAdapter paidEmiAdapter;
    private FragmentPaidEmiBinding binding = null;

    public PaidEmiFragment() {
    }

    public static PaidEmiFragment newInstance(Bundle args) {
        PaidEmiFragment fragment = new PaidEmiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaidEmiBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        loanEmiList = new ArrayList<>();
        paidEmiAdapter = new PaidEmiAdapter(loanEmiList, context);
        binding.recyclerView.setAdapter(paidEmiAdapter);
    }

    public void setLoanEmiList(List<LoanEmiDetailResponse> emiList) {
        if (loanEmiList.size() > 0)
            loanEmiList.clear();
        loanEmiList.addAll(emiList);
        paidEmiAdapter.notifyDataSetChanged();
    }


}
