package com.dkglabs.products.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.model.response.ProductResponse;
import com.dkglabs.products.R;
import com.dkglabs.products.databinding.LayoutProductBinding;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 29,April,2023
 * Project e_savari_customer
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductResponse> productList;
    private Context context;

    public ProductAdapter(List<ProductResponse> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutProductBinding binding = LayoutProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductResponse productResponse = productList.get(position);
        holder.setView(productResponse);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(productList) ? productList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutProductBinding binding = null;

        public ViewHolder(LayoutProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setView(ProductResponse response) {
            binding.productName.setText(response.getProductName());
            binding.productDescription.setText(response.getProductDescription());
            //Glide.with(context).load(response.getProductImage()).placeholder(R.drawable.img).into(binding.productImage);
            binding.productPrice.setText(String.format(context.getString(R.string.price_placeholder), response.getProductPrice()));
            binding.productRatingCount.setText("(" + response.getRatingCount() + ")");
            binding.productRating.setText(response.getRating());
            binding.ratingBar.setRating(Float.valueOf(response.getRating()) / 5);
        }
    }
}
