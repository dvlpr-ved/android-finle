package com.dgklabs.collection_agent.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.dgklabs.collection_agent.databinding.ActivityCollectionMainBinding;

public class CollectionMainActivity extends AppCompatActivity {
    ActivityCollectionMainBinding binding;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCollectionMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        String title="Collection Agent";
        if (toolbar == null) {
            toolbar = findViewById(com.dkglabs.base.R.id.toolbar);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(title);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(com.dkglabs.base.R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }
}