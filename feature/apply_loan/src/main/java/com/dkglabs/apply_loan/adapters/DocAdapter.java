package com.dkglabs.apply_loan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.apply_loan.databinding.LayoutDocumentsBinding;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.DocDetails;

import java.util.List;

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder> {

    private List<DocDetails> docList;
    private Context context;
    private View.OnClickListener listener;

    public DocAdapter(List<DocDetails> docList, Context context, View.OnClickListener listener) {
        this.docList = docList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutDocumentsBinding binding = LayoutDocumentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(docList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(docList) ? docList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutDocumentsBinding binding = null;

        public ViewHolder(LayoutDocumentsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.selectDoc.setOnClickListener(listener);
        }

        public void bind(DocDetails doc, int position) {
            binding.selectDoc.setText(String.format("Upload %s", doc.getVrfSName()));
            binding.progressMessage.setText("Uploading " + doc.getVrfSName());
            binding.docUploaded.setText(doc.getVrfSName() + " uploaded");
            binding.selectDoc.setTag(position);

            if (doc.isUploading() && !doc.isUploadStatus()) {
                UIUtils.showView(binding.progress);
            } else {
                UIUtils.hideViewGone(binding.progress);
            }

            if (doc.isUploadStatus()) {
                UIUtils.hideViewGone(binding.selectDoc);
                UIUtils.showView(binding.docUploaded);
            } else {
                UIUtils.hideViewGone(binding.docUploaded);
                UIUtils.showView(binding.selectDoc);
            }
        }
    }
}
