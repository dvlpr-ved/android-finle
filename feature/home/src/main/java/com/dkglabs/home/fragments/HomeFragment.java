package com.dkglabs.home.fragments;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.home.R;
import com.dkglabs.home.adapters.HomeSliderAdapter;
import com.dkglabs.home.databinding.FragmentHomeBinding;
import com.dkglabs.home.model.OfferCard;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.products.fragments.ProductHomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private final int SLIDER_DELAY = 10000;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<OfferCard> offerCardList;
    private HomeSliderAdapter adapter = null;
    private Runnable runnable = null;
    private FragmentHomeBinding binding = null;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductHomeFragment productHomeFragment = ProductHomeFragment.newInstance();
        FragmentTransaction transactionMandatory = getChildFragmentManager().beginTransaction();
        transactionMandatory.replace(R.id.product_fragment_container, productHomeFragment).commit();
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), o -> {
            AppViewModel model = (AppViewModel) o;
            if (model.isShowAllProducts()) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_productActivity);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpToolbar(String.format(getString(R.string.home_toolbar_name), PersistentManager.getUserResponse().getFirstName()));

        offerCardList = new ArrayList<>();
        addOffers();

        adapter = new HomeSliderAdapter(offerCardList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setClipChildren(false);
        binding.viewPager.setOffscreenPageLimit(1);
        setTransFormer();
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

        binding.cardApplyLoan.setOnClickListener(this);
        binding.allLoanDocs.setOnClickListener(this);
        binding.cardLoanCalculator.setOnClickListener(this);
        binding.cardLoanEligibility.setOnClickListener(this);
        binding.cardNearestDealer.setOnClickListener(this);
        binding.cardNearestPartner.setOnClickListener(this);
    }

    private void addOffers() {
        List<String> offerDescription = Arrays.asList(getResources().getStringArray(R.array.offer_description));
        List<String> offerTitle = Arrays.asList(getResources().getStringArray(R.array.offer_title));
        TypedArray offerImages = getResources().obtainTypedArray(R.array.offer_images);
        for (int i = 0; i < offerDescription.size(); i++) {
            offerCardList.add(new OfferCard(offerTitle.get(i), offerDescription.get(i), offerImages.getDrawable(i)));
        }
    }

    private void setTransFormer() {
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(42));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        binding.viewPager.setPageTransformer(compositePageTransformer);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 8;
                outRect.right = 8;
                outRect.top = 8;
            }
        };

        binding.viewPager.addItemDecoration(itemDecoration);
    }

    private void updateSlide(int position) {
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
    }


    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.cardApplyLoan) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_applyLoanActivity);
        } else if (id == R.id.allLoanDocs) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_allLoanDocumentFragment);
        } else if (id == R.id.cardLoanCalculator) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_loanCalculatorFragment);
        } else if (id == R.id.cardNearestDealer) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_nearestDealerFragment);
        } else if (id == R.id.cardNearestPartner) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_nearestPartnerFragment);
        }else if (id == R.id.cardLoanEligibility){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_loanEligibilityFragment);
        }
    }
}
