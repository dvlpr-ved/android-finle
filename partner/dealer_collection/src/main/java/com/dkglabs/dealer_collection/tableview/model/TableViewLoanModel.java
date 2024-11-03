package com.dkglabs.dealer_collection.tableview.model;

import androidx.annotation.NonNull;

import com.dkglabs.base.utils.AppConstants;
import com.dkglabs.base.utils.DateUtils;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;

import java.util.ArrayList;
import java.util.List;

public class TableViewLoanModel {
    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    public static TableViewLoanModel newInstance() {
        return new TableViewLoanModel();
    }

    public TableViewLoanModel() {

    }


    @NonNull
    private List<RowHeaderModel> createRowHeaderList(List<LoanApplicantResponse> userList) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            RowHeaderModel header = new RowHeaderModel("idLoan" + i, userList.get(i).getLoanId());
            list.add(header);
        }

        return list;
    }


        /*
        "Loan ID" - Row head
        "Name" - Col 0
        "Phone" - Col 1
        "Partner code" - Col 2
        "Amount (₹)" - Col 3
        "Terms" - Col 4
        "EMI (₹)" - Col 5
        "First EMI Date" - Col 6
        "Due EMI (₹)" - Col 7
        "Late Charges Due (₹)" - Col 8
        "Extra Paid (₹)" - Col 9
        "Status (Open/Close)" - Col 10
        "Sub Status (Active/Inactive)" - Col 11
        "Last 6 month1" - Col 12
        "Last 6 month2" - Col 13
        "Last 6 month3" - Col 14
        "Last 6 month4" - Col 15
        "Last 6 month5" - Col 16
        "Last 6 month6" - Col 17
        */

    @NonNull
    private List<ColumnHeaderModel> createColumnHeaderModelList(String[] columnList) {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("idName", columnList[0]));
        list.add(new ColumnHeaderModel("idPhone", columnList[1]));
        list.add(new ColumnHeaderModel("idPartner", columnList[2]));
        list.add(new ColumnHeaderModel("idAmount", columnList[3]));
        list.add(new ColumnHeaderModel("idTerms", columnList[4]));
        list.add(new ColumnHeaderModel("idEmi", columnList[5]));
        list.add(new ColumnHeaderModel("idFirstEmiDate", columnList[6]));
        list.add(new ColumnHeaderModel("idDueEmi", columnList[7]));
        list.add(new ColumnHeaderModel("idLateCharges", columnList[8]));
        list.add(new ColumnHeaderModel("idExtraPaid", columnList[9]));
        list.add(new ColumnHeaderModel("idStatus", columnList[10]));
        list.add(new ColumnHeaderModel("idSubStatus", columnList[11]));
        list.add(new ColumnHeaderModel("idMonth1", columnList[12]));
        list.add(new ColumnHeaderModel("idMonth2", columnList[13]));
        list.add(new ColumnHeaderModel("idMonth3", columnList[14]));
        list.add(new ColumnHeaderModel("idMonth4", columnList[15]));
        list.add(new ColumnHeaderModel("idMonth5", columnList[16]));
        list.add(new ColumnHeaderModel("idMonth6", columnList[17]));

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<CellModel>> createCellModelList(List<LoanApplicantResponse> userList, String[] columnList) {
        List<List<CellModel>> collectionList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            LoanApplicantResponse loanResponse = userList.get(i);

            List<CellModel> cellModelList = new ArrayList<>();
            cellModelList.add(new CellModel("1-" + i, loanResponse.getName()));
            cellModelList.add(new CellModel("2-" + i, loanResponse.getPhone()));
            cellModelList.add(new CellModel("3-" + i, loanResponse.getPartnerCode()));
            cellModelList.add(new CellModel("4-" + i, loanResponse.getLoanAmount()));
            cellModelList.add(new CellModel("5-" + i, loanResponse.getTerm()));
            cellModelList.add(new CellModel("6-" + i, loanResponse.getLoanEmiAmount()));
            cellModelList.add(new CellModel("7-" + i, DateUtils.getNewDate(AppConstants.SERVER_FORMAT, loanResponse.getLoanEmiPaymentDate(), AppConstants.DOB_FORMAT_PROFILE)));
            cellModelList.add(new CellModel("8-" + i, loanResponse.getDueEmiAmount()));
            cellModelList.add(new CellModel("8-" + i, loanResponse.getLateFeeDue()));
            cellModelList.add(new CellModel("9-" + i, loanResponse.getExtraPaid()));
            cellModelList.add(new CellModel("10-" + i, loanResponse.getStatus()));
            cellModelList.add(new CellModel("11-" + i, loanResponse.getSubStatus()));

            List<LoanEmiDetailResponse> list = loanResponse.getEmiDetails();
            if(list != null & list.size() > 0){
                cellModelList.add(new CellModel("12-" + i, setLoanMonthData(list, columnList[12])));
                cellModelList.add(new CellModel("13-" + i, setLoanMonthData(list, columnList[13])));
                cellModelList.add(new CellModel("14-" + i, setLoanMonthData(list, columnList[14])));
                cellModelList.add(new CellModel("15-" + i, setLoanMonthData(list, columnList[15])));
                cellModelList.add(new CellModel("16-" + i, setLoanMonthData(list, columnList[16])));
                cellModelList.add(new CellModel("17-" + i, setLoanMonthData(list, columnList[17])));
            } else {
                cellModelList.add(new CellModel("12-" + i, "₹ 0"));
                cellModelList.add(new CellModel("13-" + i, "₹ 0"));
                cellModelList.add(new CellModel("14-" + i, "₹ 0"));
                cellModelList.add(new CellModel("15-" + i, "₹ 0"));
                cellModelList.add(new CellModel("16-" + i, "₹ 0"));
                cellModelList.add(new CellModel("17-" + i, "₹ 0"));
            }

            collectionList.add(cellModelList);
        }

        return collectionList;
    }

    private String setLoanMonthData(List<LoanEmiDetailResponse> list, String month) {
        int amount = 0;
        if (list != null && list.size() > 0)
            for (LoanEmiDetailResponse loanEmiDetailResponse : list) {
                if (loanEmiDetailResponse.getLoanEmiStatus().equals("PAID") && DateUtils.matchMonthAndYear(loanEmiDetailResponse.getUpdatedDate(), AppConstants.SERVER_FORMAT, month, AppConstants.EMI_MONTH_FORMAT)) {
                    amount += loanEmiDetailResponse.getLoanEmiAmount();
                }
            }

        return "₹ " + amount;
    }

    @NonNull
    public List<List<CellModel>> getCellModelList() {
        return mCellModelList;
    }

    @NonNull
    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    @NonNull
    public List<ColumnHeaderModel> getColumHeaderModeList() {
        return mColumnHeaderModelList;
    }

    public void generateListForTableView(List<LoanApplicantResponse> users, String[] columnList) {
        mColumnHeaderModelList = createColumnHeaderModelList(columnList);
        mCellModelList = createCellModelList(users, columnList);
        mRowHeaderModelList = createRowHeaderList(users);
    }
}
