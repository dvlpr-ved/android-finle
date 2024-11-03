package com.dkglabs.apply_loan.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.model.response.KycResultModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KycResultFragment extends BaseFragment implements View.OnClickListener {
    Button btnNext;
    TextView txtFaceMatchScore;
    TextView txtAudioMatchScore;
    KycResultModel response;
    double facePer = 0;
    double audioPer = 0;
    String facePercentage;
    String audioPercentage;
    View view;

    public KycResultFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        response = KycResultFragmentArgs.fromBundle(getArguments()).getKycCallResult();

        view = inflater.inflate(R.layout.fragment_kyc_result, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        btnNext = view.findViewById(R.id.btnNext);
        txtFaceMatchScore = view.findViewById(R.id.txtFaceMatchScore);
        txtAudioMatchScore = view.findViewById(R.id.txtAudioMatchScore);

        initializeView();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(view).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }

    @SuppressLint("ResourceAsColor")
    private void initializeView() {
        btnNext.setOnClickListener(this);
        if (response.getResult().getVideoVerification().getVideoFaceMatch().size() > 0) {
            facePercentage = response.getResult().getVideoVerification().getVideoFaceMatch().get(0).getMatchStatistics().getMatchPercentage();
        }
        audioPercentage = response.getResult().getVideoVerification().getAudioMatch().getMatchAudioScore();
        if (facePercentage != null)
            facePer = extractNumber(facePercentage);
        if (audioPercentage != null)
            audioPer = extractNumber(audioPercentage);


        if (facePer <= 70 || audioPer <= 70) {
            btnNext.setText("Retry");
        }
        if (facePer <= 30) {
            txtFaceMatchScore.setTextColor(Color.RED);
        } else if (facePer > 30 && facePer <= 74) {
            txtFaceMatchScore.setTextColor(Color.YELLOW);
        } else {
            txtFaceMatchScore.setTextColor(Color.GREEN);
        }

        if (audioPer <= 30) {
            txtAudioMatchScore.setTextColor(Color.RED);
        } else if (audioPer > 30 && audioPer <= 74) {
            txtAudioMatchScore.setTextColor(Color.YELLOW);
        } else {
            txtAudioMatchScore.setTextColor(Color.GREEN);
        }


        if (facePercentage != null)
            txtFaceMatchScore.setText(txtFaceMatchScore.getText().toString() + " " + facePercentage);
        else
            txtFaceMatchScore.setText(txtFaceMatchScore.getText().toString() + " " + "0");
        if (audioPercentage != null)
            txtAudioMatchScore.setText(txtAudioMatchScore.getText().toString() + " " + audioPercentage);
        else
            txtAudioMatchScore.setText(txtAudioMatchScore.getText().toString() + " " + "0");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnNext.getId()) {
            if (facePer <= 70 || audioPer <= 70) {
                Navigation.findNavController(v).navigate(R.id.action_kycResultFragment_to_loanVideoKYCFragment);
                return;
            }
            Navigation.findNavController(v).navigate(KycResultFragmentDirections.actionKycResultFragmentToESignDocFragment());
        }
    }

    public static double extractNumber(String input) {
        // Regular expression to match a number with optional decimal part
        String regex = "(\\d+(?:\\.\\d+)?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String numberStr = matcher.group(1);
            return Double.parseDouble(numberStr);
        }
        return 0;
    }
}