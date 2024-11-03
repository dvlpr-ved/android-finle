package com.dkglabs.dealer_collection.tableview.model;

import android.view.View;

import androidx.annotation.NonNull;

import com.dkglabs.model.response.LoanApplicantResponse;

import java.util.ArrayList;
import java.util.List;

public class TableViewCollectionModel {

    // Pay button Columns indexes
    public static final int PAY_BUTTON_COLUMN_INDEX = 8;
    public static final int PAY_BUTTON_CELL_TYPE = 1;

    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;
    private final View.OnClickListener listener;

    public TableViewCollectionModel(View.OnClickListener listener) {
        this.listener = listener;
    }

    public static TableViewCollectionModel newInstance(View.OnClickListener listener) {
        return new TableViewCollectionModel(listener);
    }

    public int getCellItemViewType(int column) {

        if (column == PAY_BUTTON_COLUMN_INDEX) {// 8. column header is action.
            return PAY_BUTTON_CELL_TYPE;
        }
        return 0;
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
        "Amount (₹)" - Col 2
        "Due (₹)" - Col 3
        "EMI Date" - Col 4
        "Status" - Col 5
        "Last Paid (₹)" - Col 6
        "Last Paid Date (₹)" - Col 7
        "Action" - Col 8
        */

    @NonNull
    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("idName", "Name"));
        list.add(new ColumnHeaderModel("idPhone", "Phone"));
        list.add(new ColumnHeaderModel("idAmount", "Amount (₹)"));
        list.add(new ColumnHeaderModel("idDue", "Due"));
        list.add(new ColumnHeaderModel("idEmiDate", "EMI Date"));
        list.add(new ColumnHeaderModel("idStatus", "Status"));
        list.add(new ColumnHeaderModel("idLastPaid", "Last Paid (₹)"));
        list.add(new ColumnHeaderModel("idLastPaidDate", "Last Paid Date"));
        list.add(new ColumnHeaderModel("idAction", "Action"));

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<CellModel>> createCellModelList(List<LoanApplicantResponse> userList) {
        List<List<CellModel>> collectionList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            LoanApplicantResponse userEmiResponse = userList.get(i);

            List<CellModel> cellModelList = new ArrayList<>();
            cellModelList.add(new CellModel("1-" + i, userEmiResponse.getName()));
            cellModelList.add(new CellModel("2-" + i, userEmiResponse.getPhone()));
            cellModelList.add(new CellModel("3-" + i, userEmiResponse.getLoanAmount()));
            cellModelList.add(new CellModel("4-" + i, userEmiResponse.getDueEmiAmount()));
            cellModelList.add(new CellModel("5-" + i, userEmiResponse.getLoanEmiPaymentDate()));
            cellModelList.add(new CellModel("6-" + i, userEmiResponse.getStatus()));
            cellModelList.add(new CellModel("7-" + i, userEmiResponse.getLastPaidAmount()));
            cellModelList.add(new CellModel("8-" + i, userEmiResponse.getLastPaidDate()));
            cellModelList.add(new CellModel("9-" + i, userEmiResponse));

            collectionList.add(cellModelList);
        }

        return collectionList;
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

    public void generateListForTableView(List<LoanApplicantResponse> users) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(users);
        mRowHeaderModelList = createRowHeaderList(users);
    }

    public View.OnClickListener getClickListener() {
        return listener;
    }
}
