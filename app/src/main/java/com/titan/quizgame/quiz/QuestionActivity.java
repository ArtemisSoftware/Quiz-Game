package com.titan.quizgame.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.QuizViewModel;
import com.titan.quizgame.R;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends BaseActivity {

    @BindView(R.id.spinner_difficulty)
    Spinner spinnerDifficulty;

    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;


    @BindView(R.id.txt_question)
    TextInputLayout txt_question;

    @BindView(R.id.txt_option_one)
    TextInputLayout txt_option_one;

    @BindView(R.id.txt_option_two)
    TextInputLayout txt_option_two;

    @BindView(R.id.txt_option_three)
    TextInputLayout txt_option_three;


    @BindView(R.id.number_picker_solution)
    NumberPicker number_picker_solution;




    private QuizViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewModel = ViewModelProviders.of(this, providerFactory).get(QuizViewModel.class);

        number_picker_solution.setMinValue(1);
        number_picker_solution.setMaxValue(3);

    }


    private void saveQuestion() {

        if (txt_question.getEditText().getText().toString().trim().isEmpty()
                ||
                txt_option_one.getEditText().getText().toString().trim().isEmpty()
                ||
                txt_option_two.getEditText().getText().toString().trim().isEmpty()
                ||
                txt_option_three.getEditText().getText().toString().trim().isEmpty()) {
            //Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }


        Question question = new Question(txt_question.getEditText().getText().toString(),
                                         txt_option_one.getEditText().getText().toString(), txt_option_two.getEditText().getText().toString(), txt_option_three.getEditText().getText().toString(),
                                         number_picker_solution.getValue(),
                                         spinnerDifficulty.getSelectedItem().toString(), ((Category) spinnerCategory.getSelectedItem()).getId());


        /*

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
        */
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
