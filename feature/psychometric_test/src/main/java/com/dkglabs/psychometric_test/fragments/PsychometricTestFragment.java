package com.dkglabs.psychometric_test.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.PsyAnswerListRequest;
import com.dkglabs.model.request.PsyAnswerRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PsyResultResponse;
import com.dkglabs.model.response.QuestionAnswerListResponse;
import com.dkglabs.model.response.PsyQuestionAnswerResponse;
import com.dkglabs.psychometric_test.R;
import com.dkglabs.psychometric_test.adapters.PsychometricTestAdapter;
import com.dkglabs.psychometric_test.databinding.FragmentPsychometricTestBinding;
import com.dkglabs.psychometric_test.interfaces.PsyAnswerListener;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.PsychometricManager;

import java.util.ArrayList;
import java.util.List;


public class PsychometricTestFragment extends BaseFragment implements View.OnClickListener, ResponseListener, PsyAnswerListener {


    private List<PsyQuestionAnswerResponse> questionList;
    private PsychometricTestAdapter adapter;

    private FragmentPsychometricTestBinding binding;

    public PsychometricTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setEnabled(false);
                //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricTestFragment_pop_including_psychometricStartTestFragment);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPsychometricTestBinding.inflate(getLayoutInflater(), container, false);
        //requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        questionList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
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
        adapter = new PsychometricTestAdapter(getContext(), questionList, this);
        binding.recyclerView.setAdapter(adapter);


        binding.buttonSubmit.setOnClickListener(this);
        progressDialog = UIUtils.showProgressDialog(getActivity(), "Loading questions...");
        PsychometricManager.getPsychometricQuestion(1001,  this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSubmit) {
            verifyResult();
        }
    }

    private void verifyResult() {
        List<PsyAnswerRequest> answerListRequests = new ArrayList<>();
        int attempt = 0;

        for (PsyQuestionAnswerResponse questionResponse : questionList) {
            PsyAnswerRequest answerRequest = new PsyAnswerRequest();
            if (!questionResponse.getSelectedOption().isEmpty()) {
                attempt++;
            }
            answerRequest.setUserAnswer(questionResponse.getSelectedOption());
            answerRequest.setQuestionCode(questionResponse.getQuestionCode());
            answerRequest.setQuestionNumber(questionResponse.getQuestionNumber());

            answerListRequests.add(answerRequest);
        }

        if (attempt < 1) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.select_option_and_submit));
            return;
        }

        PsyAnswerListRequest request = new PsyAnswerListRequest();
        request.setPsyAnswerRequestList(answerListRequests);
        request.setUserId(PersistentManager.getUserResponse().getUserId());

        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.saving_answer));
        PsychometricManager.savePsychometricQuestion(1002, request,  this);

    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == 1001) {
            QuestionAnswerListResponse listResponse = (QuestionAnswerListResponse) response.getResponseData();
            if (questionList.size() > 0)
                questionList.clear();

            questionList.addAll(listResponse.getQuestionAnswerList());
            adapter.notifyDataSetChanged();
        } else if (requestCode == 1002) {
            PsyResultResponse psyResultResponse = (PsyResultResponse) response.getResponseData();
            Bundle bundle = new Bundle();
            bundle.putSerializable("result", psyResultResponse);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_psychometricTestFragment_to_psychometricTestResultFragment, bundle);
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(progressDialog);
    }

    @Override
    public void onAnswerSelect(int position, String selectOption) {
        LogsManager.printLog("Answer " + position + " : " + selectOption);
        PsyQuestionAnswerResponse response = questionList.get(position);
        response.setSelectedOption(selectOption);
        questionList.set(position, response);
        adapter.notifyItemChanged(position);
    }
}