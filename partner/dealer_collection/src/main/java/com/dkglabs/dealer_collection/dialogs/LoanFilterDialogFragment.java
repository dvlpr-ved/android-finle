package com.dkglabs.dealer_collection.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.databinding.FragmentLoanFilterDialogBinding;
import com.dkglabs.dealer_collection.model.LoanFilterModel;
import com.google.android.material.chip.Chip;

import java.util.Arrays;
import java.util.List;

public class LoanFilterDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private NavController navController;
    private LoanFilterModel filter;
    private FragmentLoanFilterDialogBinding binding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = LoanFilterDialogFragmentArgs.fromBundle(getArguments()).getFilter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanFilterDialogBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {

        List<String> statusList = Arrays.asList("Open", "Close");
        List<String> subStatusList = Arrays.asList("Active", "Inactive");

        for (String status : statusList) {
            Chip chip = (Chip) LayoutInflater.from(context).inflate(R.layout.layout_filter_chip, null);
            chip.setText(status);
            chip.setChecked(status.equals(filter.getStatus()));
            binding.chipGroupStatus.addView(chip);
        }

        for (String subStatus : subStatusList) {
            Chip chip = (Chip) LayoutInflater.from(context).inflate(R.layout.layout_filter_chip, null);
            chip.setText(subStatus);
            chip.setChecked(subStatus.equals(filter.getSubStatus()));
            binding.chipGroupSubStatus.addView(chip);
        }


        binding.chipGroupStatus.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip chip = group.findViewById(checkedIds.get(0));
                filter.setStatus(chip.getText().toString());
            } else {
                filter.setStatus("None");
            }
        });

        binding.chipGroupSubStatus.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip chip = group.findViewById(checkedIds.get(0));
                filter.setSubStatus(chip.getText().toString());
            } else {
                filter.setSubStatus("None");
            }
        });

        binding.buttonFilter.setOnClickListener(this);
        binding.buttonClear.setOnClickListener(this);

        navController = Navigation.findNavController(getParentFragment().getView());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonClear) {
            clearAllFilter();
        } else if (id == R.id.buttonFilter) {
            applyFilter();
        }
    }

    private void applyFilter() {
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("filter", filter);
        dismiss();
    }

    private void clearAllFilter() {
        binding.chipGroupStatus.clearCheck();
        binding.chipGroupSubStatus.clearCheck();
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("filter", filter);
        filter.setStatus("None");
        filter.setSubStatus("None");
        dismiss();
    }
}
