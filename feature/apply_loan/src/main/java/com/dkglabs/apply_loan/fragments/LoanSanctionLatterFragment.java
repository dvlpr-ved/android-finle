package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.databinding.FragmentLoanSanctionLatterBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.model.response.GenerateContractResponse;
import com.dkglabs.model.response.LoanProcessingDocument;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoanSanctionLatterFragment extends BaseFragment implements View.OnClickListener {

    private LoanProcessingDocument mLoanProcessingDocument;
    private GenerateContractResponse slGenerateContractResponse;

    private Handler handler;
    private ExecutorService executors;
    private Runnable runnable;
    private InputStream inputStream;
    private FragmentLoanSanctionLatterBinding binding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoanProcessingDocument = LoanKfsFragmentArgs.fromBundle(getArguments()).getLoanProcessingDocument();
        slGenerateContractResponse = getKfsGenerateContractResponse(mLoanProcessingDocument.getGenerateContractResponses());
    }

    private GenerateContractResponse getKfsGenerateContractResponse(List<GenerateContractResponse> generateContractResponses) {
        if (AppUtils.isCollectionContainData(generateContractResponses)) {
            for (GenerateContractResponse generateContractResponse : generateContractResponses) {
                if (generateContractResponse.getTemplateName().equalsIgnoreCase("20240803_sanction_letter")) {
                    return generateContractResponse;
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoanSanctionLatterBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonVideoKyc.setOnClickListener(this);

        handler = new Handler(Looper.getMainLooper());
        executors = Executors.newSingleThreadExecutor();
        executors.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    inputStream = new URL(slGenerateContractResponse.getUrl()).openStream();
                    handler.post(runnable);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                    binding.buttonVideoKyc.setEnabled(true);
                } else {
                    binding.buttonVideoKyc.setEnabled(false);
                }
            }
        });
    }

    private void showPdfFromInputStream(InputStream inputStream) {

        binding.pdfViewSanctionLatter.fromStream(inputStream).scrollHandle(new DefaultScrollHandle(context))
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                        if (page == binding.pdfViewSanctionLatter.getPageCount() - 1 && positionOffset > 0) {
                            binding.checkboxTermsConditions.setEnabled(true);
                        }
                    }
                }).load();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.buttonVideoKyc.getId()) {
            Navigation.findNavController(binding.getRoot()).navigate(LoanSanctionLatterFragmentDirections.actionLoanSanctionLatterFragmentToEmiCollectionFragment(LoanPersistentManager.getLoanResponse()));
        }

    }
}
