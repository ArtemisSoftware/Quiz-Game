package com.titan.quizgame.player.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.titan.quizgame.R;
import com.titan.quizgame.player.models.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Board> results;

    //private RequestManager requestManager;

    private ViewPreloadSizeProvider<String> preloadSizeProvider;



    public BoardRecyclerAdapter(/*RequestManager requestManager,*/ ViewPreloadSizeProvider<String> preloadSizeProvider) {
        //this.requestManager = requestManager;
        this.results = new ArrayList<>();
        this.preloadSizeProvider = preloadSizeProvider;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_list_item, parent, false);
        return new BoardViewHolder(view/*, this.requestManager*/, preloadSizeProvider);


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BoardViewHolder) holder).onBind(this.results.get(position));
    }



    @Override
    public int getItemCount() {

        if(results != null) {
            return results.size();
        }

        return 0;
    }


    public void setResults(List<Board> results){
        this.results.addAll(results);
        notifyDataSetChanged();
    }


    public List<Board> getResults() {
        return results;
    }

    /*
    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String url = this.results.get(position).getUrl();
        if(TextUtils.isEmpty(url)){
            return Collections.emptyList();
        }

        return Collections.singletonList(url);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return requestManager.load(item);
    }
*/


}