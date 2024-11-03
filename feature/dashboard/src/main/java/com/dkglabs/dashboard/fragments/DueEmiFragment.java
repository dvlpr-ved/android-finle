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
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.dashboard.adapters.DueEmiAdapter;
import com.dkglabs.dashboard.databinding.FragmentDueEmiBinding;
import com.dkglabs.model.response.LoanEmiDetailResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 24 August,2023
 * Project e_savari
 */
public class DueEmiFragment extends BaseFragment {
    private List<LoanEmiDetailResponse> loanEmiList;
    private DueEmiAdapter dueEmiAdapter;
    private FragmentDueEmiBinding binding = null;

    public DueEmiFragment() {
    }

    public static DueEmiFragment newInstance(Bundle args) {
        DueEmiFragment fragment = new DueEmiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDueEmiBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        loanEmiList = new ArrayList<>();
        dueEmiAdapter = new DueEmiAdapter(loanEmiList, context);
        binding.recyclerView.setAdapter(dueEmiAdapter);
    }

    public void setLoanEmiList(List<LoanEmiDetailResponse> emiList) {
        if (emiList.size() == 0) {
            UIUtils.showView(binding.llNotPaid);
            return;
        }

        UIUtils.hideViewGone(binding.llNotPaid);
        if (loanEmiList.size() > 0)
            loanEmiList.clear();
        loanEmiList.addAll(emiList);
        dueEmiAdapter.notifyDataSetChanged();
    }
}
