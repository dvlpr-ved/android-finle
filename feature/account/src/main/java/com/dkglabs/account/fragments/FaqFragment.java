package com.dkglabs.account.fragments;

import static com.dkglabs.account.constants.FAndQConst.PHONE_NUMBER;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.account.R;
import com.dkglabs.account.adapters.FaqAdapter;
import com.dkglabs.account.databinding.FragmentFaqBinding;
import com.dkglabs.account.model.FaqModel;
import com.dkglabs.base.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 3/22/2023.
 */
public class FaqFragment extends BaseFragment implements View.OnClickListener {
    private List<FaqModel> listFaq;
    private FaqAdapter adapter;
    private FragmentFaqBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFaqBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar("FAQ");
        addListData();

        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 10;
                outRect.bottom = 10;
                outRect.left = 10;
                outRect.top = 10;
            }
        });
        binding.recyclerView.setLayoutManager(manager);
        adapter = new FaqAdapter(listFaq, context);
        binding.recyclerView.setAdapter(adapter);
        binding.buttonGetInTouch.setOnClickListener(this);
    }

    private void addListData() {
        listFaq = new ArrayList<>();
        List<String> faqQuestions = Arrays.asList(getResources().getStringArray(R.array.faq_questions));
        List<String> faqAnswers = Arrays.asList(getResources().getStringArray(R.array.faq_answers));
        TypedArray faqIcons = getResources().obtainTypedArray(R.array.faq_icons);
        for (int i = 0; i < faqQuestions.size(); i++) {
            listFaq.add(new FaqModel(faqQuestions.get(i), faqAnswers.get(i), faqIcons.getDrawable(i)));
        }
    }

    @Override
    public void onClick(View view) {
        if(binding.buttonGetInTouch.getId()==view.getId()){
            OpenNumberPad();
        }
        switch (view.getId()) {

        }
    }

    private void OpenNumberPad() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
        startActivity(intent);
    }
}