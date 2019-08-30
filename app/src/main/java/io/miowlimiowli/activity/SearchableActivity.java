package io.miowlimiowli.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.miowlimiowli.R;

public class SearchableActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInsetanceState){
        super.onCreate(savedInsetanceState);
        setContentView(R.layout.search);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }
}
