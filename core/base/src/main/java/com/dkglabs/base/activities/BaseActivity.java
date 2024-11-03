package com.dkglabs.base.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.dkglabs.base.R;
import com.dkglabs.base.manager.LanguageManager;
import com.dkglabs.base.model.ItemViewModel;
import com.dkglabs.base.utils.UIUtils;

/**
 * Created by Himanshu Srivastava on 3/22/2023.
 */
public class BaseActivity extends AppCompatActivity {
    protected final int FULL_SCREEN_FLAG = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    protected ItemViewModel viewModel;
    protected Toolbar toolbar;
    protected Dialog progressDialog;
    protected Dialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageManager.setLocale(newBase));
    }

    protected ItemViewModel getViewModel() {
        return new ViewModelProvider(this).get(ItemViewModel.class);
    }

    protected void startActivity(Class className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    protected void startActivity(Class className, View view, String transitionName) {
        Intent intent = new Intent(this, className);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, transitionName);
        startActivity(intent, optionsCompat.toBundle());
    }

    protected void startWebActivity(Activity activity, String title, String url) {
        UIUtils.openBrowser(activity, url, title);
    }

    public void closeAllActivity() {
        finishAffinity();
    }

    protected void closeActivity() {
        finish();
    }

    public static void openOtherApp(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(packageName);

        PackageManager packageManager = context.getPackageManager();
        if (packageManager.getLaunchIntentForPackage(packageName) == null) {
            // The other app is not installed.
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Install other app");
            builder.setMessage("The other app is not installed. Do you want to install it now?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // Install the other app.
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setData(Uri.parse("market://details?id=" + packageName));
                context.startActivity(installIntent);
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                // Dismiss the dialog.
                dialog.dismiss();
            });
            builder.show();
        } else {
            // The other app is installed.
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Open other app");
            builder.setMessage("The user is not valid. Do you want to open other app now?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // Open the other app.
                context.startActivity(intent);
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                // Dismiss the dialog.
                dialog.dismiss();
            });
            builder.show();
        }
    }

    public void setUpBackToolbar(String title) {
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(title);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void setToolbar(String title) {
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(title);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    public boolean isInternetAvailable(View view) {
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        if (view != null) {
            UIUtils.showSnackbar(view, getString(R.string.no_internet_message));
        }
        return false;
    }

    public boolean isInternetAvailable(View view, String action, View.OnClickListener listener) {
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        if (view != null) {
            UIUtils.showSnackbar(view,
                    getString(R.string.no_internet_message), action, listener);
        }
        return false;
    }

    public Context getContext() {
        return this;
    }

    public void fullScreenActivity() {
        getWindow().getDecorView().setSystemUiVisibility(FULL_SCREEN_FLAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
