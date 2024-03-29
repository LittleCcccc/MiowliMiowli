package io.miowlimiowli.others;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "io.miowlimiowli.others.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
