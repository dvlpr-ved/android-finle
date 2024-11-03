package com.dkglabs.loan_application.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.PermissionUtils;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.adapters.ApplicantDocAdapter;
import com.dkglabs.loan_application.databinding.FragmentLoanDocumentsBinding;
import com.dkglabs.loan_application.fragments.LoanFulfillmentFeildFragmentDirections;
import com.dkglabs.loan_application.fragments.LoanFulfillmentFragmentDirections;
import com.dkglabs.loan_application.model.ApplicantDetail;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.FulfillStaticModelList;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.internal.Util;

public class LoanFulfillmentFeildFragment extends BaseFragment implements View.OnClickListener {

    private ApplicantDetail applicantDetail;
    private LoanApplicantResponse LoanApplicantResponse;
    private List<ApplicantDocDetails> docDetailsList;
    private ApplicantDocAdapter adapter;
    private FragmentLoanDocumentsBinding binding = null;
    FulfillStaticModelList staticModel;
    protected ApplicantDocDetails response;
    private ProgressDialog dialog;

    public LoanFulfillmentFeildFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicantDetail = LoanFulfillmentFragmentArgs.fromBundle(getArguments()).getApplicantDetail();
        staticModel=new FulfillStaticModelList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanDocumentsBinding.inflate(inflater, container, false);

        ViewGroup parentView = (ViewGroup) binding.getRoot();
        View includedLayout = parentView.findViewById(R.id.llHeader);
        if (includedLayout != null) {
            includedLayout.setVisibility(View.GONE);
        }
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar("Field Inspection");
        docDetailsList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(manager);
        adapter = new ApplicantDocAdapter(docDetailsList, this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        setUpBackToolbar(applicantDetail.getDocuments());

        LoanApplicantResponse = applicantDetail.getLoanApplicantResponse();
        List<DocDetails> docDetails = new ArrayList<>();
        docDetails.addAll(applicantDetail.getDocDetailsList());

        binding.llHeader.setUserName(LoanApplicantResponse.getName());
        updateDocumentProgress(docDetails);
        updateListUi(docDetails);
    }

    private void updateListUi(List<DocDetails> docDetails) {
        for (DocDetails docDetail : docDetails) {
            ApplicantDocDetails details = new ApplicantDocDetails();
            HashMap<String,List<String>> vrfsList= FulfillStaticModelList.getFulFilVrfsList();
            if(vrfsList.get( applicantDetail.getFulFilVrfs() ).contains( docDetail.getVrfsCode() ) ){
                details.setDocDetails(docDetail);
                details.setTITLE(docDetail.getVrfSName());
                details.setIcon( context.getDrawable( staticModel.getFullFillIcon().get( docDetail.getVrfsCode()) ) );
                docDetailsList.add(details);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void updateDocumentProgress(List<DocDetails> docDetails) {
        int uploadedDoc = 0;
        for (DocDetails docDetail : docDetails) {
            if (docDetail.isUploadStatus())
                uploadedDoc++;
        }

        int docProgress = uploadedDoc / docDetails.size() * 100;

        binding.llHeader.setCompletion(getString(R.string.placeholder_progress_compleion, docProgress));
        binding.llHeader.setProgress(docProgress);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        response=(ApplicantDocDetails)view.getTag();
        if( response.getDocDetails().isUploadStatus() ){
            Toast.makeText(context, response.getDocDetails().getVrfSName() + " Uploaded", Toast.LENGTH_SHORT).show();
        }else {
            response.setUserId(applicantDetail.getUserId());
            if(response.getDocDetails().getVrfsCode().equals("A506") || response.getDocDetails().getVrfsCode().equals("A507")) {
                response.setLoneId(applicantDetail.getLoanApplicantResponse().getLoanId());
                response.setUserId(applicantDetail.getUserId());
                response.setPre_post(2);
                response.setFulFilNumberType(staticModel.getPreNumberMap().get(response.getDocDetails().getVrfsCode()));

                LoanFulfillmentFeildFragmentDirections.ActionLoanFulfillmentFeildFragmentToFieldInspectionFragment direction=
                        LoanFulfillmentFeildFragmentDirections.actionLoanFulfillmentFeildFragmentToFieldInspectionFragment(response);
                Navigation.findNavController(binding.getRoot()).navigate(direction);

            }else {
                response.setLoneId(applicantDetail.getLoanApplicantResponse().getLoanId());
                response.setUserId(applicantDetail.getUserId());
                response.setPre_post(2);
                response.setFulFilNumberType(staticModel.getPreNumberMap().get(response.getDocDetails().getVrfsCode()));

                LoanFulfillmentFeildFragmentDirections.ActionLoanFulfillmentFeildFragmentToPrePostDocUpload direction=
                        LoanFulfillmentFeildFragmentDirections.actionLoanFulfillmentFeildFragmentToPrePostDocUpload(response);
                Navigation.findNavController(binding.getRoot()).navigate(direction);
            }
        }
    }

}