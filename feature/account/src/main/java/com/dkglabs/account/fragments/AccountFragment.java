package com.dkglabs.account.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentAccountBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.remote.manager.UserManager;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class AccountFragment extends BaseFragment implements View.OnClickListener {
    private FragmentAccountBinding binding = null;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewName.setText(String.format(getString(R.string.user_name_placeholder), PersistentManager.getUserResponse().getFirstName(), PersistentManager.getUserResponse().getLastName()));
        binding.textViewPhone.setText(String.format(getString(R.string.user_account_phone_placeholder), PersistentManager.getUserResponse().getMobileNumber()));

        GlideUrl imageUrl = UserManager.downloadImage(PersistentManager.getUserResponse().getUserId(), PersistentManager.getAuthToken());
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.signatureOf(new ObjectKey(PersistentManager.getImageSignature())))
                .placeholder(com.dkglabs.base.R.drawable.placeholder_user)
                .into(binding.accountImage);

        AppViewModel appViewModel = new AppViewModel();
        viewModel.setSelectedItem(appViewModel);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), o -> {
            AppViewModel model = (AppViewModel) o;

        });
    }

    private void initializeView() {
        setUpBackToolbar(getString(com.dkglabs.base.R.string.account));
        binding.accountImage.setOnClickListener(this);
        binding.buttonEditAccount.setOnClickListener(this);
        binding.cardMyAccount.setOnClickListener(this);
        binding.cardNearestDealer.setOnClickListener(this);
        binding.cardNearestPartner.setOnClickListener(this);
        binding.cardFaq.setOnClickListener(this);
        binding.cardLanguage.setOnClickListener(this);
        binding.cardEvScore.setOnClickListener(this);
        binding.cardTheme.setOnClickListener(this);
        binding.cardTerms.setOnClickListener(this);
        binding.cardPrivacy.setOnClickListener(this);
        binding.cardLogout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.accountImage) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_accountImageFragment);
        } else if (id == R.id.buttonEditAccount) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_editAccountFragment);
        } else if (id == R.id.cardMyAccount) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_myAccountFragment);
        } else if (id == R.id.cardNearestDealer) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_nearestDealerFragment);
        } else if (id == R.id.cardNearestPartner) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_nearestPartnerFragment);
        } else if (id == R.id.cardFaq) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_faqFragment);
        } else if (id == R.id.cardLanguage) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_languageFragment);
        } else if (id == R.id.cardEvScore) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_evScoreFragment);
        } else if (id == R.id.cardTheme) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_themeFragment);
        } else if (id == R.id.cardTerms) {
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setTerms(true);
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.cardPrivacy) {
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setPrivacy(true);
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.cardLogout) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_accountFragment_to_logoutDialogFragment);
        }
    }
}
