package com.dkglabs.e_savari.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.e_savari.R;

import java.util.ArrayList;

/**
 * Created by Himanshu Srivastava on 3/30/2023.
 */
public class AppGuideAdapter extends RecyclerView.Adapter<AppGuideAdapter.ViewHolder> {

    private ArrayList<Integer> onBoardingImage;

    public AppGuideAdapter(ArrayList<Integer> onBoardingImage) {
        this.onBoardingImage = onBoardingImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_app_guide_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setOnBoardingImage(onBoardingImage.get(position));
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(onBoardingImage) ? onBoardingImage.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView onBoardingImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            onBoardingImage = itemView.findViewById(R.id.onBoardingImage);
        }

        public void setOnBoardingImage(int image) {
            onBoardingImage.setImageResource(image);
        }
    }
}