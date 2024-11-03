package com.dkglabs.psychometric_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.model.response.PsyQuestionAnswerResponse;
import com.dkglabs.psychometric_test.R;
import com.dkglabs.psychometric_test.interfaces.PsyAnswerListener;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 29,April,2023
 * Project e_savari_customer
 */
public class PsychometricTestAdapter extends RecyclerView.Adapter<PsychometricTestAdapter.ViewHolder> {

    private List<PsyQuestionAnswerResponse> questionList;
    private PsyAnswerListener listener;
    private Context context;

    public PsychometricTestAdapter(Context context, List<PsyQuestionAnswerResponse> questionList, PsyAnswerListener listener) {
        this.questionList = questionList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_psychometric_test_question, parent, false);
        return new PsychometricTestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PsyQuestionAnswerResponse psyQuestionAnswerResponse = questionList.get(position);
        holder.setView(psyQuestionAnswerResponse, position);
    }

    @Override
    public int getItemCount() {
        return AppUtils.isCollectionContainData(questionList) ? questionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView tvQusNo;
        private MaterialTextView tvPTQus;
        private RadioGroup radioGroupOption;
        private RadioButton radioButtonOptionA;
        private RadioButton radioButtonOptionB;
        private RadioButton radioButtonOptionC;
        private RadioButton radioButtonOptionD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQusNo = itemView.findViewById(R.id.tvQusNo);
            tvPTQus = itemView.findViewById(R.id.tvPTQus);
            radioGroupOption = itemView.findViewById(R.id.radioGroupOption);
            radioButtonOptionA = itemView.findViewById(R.id.radioButtonOptionA);
            radioButtonOptionA.setOnClickListener(this);
            radioButtonOptionB = itemView.findViewById(R.id.radioButtonOptionB);
            radioButtonOptionB.setOnClickListener(this);
            radioButtonOptionC = itemView.findViewById(R.id.radioButtonOptionC);
            radioButtonOptionC.setOnClickListener(this);
            radioButtonOptionD = itemView.findViewById(R.id.radioButtonOptionD);
            radioButtonOptionD.setOnClickListener(this);
        }

        public void setView(PsyQuestionAnswerResponse psyQuestion, int pos) {
            LogsManager.printLog("Position :" + pos + " : " + psyQuestion);
            tvQusNo.setText(String.format(context.getString(R.string.placeholder_question_no), (pos + 1)));
            tvPTQus.setText(psyQuestion.getQuestion());
            radioButtonOptionA.setText(psyQuestion.getOptionA());
            radioButtonOptionB.setText(psyQuestion.getOptionB());
            radioButtonOptionC.setText(psyQuestion.getOptionC());
            radioButtonOptionD.setText(psyQuestion.getOptionD());
            radioGroupOption.clearCheck();
            radioButtonOptionA.setTag(pos);
            radioButtonOptionB.setTag(pos);
            radioButtonOptionC.setTag(pos);
            radioButtonOptionD.setTag(pos);

            switch (psyQuestion.getSelectedOption()) {
                case "A":
                    radioButtonOptionA.setChecked(true);
                    break;
                case "B":
                    radioButtonOptionB.setChecked(true);
                    break;
                case "C":
                    radioButtonOptionC.setChecked(true);
                    break;
                case "D":
                    radioButtonOptionD.setChecked(true);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.radioButtonOptionA) {
                listener.onAnswerSelect((Integer) view.getTag(), "A");
            } else if (id == R.id.radioButtonOptionB) {
                listener.onAnswerSelect((Integer) view.getTag(), "B");
            } else if (id == R.id.radioButtonOptionC) {
                listener.onAnswerSelect((Integer) view.getTag(), "C");
            } else if (id == R.id.radioButtonOptionD) {
                listener.onAnswerSelect((Integer) view.getTag(), "D");
            }
        }
    }
}
