package com.dkglabs.account.fragments;


import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentTermsBinding;
import com.dkglabs.base.manager.LanguageManager;
import com.dkglabs.model.viewmodel.AppViewModel;


/**
 * Created by Himanshu Srivastava on 3/22/2023.
 */
public class TermsFragment extends BaseFragment implements View.OnClickListener {

    private View view = null;
    private FragmentTermsBinding binding = null;

    public TermsFragment() {
    }

    public static TermsFragment newInstance(@Nullable Bundle args) {
        TermsFragment fragment = new TermsFragment();
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
        binding = FragmentTermsBinding.inflate(inflater, container, false);

        view = binding.getRoot();
        initializeView();

        return view;
    }


    private void initializeView() {

        binding.buttonAgree.setOnClickListener(this);

        SpannableString spanString = new SpannableString(getString(R.string.terms_condition_message));

        ClickableSpan termsAndCondition = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                AppViewModel appViewModel = new AppViewModel();
                appViewModel.setTerms(true);
                viewModel.setSelectedItem(appViewModel);
            }
        };
        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                AppViewModel appViewModel = new AppViewModel();
                appViewModel.setPrivacy(true);
                viewModel.setSelectedItem(appViewModel);
            }
        };

        if (LanguageManager.getUserLanguage().equals("en")) {
            setTextEnglish(spanString, termsAndCondition, privacy);
        } else if (LanguageManager.getUserLanguage().equals("hi")) {
            setTextHindi(spanString, termsAndCondition, privacy);
        }
        binding.textViewTerms.setMovementMethod(LinkMovementMethod.getInstance());
        binding.textViewTerms.setText(spanString, TextView.BufferType.SPANNABLE);
        binding.textViewTerms.setSelected(true);
    }

    private void setTextHindi(SpannableString spanString, ClickableSpan termsAndCondition, ClickableSpan privacy) {
        // जारी रखकर आप हमारे नियमों और शर्तों के साथ-साथ हमारी गोपनीयता नीति से सहमत हैं
        // Character starting from 19 - 35 is Terms and condition.
        // Character starting from 53 - 66 is privacy policy.
        spanString.setSpan(termsAndCondition, 19, 35, 0);
        spanString.setSpan(privacy, 53, 66, 0);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(com.dkglabs.base.R.color.md_theme_dark_outlineVariant)), 19, 35, 0);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(com.dkglabs.base.R.color.md_theme_dark_outlineVariant)), 53, 66, 0);
        spanString.setSpan(new UnderlineSpan(), 19, 35, 0);
        spanString.setSpan(new UnderlineSpan(), 53, 66, 0);
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 19, 35, 0);
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 53, 66, 0);
    }

    private void setTextEnglish(SpannableString spanString, ClickableSpan termsAndCondition, ClickableSpan privacy) {
        // Character starting from 31 - 51 is Terms and condition.
        // Character starting from 67 - 81 is privacy policy.
        spanString.setSpan(termsAndCondition, 31, 51, 0);
        spanString.setSpan(privacy, 67, 81, 0);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(com.dkglabs.base.R.color.md_theme_dark_outlineVariant)), 31, 51, 0);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(com.dkglabs.base.R.color.md_theme_dark_outlineVariant)), 67, 81, 0);
        spanString.setSpan(new UnderlineSpan(), 31, 51, 0);
        spanString.setSpan(new UnderlineSpan(), 67, 81, 0);
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 31, 51, 0);
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 67, 81, 0);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAgree) {
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setTermsAgree(true);
            viewModel.setSelectedItem(appViewModel);
        }
    }
}