package com.titan.quizgame.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.material.textfield.TextInputLayout;
import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.Loader;
import com.titan.quizgame.util.UIMessages;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

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


        subscribeObservers();
        viewModel.loadConfigurations();
    }



    private void subscribeObservers(){

        viewModel.observeCategories().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        spinnerCategory.setAdapter(Loader.loadCategories(getApplicationContext(), (List<Category>) resource.data));
                        break;

                    case ERROR:

                        UIMessages.error(pDialog, resource.message, ((String) resource.data));
                        break;

                }
            }
        });

        viewModel.observeDifficulty().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        spinnerDifficulty.setAdapter(Loader.loadStringArray(getApplicationContext(), (String[]) resource.data));
                        break;

                    case ERROR:

                        UIMessages.error(pDialog, resource.message, ((String) resource.data));
                        break;

                }

            }
        });

        viewModel.observeQuestions().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {

                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        finishRegister();
                        break;

                    case ERROR:

                        UIMessages.error(pDialog, resource.message, ((String) resource.data));
                        break;
                }
            }
        });
    }


    private void finishRegister() {

        Closure method = new Closure() {
            @Override
            public void exec() {
                setResult(RESULT_OK);
                finish();
            }
        };

        UIMessages.success(pDialog, "Contribution", "Question saved", method);
    }


    private void saveQuestion() {

        if (txt_question.getEditText().getText().toString().trim().isEmpty()
                ||
                txt_option_one.getEditText().getText().toString().trim().isEmpty()
                ||
                txt_option_two.getEditText().getText().toString().trim().isEmpty()
                ||
                txt_option_three.getEditText().getText().toString().trim().isEmpty()) {

            UIMessages.error(pDialog, "Incomplete fields");
            return;
        }


        Question question = new Question(txt_question.getEditText().getText().toString(),
                                         txt_option_one.getEditText().getText().toString(), txt_option_two.getEditText().getText().toString(), txt_option_three.getEditText().getText().toString(),
                                         number_picker_solution.getValue(),
                                         spinnerDifficulty.getSelectedItem().toString(), ((Category) spinnerCategory.getSelectedItem()).getId());


        viewModel.saveQuestions(question);

    }


    @OnClick(R.id.btn_save)
    public void onButtonClick(View view) {
        saveQuestion();
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
