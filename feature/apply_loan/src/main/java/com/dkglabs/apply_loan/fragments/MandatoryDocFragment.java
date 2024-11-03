package com.dkglabs.apply_loan.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.adapters.DocAdapter;
import com.dkglabs.apply_loan.databinding.FragmentMandatoryDocBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;

import java.util.ArrayList;
import java.util.List;


public class MandatoryDocFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private List<DocDetails> docDetailsList;

    private DocAdapter adapter;
    private FragmentMandatoryDocBinding binding;
    private int position = -1;

    public MandatoryDocFragment() {
        // Required empty public constructor
    }

    public static MandatoryDocFragment newInstance(Bundle args) {
        MandatoryDocFragment fragment = new MandatoryDocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMandatoryDocBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        docDetailsList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
    }

    public void setDocDetailsList(List<DocDetails> docList) {
        if (docDetailsList.size() > 0) docDetailsList.clear();

        docDetailsList.addAll(docList);
        adapter = new DocAdapter(docDetailsList, getContext(), this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.selectDoc) {
            position = (int) view.getTag();
            openFile();
        }
    }

    private void openFile() {
        String[] mimeTypes = new String[]{"image/*", "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*|application/pdf");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, position);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        docDetailsList.get(requestCode).setUploadStatus(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {
        docDetailsList.get(requestCode).setUploading(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                uplaodDocument(uri, requestCode);
            }
        }
    }

    private void uplaodDocument(Uri uri, int requestCode) {
        docDetailsList.get(requestCode).setUploading(true);
        adapter.notifyDataSetChanged();
        DocumentManager.uploadDocumentCustomerApplication(requestCode, context, uri, PersistentManager.getUserResponse().getUserId(), docDetailsList.get(requestCode),  this);
    }
}