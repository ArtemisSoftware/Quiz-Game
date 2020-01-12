package com.titan.quizgame.player.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.titan.quizgame.R;
import com.titan.quizgame.player.models.Board;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.txt_name)
    TextView txt_name;

    @BindView(R.id.txt_score)
    TextView txt_score;


    //@BindView(R.id.picture_image)
    //ImageView picture_image;


    ViewPreloadSizeProvider viewPreloadSizeProvider;

    //RequestManager requestManager;

    public BoardViewHolder(@NonNull View itemView/*, RequestManager requestManager*/, ViewPreloadSizeProvider viewPreloadSizeProvider) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        //this.requestManager = requestManager;
        this.viewPreloadSizeProvider = viewPreloadSizeProvider;

    }

    public void onBind(Board board){

        //requestManager.load(picture.getUrl()).into(picture_image);
        //viewPreloadSizeProvider.setView(picture_image);

        txt_name.setText(board.getName());
        txt_score.setText(board.getHighScore() + "");
    }

}
