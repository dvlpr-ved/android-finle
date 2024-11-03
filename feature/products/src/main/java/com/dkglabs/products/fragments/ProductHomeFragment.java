package com.dkglabs.products.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.model.response.ProductResponse;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.products.R;
import com.dkglabs.products.adapters.ProductAdapter;
import com.dkglabs.products.databinding.FragmentProductHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductHomeFragment extends BaseFragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private List<ProductResponse> bestDealList;
    private List<ProductResponse> topSellerList;
    private List<ProductResponse> productList;
    private ProductAdapter adapter;
    private FragmentProductHomeBinding binding = null;

    public ProductHomeFragment() {
        // Required empty public constructor
    }

    public static ProductHomeFragment newInstance() {
        return new ProductHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductHomeBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(manager);
        addListItem();

        productList = new ArrayList<>();
        productList.addAll(topSellerList);
        adapter = new ProductAdapter(productList, context);
        binding.recyclerView.setAdapter(adapter);

        binding.productOption.setOnClickListener(this);

    }

    private void addListItem() {
        bestDealList = new ArrayList<>();
//        bestDealList.add(new ProductResponse("1001", "Product best deal 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "3.6", "12", "105000", "0", "0"));
//        bestDealList.add(new ProductResponse("1002", "Product best deal 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.2", "20", "155000", "0", "0"));
//        bestDealList.add(new ProductResponse("1003", "Product best deal 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "2.2", "2", "254000", "0", "0"));

        topSellerList = new ArrayList<>();
//        topSellerList.add(new ProductResponse("1004", "Product top seller 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "3.4", "112", "125000", "0", "0"));
//        topSellerList.add(new ProductResponse("1005", "Product top seller 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.2", "20", "295000", "0", "0"));
//        topSellerList.add(new ProductResponse("1006", "Product top seller 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.4", "29", "125000", "0", "0"));

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menuBestDeals) {
            binding.productOption.setText(R.string.text_best_deals);
            productList.clear();
            productList.addAll(bestDealList);
            adapter.notifyDataSetChanged();
            return true;
        } else if (menuItem.getItemId() == R.id.menuTopSeller) {
            binding.productOption.setText(R.string.text_top_seller);
            productList.clear();
            productList.addAll(topSellerList);
            adapter.notifyDataSetChanged();
            return true;
        } else if (menuItem.getItemId() == R.id.menuAll) {
            AppViewModel model = new AppViewModel();
            model.setShowAllProducts(true);
            viewModel.setSelectedItem(model);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.productOption) {
            PopupMenu popupMenu = new PopupMenu(context, binding.cardView, Gravity.END);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.popup_menu_product_home, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }
    }
}