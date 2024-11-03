package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentUserEvScoreBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.UserEvScoreParams;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.EvScoreManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserEvScoreFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private List<String> kidList;
    private List<String> memberList;
    private List<String> houseLocationList;
    private List<String> areaList;
    private List<String> hobbyList;
    private FragmentUserEvScoreBinding binding = null;

    public UserEvScoreFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserEvScoreBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(7);
        viewModel.setSelectedItem(applyLoanModel);
    }

    private void initializeView() {
        memberList = new ArrayList<>();
        memberList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_member)));
        ArrayAdapter<String> memberAdapter = getArrayAdapter(memberList);
        binding.noOfFamilyMember.setAdapter(memberAdapter);

        kidList = new ArrayList<>();
        kidList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_kids)));
        ArrayAdapter<String> kidAdapter = getArrayAdapter(kidList);
        binding.noOfKids.setAdapter(kidAdapter);

        ArrayAdapter<String> collegeKidAdapter = getArrayAdapter(kidList);
        binding.noOfKidsInCollege.setAdapter(collegeKidAdapter);

        ArrayAdapter<String> girlKidAdapter = getArrayAdapter(kidList);
        binding.noOfGirl.setAdapter(girlKidAdapter);

        houseLocationList = new ArrayList<>();
        houseLocationList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_location)));
        ArrayAdapter<String> locationAdapter = getArrayAdapter(houseLocationList);
        binding.houseLocation.setAdapter(locationAdapter);

        areaList = new ArrayList<>();
        areaList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_house_area)));
        ArrayAdapter<String> areaAdapter = getArrayAdapter(areaList);
        binding.houseArea.setAdapter(areaAdapter);

        hobbyList = new ArrayList<>();
        hobbyList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_hobbies)));
        ArrayAdapter<String> hobbyAdapter = getArrayAdapter(hobbyList);
        binding.hobbies.setAdapter(hobbyAdapter);

        binding.radioCriminalOffence.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = binding.getRoot().findViewById(checkedId);
                switch (button.getTag().toString()) {
                    case "yes":
                        for (int i = 0; i < binding.radioSeverity.getChildCount(); i++)
                            binding.radioSeverity.getChildAt(i).setEnabled(true);

                        binding.txtCrime.setVisibility(View.VISIBLE);
                        binding.radioSeverity.setVisibility(View.VISIBLE);
                        break;
                    case "no":
                        for (int i = 0; i < binding.radioSeverity.getChildCount(); i++)
                            binding.radioSeverity.getChildAt(i).setEnabled(false);
                        binding.radioSeverity.clearCheck();
                        break;
                }
            }
        });

        binding.buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonNext) {
            saveEvScoreParam();
        }
    }

    private void saveEvScoreParam() {
        UserEvScoreParams evScoreParams = new UserEvScoreParams();

        int radioResidenceAreaId = -1;
        String areaOfResidence = "";
//        radioResidenceAreaId = binding.radioResidenceArea.getCheckedRadioButtonId();
//        if (radioResidenceAreaId != -1) {
//            RadioButton radioResidenceAreaButton = binding.getRoot().findViewById(radioResidenceAreaId);
//            areaOfResidence = radioResidenceAreaButton.getTag().toString();
//        }

        int healthAndLifeInsuranceId = -1;
        String healthAndLifeInsurance = "";
        healthAndLifeInsuranceId = binding.radioInsurance.getCheckedRadioButtonId();
        if (healthAndLifeInsuranceId != -1) {
            RadioButton healthAndLifeInsuranceButton = binding.getRoot().findViewById(healthAndLifeInsuranceId);
            healthAndLifeInsurance = healthAndLifeInsuranceButton.getTag().toString();
        }

        int healthIssueId = -1;
        String healthIssue = "";
        healthIssueId = binding.radioHealthIssue.getCheckedRadioButtonId();
        if (healthIssueId != -1) {
            RadioButton healthIssueButton = binding.getRoot().findViewById(healthIssueId);
            healthIssue = healthIssueButton.getTag().toString();
        }

        int houseTypeId = -1;
        String houseType = "";
        houseTypeId = binding.radioHouseType.getCheckedRadioButtonId();
        if (houseTypeId != -1) {
            RadioButton houseTypeButton = binding.getRoot().findViewById(houseTypeId);
            houseType = houseTypeButton.getTag().toString();
        }

        int parkingSpaceId = -1;
        String parkingSpace = "";
        parkingSpaceId = binding.radioParkingSpace.getCheckedRadioButtonId();
        if (parkingSpaceId != -1) {
            RadioButton parkingSpaceButton = binding.getRoot().findViewById(parkingSpaceId);
            parkingSpace = parkingSpaceButton.getTag().toString();
        }

        int politicalConnectionId = -1;
        String politicalConnection = "";
        politicalConnectionId = binding.radioPoliticalConnection.getCheckedRadioButtonId();
        if (politicalConnectionId != -1) {
            RadioButton politicalConnectionButton = binding.getRoot().findViewById(politicalConnectionId);
            politicalConnection = politicalConnectionButton.getTag().toString();
        }

        int criminalOffenceId = -1;
        String criminalOffence = "";
        criminalOffenceId = binding.radioCriminalOffence.getCheckedRadioButtonId();
        if (criminalOffenceId != -1) {
            RadioButton criminalOffenceButton = binding.getRoot().findViewById(criminalOffenceId);
            criminalOffence = criminalOffenceButton.getTag().toString();
        }

        int severityOfCriminalOffenceId = -1;
        String severityOfCriminalOffence = "";
        severityOfCriminalOffenceId = binding.radioSeverity.getCheckedRadioButtonId();
        if (criminalOffence.equals("yes")) {
            if (severityOfCriminalOffenceId != -1) {
                RadioButton severityOfCriminalOffenceButton = binding.getRoot().findViewById(severityOfCriminalOffenceId);
                severityOfCriminalOffence = severityOfCriminalOffenceButton.getTag().toString();
            } else {
                binding.radioSeverity.requestFocus();
                UIUtils.showToast(context, "Select the severity of the criminal offense");
                return;
            }
        }

        int teetotalerId = -1;
        String teetotaler = "";
        teetotalerId = binding.radioTeetotaler.getCheckedRadioButtonId();
        if (teetotalerId != -1) {
            RadioButton teetotalerButton = binding.getRoot().findViewById(teetotalerId);
            teetotaler = teetotalerButton.getTag().toString();
        }

        evScoreParams.setUserId(PersistentManager.getUserResponse().getUserId());
        evScoreParams.setLoanId(LoanPersistentManager.getLoanId());
        evScoreParams.setAreaOfHouseInSqFt(binding.houseArea.getText().toString().trim());
        evScoreParams.setAreaOfResidence(areaOfResidence);
        evScoreParams.setCriminalOffence(criminalOffence);
        evScoreParams.setGirlChild(binding.noOfGirl.getText().toString().trim());
        if(healthAndLifeInsurance.equals("health insurance"))healthAndLifeInsurance="only one";
        evScoreParams.setHealthAndLifeInsurance(healthAndLifeInsurance);
        evScoreParams.setHealthIssue(healthIssue);
        evScoreParams.setHobbies(binding.hobbies.getText().toString().trim());
        evScoreParams.setHouseType(houseType);
        evScoreParams.setLocationOfHouse(binding.houseLocation.getText().toString().trim());
        evScoreParams.setNumberOfFamilyMembers(binding.noOfFamilyMember.getText().toString().trim());
        evScoreParams.setNumberOfKids(binding.noOfKids.getText().toString().trim());
        evScoreParams.setNumberOfKidsInSchool(binding.noOfKidsInCollege.getText().toString().trim());
        evScoreParams.setParkingSpace(parkingSpace);
        evScoreParams.setPoliticalConnection(politicalConnection);
        evScoreParams.setSeverityOfCriminalOffence(severityOfCriminalOffence);
        evScoreParams.setTeetotaler(teetotaler);

        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.saving_details));
        EvScoreManager.saveEVScoreParams(1001, evScoreParams,  this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userEvScoreFragment_to_loanEvScoreFragment);
    }


    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
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