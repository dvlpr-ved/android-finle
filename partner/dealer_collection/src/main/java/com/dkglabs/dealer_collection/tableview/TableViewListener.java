package com.dkglabs.dealer_collection.tableview;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.dkglabs.dealer_collection.tableview.holder.ColumnHeaderViewHolder;
import com.dkglabs.dealer_collection.tableview.popup.ColumnHeaderLongPressPopup;
import com.dkglabs.dealer_collection.tableview.popup.RowHeaderLongPressPopup;

/**
 * Created by evrencoskun on 21/09/2017.
 */

public class TableViewListener implements ITableViewListener {
    @NonNull
    private final Context mContext;
    @NonNull
    private final TableView mTableView;

    public TableViewListener(@NonNull TableView tableView) {
        this.mContext = tableView.getContext();
        this.mTableView = tableView;
    }


    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {


    }

    @Override
    public void onCellDoubleClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, final int column,
                                  int row) {

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {

    }

    @Override
    public void onColumnHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {

        if (columnHeaderView instanceof ColumnHeaderViewHolder) {
            // Create Long Press Popup
            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup(
                    (ColumnHeaderViewHolder) columnHeaderView, mTableView);
            // Show
            popup.show();
        }
    }


    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }

    @Override
    public void onRowHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

        // Create Long Press Popup
        RowHeaderLongPressPopup popup = new RowHeaderLongPressPopup(rowHeaderView, mTableView);
        // Show
        popup.show();
    }

}
