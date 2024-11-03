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

import com.dkglabs.apply_loan.databinding.FragmentLoanKfsBinding;
import com.dkglabs.base.fragments.BaseFragment;
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

public class LoanKfsFragment extends BaseFragment implements View.OnClickListener {

    private LoanProcessingDocument mLoanProcessingDocument;
    private GenerateContractResponse kfsGenerateContractResponse;

    private Handler handler;
    private ExecutorService executors;
    private Runnable runnable;
    private InputStream inputStream;

    private FragmentLoanKfsBinding binding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoanProcessingDocument = LoanKfsFragmentArgs.fromBundle(getArguments()).getLoanProcessingDocument();
        kfsGenerateContractResponse = getKfsGenerateContractResponse(mLoanProcessingDocument.getGenerateContractResponses());
    }

    private GenerateContractResponse getKfsGenerateContractResponse(List<GenerateContractResponse> generateContractResponses) {
        if (AppUtils.isCollectionContainData(generateContractResponses)) {
            for (GenerateContractResponse generateContractResponse : generateContractResponses) {
                if (generateContractResponse.getTemplateName().equalsIgnoreCase("20240803_Key_Facts_Statement")) {
                    return generateContractResponse;
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoanKfsBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonNext.setOnClickListener(this);

        handler = new Handler(Looper.getMainLooper());
        executors = Executors.newSingleThreadExecutor();
        executors.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    inputStream = new URL(kfsGenerateContractResponse.getUrl()).openStream();
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
                    binding.buttonNext.setEnabled(true);
                } else {
                    binding.buttonNext.setEnabled(false);
                }
            }
        });
    }

    private void showPdfFromInputStream(InputStream inputStream) {

        binding.pdfViewKfs.fromStream(inputStream).scrollHandle(new DefaultScrollHandle(context))
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                        if (page == binding.pdfViewKfs.getPageCount() - 1 && positionOffset > 0) {
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
        if (id == binding.buttonNext.getId()) {
            Navigation.findNavController(binding.getRoot()).navigate(LoanKfsFragmentDirections.actionLoanKfsFragmentToLoanSanctionLatterFragment(mLoanProcessingDocument));
        }

    }
}
