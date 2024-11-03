package com.dkglabs.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.dkglabs.base.model.ItemViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Created by Himanshu Srivastava on 10/10/2022.
 */
public class BaseDialogFragment extends BottomSheetDialogFragment {

    protected Context context;
    protected ItemViewModel viewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing() && !isRemoving();
    }


    @Override
    public void dismiss() {
        if (isShowing())
            super.dismiss();
    }


}
