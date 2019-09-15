package kz.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import kz.newsapp.model.Article;
import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NewsViewHolder> {

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public Adapter(List<Article> articles, Context context){
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_news,viewGroup , false);
        return new NewsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder viewHolder, int i) {
        final  NewsViewHolder holder = viewHolder;
        Article model = articles.get(i);

        Glide.with(context)
                .load(model.getUrlToImage())
                .into(holder.mImage);

        holder.mTitle.setText(model.getTitle());
        holder.mDesc.setText(model.getDescription());
        holder.mDate.setText(model.getPublishedAt());
        holder.mSource.setText(model.getSource().getName());
    }

    @Override
    public int getItemCount() {
        return 9 ;//articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener() {

    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mTitle,mDesc, mSource, mDate;
        ImageView mImage;
        ProgressBar mProgressBar;
        OnItemClickListener onItemClickListener;

        public NewsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            mTitle = itemView.findViewById(R.id.title);
            mDesc = itemView.findViewById(R.id.desc);
            mDate = itemView.findViewById(R.id.publishedDate);
            mImage = itemView.findViewById(R.id.image);
            mSource = itemView.findViewById(R.id.source);
            mProgressBar = itemView.findViewById(R.id.progressLoaded);

            this.onItemClickListener = onItemClickListener;

        }

        public void addArticles(List<Article> articles){
            for(Article a: articles){
                articles.add(a);
            }
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClickListener(view,getAdapterPosition() );
        }
    }
}
