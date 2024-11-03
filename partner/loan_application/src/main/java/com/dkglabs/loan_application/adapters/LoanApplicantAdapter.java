package com.dkglabs.loan_application.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.loan_application.databinding.LayoutLoanApplicationBinding;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.math.BigDecimal;
import java.util.List;

public class LoanApplicantAdapter extends RecyclerView.Adapter<LoanApplicantAdapter.ViewHolder> {

    private final List<LoanApplicantResponse> loanApplicantList;
    private LoanApplicantResponse loanApplicant;
    private final Context context;
    private final View.OnClickListener listener;
    LayoutLoanApplicationBinding mainBinding;

    public LoanApplicantAdapter(Context context, List<LoanApplicantResponse> loanApplicantList, View.OnClickListener listener) {
        this.loanApplicantList = loanApplicantList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutLoanApplicationBinding binding = LayoutLoanApplicationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        mainBinding = binding;
        return new LoanApplicantAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loanApplicant = loanApplicantList.get(position);
//        double scientificNotation = loanApplicant.getLoanAmount();
//        BigDecimal bigDecimal = BigDecimal.valueOf(scientificNotation);
//        String decimalRepresentation = bigDecimal.toPlainString();
//        mainBinding.textLoanAmount.setText(decimalRepresentation);
        holder.setView(loanApplicant);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(loanApplicantList) ? loanApplicantList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutLoanApplicationBinding binding = null;

        public ViewHolder(LayoutLoanApplicationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void setView(LoanApplicantResponse loanApplicantList) {
            binding.buttonUpload.setTag(loanApplicantList);
            binding.buttonUpload.setOnClickListener(listener);
            binding.btnEmi.setOnClickListener(listener);
            binding.btnEmi.setTag(loanApplicantList);
            binding.setLoanApplicant(loanApplicantList);
        }
    }
}
