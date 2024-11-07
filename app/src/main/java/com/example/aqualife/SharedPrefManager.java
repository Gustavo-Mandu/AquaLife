package com.example.aqualife;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String PREFS_NAME = "aqualife_prefs";
    private static final String KEY_LAST_ACTIVITY_ID = "last_activity_id";

    private SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Salvar o ID da última atividade
    public void saveLastActivityId(String username, int activityId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(username + KEY_LAST_ACTIVITY_ID, activityId);
        editor.apply();
    }

    // Recuperar o ID da última atividade
    public int getLastActivityId(String username) {
        return sharedPreferences.getInt(username + KEY_LAST_ACTIVITY_ID, 0); // 0 é o valor padrão
    }
}
