package com.yom.hospitalmanagementyom.activity.home.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Questions extends AppCompatActivity {

    private TextView counter , questionText;
    private int i;
    private String count_list[];
    private Button ans1, ans2,nextBtn;
    private String questions[],firstAnswers[],SecondAnswers[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        i =0;
        counter = findViewById(R.id.count);
        questionText = findViewById(R.id.Q);
        ans1 = findViewById(R.id.btn_ans_1);
        ans2 = findViewById(R.id.btn_ans_2);
        nextBtn = findViewById(R.id.nextBtn);
        count_list = new String[]{"2/4","3/4","4/4"};
        questions = new String[]{getString(R.string.Q2),getString(R.string.Q3),getString(R.string.Q4)};
        firstAnswers = new String[]{getString(R.string.ans1Q2),getString(R.string.ans1Q3),getString(R.string.ans1Q4)};
        SecondAnswers = new String[]{getString(R.string.ans2Q2),getString(R.string.ans2Q3),getString(R.string.ans2Q4)};

    }

    public void next(View view) {
        counter.setText(count_list[i]);
        questionText.setText(questions[i]);
        ans1.setText(firstAnswers[i]);
        ans2.setText(SecondAnswers[i]);

        if(i < 2)
            i++;

        else
        {
            nextBtn.setText("Finish");
            TastyToast.makeText(this,"GREAT!!, You are good !",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();

        }

    }
}