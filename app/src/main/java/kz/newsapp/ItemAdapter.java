package kz.newsapp;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import kz.newsapp.model.Article;

public class ItemAdapter extends PagedListAdapter<Article, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private OnItemClickListener listener;
    private Article item;
    protected ItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_item_news,viewGroup , false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
         item = getItem(i);
        if(item != null){
            Glide.with(mCtx)
                    .load(item.getUrlToImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            itemViewHolder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            itemViewHolder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(itemViewHolder.mImage);


            itemViewHolder.mTitle.setText(item.getTitle());
            itemViewHolder.mDesc.setText(item.getDescription());
            itemViewHolder.mDate.setText(item.getPublishedAt());
            itemViewHolder.mSource.setText(item.getSource().getName());
        }
    }

    private static  DiffUtil.ItemCallback<Article> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Article>() {
                @Override
                public boolean areItemsTheSame(@NonNull Article article, @NonNull Article t1) {
                    return article.getTitle() == t1.getTitle();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Article article, @NonNull Article t1) {
                    return article.equals(t1);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle,mDesc, mSource, mDate;
        ImageView mImage;
        ProgressBar mProgressBar;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mDesc = itemView.findViewById(R.id.desc);
            mDate = itemView.findViewById(R.id.publishedDate);
            mImage = itemView.findViewById(R.id.image);
            mSource = itemView.findViewById(R.id.source);
            mProgressBar = itemView.findViewById(R.id.progressLoaded);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(mCtx,"Clicked",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(mCtx, DetailActivity.class);
                    i.putExtra("author", item.getAuthor());
                    i.putExtra("content",item.getContent());
                    i.putExtra("description",item.getDescription());
                    i.putExtra("date",item.getPublishedAt());
                    i.putExtra("url",item.getUrl());
                    i.putExtra("title", item.getTitle());
                    i.putExtra("image", item.getUrlToImage());
                    i.putExtra("source",item.getSource().getName());
                    mCtx.startActivity(i);
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(Article article);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
