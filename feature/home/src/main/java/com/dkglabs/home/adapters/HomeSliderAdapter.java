package com.dkglabs.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.home.R;
import com.dkglabs.home.model.OfferCard;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

/**
 * Created by Himanshu Srivastava on 3/30/2023.
 */
public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.SliderViewHolder> {

    private ArrayList<OfferCard> offerCardList;

    public HomeSliderAdapter(ArrayList<OfferCard> offerCardList) {
        this.offerCardList = offerCardList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setOnBoardingImage(offerCardList.get(position));
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(offerCardList) ? offerCardList.size() : 0;
    }


    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView offerTitle;
        private MaterialTextView offerDescription;
        private ImageView offerImage;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            offerImage = itemView.findViewById(R.id.offerImage);
            offerTitle = itemView.findViewById(R.id.offerTitle);
            offerDescription = itemView.findViewById(R.id.offerDescription);
        }

        public void setOnBoardingImage(OfferCard offerCard) {
            offerImage.setImageDrawable(offerCard.getImage());
            offerTitle.setText(offerCard.getOfferTitle());
            offerDescription.setText(offerCard.getOfferDescription());
        }
    }

}