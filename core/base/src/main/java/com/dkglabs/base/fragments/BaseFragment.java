package com.dkglabs.base.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dkglabs.base.R;
import com.dkglabs.base.interfaces.CallBackInterface;
import com.dkglabs.base.model.ItemViewModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;
import java.util.Timer;


public class BaseFragment extends Fragment {

    protected OnBackPressedCallback callback;
    protected Dialog progressDialog;
    protected Dialog alertDialog;
    protected Context context;
    protected Toolbar toolbar;
    protected ShapeableImageView toolbarProfileImage;
    protected ItemViewModel viewModel;
    protected static final int FIELD_INSPECTION_COUNT=2;
    protected static int COUNT_FIELD_INSPECTION=0;
    protected View.OnClickListener clickListener;
    private CallBackInterface callBackInterface;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    //Toolbar
    public void setUpBackToolbar(String title) {
        if (toolbar == null) {
            toolbar = ((AppCompatActivity) context).findViewById(R.id.toolbar);
        }

        if (toolbar != null) {
            ((AppCompatActivity) context).setSupportActionBar(toolbar);
            toolbar.setTitle(title);
            ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(title);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }

    public void setUpToolbar(String title) {
        if (toolbar == null) {
            toolbar = ((AppCompatActivity) context).findViewById(R.id.toolbar);
        }
        if (toolbar != null) {
            ((AppCompatActivity) context).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
            toolbar.setTitle(title);
            if (actionBar != null) {
                actionBar.setTitle(title);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }

    }

    public void showToolbar() {
        if (toolbar == null) {
            toolbar = ((AppCompatActivity) context).findViewById(R.id.toolbar);
        }
        if (toolbar != null) {
            ((AppCompatActivity) context).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
            actionBar.show();
        }
    }

    public void hideToolbar() {
        if (toolbar == null) {
            toolbar = ((AppCompatActivity) context).findViewById(R.id.toolbar);
        }
        if (toolbar != null) {
            ((AppCompatActivity) context).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
            actionBar.hide();
        }
    }

    //callback
    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public void callBackCode(Enum code) {
        if (callBackInterface != null) {
            callBackInterface.callBackInterface(code);
        }
    }

    public void closeAllActivity() {
        getActivity().finishAffinity();
    }

    public void closeActivity() {
        getActivity().finish();
    }

    public ArrayAdapter<String> getArrayAdapter(List<String> list) {
        return new ArrayAdapter<>(
                getContext(),
                R.layout.layout_auto_complete_text,
                list
        );
    }

    protected void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // below line is the title for our alert dialog.
        builder.setTitle(getString(R.string.need_permission));

        // below line is our message for our dialog
        builder.setMessage(getString(R.string.permission_message));
        builder.setPositiveButton(R.string.goto_settings, (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton(getString(R.string.text_cancel), (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
        });
        // below line is used to display our dialog
        builder.show();
    }

}
