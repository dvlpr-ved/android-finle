package com.dkglabs.loan_application.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.databinding.LayoutApplicationItemBinding;
import com.dkglabs.loan_application.model.ApplicantDocDetails;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

public class ApplicantDocAdapter extends RecyclerView.Adapter<ApplicantDocAdapter.ViewHolder> {

    private final List<ApplicantDocDetails> applicantDocDetails;
    private final View.OnClickListener clickListener;
    public ApplicantDocAdapter(List<ApplicantDocDetails> applicantDocDetails, View.OnClickListener clickListener) {
        this.applicantDocDetails = applicantDocDetails;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutApplicationItemBinding binding = LayoutApplicationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ApplicantDocAdapter.ViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        ApplicantDocDetails loanResponse = applicantDocDetails.get(position);
        holder.setView(loanResponse);
        loanResponse.setPosition(position);
        holder.binding.matUpload.setText( loanResponse.getTITLE());
        if(loanResponse.getDocDetails().isUploadStatus() && loanResponse.isMain_fullFill() )
            holder.binding.matUpload.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
        holder.binding.loanDoc.setTag(loanResponse);
    }
    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(applicantDocDetails) ? applicantDocDetails.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutApplicationItemBinding binding = null;

        public ViewHolder(LayoutApplicationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setView(ApplicantDocDetails response) {
            binding.setApplicantDocDetail(response);
            binding.loanDoc.setOnClickListener(clickListener);
        }
    }
}
