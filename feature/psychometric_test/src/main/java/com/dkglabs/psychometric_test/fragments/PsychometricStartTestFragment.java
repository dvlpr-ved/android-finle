package com.dkglabs.psychometric_test.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.interfaces.DialogActionInterface;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.psychometric_test.R;
import com.dkglabs.psychometric_test.databinding.FragmentPsychometricStartTestBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsychometricStartTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsychometricStartTestFragment extends BaseFragment implements View.OnClickListener {


    private FragmentPsychometricStartTestBinding binding = null;

    public PsychometricStartTestFragment() {
        // Required empty public constructor
    }

    public static PsychometricStartTestFragment newInstance(Bundle args) {
        PsychometricStartTestFragment fragment = new PsychometricStartTestFragment();
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
        binding = FragmentPsychometricStartTestBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.messagePsychometricTest.setText(String.format(getResources().getString(R.string.message_psychometric_test), PersistentManager.getUserResponse().getFirstName()));
        binding.buttonStartTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonStartTest) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricStartTestFragment_to_navTest);
        }
    }
}