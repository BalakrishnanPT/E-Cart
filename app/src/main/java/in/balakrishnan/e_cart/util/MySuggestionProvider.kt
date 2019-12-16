package `in`.balakrishnan.e_cart.util

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }


    companion object {
        const val AUTHORITY = "in.company.contacts.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}