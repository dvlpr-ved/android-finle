package com.dkglabs.apply_loan.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentDealerMappingBinding;
import com.dkglabs.apply_loan.fragments.DealerMappingFragmentDirections;
import com.dkglabs.apply_loan.model.NbfcAndDealerData;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.GetDealerDetails;
import com.dkglabs.model.response.NbfcDetails;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

public class DealerMappingFragment extends BaseFragment implements View.OnClickListener, ResponseListener {
    FragmentDealerMappingBinding binding;
    private static final int DEALER_VERIFICATION_CODE = 1001;
    private static final int NBFC_VERIFICATION_CODE = 1002;
    private boolean isDealer = false;
    private boolean isNBFC = false;
    protected GetDealerDetails dealerDetails;
    protected List<String> NBFC_LIST;
    private HashMap<String, String> nbfcNameIdMap;
    protected String NbfcID;
    protected String DealerId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDealerMappingBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonVerifyDealer.setOnClickListener(this);
        binding.buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.buttonVerifyDealer) {
            String DEALER_ID = binding.dealerId.getEditText().getText().toString().trim();
            if (DEALER_ID.length() <= 0) {
                UIUtils.showToast(binding.getRoot().getContext(), "Enter the dealer id");
            } else {
                VerifyDealer(DEALER_ID);
            }
        }
        if (v == binding.buttonSubmit) {
            String nbfcName = binding.dropNbfcList.getText().toString().trim();
            if (nbfcName.length() <= 0 || nbfcName == null) {
                UIUtils.showToast(requireActivity(), "Please Select an NBFC");
                return;
            }
//            if(!nbfcNameIdMap.get(nbfcName).equals("50000002")){
//                UIUtils.showToast(requireActivity(),"NBFC Not Active.");
//                return;
//            }
            DealerId = dealerDetails.getDealerId();
            NbfcID = nbfcNameIdMap.get(nbfcName);
            startNavigation(DealerId, NbfcID, nbfcName);
        }
    }

    private void startNavigation(String dealerId, String nbfcID, String nbfcName) {
        NbfcAndDealerData nbfcAndDealerData = new NbfcAndDealerData();
        nbfcAndDealerData.setNbfcName(nbfcName);
        nbfcAndDealerData.setNbfcId(nbfcID);
        nbfcAndDealerData.setPartnerId(dealerId);

        DealerMappingFragmentDirections.ActionDealerMappingFragmentToNbfcDetailsFragment direction =
                DealerMappingFragmentDirections.actionDealerMappingFragmentToNbfcDetailsFragment(nbfcAndDealerData);
        Navigation.findNavController(binding.getRoot()).navigate(direction);
    }

    private void SetNbfcList(List<NbfcDetails> nbfcDetailsList) {
        NBFC_LIST = new ArrayList<>();
        nbfcNameIdMap = new HashMap<>();
        for (NbfcDetails nbfc : nbfcDetailsList) {
            if (nbfc.getIsActive().equals("N"))
                continue;
            StringBuilder sb = new StringBuilder();
            if (nbfc.getNbfcId().equals("50000002"))
                sb.append("Fexprime Finance Pvt. Ltd");
            else {
                sb.append(nbfc.getFirstName());
                sb.append(" ");
                sb.append(nbfc.getLastName());
            }
            nbfcNameIdMap.put(sb.toString().trim(), nbfc.getNbfcId());
            NBFC_LIST.add(sb.toString().trim());
        }

        setVisbleComp();
        ArrayAdapter<String> nbfcAdapter = getArrayAdapter(NBFC_LIST);
        binding.dropNbfcList.setAdapter(nbfcAdapter);
    }

    private void setVisbleComp() {
        binding.selectNBFC.setVisibility(View.VISIBLE);
        binding.txtSelectNbfc.setVisibility(View.VISIBLE);
        binding.buttonSubmit.setVisibility(View.VISIBLE);
    }

    private void VerifyDealer(String dealer_id) {
        progressDialog = UIUtils.showProgressDialog(requireActivity());
        progressDialog.setCancelable(false);
        DocumentManager.getDealerDetail(DEALER_VERIFICATION_CODE, dealer_id,  this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == DEALER_VERIFICATION_CODE) {
            dealerDetails = (GetDealerDetails) response.getResponseData();
            binding.dealerId.setEnabled(false);
            binding.buttonVerifyDealer.setEnabled(false);
            binding.buttonVerifyDealer.setBackgroundColor(Color.GRAY);
            isDealer = true;
            getNbfcDetails();
        }
        if (requestCode == NBFC_VERIFICATION_CODE) {
            List<NbfcDetails> nbfcDetails = (List<NbfcDetails>) response.getResponseData();
            SetNbfcList(nbfcDetails);
            isNBFC = true;
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        Toast.makeText(binding.getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        Toast.makeText(binding.getRoot().getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void commonCall(int requestCode) {
        switch (requestCode) {
            case DEALER_VERIFICATION_CODE:
            case NBFC_VERIFICATION_CODE:
                UIUtils.dismissDialog(progressDialog);
                break;
        }
    }

    private void getNbfcDetails() {
        DocumentManager.getNbfcDetails(NBFC_VERIFICATION_CODE,  this);
    }
}