package com.example.collegeevent.feedsection;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.collegeevent.feedsection.model.Article;
import com.example.collegeevent.R;

import java.util.List;

import okhttp3.internal.Util;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
private List<Article> articles;
private Context context;
private OnItemClickListener onItemClickListener;

public Adapter(List<Article> articles, Context context)
{
    this.articles= articles;
    this.context= context;
}


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final MyViewHolder holder1=holder;
        final Article articleModel= articles.get(position);

        Toast.makeText(context, "onBindViewHolder", Toast.LENGTH_LONG).show();//5
        RequestOptions requestOptions= new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(context)
                .load(articleModel.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder1.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(holder1.imageView);
        holder1.progressBar.setVisibility(View.GONE);
        holder1.title.setText(articleModel.getAuthor());
        holder1.author.setText(articleModel.getAuthor());
        holder1.desc.setText(articleModel.getDescription());
        holder1.published_at.setText(articleModel.getPublishedAt());
        //holder1.source.setText(articleModel.getSource().toString());
        holder1.time.setText("\u2022 "+ Utils.DateToTimeFormat(articleModel.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener= onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view,int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title,desc,author,published_at,source,time;
        ImageView imageView;
        ProgressBar progressBar;

        OnItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            Toast.makeText(context, "MyViewHolder", Toast.LENGTH_LONG).show();//6
            itemView.setOnClickListener(this);
            title= itemView.findViewById(R.id.title);
            desc= itemView.findViewById(R.id.desc);
            author= itemView.findViewById(R.id.author);
            published_at= itemView.findViewById(R.id.publishedAt);
            source= itemView.findViewById(R.id.source);
            time= itemView.findViewById(R.id.time);
            progressBar= itemView.findViewById(R.id.progressbar_load_photo);
            imageView= itemView.findViewById(R.id.img);
            this.onItemClickListener= onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
