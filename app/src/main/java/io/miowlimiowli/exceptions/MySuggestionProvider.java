package io.miowlimiowli.exceptions;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "io.miowlimiowli.exceptions.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
