package com.dkglabs.dashboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.utils.AppConstants;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.DateUtils;
import com.dkglabs.dashboard.R;
import com.dkglabs.dashboard.databinding.LayoutEmiBinding;
import com.dkglabs.model.response.LoanEmiDetailResponse;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 23 August,2023
 * Project e_savari_customer
 */
public class DueEmiAdapter extends RecyclerView.Adapter<DueEmiAdapter.ViewHolder> {

    private List<LoanEmiDetailResponse> listEmi;
    private Context context;

    public DueEmiAdapter(List<LoanEmiDetailResponse> listEmi, Context context) {
        this.listEmi = listEmi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutEmiBinding binding = LayoutEmiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DueEmiAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoanEmiDetailResponse loanEmiDetail = listEmi.get(position);
        holder.setView(loanEmiDetail);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(listEmi) ? listEmi.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutEmiBinding binding = null;

        public ViewHolder(LayoutEmiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setView(LoanEmiDetailResponse loanEmiDetail) {
//            binding.emi.setText(String.format(context.getString(R.string.placeholder_rupee), loanEmiDetail.getLoanEmiAmount()));
            binding.emi.setText("₹ "+loanEmiDetail.getLoanEmiAmount().toString());
            binding.emiMonth.setText(DateUtils.getNewDate(AppConstants.SERVER_FORMAT, loanEmiDetail.getLoanEmiPaymentDate(), AppConstants.EMI_MONTH_FORMAT));
            binding.dueDate.setText(DateUtils.getNewDate(AppConstants.SERVER_FORMAT, loanEmiDetail.getLoanEmiPaymentDate(), AppConstants.EMI_DATE_FORMAT));

        }
    }
}
