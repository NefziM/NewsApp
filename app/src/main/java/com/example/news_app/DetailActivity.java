package com.example.news_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.contentTextView);
        imageView = findViewById(R.id.articleImageView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.inflateMenu(R.menu.menu_detail);

        Intent intent = getIntent();
        Article article = (Article) intent.getSerializableExtra("ARTICLE");

        if (article != null) {
            titleTextView.setText(article.getTitle());
            descriptionTextView.setText(article.getDescription());
            Glide.with(this)
                    .load(article.getUrlToImage())
                    .into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent intent = getIntent();
            Article article = (Article) intent.getSerializableExtra("ARTICLE");

            if (article != null) {
                String shareText = article.getTitle() + "\n" + article.getDescription() + "\n" + article.getUrl();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                // Par défaut, lancer un Intent de partage standard
                Intent defaultShareIntent = new Intent(Intent.ACTION_SEND);
                defaultShareIntent.setType("text/plain");
                defaultShareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(defaultShareIntent, "Share via"));
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}








