package be.buithg.etghaifgte.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import be.buithg.etghaifgte.R

object Constants {
    const val DEFAULT_PP_LINK = "https://sites.google.com/view/examplesampleprivacypolicy"
    const val DEFAULT_TOS_LINK = "https://sites.google.com/view/examplesampletermsofuse"
    const val DEFAULT_DOMAIN_LINK = "https://pastebin.com/raw/q1CQaY4k"
    const val MAIN_OFFER_LINK_KEY = "main_offer_link"
    const val USER_STATUS_KEY = "user_status"
    const val WELCOME_KEY = "welcome"
    const val ACHIEVEMENTS_COUNT_KEY = "achievements_count"
    const val ACHIEVEMENT_STREAK_KEY = "achievement_streak"
    const val ACHIEVEMENT_TOURNAMENT_KEY = "achievement_tournament"
    const val ACHIEVEMENT_FIRST_WIN_KEY = "achievement_first_win"
    const val LEVEL_KEY = "user_level"
    private const val SHARED_PREFERENCES_KEY = "example_sample_shared_preferences"

    fun Context.getSharedPreferences(): SharedPreferences {
        return this.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun FragmentManager.launchNewFragment(fragment: Fragment) {
        this.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
    fun FragmentManager.launchNewFragmentWithNew(fragment: Fragment) {
        this.beginTransaction().apply {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun FragmentManager.launchNewFragmentWithoutBackstack(fragment: Fragment) {
        this.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            commit()
        }
    }
}