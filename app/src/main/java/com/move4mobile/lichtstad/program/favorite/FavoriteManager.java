package com.move4mobile.lichtstad.program.favorite;

import android.content.Context;
import android.content.SharedPreferences;

import com.move4mobile.lichtstad.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FavoriteManager {

    private static final String PREFERENCES_NAME = "favorites";

    public static void registerFavorite(Context context, String date, String key) {
        getFavoritePreferences(context).edit()
                .putBoolean(getPreferenceKey(date, key), true)
                .apply();
    }

    public static void unregisterFavorite(Context context, String date, String key) {
        getFavoritePreferences(context).edit()
                .remove(getPreferenceKey(date, key))
                .apply();
    }

    public static boolean isFavorite(Context context, String dateAndKey) {
        return getFavoritePreferences(context).getBoolean(dateAndKey, false);
    }

    public static Collection<String> getAllFavoritesOnDate(Context context, String date) {
        Set<String> favorites = new HashSet<>();
        for (Map.Entry<String, ?> preference : getFavoritePreferences(context).getAll().entrySet()) {
            if (preference.getKey().startsWith(date) && preference.getValue() instanceof Boolean && ((Boolean)(preference.getValue()))) {
                favorites.add(getKeyFromPreferenceKey(preference.getKey()));
            }
        }
        return favorites;
    }

    public static Map<String, Collection<String>> getAllFavorites(Context context) {
        Map<String, Collection<String>> allFavorites = new HashMap<>();
        for (String date : context.getResources().getStringArray(R.array.lichtstad_days)) {
            allFavorites.put(date, getAllFavoritesOnDate(context, date));
        }
        return allFavorites;
    }

    public static void registerFavoriteChangeListener(Context context, SharedPreferences.OnSharedPreferenceChangeListener changeListener) {
        getFavoritePreferences(context).registerOnSharedPreferenceChangeListener(changeListener);
    }

    private static SharedPreferences getFavoritePreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, 0);
    }

    public static String getPreferenceKey(String date, String key) {
        return date + "/programs/" + key;
    }

    public static String getDateFromPreferenceKey(String preferenceKey) {
        return preferenceKey.substring(0, preferenceKey.indexOf("/programs/"));
    }

    public static String getKeyFromPreferenceKey(String preferenceKey) {
        return preferenceKey.split("/programs/")[1];
    }

}
