package com.dkglabs.account.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.account.databinding.LayoutDealerBinding;
import com.dkglabs.model.response.DealerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 29,April,2023
 * Project e_savari_customer
 */
public class DealerAdapter extends RecyclerView.Adapter<DealerAdapter.ViewHolder> implements Filterable {

    private List<DealerResponse> dealerList;
    private List<DealerResponse> dealerFullList;
    private Context context;
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<DealerResponse> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dealerFullList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (DealerResponse item : dealerFullList) {
                    if (item.getDealerName().toLowerCase().contains(filterPattern) || item.getDealerAddress().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.values != null) {
                dealerList.clear();
                dealerList.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        }
    };

    public DealerAdapter(List<DealerResponse> dealerList, Context context) {
        this.dealerList = dealerList;
        dealerFullList = new ArrayList<>(dealerList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutDealerBinding binding = LayoutDealerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DealerAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DealerResponse dealerResponse = dealerList.get(position);
        holder.setView(dealerResponse);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(dealerList) ? dealerList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutDealerBinding binding = null;

        public ViewHolder(LayoutDealerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setView(DealerResponse response) {
            binding.tvName.setText(response.getDealerName());
            binding.tvAddress.setText(response.getDealerAddress());
            Glide.with(context).load(response.getDealerImage()).placeholder(com.dkglabs.base.R.drawable.placeholder_user).into(binding.profileImage);
            binding.buttonCall.setTag(response);
            binding.buttonMap.setTag(response);
        }
    }
}
