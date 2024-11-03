package com.dkglabs.dealer_collection.tableview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.tableview.model.TableViewCollectionModel;
import com.dkglabs.dealer_collection.tableview.holder.CellViewHolder;
import com.dkglabs.dealer_collection.tableview.holder.ColumnHeaderViewHolder;
import com.dkglabs.dealer_collection.tableview.holder.PayButtonViewHolder;
import com.dkglabs.dealer_collection.tableview.holder.RowHeaderViewHolder;
import com.dkglabs.dealer_collection.tableview.model.CellModel;
import com.dkglabs.dealer_collection.tableview.model.ColumnHeaderModel;
import com.dkglabs.dealer_collection.tableview.model.RowHeaderModel;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import java.util.List;


public class TableViewCollectionAdapter extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel, CellModel> {

    // Add new one if it necessary..

    private static final String LOG_TAG = TableViewCollectionAdapter.class.getSimpleName();

    @NonNull
    private final TableViewCollectionModel mTableViewModel;

    public TableViewCollectionAdapter(TableViewCollectionModel tableViewModel) {
        super();
        this.mTableViewModel = tableViewModel;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View layout;

        if (viewType == TableViewCollectionModel.PAY_BUTTON_CELL_TYPE) {// Get button cell layout which has Button on the base instead of TextView.
            layout = inflater.inflate(R.layout.table_view_button_cell_layout, parent, false);

            return new PayButtonViewHolder(layout);
        }// For cells that display a text
        layout = inflater.inflate(R.layout.table_view_cell_layout, parent, false);

        // Create a CellModel ViewHolder
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable CellModel cellModelItemModel, int
            columnPosition, int rowPosition) {

        if (holder.getItemViewType() == TableViewCollectionModel.PAY_BUTTON_CELL_TYPE) {
            PayButtonViewHolder payButtonViewHolder = (PayButtonViewHolder) holder;
            payButtonViewHolder.setData(cellModelItemModel, mTableViewModel.getClickListener());
        } else {// Get the holder to update cell item text
            CellViewHolder viewHolder = (CellViewHolder) holder;
            viewHolder.setCell(cellModelItemModel);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeaderModel ViewHolder
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeaderModel
            columnHeaderItemModel, int columnPosition) {

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeaderItemModel);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get Row Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeaderModel rowHeaderModelItemModel,
                                          int rowPosition) {

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.setCell(rowHeaderModelItemModel);
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        // Get Corner xml layout
        View corner = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
        return corner;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        // The unique ID for this type of column header item
        // If you have different items for CellModel View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return mTableViewModel.getCellItemViewType(position);
    }

    public void setCollectionUserEmiList(List<LoanApplicantResponse> list) {
        mTableViewModel.generateListForTableView(list);
        setAllItems(mTableViewModel.getColumHeaderModeList(), mTableViewModel.getRowHeaderModelList(), mTableViewModel.getCellModelList());
    }
}