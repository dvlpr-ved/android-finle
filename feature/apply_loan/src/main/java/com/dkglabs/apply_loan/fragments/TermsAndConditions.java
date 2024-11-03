package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.L;
import com.dkglabs.apply_loan.R;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LanguageManager;
import com.github.barteksc.pdfviewer.PDFView;

public class TermsAndConditions extends BaseFragment {
    View view=null;
    public TermsAndConditions() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
        initializeView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeView() {
        PDFView pdfView =view.findViewById(R.id.txtTermsAndConditionsDoc);
        if(LanguageManager.getUserLanguage().equals(LanguageManager.ENGLISH)) {
            pdfView.fromAsset("TermsAndConditionEnglish.pdf").load();
        }else if(LanguageManager.getUserLanguage().equals(LanguageManager.HINDI)){
            pdfView.fromAsset("TermsAndConditionHindi.pdf").load();
        }

    }
}