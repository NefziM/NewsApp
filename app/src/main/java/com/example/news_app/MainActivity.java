package com.example.news_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Article> articleList = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        categoryRecyclerView = findViewById(R.id.category_recycler_view);
        newsRecyclerView = findViewById(R.id.recycler_view);
        SearchView searchView = findViewById(R.id.search_view);

        setupCategories();
        setupRecyclerViews();
        loadAllNews();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNews(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadAllNews();
                }
                return false;
            }
        });
    }

    private void setupCategories() {
        categories.add(new Category("All News", R.drawable.ic_all_news));
        categories.add(new Category("Technology", R.drawable.ic_technology));
        categories.add(new Category("Politics", R.drawable.ic_politics));
        categories.add(new Category("Sports", R.drawable.ic_sports));
        categories.add(new Category("Business", R.drawable.ic_business));
        categories.add(new Category("Health", R.drawable.ic_health));
        categories.add(new Category("Science", R.drawable.ic_science));
        categories.add(new Category("Entertainment", R.drawable.ic_entertainment));
    }

    private void setupRecyclerViews() {
        categoryAdapter = new CategoryAdapter(categories, category -> onCategorySelected(category));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Passer l'écouteur de clic au NewsAdapter
        newsAdapter = new NewsAdapter(this, articleList, article -> {
            // Créer un Intent pour rediriger vers DetailActivity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("ARTICLE", article);  // Passer l'article sélectionné
            startActivity(intent);
        });

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setAdapter(newsAdapter);
    }

    private void onCategorySelected(Category category) {
        if ("All News".equals(category.getName())) {
            loadAllNews();
        } else {
            loadNewsByCategory(category);
        }
    }

    private void loadAllNews() {
        NewsApiService apiService = ApiClient.getRetrofitInstance().create(NewsApiService.class);

        Call<NewsResponse> call = apiService.getNews("tesla", "f16a54ef686d40c1a75f833562057101");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleList.clear();
                    articleList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNewsByCategory(Category category) {
        NewsApiService apiService = ApiClient.getRetrofitInstance().create(NewsApiService.class);

        Call<NewsResponse> call = apiService.getNews(category.getName(), "f16a54ef686d40c1a75f833562057101");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleList.clear();
                    articleList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchNews(String query) {
        NewsApiService apiService = ApiClient.getRetrofitInstance().create(NewsApiService.class);

        Call<NewsResponse> call = apiService.getNews(query, "f16a54ef686d40c1a75f833562057101");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleList.clear();
                    articleList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
