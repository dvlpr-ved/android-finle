package com.dkglabs.dealer_collection.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.DateUtils;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;
import com.dkglabs.data.interfaces.DatabaseListener;
import com.dkglabs.dealer_collection.databinding.FragmentTableViewBinding;
import com.dkglabs.dealer_collection.model.LoanFilterModel;
import com.dkglabs.dealer_collection.tableview.TableViewListener;
import com.dkglabs.dealer_collection.tableview.adapters.TableViewLoanAdapter;
import com.dkglabs.dealer_collection.tableview.model.TableViewLoanModel;
import com.dkglabs.dealer_collection.view_model.CollectionViewModel;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.evrencoskun.tableview.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class LoanDetailFragment extends BaseFragment implements View.OnClickListener, DatabaseListener.TaskCompleteListener {
    private Filter mTableFilter;
    private String[] columnList;
    private TableViewLoanAdapter tableViewLoanAdapter;
    private List<LoanApplicantResponse> loanApplicantsList;
    private List<LoanApplicant> loanApplicant;
    private List<LoanApplicantEMI> loanApplicantEmi;
    private CollectionViewModel mCollectionViewModel;
    private List<LoanApplicantResponse> userEmiResponseList;
    private LoanFilterModel filterModel;
    private MutableLiveData<LoanFilterModel> filterData;
    private FragmentTableViewBinding binding;

    public LoanDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTableViewBinding.inflate(inflater, container, false);

        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCollectionViewModel = new ViewModelProvider(requireActivity()).get(CollectionViewModel.class);
        mCollectionViewModel.getAllApplicants().observe(getViewLifecycleOwner(), new Observer<List<LoanApplicant>>() {
            @Override
            public void onChanged(List<LoanApplicant> loanApplicantList) {
                if (loanApplicantList != null) {
                    if (loanApplicant != null && loanApplicant.size() > 0)
                        loanApplicant.clear();

                    loanApplicant.addAll(loanApplicantList);
                    updateLoanList();
                }
            }
        });

        mCollectionViewModel.getAllApplicantsEmi().observe(getViewLifecycleOwner(), new Observer<List<LoanApplicantEMI>>() {
            @Override
            public void onChanged(List<LoanApplicantEMI> loanApplicantEmiList) {
                if (loanApplicantEmiList != null) {
                    if (loanApplicantEmi != null && loanApplicantEmi.size() > 0)
                        loanApplicantEmi.clear();

                    loanApplicantEmi.addAll(loanApplicantEmiList);
                    updateLoanList();
                }
            }
        });

        filterData = Navigation.findNavController(binding.getRoot())
                .getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("filter");
        filterData.observe(getViewLifecycleOwner(), new Observer<LoanFilterModel>() {
            @Override
            public void onChanged(LoanFilterModel collectionFilterModel) {
                filterTable(collectionFilterModel);
            }
        });
    }


    private void initializeView() {

        filterModel = new LoanFilterModel("None", "None");
        binding.layoutSearchFilter.filterButton.setOnClickListener(this);

        loanApplicantsList = new ArrayList<>();
        loanApplicant = new ArrayList<>();
        loanApplicantEmi = new ArrayList<>();
        columnList = getColumnName();

        // Create TableView View model class  to group view models of TableView
        TableViewLoanModel tableViewLoanModel = TableViewLoanModel.newInstance();

        // Create TableView Adapter
        tableViewLoanAdapter = new TableViewLoanAdapter(tableViewLoanModel);

        binding.tableView.setAdapter(tableViewLoanAdapter);
        binding.tableView.setTableViewListener(new TableViewListener(binding.tableView));
        mTableFilter = new Filter(binding.tableView);

        binding.layoutSearchFilter.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchApplicant(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchApplicant(newText);
                return true;
            }
        });
    }

    private void searchApplicant(String query) {
        mCollectionViewModel.searchApplicant(query, this);
    }

    private String[] getColumnName() {
        String[] columnName = new String[18];
        columnName[0] = "Name";
        columnName[1] = "Phone";
        columnName[2] = "Partner code";
        columnName[3] = "Amount (₹)";
        columnName[4] = "Terms";
        columnName[5] = "EMI (₹)";
        columnName[6] = "First EMI Date";
        columnName[7] = "Due EMI (₹)";
        columnName[8] = "Late Charges Due (₹)";
        columnName[9] = "Extra Paid (₹)";
        columnName[10] = "Status (Open/Close)";
        columnName[11] = "Sub Status (Active/Inactive)";
        int i = 12;
        for (String monthName : DateUtils.getLastSixMonthList()) {
            columnName[i++] = monthName;
        }
        return columnName;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.layoutSearchFilter.filterButton.getId()) {
            LoanDetailFragmentDirections.ActionLoanFragmentToLoanFilterDialogFragment direction = LoanDetailFragmentDirections.actionLoanFragmentToLoanFilterDialogFragment(filterModel);
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }
    }


    private void updateLoanList() {
        if (loanApplicantsList != null && loanApplicantsList.size() > 0)
            loanApplicantsList.clear();

        if (loanApplicant != null && loanApplicant.size() > 0) {
            for (LoanApplicant applicant : loanApplicant) {
                LoanApplicantResponse loanApplicantResponse = new LoanApplicantResponse();
                loanApplicantResponse.setLoanId(applicant.getLoanId());
                loanApplicantResponse.setUserId(applicant.getUserId());
                loanApplicantResponse.setName(applicant.getName());
                loanApplicantResponse.setPhone(applicant.getPhone());
                loanApplicantResponse.setEmail(applicant.getEmail());
                loanApplicantResponse.setPartnerCode(applicant.getPartnerCode());
                loanApplicantResponse.setLoanAmount(applicant.getLoanAmount());
                loanApplicantResponse.setTerm(applicant.getTerm());
                loanApplicantResponse.setLoanEmiAmount(applicant.getLoanEmiAmount());
                loanApplicantResponse.setLoanEmiPaymentDate(applicant.getLoanEmiPaymentDate());
                loanApplicantResponse.setEmiDue(applicant.getEmiDue());
                loanApplicantResponse.setLateFeeDue(applicant.getLateFeeDue());
                loanApplicantResponse.setExtraPaid(applicant.getExtraPaid());
                loanApplicantResponse.setStatus(applicant.getStatus());
                loanApplicantResponse.setSubStatus(applicant.getSubStatus());
                loanApplicantResponse.setLastPaidAmount(applicant.getLastPaidAmount());
                loanApplicantResponse.setLastPaidDate(applicant.getLastPaidDate());
                loanApplicantResponse.setDueEmiAmount(applicant.getDueEmiAmount());
                loanApplicantResponse.setCurrentEmiPaymentDate(applicant.getCurrentEmiPaymentDate());


                List<LoanEmiDetailResponse> loanEmiDetailResponseList = new ArrayList<>();
                for (LoanApplicantEMI applicantEMI : loanApplicantEmi) {
                    LoanEmiDetailResponse loanEmiDetailResponse = new LoanEmiDetailResponse();
                    if (applicantEMI.getLoanId().equals(applicant.getLoanId())) {
                        loanEmiDetailResponse.setLoanEmiId(applicantEMI.getLoanEmiId());
                        loanEmiDetailResponse.setLoanId(applicantEMI.getLoanId());
                        loanEmiDetailResponse.setLoanMonth(applicantEMI.getLoanMonth());
                        loanEmiDetailResponse.setLoanEmiAmount(applicantEMI.getLoanEmiAmount());
                        loanEmiDetailResponse.setLoanCurrentBalance(applicantEMI.getLoanCurrentBalance());
                        loanEmiDetailResponse.setLoanEmiInterest(applicantEMI.getLoanEmiInterest());
                        loanEmiDetailResponse.setLoanEmiPrincipal(applicantEMI.getLoanEmiPrincipal());
                        loanEmiDetailResponse.setLoanEmiPaymentDate(applicantEMI.getLoanEmiPaymentDate());
                        loanEmiDetailResponse.setLoanEmiStatus(applicantEMI.getLoanEmiStatus());
                        loanEmiDetailResponse.setRemarks(applicantEMI.getRemarks());
                        loanEmiDetailResponse.setCreatedBy(applicantEMI.getCreatedBy());
                        loanEmiDetailResponse.setCreatedDate(applicantEMI.getCreatedDate());
                        loanEmiDetailResponse.setUpdatedDate(applicantEMI.getUpdatedDate());
                        loanEmiDetailResponseList.add(loanEmiDetailResponse);
                    }
                }
                loanApplicantResponse.setEmiDetails(loanEmiDetailResponseList);

                loanApplicantsList.add(loanApplicantResponse);
            }
        }
        if (loanApplicantsList != null && loanApplicantsList.size() > 0)
            tableViewLoanAdapter.setCollectionUserEmiList(loanApplicantsList, columnList);

        filterTable(filterModel);
    }

    @Override
    public void onSuccess(Object object) {
        List<LoanApplicant> loanApplicantList = (List<LoanApplicant>) object;
        if (loanApplicantList != null) {
            if (loanApplicant != null && loanApplicant.size() > 0)
                loanApplicant.clear();

            loanApplicant.addAll(loanApplicantList);
            updateLoanList();
        }
    }

    private void filterTable(LoanFilterModel collectionFilterModel) {
        if (!collectionFilterModel.getStatus().equals("None"))
            mTableFilter.set(10, collectionFilterModel.getStatus());
        else mTableFilter.set(10, "");
        if (!collectionFilterModel.getSubStatus().equals("None"))
            mTableFilter.set(11, collectionFilterModel.getStatus());
        else mTableFilter.set(11, "");

    }
}