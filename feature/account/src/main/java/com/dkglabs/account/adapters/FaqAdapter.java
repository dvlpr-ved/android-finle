package com.dkglabs.account.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.account.model.FaqModel;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.account.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 29,April,2023
 * Project e_savari_customer
 */
public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    private List<FaqModel> listFaq;
    private Context context;

    public FaqAdapter(List<FaqModel> listFaq, Context context) {
        this.listFaq = listFaq;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_faq, parent, false);
        return new FaqAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FaqModel faq = listFaq.get(position);
        holder.setView(faq);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(listFaq) ? listFaq.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView tvFaqQus;
        private MaterialTextView tvFaqAns;
        private ImageView iconFaq;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFaqQus = itemView.findViewById(R.id.tvFaqQus);
            tvFaqAns = itemView.findViewById(R.id.tvFaqAns);
            iconFaq = itemView.findViewById(R.id.iconFaq);
        }

        public void setView(FaqModel faq) {
            tvFaqQus.setText(faq.getFaqQuestion());
            tvFaqAns.setText(faq.getFaqAnswer());
            iconFaq.setImageDrawable(faq.getFaqIcon());
        }
    }
}
