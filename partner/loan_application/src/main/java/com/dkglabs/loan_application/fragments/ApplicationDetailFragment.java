package com.dkglabs.loan_application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.databinding.FragmentApplicationDetailBinding;
import com.dkglabs.loan_application.model.ApplicantDetail;
import com.dkglabs.loan_application.staticModels.FulfillStaticModelList;
import com.dkglabs.loan_application.staticModels.PostStaticModelList;
import com.dkglabs.loan_application.staticModels.PreStaticModelList;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDetailFragment extends BaseFragment implements ResponseListener ,View.OnClickListener{
    private Boolean isLoading = false;
    private LoanApplicantResponse loanApplicantResponse;
    private FragmentApplicationDetailBinding binding = null;
    private List<DocDetails> preDisbursmentList;
    private List<DocDetails> loanFullFillmentList;
    private List<DocDetails> postDisbursmentList;

    public ApplicationDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanApplicantResponse = ApplicationDetailFragmentArgs.fromBundle(getArguments()).getLoanApplicantResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentApplicationDetailBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLoanApplicantResponse(loanApplicantResponse);
        isLoading = true;
        showProgressbar(isLoading);
        LoanManager.dealerDocuments(1001, loanApplicantResponse.getUserId(),  this);
    }
    private void showProgressbar(Boolean isLoading) {
        if(isLoading){
            UIUtils.showView(binding.progressLayout.getRoot());
            binding.progressLayout.setMessage("Checking applicant documents...");
        } else {
            UIUtils.hideViewGone(binding.progressLayout.getRoot());
        }
    }

    private void initializeView() {
        setUpBackToolbar("Application Detail");
        binding.preDisbursement.setTitle(getString(R.string.text_pre_disbursement));
        binding.loanFulfilment.setTitle(getString(R.string.text_loan_fulfillment));
        binding.postDisbursement.setTitle(getString(R.string.text_post_disbursement));

        preDisbursmentList = new ArrayList<>();
        loanFullFillmentList = new ArrayList<>();
        postDisbursmentList = new ArrayList<>();

        updateDocumentProgress(0, 0, 0);

        binding.preDisbursement.getRoot().setOnClickListener(this);
        binding.loanFulfilment.getRoot().setOnClickListener(this);
        binding.postDisbursement.getRoot().setOnClickListener(this);
    }
    private void updateDocumentProgress(int preDisbursement, int loanFulfilment, int postDisbursement) {
        if(getActivity()!=null) {
            binding.preDisbursement.setCompletion(getString(R.string.placeholder_progress_compleion, preDisbursement));
            binding.loanFulfilment.setCompletion(getString(R.string.placeholder_progress_compleion, loanFulfilment));
            binding.postDisbursement.setCompletion(getString(R.string.placeholder_progress_compleion, postDisbursement));
            binding.preDisbursement.setProgress(preDisbursement);
            binding.loanFulfilment.setProgress(loanFulfilment);
            binding.postDisbursement.setProgress(postDisbursement);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.preDisbursement.getRoot().getId()) {
            ApplicantDetail applicantDetail = new ApplicantDetail();
            applicantDetail.setDocuments(getString(R.string.text_pre_disbursement));
            applicantDetail.setLoanApplicantResponse(loanApplicantResponse);
            applicantDetail.setDocDetailsList(preDisbursmentList);
            applicantDetail.setUserId(loanApplicantResponse.getUserId());
            ApplicationDetailFragmentDirections.ActionApplicationDetailFragmentToPreDisbursementFragment direction = ApplicationDetailFragmentDirections.actionApplicationDetailFragmentToPreDisbursementFragment(applicantDetail);
            if (isLoading) {
                UIUtils.showSnackbar(view, "Refreshing loan documents. Please wait...");
                return;
            }
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        } else if (id == binding.loanFulfilment.getRoot().getId()) {
            ApplicantDetail applicantDetail = new ApplicantDetail();
            applicantDetail.setDocuments(getString(R.string.text_loan_fulfillment));
            applicantDetail.setLoanApplicantResponse(loanApplicantResponse);
            applicantDetail.setDocDetailsList(loanFullFillmentList);
            applicantDetail.setUserId(loanApplicantResponse.getUserId());
            ApplicationDetailFragmentDirections.ActionApplicationDetailFragmentToLoanFulfillmentFragment direction = ApplicationDetailFragmentDirections.actionApplicationDetailFragmentToLoanFulfillmentFragment(applicantDetail);
            if (isLoading) {
                UIUtils.showSnackbar(view, "Refreshing loan documents. Please wait...");
                return;
            }
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        } else if (id == binding.postDisbursement.getRoot().getId()) {
            ApplicantDetail applicantDetail = new ApplicantDetail();
            applicantDetail.setDocuments(getString(R.string.text_post_disbursement));
            applicantDetail.setLoanApplicantResponse(loanApplicantResponse);
            applicantDetail.setDocDetailsList(postDisbursmentList);
            applicantDetail.setUserId(loanApplicantResponse.getUserId());
            ApplicationDetailFragmentDirections.ActionApplicationDetailFragmentToPostDisbursementFragment direction = ApplicationDetailFragmentDirections.actionApplicationDetailFragmentToPostDisbursementFragment(applicantDetail);
            if (isLoading) {
                UIUtils.showSnackbar(view, "Refreshing loan documents. Please wait...");
                return;
            }
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }
    }
    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == 1001) {
            List<DocDetails> docDetailResponse = (List<DocDetails>) response.getResponseData();
            validateList(docDetailResponse);
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {
        isLoading = false;
        showProgressbar(isLoading);
    }

    private void validateList(List<DocDetails> docDetailResponse) {
        int preDisbursement = 0;
        int loanFulfilment = 0;
        int postDisbursement = 0;
        int preSize=0;
        int postSize=0;
        int fulFilSize=0;
        FulfillStaticModelList modelListFull=new FulfillStaticModelList();
        PreStaticModelList modelListPre=new PreStaticModelList();
        PostStaticModelList modelListPost=new PostStaticModelList();
        if (docDetailResponse != null) {
            for (DocDetails docDetails : docDetailResponse) {
                switch (docDetails.getVrfName()) {
                    case "Pre Disbursement":
                        preDisbursmentList.add(docDetails);
                        if (docDetails.isUploadStatus() && modelListPre.getPreDocumentMap().containsKey(docDetails.getVrfsCode() ))
                            preDisbursement++;
                        if((docDetails.getMandatory().equals("Y") && docDetails.getVrfCode().equals("A4")) || modelListPre.getPreDocumentMap().containsKey(docDetails.getVrfsCode()) )
                            preSize++;

                        break;
                    case "Loan Fullfilment":
                    case "Loan Fulfillment Field":
                        loanFullFillmentList.add(docDetails);
                        if (docDetails.isUploadStatus() && modelListFull.getFulFillVrfsCodeMap().containsKey(docDetails.getVrfsCode()))
                            loanFulfilment++;
                        if((docDetails.getMandatory().equals("Y") && docDetails.getVrfCode().equals("A5")) || modelListFull.getFulFillVrfsCodeMap().containsKey(docDetails.getVrfsCode()))
                            fulFilSize++;
                        break;
                    case "Post Disbursement":
                        postDisbursmentList.add(docDetails);
                        if (docDetails.isUploadStatus() && modelListPost.getPostIconMap().containsKey(docDetails.getVrfsCode()) )
                            postDisbursement++;
                        if((docDetails.getMandatory().equals("Y") && docDetails.getVrfCode().equals("A6"))||modelListPost.getPostIconMap().containsKey(docDetails.getVrfsCode()))
                            postSize++;
                        break;
                }
            }
        }
        preSize=modelListPre.getPreDocumentMap().size();
        fulFilSize=modelListFull.getFulFillVrfsCodeMap().size();
        postSize=modelListPost.getPostDocumentMap().size();

        int preDisbursementProgress = (preDisbursement * 100)/ preSize;
        int loanFulfilmentProgress = (loanFulfilment * 100) / fulFilSize  ;
        int postDisbursementProgress = (postDisbursement * 100)/ postSize  ;

        updateDocumentProgress(preDisbursementProgress, loanFulfilmentProgress, postDisbursementProgress);
    }
}