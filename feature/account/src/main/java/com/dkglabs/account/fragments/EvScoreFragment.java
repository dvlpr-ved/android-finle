package com.dkglabs.account.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.navigation.Navigation;

import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentEvScoreBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.interfaces.DialogActionInterface;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.EvScoreResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.EvScoreManager;
import com.google.android.material.snackbar.Snackbar;


public class EvScoreFragment extends BaseFragment implements ResponseListener, MenuProvider {

    private Snackbar snackbar;
    private FragmentEvScoreBinding binding = null;
    private Boolean isLoading = false;

    public EvScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (snackbar != null)
                    UIUtils.dismissSnackbar(snackbar);
                else
                    Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEvScoreBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(this, getViewLifecycleOwner());
        binding.evScore.setText("");
        binding.circleProgressBar.setProgress(0);
        isLoading = true;
        if (PersistentManager.getUserResponse().getLoanId().equals("")) {
            showEVNotCalculateDialog();
        } else {
            EvScoreManager.getEvScore(1001, PersistentManager.getUserResponse().getUserId(), PersistentManager.getUserResponse().getLoanId(),  this);
        }
    }

    private void initializeView() {
        setUpBackToolbar(getString(R.string.ev_score));
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        EvScoreResponse evScoreResponse = (EvScoreResponse) response.getResponseData();
        binding.evScore.setText(evScoreResponse.getEvScore());
        binding.circleProgressBar.setProgress(Float.parseFloat(evScoreResponse.getEvScore()));
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        snackbar = UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message, "Calculate Now", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEVScore();
            }
        });
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        isLoading = !isLoading;
        UIUtils.hideViewGone(binding.evScoreProgress);
    }

    private void calculateEVScore() {
        if (!isLoading) {
            isLoading = !isLoading;
            UIUtils.showView(binding.evScoreProgress);
            binding.evScore.setText("");
            binding.circleProgressBar.setProgress(0);
            EvScoreManager.calculateEvScore(1001, PersistentManager.getUserResponse().getUserId(), PersistentManager.getUserResponse().getLoanId(),  this);
        }
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_ev_score, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_refresh) {
            calculateEVScore();
            return true;
        }
        return false;
    }

    private void showEVNotCalculateDialog() {
        UIUtils.showAlertDialog(context, getString(R.string.ev_score), getString(R.string.ev_score_dialog_message), getString(com.dkglabs.base.R.string.text_okay), new DialogActionInterface() {
            @Override
            public void onDialogActionListener(Dialog dialog, int tag) {
                if (tag == DialogActionInterface.POSITIVE_TAG) {
                    UIUtils.dismissDialog(dialog);
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                }
            }
        });
    }
}