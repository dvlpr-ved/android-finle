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
import com.dkglabs.dealer_collection.databinding.FragmentCollectionFilterDialogBinding;
import com.dkglabs.dealer_collection.model.CollectionFilterModel;
import com.google.android.material.chip.Chip;

import java.util.Arrays;
import java.util.List;

public class CollectionFilterDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private NavController navController;
    private CollectionFilterModel filter;
    private FragmentCollectionFilterDialogBinding binding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = CollectionFilterDialogFragmentArgs.fromBundle(getArguments()).getFilter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCollectionFilterDialogBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {

        List<String> statusList = Arrays.asList("Approved", "Pending", "Disbursed");

        for (String status : statusList) {
            Chip chip = (Chip) LayoutInflater.from(context).inflate(R.layout.layout_filter_chip, null);
            chip.setText(status);
            chip.setChecked(status.equals(filter.getStatus()));
            binding.chipGroupStatus.addView(chip);
        }

        binding.chipGroupStatus.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip chip = group.findViewById(checkedIds.get(0));
                filter.setStatus(chip.getText().toString());
            } else {
                filter.setStatus("None");
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
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("filter", filter);
        filter.setStatus("None");
        dismiss();
    }
}
