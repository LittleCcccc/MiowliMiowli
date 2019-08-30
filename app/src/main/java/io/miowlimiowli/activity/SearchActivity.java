package io.miowlimiowli.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.miowlimiowli.R;
import io.miowlimiowli.exceptions.MySuggestionProvider;
import io.miowlimiowli.fragment.NewsListFragment;


public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private String mKeyword;
    public static Intent newIntent(Context context) {
        return new Intent(context,SearchActivity.class);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search_activity);
        init();


    }

    public void init(){
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        mSearchView = findViewById(R.id.news_search_view);

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mKeyword=query;
                searchNews();

                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY,MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query,null);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.onGroupPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onGroupPressed() {

        this.finish();
    }

    public void searchNews()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsListFragment fragment = NewsListFragment.newInstance("","海贼王");
        fragmentTransaction.add(R.id.news_list_layout,fragment);
        fragmentTransaction.commit();
    }

    public void clearHistory(){
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.clearHistory();
    }
}
