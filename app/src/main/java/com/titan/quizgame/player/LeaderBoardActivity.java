package com.titan.quizgame.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;
import com.titan.quizgame.player.adapters.BoardRecyclerAdapter;
import com.titan.quizgame.player.models.Board;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.constants.ActivityCode;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LeaderBoardActivity extends BaseActivity {

    @BindView(R.id.txt_name)
    TextView txt_name;

    @BindView(R.id.txt_difficulty)
    TextView txt_difficulty;

    @BindView(R.id.txt_score)
    TextView txt_score;

    @BindView(R.id.txt_category)
    TextView txt_category;


    @BindView(R.id.board_list)
    RecyclerView recyclerView;

    private BoardRecyclerAdapter boardRecyclerAdapter;

    private PlayerViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(PlayerViewModel.class);

        ((CircularImageView) findViewById(R.id.img_plus)).setVisibility(View.GONE);

        initRecyclerView();
        subscribeObservers();

        viewModel.loadLeaderBoard();
    }


    private void initRecyclerView(){

        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();

        boardRecyclerAdapter = new BoardRecyclerAdapter(/*this, requestManager,*/ viewPreloader);
        recyclerView.setAdapter(boardRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void subscribeObservers() {

        viewModel.observePlayers().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        fillTopPlayer(((List<Board>) resource.data).get(0));

                        ((List<Board>) resource.data).remove(0);
                        boardRecyclerAdapter.setResults((List<Board>) resource.data);
                        break;

                    case ERROR:

                        break;

                }
            }
        });
    }


    private void fillTopPlayer(Board board){

        txt_name.setText(board.getName());
        txt_difficulty.setText(board.getDifficulty());
        txt_score.setText(board.getPoints() + "");
        txt_category.setText(board.getCategory());

        txt_category.setText(board.getCategory());
    }


}
