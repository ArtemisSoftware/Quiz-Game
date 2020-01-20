package com.titan.quizgame.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.R;
import com.titan.quizgame.player.adapters.BoardRecyclerAdapter;

import butterknife.BindView;

public class LeaderBoardActivity extends AppCompatActivity {


    @BindView(R.id.board_list)
    RecyclerView recyclerView;

    private BoardRecyclerAdapter boardRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        ((CircularImageView) findViewById(R.id.img_plus)).setVisibility(View.GONE);

        initRecyclerView();
    }


    private void initRecyclerView(){

        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();

        boardRecyclerAdapter = new BoardRecyclerAdapter(/*this, requestManager,*/ viewPreloader);
        recyclerView.setAdapter(boardRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
