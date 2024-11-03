package com.dkglabs.home.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.home.R;
import com.dkglabs.home.databinding.FragmentLoanCalculatorBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.color.MaterialColors;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LoanCalculatorFragment extends BaseFragment {
    private static final int MAX_AMOUNT = 150;
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_LENGTH = 15;
    private static final int MIN_LENGTH = 0;
    private static final int MAX_INTEREST = 50;
    private static final int MIN_INTEREST = 0;

    private Double principal = 0.0;
    private int year = 0;
    private Double rate = 0.0;
    private FragmentLoanCalculatorBinding binding = null;

    public LoanCalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanCalculatorBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        principal = 180000.0;
        year = 2;
        rate = 25.0;

        setUpBackToolbar(getString(R.string.text_loan_calculator));

        binding.tvMonthlyPaymentValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), 0.0));
        binding.tvPrincipalValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), principal));
        binding.tvTotalInterestValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), 0.0));
        binding.tvTotalPayableValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), 0.0));

        binding.seekBarAmount.setMax(MAX_AMOUNT);
        binding.tvAmountValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), AppUtils.getConvertedAmount( (int)(principal/10000) )) );

        binding.seekBarAmount.setProgress((int)(principal/10000));
        binding.seekBarLength.setProgress(year);
        binding.seekBarInterest.setProgress(MAX_INTEREST);
        calculateLoan();
        binding.seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                principal = AppUtils.getConvertedAmount(progress);
                binding.tvAmountValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), principal));
                binding.tvPrincipalValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), principal));
                calculateLoan();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBarLength.setMax(MAX_LENGTH);
        binding.tvLengthValue.setText(String.format(getString(R.string.placeholder_length), year));
        binding.seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                year = seekBar.getProgress();
                binding.tvLengthValue.setText(String.format(getString(R.string.placeholder_length), year));
                calculateLoan();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBarInterest.setMax(MAX_INTEREST);
        binding.tvInterestValue.setText(String.format(getString(R.string.placeholder_interest), AppUtils.getConvertedInterest(MAX_INTEREST), '%'));
        binding.seekBarInterest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                rate = AppUtils.getConvertedInterest(progress);
                binding.tvInterestValue.setText(String.format(getString(R.string.placeholder_interest), rate, '%'));
                calculateLoan();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initPieChart();
    }

    private void calculateLoan() {
        if (principal <= 0.0 || rate <= 0.0 || year <= 0) {
            binding.pieChart.clear();
            binding.tvMonthlyPaymentValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), 0.0));
            binding.tvTotalInterestValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), 0.0));
            binding.tvTotalPayableValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), 0.0));
            return;
        }


        int time = year * 12; //In months
        Double r = (rate / (12 * 100));
        Double emi = ((principal * r * AppUtils.getPower(1 + r, time)) / (AppUtils.getPower(1 + r, time) - 1));
        Double totalPayable = emi * time;
        Double totalInterest = (emi * time) - principal;


        binding.tvMonthlyPaymentValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), emi));
        binding.tvTotalPayableValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), totalPayable));
        binding.tvTotalInterestValue.setText(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), totalInterest));
        DecimalFormat df = new DecimalFormat("0.00");
        setPieChartData(Float.valueOf(df.format(principal)), Float.valueOf(df.format(totalInterest)));
    }

    private void setPieChartData(Float principal, Float totalInterest) {
        ArrayList<PieEntry> dataValue = new ArrayList<>();
        dataValue.add(new PieEntry(principal, getString(R.string.principal)));
        dataValue.add(new PieEntry(totalInterest, getString(R.string.total_interest)));

        PieDataSet pieDataSet = new PieDataSet(dataValue, "");

        int[] colorArr = new int[]{MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorPrimary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_primary)),
                MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorTertiary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_tertiary))};
        List<Integer> colorTextArr = new ArrayList<>();
        colorTextArr.add(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnPrimary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onPrimary)));
        colorTextArr.add(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnTertiary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onTertiary)));

        pieDataSet.setColors(colorArr);
        pieDataSet.setValueTextColors(colorTextArr);
        pieDataSet.setValueTextSize(12);

        PieData pieData = new PieData(pieDataSet);
        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();
    }

    private void initPieChart() {
        binding.pieChart.setNoDataText("Set loan values");
        binding.pieChart.setNoDataTextColor(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnSurface, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onSurface)));

        binding.pieChart.setDrawEntryLabels(false);
        /*Description description = new Description();
        description.setTextColor(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorPrimary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_primary)));
        binding.pieChart.setDescription(description);*/
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setTextColor(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnSurface, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onSurface)));
        binding.pieChart.setHoleColor(Color.TRANSPARENT);
        binding.pieChart.invalidate();
    }


}