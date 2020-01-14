package com.titan.quizgame.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;

import butterknife.BindView;

public class QuestionActivity extends BaseActivity {
    /*
        @BindView(R.id.spinner_difficulty)
        Spinner spinnerDifficulty;

        @BindView(R.id.spinner_category)
        Spinner spinnerCategory;


        @BindView(R.id.txt_input_user)
        TextInputLayout txt_input_user;

        @BindView(R.id.txt_input_user)
        TextInputLayout txt_input_user;

        @BindView(R.id.txt_input_user)
        TextInputLayout txt_input_user;

        @BindView(R.id.txt_input_user)
        TextInputLayout txt_input_user;

        @BindView(R.id.txt_input_user)
        TextInputLayout txt_input_user;

        @BindView(R.id.number_picker_solution)
        NumberPicker number_picker_solution;

        */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }


    private void saveQuestion() {
/*
        txt_input_user.getEditText().getText().toString();
        number_picker_solution.getValue()
        spinnerDifficulty.getSelectedItem().toString()
        (Category) spinnerCategory.getSelectedItem()
        Question question = new Question();
*/

        /*
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }


        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
        */
    }
}
