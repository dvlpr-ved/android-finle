package com.dkglabs.e_savari.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.e_savari.R;
import com.dkglabs.e_savari.adapters.AppGuideAdapter;
import com.dkglabs.e_savari.databinding.ActivityAppGuideBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;

public class AppGuideActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Integer> appGuideImages;
    private ArrayList<String> appGuideTitle;
    private ArrayList<String> appGuideMessage;
    private AppGuideAdapter adapter = null;
    private ActivityAppGuideBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fullScreenActivity();
        initializeView();
    }

    private void initializeView() {
        binding.textViewOnBoardingTitle.setInAnimation(getContext(), android.R.anim.fade_in);
        binding.textViewOnBoardingTitle.setOutAnimation(getContext(), android.R.anim.fade_out);
        binding.textViewOnBoardingMessage.setInAnimation(getContext(), android.R.anim.fade_in);
        binding.textViewOnBoardingMessage.setOutAnimation(getContext(), android.R.anim.fade_out);

        appGuideTitle = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.app_guide_title)));
        appGuideMessage = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.app_guide_message)));

        binding.buttonNext.setOnClickListener(this);

        appGuideImages = new ArrayList<>();

        appGuideImages.add(R.drawable.app_guide_image_1);
        appGuideImages.add(R.drawable.app_guide_image_2);
        appGuideImages.add(R.drawable.app_guide_image_3);
        appGuideImages.add(R.drawable.app_guide_image_4);
        appGuideImages.add(R.drawable.app_guide_image_5);

        adapter = new AppGuideAdapter(appGuideImages);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setClipChildren(false);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateLayoutForPosition(position);
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                updateLayoutForPosition(position);
            }
        }).attach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                if (binding.tabLayout.getSelectedTabPosition() != appGuideImages.size() - 1) {
                    binding.tabLayout.selectTab(
                            binding.tabLayout.getTabAt(binding.tabLayout.getSelectedTabPosition() + 1)
                    );
                } else {
                    PersistentManager.setNewUser(false);
                    startActivity(UserAuthActivity.class);
                    closeAllActivity();
                }
                break;
        }
    }

    private void updateLayoutForPosition(int position) {
        if (position == appGuideImages.size() - 1) {
            binding.buttonNext.setText(getString(com.dkglabs.base.R.string.text_finish));
        } else {
            binding.buttonNext.setText(getString(com.dkglabs.base.R.string.text_next));
        }

        binding.textViewOnBoardingTitle.setText(appGuideTitle.get(position));
        binding.textViewOnBoardingMessage.setText(appGuideMessage.get(position));
    }

}