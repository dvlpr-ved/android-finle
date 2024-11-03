package com.dkglabs.products.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.model.response.ProductResponse;
import com.dkglabs.products.R;
import com.dkglabs.products.adapters.ProductAdapter;
import com.dkglabs.products.databinding.FragmentProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private List<ProductResponse> productList;
    private ProductAdapter adapter;
    private FragmentProductListBinding binding = null;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar(getString(R.string.text_products));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(manager);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, context);
        binding.recyclerView.setAdapter(adapter);

        binding.radioGroupProduct.setOnCheckedChangeListener(this);
    }

    private List<ProductResponse> addListItem() {
        List<ProductResponse> productList = new ArrayList<>();
//        productList.add(new ProductResponse("1001", "Product best deal 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "3.6", "12", "105000", "0", "0"));
//        productList.add(new ProductResponse("1002", "Product best deal 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.2", "20", "155000", "0", "0"));
//        productList.add(new ProductResponse("1003", "Product best deal 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "2.2", "2", "254000", "0", "0"));
//        productList.add(new ProductResponse("1004", "Product top seller 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "3.4", "112", "125000", "0", "0"));
//        productList.add(new ProductResponse("1005", "Product top seller 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.2", "20", "295000", "0", "0"));
//        productList.add(new ProductResponse("1006", "Product top seller 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.4", "29", "125000", "0", "0"));
        return productList;
    }

    private List<ProductResponse> addListItemBestDeal() {
        List<ProductResponse> productList = new ArrayList<>();
//        productList.add(new ProductResponse("1001", "Product best deal 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "3.6", "12", "105000", "0", "0"));
//        productList.add(new ProductResponse("1002", "Product best deal 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.2", "20", "155000", "0", "0"));
//        productList.add(new ProductResponse("1003", "Product best deal 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "2.2", "2", "254000", "0", "0"));
        return productList;
    }

    private List<ProductResponse> addListItemTopSeller() {
        List<ProductResponse> productList = new ArrayList<>();
//        productList.add(new ProductResponse("1004", "Product top seller 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "3.4", "112", "125000", "0", "0"));
//        productList.add(new ProductResponse("1005", "Product top seller 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.2", "20", "295000", "0", "0"));
//        productList.add(new ProductResponse("1006", "Product top seller 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "4.4", "29", "125000", "0", "0"));
        return productList;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.radioAll) {
            productList.clear();
            productList.addAll(addListItem());
            adapter.notifyDataSetChanged();
        } else if (i == R.id.radioBestDeals) {
            productList.clear();
            productList.addAll(addListItemBestDeal());
            adapter.notifyDataSetChanged();
        }
        if (i == R.id.radioTopSeller) {
            productList.clear();
            productList.addAll(addListItemTopSeller());
            adapter.notifyDataSetChanged();
        }
    }
}