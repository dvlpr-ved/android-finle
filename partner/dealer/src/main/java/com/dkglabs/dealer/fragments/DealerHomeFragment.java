package com.dkglabs.dealer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.dealer.R;
import com.dkglabs.dealer.databinding.FragmentDealerHomeBinding;


public class DealerHomeFragment extends BaseFragment implements View.OnClickListener, MenuProvider {

    private FragmentDealerHomeBinding binding = null;

    public DealerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDealerHomeBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpToolbar(String.format("Hi %1$s!", PersistentManager.getUserResponse().getFirstName()));
        binding.cardApplication.setOnClickListener(this);
        binding.cardCollection.setOnClickListener(this);
        binding.cardLoanDetails.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(this, getViewLifecycleOwner());
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_dealer_home, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_options) {
            openHomeMenuFragment();
            return true;
        }
        return false;
    }

    private void openHomeMenuFragment() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dealerHomeFragment_to_homeMenuDialogFragment);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.cardApplication){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dealerHomeFragment_to_loanApplicationActivity);
        }else if(id == R.id.cardCollection){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dealerHomeFragment_to_collectionActivity);
        }else if(id == R.id.cardLoanDetails){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dealerHomeFragment_to_loanDetailsActivity);
        }
    }
}