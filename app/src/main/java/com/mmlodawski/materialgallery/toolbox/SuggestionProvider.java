/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.toolbox;

import android.content.SearchRecentSuggestionsProvider;

public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = SuggestionProvider.class.getCanonicalName();
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}