package com.dkglabs.apply_loan.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.apply_loan.databinding.FragmentESignDocBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.ESignResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.response.SignerDetail;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ESignDocFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private static final int ESIGN_LOAN_DOCUMENT = 101;

    private Handler handler;
    private ExecutorService executors;
    private Runnable runnable;
    private InputStream inputStream;

    private LoanResponse loanResponse;
    private ESignResponse eSignResponse;

    private FragmentESignDocBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loanResponse = LoanPersistentManager.getLoanResponse();

        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                closeActivity();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentESignDocBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }


    private void initializeView() {
        binding.buttonNext.setOnClickListener(this);

        handler = new Handler(Looper.getMainLooper());
        executors = Executors.newSingleThreadExecutor();

        runnable = new Runnable() {
            @Override
            public void run() {
                showPdfFromInputStream(inputStream);
            }
        };

        binding.checkboxTermsConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.buttonNext.setEnabled(true);
                } else {
                    binding.buttonNext.setEnabled(false);
                }
            }
        });

        esignLoanDocument();
    }


    private void showPdfFromInputStream(InputStream inputStream) {

        binding.pdfViewKfs.fromStream(inputStream).scrollHandle(new DefaultScrollHandle(context)).onPageScroll(new OnPageScrollListener() {
            @Override
            public void onPageScrolled(int page, float positionOffset) {
                if (page == binding.pdfViewKfs.getPageCount() - 1 && positionOffset > 0) {
                    binding.checkboxTermsConditions.setEnabled(true);
                }
            }
        }).load();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.buttonNext.getId()) {
            if (eSignResponse != null) {
                proceedForEsignedUrl(eSignResponse);
            }
        }
    }

    private void proceedForEsignedUrl(ESignResponse eSignResponse) {
        List<SignerDetail> signerDetails = new ArrayList<>();
        if (AppUtils.isCollectionContainData(eSignResponse.getSignerdetail())) {
            signerDetails.addAll(eSignResponse.getSignerdetail());
            if (!signerDetails.isEmpty()) {
                SignerDetail signerDetail = signerDetails.get(0);
                if (signerDetail != null) {
                    try {
                        Uri uri = Uri.parse(signerDetail.getEsignUrl());
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void esignLoanDocument() {
        progressDialog = UIUtils.showProgressDialog(getActivity(), progressDialog, "Loading documents. Please wait...");
        LoanManager.esignLoanDocument(ESIGN_LOAN_DOCUMENT, loanResponse.getLoanId(), this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case ESIGN_LOAN_DOCUMENT:
                eSignResponse = (ESignResponse) response.getResponseData();
                loadESignDocumentPreview(eSignResponse);
                break;
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showToast(context, message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showGenericErrorDialog(binding.getRoot(), context);
    }

    @Override
    public void commonCall(int requestCode) {
        switch (requestCode) {
            case ESIGN_LOAN_DOCUMENT:
                UIUtils.dismissDialog(progressDialog);
                break;
        }
    }

    private void loadESignDocumentPreview(ESignResponse esignResponse) {
        if (esignResponse != null && !esignResponse.getPdf().isEmpty()) {
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        inputStream = new URL(esignResponse.getPdf()).openStream();
                        handler.post(runnable);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
