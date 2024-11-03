package com.dkglabs.dashboard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.AppConstants;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.dashboard.utils.EmiConst;
import com.dkglabs.dashboard.R;
import com.dkglabs.dashboard.databinding.FragmentEmiDetailsBinding;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class EmiDetailsFragment extends BaseFragment implements View.OnClickListener {

    private DueEmiFragment dueEmiFragment;
    private PaidEmiFragment paidEmiFragment;
    private LoanDataResponse loanDataResponse;
    private List<LoanEmiDetailResponse> dueEmiList;
    private FragmentEmiDetailsBinding binding = null;

    public EmiDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmiDetailsBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loanDataResponse = (LoanDataResponse) getArguments().getSerializable("loanDataResponse");

        FragmentTransaction transactionDue = getChildFragmentManager().beginTransaction();
        dueEmiFragment = DueEmiFragment.newInstance(null);
        transactionDue.replace(R.id.due_emi_fragment_container, dueEmiFragment).commit();

        FragmentTransaction transactionPaid = getChildFragmentManager().beginTransaction();
        paidEmiFragment = PaidEmiFragment.newInstance(null);
        transactionPaid.replace(R.id.paid_emi_fragment_container, paidEmiFragment).commit();

        //updateUi();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUi();
    }

    private void initializeView() {
        setUpBackToolbar("EMI Details");
        dueEmiList = new ArrayList<>();
    }

    @SuppressLint("SetTextI18n")
    private void updateUi() {
//        binding.monthlyEmiAmount.setText(String.format(getString(R.string.placeholder_rupee), loanDataResponse.getLoanEmiDetailsList().get(0).getLoanEmiAmount() ));
        if(loanDataResponse.getLoanEmiPaymentMode().equals(EmiConst.PAYMENT_MODE))
            binding.payEmi.setVisibility(View.GONE);

        // Get the context and use getString to format the string resource with dynamic data
        String frequencyText = getString(R.string.frequency) + " " + loanDataResponse.getLoanEmiFrequency();
        String paymentModeText = getString(R.string.payment_mode) + " " + loanDataResponse.getLoanEmiPaymentMode();

        // Set the text to the TextView
        binding.txtFreq.setText(frequencyText);
        binding.txtMode.setText(paymentModeText);


        binding.monthlyEmiAmount.setText("₹ "+loanDataResponse.getLoanEmiDetailsList().get(0).getLoanEmiAmount());
        binding.totalEmi.setText("" + loanDataResponse.getLoanEmiDetailsList().size());
//        Toast.makeText(context, "Mon EMI : " + loanDataResponse.getLoanEmiDetailsList().get(0).getLoanEmiAmount(), Toast.LENGTH_SHORT).show();

        int pendngEmi = 0;
        int paidEmi = 0;
        List<LoanEmiDetailResponse> paidEmiList = new ArrayList<>();
        for (LoanEmiDetailResponse emiDetailResponse : loanDataResponse.getLoanEmiDetailsList()) {
            if (emiDetailResponse.getLoanEmiStatus().equals(EmiConst.EMI_PAID)) {
                paidEmiList.add(emiDetailResponse);
                ++paidEmi;
            } else if (emiDetailResponse.getLoanEmiStatus().equals(EmiConst.EMI_PENDING)) {
                ++pendngEmi;
//                if (AppUtils.isEmiDueDatePass(AppConstants.SERVER_FORMAT, emiDetailResponse.getLoanEmiPaymentDate()))
                dueEmiList.add(emiDetailResponse);
            }
        }
        binding.emiPaid.setText("" + paidEmi);
        binding.pendngEmi.setText("" + pendngEmi);

        paidEmiFragment.setLoanEmiList(AppUtils.reverseList(paidEmiList));
        dueEmiFragment.setLoanEmiList(dueEmiList);

        binding.payEmi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.payEmi) {
            LoanDataResponse dueEmiLoanData = loanDataResponse;
            if(!dueEmiList.isEmpty()) {
                dueEmiLoanData.setLoanEmiDetailsList(dueEmiList);
                EmiDetailsFragmentDirections.ActionEmiDetailsFragmentToPayEmiFragment direction = EmiDetailsFragmentDirections.actionEmiDetailsFragmentToPayEmiFragment(dueEmiLoanData);
                Navigation.findNavController(binding.getRoot()).navigate(direction);
            }else{
                Toast.makeText(context, "No Active EMI", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
