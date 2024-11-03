package com.dkglabs.loan_application.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.adapters.ApplicantDocAdapter;
import com.dkglabs.loan_application.databinding.FragmentLoanDocumentsBinding;
import com.dkglabs.loan_application.fragments.PreDisbursementFragmentDirections;
import com.dkglabs.loan_application.model.ApplicantDetail;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.util.ArrayList;
import java.util.List;


public class PreDisbursementFragment extends BaseFragment implements View.OnClickListener {

    private ApplicantDetail applicantDetail;
    private LoanApplicantResponse LoanApplicantResponse;
    private List<ApplicantDocDetails> docDetailsList;
    private ApplicantDocAdapter adapter;
    PreStaticModelList staticModel;
    private FragmentLoanDocumentsBinding binding = null;


    public PreDisbursementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicantDetail = PreDisbursementFragmentArgs.fromBundle(getArguments()).getApplicantDetail();
        staticModel=new PreStaticModelList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanDocumentsBinding.inflate(inflater, container, false);
        if(binding!=null)
            initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        docDetailsList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(manager);
        adapter = new ApplicantDocAdapter(docDetailsList, this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpBackToolbar(applicantDetail.getDocuments());

        LoanApplicantResponse = applicantDetail.getLoanApplicantResponse();
        List<DocDetails> docDetails = new ArrayList<>();
        docDetails.addAll(applicantDetail.getDocDetailsList());

        binding.llHeader.setUserName(LoanApplicantResponse.getName());
        updateDocumentProgress(docDetails);
        updateListUi(docDetails);
    }
    private void updateListUi(List<DocDetails> docDetails) {
        for(DocDetails docDetail : docDetails){
            ApplicantDocDetails details = new ApplicantDocDetails();
            if(staticModel.getPreVrfsCodeMap().containsKey( docDetail.getVrfsCode() )) {
                details.setDocDetails(docDetail);
                details.setTITLE(staticModel.getPreDocumentMap().get(docDetail.getVrfsCode()));
                details.setPre_post(1);
                details.setPreNumberType(staticModel.getPreNumberMap().get( docDetail.getVrfsCode() ));
                details.setIcon(context.getDrawable(staticModel.getPreIconMap().get(docDetail.getVrfsCode())));
                docDetailsList.add(details);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void updateDocumentProgress(List<DocDetails> docDetails) {
        PreStaticModelList modelListPre=new PreStaticModelList();
        int uploadedDoc = 0;
        for(DocDetails docDetail : docDetails){
            if (docDetail.isUploadStatus() && modelListPre.getPreDocumentMap().containsKey(docDetail.getVrfsCode() ))
                uploadedDoc++;
        }

        int docProgress =( uploadedDoc * 100 )/ staticModel.getPreDocumentMap().size();

        binding.llHeader.setCompletion(getString(R.string.placeholder_progress_compleion, docProgress));
        binding.llHeader.setProgress(docProgress);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        ApplicantDocDetails response = (ApplicantDocDetails) view.getTag();
        response.setLoneId(applicantDetail.getLoanApplicantResponse().getLoanId());
        response.setUserId(applicantDetail.getUserId());
        if (response.getDocDetails().isUploadStatus()) {
            Toast.makeText(context, "Document is Uploaded", Toast.LENGTH_SHORT).show();
        } else {
            //code
            com.dkglabs.loan_application.fragments.PreDisbursementFragmentDirections.ActionPreDisbursementFragmentToPrePostDocUpload action=PreDisbursementFragmentDirections.actionPreDisbursementFragmentToPrePostDocUpload(response);
            Navigation.findNavController(view).navigate(action);
        }
    }
}