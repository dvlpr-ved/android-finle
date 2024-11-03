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
import com.dkglabs.loan_application.fragments.PostDisbursementFragmentDirections;
import com.dkglabs.loan_application.model.ApplicantDetail;
import com.dkglabs.loan_application.model.ApplicantDocDetails;
import com.dkglabs.loan_application.staticModels.PostStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.util.ArrayList;
import java.util.List;

public class PostDisbursementFragment extends BaseFragment implements View.OnClickListener {

    private ApplicantDetail applicantDetail;
    private LoanApplicantResponse LoanApplicantResponse;
    private List<ApplicantDocDetails> docDetailsList;
    private ApplicantDocAdapter adapter;
    private FragmentLoanDocumentsBinding binding = null;
    PostStaticModelList staticModel;

    public PostDisbursementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicantDetail = PostDisbursementFragmentArgs.fromBundle(getArguments()).getApplicantDetail();
        staticModel=new PostStaticModelList();
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
//                case "A601":
//                    details.setIcon(context.getDrawable(R.drawable.ic_motor));
//                    break;
//                case "A602":
//                    details.setIcon(context.getDrawable(R.drawable.ic_doc));
//                    break;
//            }
            if(staticModel.getPostVrfsCodeMap().containsKey( docDetail.getVrfsCode() )) {
                details.setDocDetails(docDetail);
                details.setTITLE(staticModel.getPostDocumentMap().get( docDetail.getVrfsCode() ));
                details.setIcon( context.getDrawable( staticModel.getPostIconMap().get( docDetail.getVrfsCode() ) ) );
                docDetailsList.add(details);
            }
//            details.setDocDetails(docDetail);
//            docDetailsList.add(details);
        }

        adapter.notifyDataSetChanged();
    }

    private void updateDocumentProgress(List<DocDetails> docDetails) {
        PostStaticModelList modelListPost=new PostStaticModelList();
        int uploadedDoc = 0;
        int Size=0;
        for (DocDetails docDetail : docDetails) {
            if (docDetail.isUploadStatus() && modelListPost.getPostDocumentMap().containsKey(docDetail.getVrfsCode() ))
                uploadedDoc++;
        }

        int docProgress = (uploadedDoc * 100) /staticModel.getPostDocumentMap().size()  ;
        binding.llHeader.setCompletion(getString(R.string.placeholder_progress_compleion, docProgress));
        binding.llHeader.setProgress(docProgress);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        ApplicantDocDetails response=(ApplicantDocDetails)view.getTag();
        response.setLoneId(applicantDetail.getLoanApplicantResponse().getLoanId());
        response.setUserId(applicantDetail.getUserId());
        response.setPre_post(3);
        response.setPostNumberType(staticModel.getPostNumberType().get(response.getDocDetails().getVrfsCode()));
        if(response.getDocDetails().isUploadStatus()){
            Toast.makeText(context, response.getDocDetails().getVrfSName() + " Uploaded", Toast.LENGTH_SHORT).show();
        }else{
            com.dkglabs.loan_application.fragments.PostDisbursementFragmentDirections.ActionPostDisbursementFragmentToPrePostDocUpload action=PostDisbursementFragmentDirections.actionPostDisbursementFragmentToPrePostDocUpload(response);
            Navigation.findNavController(view).navigate(action);
        }

    }
}