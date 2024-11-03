package com.dkglabs.dealer_collection.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;
import com.dkglabs.data.interfaces.DatabaseListener;
import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.activities.CollectionActivity;
import com.dkglabs.dealer_collection.activities.DealerEmiPayActivity;
import com.dkglabs.dealer_collection.databinding.FragmentTableViewBinding;
import com.dkglabs.dealer_collection.model.CollectionFilterModel;
import com.dkglabs.dealer_collection.model.EmiPayModel;
import com.dkglabs.dealer_collection.tableview.TableViewListener;
import com.dkglabs.dealer_collection.tableview.adapters.TableViewCollectionAdapter;
import com.dkglabs.dealer_collection.tableview.model.CellModel;
import com.dkglabs.dealer_collection.tableview.model.TableViewCollectionModel;
import com.dkglabs.dealer_collection.view_model.CollectionViewModel;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.evrencoskun.tableview.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends BaseFragment implements View.OnClickListener, DatabaseListener.TaskCompleteListener  {
    private Filter mTableFilter;
    private TableViewCollectionAdapter tableViewCollectionAdapter;
    private List<LoanApplicantResponse> loanApplicantsList;
    private List<LoanApplicant> loanApplicant;
    private List<LoanApplicantEMI> loanApplicantEmi;
    private List<LoanApplicantResponse> userEmiResponseList;
    private CollectionViewModel mCollectionViewModel;
    private CollectionFilterModel filterModel;
    private MutableLiveData<CollectionFilterModel> filterData;
    private FragmentTableViewBinding binding;

    public CollectionFragment() {
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
        filterData.observe(getViewLifecycleOwner(), new Observer<CollectionFilterModel>() {
            @Override
            public void onChanged(CollectionFilterModel collectionFilterModel) {
                filterTable(collectionFilterModel);
            }
        });
    }

    private void initializeView() {

        filterModel = new CollectionFilterModel("None");
        binding.layoutSearchFilter.filterButton.setOnClickListener(this);

        loanApplicantsList = new ArrayList<>();
        loanApplicant = new ArrayList<>();
        loanApplicantEmi = new ArrayList<>();

        // Create TableView View model class  to group view models of TableView
        TableViewCollectionModel tableViewModel = TableViewCollectionModel.newInstance(this);

        // Create TableView Adapter
        tableViewCollectionAdapter = new TableViewCollectionAdapter(tableViewModel);

        binding.tableView.setAdapter(tableViewCollectionAdapter);
        binding.tableView.setTableViewListener(new TableViewListener(binding.tableView));
        userEmiResponseList = new ArrayList<>();
        tableViewCollectionAdapter.setCollectionUserEmiList(userEmiResponseList);

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonPay) {
            CellModel cellModel = (CellModel) view.getTag();
            LoanApplicantResponse userEmiResponse = (LoanApplicantResponse) cellModel.getData();
            setEmiPayModel(userEmiResponse);
//            Toast.makeText(context,userEmiResponse.getLoanId()+  " : "+userEmiResponse.getUserId(), Toast.LENGTH_SHORT).show();
//            Navigation.findNavController(view).;
            if(userEmiResponse.getLoanAmount()==0)
                Toast.makeText(context, "Invalid Loan Amount !", Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent(requireActivity(), DealerEmiPayActivity.class);
                startActivity(intent);
            }
        } else if (id == com.dkglabs.base.R.id.filterButton) {
            CollectionFragmentDirections.ActionCollectionFragmentToCollectionFilterDialogFragment direction = CollectionFragmentDirections.actionCollectionFragmentToCollectionFilterDialogFragment(filterModel);
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }
    }
    private void setEmiPayModel(LoanApplicantResponse userEmiResponse) {
        updatePersistentManager(userEmiResponse);
        EmiPayModel.setLoanApplicantResponse(userEmiResponse);
    }

    private void updatePersistentManager(LoanApplicantResponse userEmiResponse) {
        UserResponse userResponse=new UserResponse();
        UserResponse userResponseCurrent=PersistentManager.getUserResponse();

        userResponse.setId(userResponseCurrent.getId());

        userResponse.setUserId(userEmiResponse.getUserId());

        userResponse.setFirstName(userEmiResponse.getName());
        userResponse.setLastName(userResponseCurrent.getLastName());
        userResponse.setUserType(userResponseCurrent.getUserType());
        userResponse.setEmail(userResponseCurrent.getEmail());
        userResponse.setMobileNumber(userResponseCurrent.getMobileNumber());
        userResponse.setWhatsappNumber(userResponseCurrent.getWhatsappNumber());
        userResponse.setPassword(userResponseCurrent.getPassword());
        userResponse.setUserMode(userResponseCurrent.getUserMode());
        userResponse.setCreatedBy(userResponseCurrent.getCreatedBy());
        userResponse.setCreatedDate(userResponseCurrent.getCreatedDate());
        userResponse.setTokenDto(userResponseCurrent.getTokenDto());

        userResponse.setLoanId(userEmiResponse.getLoanId());

        userResponse.setAdminFlag(userResponseCurrent.getAdminFlag());
        userResponse.setActive(userResponseCurrent.getActive());
        userResponse.setToken(userResponseCurrent.getToken());

        PersistentManager.setUserResponse(userResponse);
        LoanPersistentManager.setLoanId(userEmiResponse.getLoanId());
        PersistentManager.setDealerReq(true);
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
            tableViewCollectionAdapter.setCollectionUserEmiList(loanApplicantsList);
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

    private void filterTable(CollectionFilterModel collectionFilterModel) {
        if (!collectionFilterModel.getStatus().equals("None"))
            mTableFilter.set(5, collectionFilterModel.getStatus());
        else mTableFilter.set(5, "");
    }

}