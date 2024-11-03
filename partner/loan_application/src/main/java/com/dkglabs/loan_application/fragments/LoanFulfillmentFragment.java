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
import com.dkglabs.loan_application.model.ApplicantDetail;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.FulfillStaticModelList;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.util.ArrayList;
import java.util.List;

public class LoanFulfillmentFragment extends BaseFragment implements View.OnClickListener {

    private ApplicantDetail applicantDetail;
    private LoanApplicantResponse LoanApplicantResponse;
    private List<ApplicantDocDetails> docDetailsList;
    private ApplicantDocAdapter adapter;
    private FragmentLoanDocumentsBinding binding = null;
     FulfillStaticModelList staticModel;
     private static final String FIELD_INSPECTION="A508";


    public LoanFulfillmentFragment() {
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
        for (DocDetails docDetail : docDetails) {
            ApplicantDocDetails details = new ApplicantDocDetails();
//            switch (docDetail.getVrfsCode()) {
//                case "A501":
//                    details.setIcon(context.getDrawable(R.drawable.ic_inspection));
//                    break;
//                case "A502":
//                    details.setIcon(context.getDrawable(R.drawable.ic_imei));
//                    break;
//                case "A503":
//                    details.setIcon(context.getDrawable(R.drawable.ic_cheque));
//                    break;
//                case "A504":
//                    details.setIcon(context.getDrawable(R.drawable.ic_doc));
//                    break;
//            }
//            if (docDetail.getVrfName().equals("Loan Fulfillment"))
            if(staticModel.getFulFillVrfsCodeMap().containsKey( docDetail.getVrfsCode() )) {
                details.setDocDetails(docDetail);
                details.setTITLE(staticModel.getFullFillDocuments().get(docDetail.getVrfsCode()));
                details.setIcon(context.getDrawable(staticModel.getFullFillIcon().get(docDetail.getVrfsCode())));

                if(staticModel.getFieldInspection().contains(docDetail.getVrfsCode()) && docDetail.isUploadStatus())
                    details.setMain_fullFill(false);
                docDetailsList.add(details);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void updateDocumentProgress(List<DocDetails> docDetails) {
        FulfillStaticModelList modelListFull=new FulfillStaticModelList();
        int uploadedDoc = 0;
        for (DocDetails docDetail : docDetails) {
            if (docDetail.isUploadStatus() && modelListFull.getFullFillDocuments().containsKey(docDetail.getVrfsCode()))
                uploadedDoc++;
        }

        int docProgress = (uploadedDoc * 100)/ modelListFull.getFullFillDocuments().size();
        binding.llHeader.setCompletion(getString(R.string.placeholder_progress_compleion, docProgress));
        binding.llHeader.setProgress(docProgress);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        ApplicantDocDetails response=(ApplicantDocDetails)view.getTag();
        response.setLoneId(applicantDetail.getLoanApplicantResponse().getLoanId());
        response.setUserId(applicantDetail.getUserId());
        if( response.getDocDetails().isUploadStatus() && response.isMain_fullFill()){
            Toast.makeText(context, "Document is Uploaded", Toast.LENGTH_SHORT).show();
        }else {
            openLoanFulfillmentFeildFragment(response);
        }
    }
    private void openLoanFulfillmentFeildFragment(ApplicantDocDetails response) {
        if(response.getDocDetails().getVrfsCode().equals(FIELD_INSPECTION)) {
            applicantDetail.setFulFilVrfs(response.getDocDetails().getVrfsCode());
            com.dkglabs.loan_application.fragments.LoanFulfillmentFragmentDirections.ActionLoanFulfillmentFragmentToLoanFulfillmentFeildFragment direction = LoanFulfillmentFragmentDirections.actionLoanFulfillmentFragmentToLoanFulfillmentFeildFragment(applicantDetail);
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }else{
            response.setLoneId(applicantDetail.getLoanApplicantResponse().getLoanId());
            response.setUserId(applicantDetail.getUserId());
            response.setPre_post(2);
            response.setFulFilNumberType(staticModel.getPreNumberMap().get(response.getDocDetails().getVrfsCode()));
            com.dkglabs.loan_application.fragments.LoanFulfillmentFragmentDirections.ActionLoanFulfillmentFragmentToPrePostDocUpload direction=LoanFulfillmentFragmentDirections.actionLoanFulfillmentFragmentToPrePostDocUpload(response);
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }
    }
}