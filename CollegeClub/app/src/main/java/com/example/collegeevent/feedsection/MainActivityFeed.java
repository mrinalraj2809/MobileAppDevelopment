package com.example.collegeevent.feedsection;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.collegeevent.MainActivity;
import com.example.collegeevent.feedsection.api.ApiClient;
import com.example.collegeevent.feedsection.api.ApiInterface;
import com.example.collegeevent.feedsection.model.Article;
import com.example.collegeevent.feedsection.model.News;
import com.example.collegeevent.R;
//import com.example.collegeevent.feedsection.model.Source;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityFeed extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    public static final String API_KEY="939edc2f3cbd427584bb1e83bf69f87c";//"7080ec9b05e04dec99d2959298c12201";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles =new ArrayList<>();
    private com.example.collegeevent.feedsection.Adapter adapter;
//    private String TAG=MainActivityFeed.class.getSimpleName();
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle,errorMessage;
    private Button btnRetry;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView topHeadline;
    private String TAG = MainActivityFeed.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        Toast.makeText(this, "Feed", Toast.LENGTH_LONG).show();
        swipeRefreshLayout =findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline =findViewById(R.id.topHeadlines);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        layoutManager=new LinearLayoutManager(MainActivityFeed.this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(recyclerView.getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");
        errorLayout=findViewById(R.id.error_layout);
        errorImage=findViewById(R.id.errorImage);
        errorTitle=findViewById(R.id.errorTitle);
        errorMessage=findViewById(R.id.errorMessage);
        btnRetry=findViewById(R.id.btnretry);

    }
    public void LoadJson(final String keyword ){
        errorLayout.setVisibility(View.GONE);
        topHeadline.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String country = Utils.getCountry();
        Call<News> call;

        if (keyword.length()>0)
        {
            call = apiInterface.getsNewsSearch(keyword,"publishedAt",API_KEY);
        }
        else
        {Toast.makeText(MainActivityFeed.this, "keyword.length()=0", Toast.LENGTH_SHORT).show();
            call = apiInterface.getNews(country,API_KEY);
        }


        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response){
                if(response.isSuccessful()&&response.body().getArticles()!=null)
                {
                    Toast.makeText(MainActivityFeed.this, "response.isSuccessful()", Toast.LENGTH_LONG).show();
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles= response.body().getArticles();
                    Toast.makeText(MainActivityFeed.this, articles.get(0).getAuthor().toString(), Toast.LENGTH_LONG).show();
                    adapter=new com.example.collegeevent.feedsection.Adapter(articles,MainActivityFeed.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }

                else{
                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    String errorCode;
                    switch(response.code()){
                        case  404:
                            errorCode="404 not found";
                            break;
                        case 500:
                            errorCode="500 server broken";
                            break;
                        default:
                            errorCode="unknown error";
                            break;
                    }
                    showErrorMessage(R.drawable.ic_error_black_24dp,
                            "No Result",
                            "Please Try Again\n"
                                    +errorCode);
                }
            }


            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(R.drawable.ic_error_black_24dp,
                        "Oops",
                        "Network failure, Please Try Again\n"
                                +t.toString());

            }
        });

    }

    private void initListener(){
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView=view.findViewById(R.id.img);
                Intent intent=new Intent(MainActivityFeed.this, NewsDetails.class);
                Toast.makeText(MainActivityFeed.this, "initListener.setOnItemClickListener", Toast.LENGTH_SHORT).show();//4
                Article article=articles.get(position);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getTitle());
                intent.putExtra("img",article.getUrlToImage());
                intent.putExtra("date",article.getPublishedAt());
                intent.putExtra("source",article.getSource().getName());
                intent.putExtra("author",article.getAuthor());

                Pair<View,String> pair=Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivityFeed.this,pair);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Toast.makeText(MainActivityFeed.this, "News1", Toast.LENGTH_SHORT).show();
                    startActivity(intent,optionsCompat.toBundle());
                }
                else{
                    Toast.makeText(MainActivityFeed.this, "News2", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem= menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.length()>2){
                    LoadJson(s);
                    onLoadingSwipeRefresh(s);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                LoadJson(s);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false,false);
        return true;
    }

    @Override
    public void onRefresh() {
        LoadJson("");Toast.makeText(MainActivityFeed.this, "onRefresh", Toast.LENGTH_SHORT).show();
    }
    private void onLoadingSwipeRefresh(final String keyword){
        Toast.makeText(MainActivityFeed.this, "onLoadingSwipeRefresh", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson("");
                    }
                }
        );
    }
    private void showErrorMessage(int imageView, String title, String message){
        if (errorLayout.getVisibility()==View.VISIBLE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("");
            }
        });


    }
}
