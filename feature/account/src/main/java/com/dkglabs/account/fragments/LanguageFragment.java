package com.dkglabs.account.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LanguageManager;
import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentLanguageBinding;
import com.dkglabs.model.viewmodel.AppViewModel;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class LanguageFragment extends BaseFragment implements View.OnClickListener {
    private AppViewModel appViewModel;
    private View view = null;
    private FragmentLanguageBinding binding = null;

    public LanguageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLanguageBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        initializeView();
        return view;
    }

    private void initializeView() {
        setUpBackToolbar(getString(com.dkglabs.base.R.string.text_language));
        appViewModel = new AppViewModel();
        binding.buttonDone.setOnClickListener(this);
        setRadioLanguage(LanguageManager.getUserLanguage());
    }

    private void setRadioLanguage(String lang) {
        switch (lang) {
            case LanguageManager.HINDI:
                binding.radioGroupLanguage.check(R.id.radioHindi);
                break;
            case LanguageManager.ENGLISH:
                binding.radioGroupLanguage.check(R.id.radioEnglish);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonDone) {

            int id = binding.radioGroupLanguage.getCheckedRadioButtonId();
            RadioButton languageButton = binding.getRoot().findViewById(id);
            String language = languageButton.getTag().toString();

            if (!LanguageManager.getUserLanguage().equals(language)) {
                LanguageManager.setUserLanguage(language);
                appViewModel.setLanguageChanged(true);
            }

            LanguageManager.setUserLanguageChanged(true);
            appViewModel.setLanguagePressed(true);
            viewModel.setSelectedItem(appViewModel);
        }
    }
}
