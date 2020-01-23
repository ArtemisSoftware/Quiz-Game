package com.titan.quizgame.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;
import com.titan.quizgame.player.adapters.BoardRecyclerAdapter;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LeaderBoardActivity extends BaseActivity {


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

                        //set values
                        break;

                    case ERROR:

                        break;

                }
            }
        });
    }

}
