package com.dkglabs.products.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.model.response.ProductImage;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.products.adapters.ProductSliderAdapter;
import com.dkglabs.products.databinding.FragmentProductBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class ProductFragment extends BaseFragment implements View.OnClickListener {
    private final int SLIDER_DELAY = 10000;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<ProductImage> productImageList;
    private ProductSliderAdapter adapter = null;
    private Runnable runnable = null;
    private FragmentProductBinding binding = null;

    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), o -> {
            AppViewModel model = (AppViewModel) o;

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpToolbar("");

        productImageList = new ArrayList<>();

        adapter = new ProductSliderAdapter(productImageList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setClipChildren(false);
        binding.viewPager.setOffscreenPageLimit(1);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                updateSlide(position);
            }
        }).attach();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (binding.tabLayout.getTabCount() != binding.tabLayout.getSelectedTabPosition() + 1)
                    updateSlide(binding.tabLayout.getSelectedTabPosition() + 1);
                else updateSlide(0);

                handler.postDelayed(this, SLIDER_DELAY);
            }
        };

        handler.post(runnable);

    }

    private void updateSlide(int position) {
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
    }


    @Override
    public void onClick(View view) {
        final int id = view.getId();

    }
}
