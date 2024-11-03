package com.dkglabs.products.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.model.response.ProductImage;
import com.dkglabs.products.R;

import java.util.ArrayList;

/**
 * Created by Himanshu Srivastava on 3/30/2023.
 */
public class ProductSliderAdapter extends RecyclerView.Adapter<ProductSliderAdapter.SliderViewHolder> {

    private ArrayList<ProductImage> productImageList;

    public ProductSliderAdapter(ArrayList<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setOnBoardingImage(productImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(productImageList) ? productImageList.size() : 0;
    }


    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView offerImage;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            offerImage = itemView.findViewById(R.id.offerImage);
        }

        public void setOnBoardingImage(ProductImage offerCard) {
            //offerImage.setImageDrawable(offerCard.getImage());
        }
    }

}