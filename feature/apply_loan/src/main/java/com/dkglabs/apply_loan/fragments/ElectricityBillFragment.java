package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentElectricityBillBinding;
import com.dkglabs.apply_loan.model.ElectricityProvider;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.DocumentDetails;
import com.dkglabs.model.applyloan.ElectricityBillDetails;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.ElectricityBillResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

import java.util.ArrayList;

public class ElectricityBillFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private FragmentElectricityBillBinding binding = null;
    private String electricityProvider = "";

    public ElectricityBillFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentElectricityBillBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(3);
        viewModel.setSelectedItem(applyLoanModel);
    }

    private void initializeView() {
        binding.buttonVerify.setOnClickListener(this);
        binding.buttonSkip.setOnClickListener(this);

        ArrayList<ElectricityProvider> electricityProviders = new ArrayList<>();
        electricityProviders.add(new ElectricityProvider("APEPDCL", "ANDHRA PRADESH - EASTERN POWER DISTRIBUTION COMPANY OF ANDHRA PRADESH LIMITED (APEPDCL)"));
        electricityProviders.add(new ElectricityProvider("APSPDCL", "ANDHRA PRADESH - SOUTHERN POWER DISTRIBUTION COMPANY OF ANDHRA PRADESH LIMITED (APSPDCL)"));
        electricityProviders.add(new ElectricityProvider("APDCL", "ASSAM - ASSAM POWER DISTRIBUTION COMPANY LIMITED (APDCL)"));
        electricityProviders.add(new ElectricityProvider("NBPDCL", "BIHAR - NORTH BIHAR POWER DISTRIBUTION COMPANY LIMITED (NBPDCL)"));
        electricityProviders.add(new ElectricityProvider("SBPDCL", "BIHAR - SOUTH BIHAR POWER DISTRIBUTION COMPANY LIMITED (SBPDCL)"));
        electricityProviders.add(new ElectricityProvider("CH_ELEC", "CHANDIGARH - CHANDIGARH STATE POWER DISTRIBUTION COMPANY LTD (CH_ELEC)"));
        electricityProviders.add(new ElectricityProvider("CSPDCL", "CHHATTISGARH - CHHATTISGARH STATE POWER DISTRIBUTION COMPANY LIMITED (CSPDCL)"));
        electricityProviders.add(new ElectricityProvider("DNH", "DADRA & NAGAR HAVELI - DNH POWER DISTRIBUTION CORPORATION LTD (DNH)"));
        electricityProviders.add(new ElectricityProvider("BSES_DL", "DELHI - BSES YAMUNA POWER LTD / BSES RAJDHANI POWER LTD (BSES_DL)"));
        electricityProviders.add(new ElectricityProvider("MUNCIPAL_DL", "DELHI - DELHI MUNCIPAL COUNCIL (MUNCIPAL_DL)"));
        electricityProviders.add(new ElectricityProvider("TATA_DL", "DELHI - NORTH - TATA POWER DELHI DISTRIBUTION LIMITED (TATA_DL)"));
        electricityProviders.add(new ElectricityProvider("UPAY_GOA", "GOA - GOA ELECTRICITY- FOR OTHERS (UPAY_GOA)"));
        electricityProviders.add(new ElectricityProvider("GOA_ELEC", "GOA - GOA ELECTRICITY- FOR TISVADI, PONDA & VERNA (GOA_ELEC)"));
        electricityProviders.add(new ElectricityProvider("MGVCL", "GUJARAT - MADHYA GUJARAT VIJ COMPANY LIMITED (MGVCL)"));
        electricityProviders.add(new ElectricityProvider("PGVCL", "GUJARAT - PASHCHIM GUJARAT VIJ COMPANY LTD. (PGVCL)"));
        electricityProviders.add(new ElectricityProvider("UGVCL", "GUJARAT - UTTAR GUJARAT VIJ COMPANY LTD. (UGVCL)"));
        electricityProviders.add(new ElectricityProvider("DGVCL", "GUJARAT - DAKSHIN GUJARAT VIJ COMPANY LIMITED (DGVCL)"));
        electricityProviders.add(new ElectricityProvider("DHBVN", "HARYANA - DAKSHIN HARYANA BIJLI VITRAN NIGAM (DHBVN)"));
        electricityProviders.add(new ElectricityProvider("UHBVN", "HARYANA - UTTAR HARYANA BIJLI VITRAN NIGAM (UHBVN)"));
        electricityProviders.add(new ElectricityProvider("HESCOM", "KARNATAKA - HUBLI ELECTRICITY SUPPLY COMPANY LIMITED (HESCOM)"));
        electricityProviders.add(new ElectricityProvider("BESCOM_NON_RAPDRP", "KARNATAKA - BANGALORE ELECTRICITY SUPPLY COMPANY LTD (For NON-RAPDRP TOWNS) (BESCOM_NON_RAPDRP)"));
        electricityProviders.add(new ElectricityProvider("GESCOM", "KARNATAKA - GULBARGA ELECTRICITY SUPPLY COMPANY LIMITED (GESCOM)"));
        electricityProviders.add(new ElectricityProvider("MESCOM", "KARNATAKA - MANGALORE ELECTRICITY SUPPLY COMPANY LIMITED (MESCOM)"));
        electricityProviders.add(new ElectricityProvider("CESCOM", "KARNATAKA - CHAMUNDESHWARI ELECTRICITY SUPPLY COMPANY LIMITED MYSORE (CESCOM)"));
        electricityProviders.add(new ElectricityProvider("BESCOM", "KARNATAKA - BANGALORE ELECTRICITY SUPPLY COMPANY LTD (BESCOM)"));
        electricityProviders.add(new ElectricityProvider("KSEB", "KERALA - KERALA STATE ELECTRICITY BOARD (KSEB)"));
        electricityProviders.add(new ElectricityProvider("MPCZ", "MADHYA PRADESH - MADHYA PRADESH MADHYA KSHETRA VIDYUT VITRAN COMPANY LIMITED (MPCZ)"));
        electricityProviders.add(new ElectricityProvider("MPEZ", "MADHYA PRADESH - MADHYA PRADESH POORV KSHETRA VIDYUT VITRAN COMPANY LIMITED (MPEZ)"));
        electricityProviders.add(new ElectricityProvider("MPWZ", "MADHYA PRADESH - MADHYA PRADESH PASCHIM KSHETRA VIDYUT VITRAN COMPANY LIMITED (MPWZ)"));
        electricityProviders.add(new ElectricityProvider("RELIANCE_MH", "MAHARASHTRA - RELIANCE ENERGY - MUMBAI (RELIANCE_MH)"));
        electricityProviders.add(new ElectricityProvider("MAHAVITRAN", "MAHARASHTRA - MAHAVITARAN-MAHARASHTRA STATE ELECTRICITY DISTRIBUTION CO. LTD (MSEDCL) (MAHAVITRAN)"));
        electricityProviders.add(new ElectricityProvider("TATA_MUMBAI", "MAHARASHTRA - TATA-POWER - MUMBAI (TATA_MUMBAI)"));
        electricityProviders.add(new ElectricityProvider("NAGALAND", "NAGALAND - NAGALAND DEPARTMENT OF POWER (NAGALAND)"));
        electricityProviders.add(new ElectricityProvider("ORISSA_CENTRAL", "ORISSA - CENTRAL ELECTRICITY SUPPLY UTILITY OF ORISSA LTD (ORISSA_CENTRAL)"));
        electricityProviders.add(new ElectricityProvider("ORISSA", "ORISSA - SUPPLY COMPANY OF ORISSA LIMITED (NORTH, SOUTH & WEST) (ORISSA)"));
        electricityProviders.add(new ElectricityProvider("PSPCL", "PUNJAB - PUNJAB STATE POWER CORPORATION LIMITED (PSPCL)"));
        electricityProviders.add(new ElectricityProvider("SIKKIM", "SIKKIM - SIKKIM ENERGY AND POWER DEPT (SIKKIM)"));
        electricityProviders.add(new ElectricityProvider("TNEB", "TAMIL NADU - TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION LIMITED (TNEB)"));
        electricityProviders.add(new ElectricityProvider("TSSPDCL", "TELANGANA - THE SOUTHERN POWER DISTRIBUTION COMPANY OF TELANGANA LIMITED (TSSPDCL)"));
        electricityProviders.add(new ElectricityProvider("UPCL", "UTTARAKHAND - UTTARAKHAND POWER CORPORATION LTD (UPCL)"));
        electricityProviders.add(new ElectricityProvider("CESC", "WEST BENGAL - CALCUTTA ELECTRIC SUPPLY CORPORATION (CESC)"));
        electricityProviders.add(new ElectricityProvider("WBSEDCL", "WEST BENGAL - WEST BENGAL STATE ELECTRICITY DISTRIBUTION COMPANY LTD (WBSEDCL)"));

        ArrayAdapter<ElectricityProvider> providerArrayAdapter = new ArrayAdapter<>(context, R.layout.layout_electricity_provider, electricityProviders);
        binding.spinnerElectricityProvider.setAdapter(providerArrayAdapter);

        binding.spinnerElectricityProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ElectricityProvider provider = (ElectricityProvider) adapterView.getItemAtPosition(position);
                electricityProvider = provider.getProviderCode();

                if (electricityProvider.equals("WBSEDCL")) {
                    binding.installationNumber.setEnabled(true);
                } else {
                    binding.installationNumber.setEnabled(false);
                }

                if (electricityProvider.equals("KSEB")) {
                    binding.mobileNo.setEnabled(true);
                } else {
                    binding.mobileNo.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonVerify) {
            validateData();
        } else if (view.getId() == R.id.buttonSkip) {
            Navigation.findNavController(binding.getRoot()).navigate(ElectricityBillFragmentDirections.actionElectricityBillFragmentToLoanUserDocumentFragment());
        }
    }

    private void validateData() {
        UIUtils.hideKeyboard(getActivity());

        String consumerNo = binding.consumerNo.getEditText().getText().toString().trim();
        String installationNumber = binding.installationNumber.getEditText().getText().toString().trim();
        String mobileNo = binding.mobileNo.getEditText().getText().toString().trim();

        if (!AppUtils.isContainsData(consumerNo)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter your consumer number");
            binding.consumerNo.requestFocus();
            return;
        }

        if (!AppUtils.isContainsData(electricityProvider)) {
            UIUtils.showSnackbar(binding.getRoot(), "Select electricity provider");
            binding.spinnerElectricityProvider.requestFocus();
            return;
        }

        if (electricityProvider.equals("WBSEDCL") && !AppUtils.isContainsData(installationNumber)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter installation number");
            binding.installationNumber.requestFocus();
            return;
        }

        if (electricityProvider.equals("KSEB") && !AppUtils.isContainsData(mobileNo)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter installation number");
            binding.mobileNo.requestFocus();
            return;
        }

        ElectricityBillDetails electricityBillDetails = new ElectricityBillDetails();
        electricityBillDetails.setConsumerNo(consumerNo);
        electricityBillDetails.setElectricityProvider(electricityProvider);
        electricityBillDetails.setInstallationNumber(installationNumber);
        electricityBillDetails.setMobileNo(mobileNo);

        DocumentDetails documentDetails = new DocumentDetails();
        documentDetails.setElectricityBillDetailsDto(electricityBillDetails);

        LoanRequest request = new LoanRequest();
        request.setDocumentDetails(documentDetails);
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setLoanId(LoanPersistentManager.getLoanId());


        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.verifying_electric_bill));
        LoanManager.saveDocumentDetails(1001, request, this);
    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        ElectricityBillResponse electricityBillResponse = (ElectricityBillResponse) response.getResponseData();
        if (electricityBillResponse != null) {
            Navigation.findNavController(binding.getRoot()).navigate(ElectricityBillFragmentDirections.actionElectricityBillFragmentToElectricityDetailsFragment(electricityBillResponse));
        } else {
            UIUtils.showToast(context, getString(com.dkglabs.base.R.string.generic_error_msg));
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(progressDialog);
    }
}