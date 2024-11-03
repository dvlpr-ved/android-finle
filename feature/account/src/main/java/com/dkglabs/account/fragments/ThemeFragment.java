package com.dkglabs.account.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentThemeBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.ThemeManager;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class ThemeFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private View view = null;
    private FragmentThemeBinding binding = null;

    public ThemeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThemeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        initializeView();
        return view;
    }

    private void initializeView() {
        setUpBackToolbar(getString(com.dkglabs.base.R.string.text_theme));
        setRadioTheme(ThemeManager.getTheme());
        binding.radioGroupTheme.setOnCheckedChangeListener(this);
    }

    private void setRadioTheme(String theme) {
        switch (theme) {
            case ThemeManager.MODE_DARK:
                binding.radioGroupTheme.check(R.id.radioDark);
                break;
            case ThemeManager.MODE_LIGHT:
                binding.radioGroupTheme.check(R.id.radioLight);
                break;
            default:
                binding.radioGroupTheme.check(R.id.radioDefault);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.radioDefault) {
            ThemeManager.changeTheme(ThemeManager.MODE_DEFAULT);
        } else if (checkedId == R.id.radioDark) {
            ThemeManager.changeTheme(ThemeManager.MODE_DARK);
        } else if (checkedId == R.id.radioLight) {
            ThemeManager.changeTheme(ThemeManager.MODE_LIGHT);
        }
    }
}
