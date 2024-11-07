package com.example.aqualife;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context; //a
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Informações do banco de dados
    private static final String DATABASE_NAME = "AquaLife.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_LAST_ACTIVITY_ID = "last_activity_id";
    public static final String TABLE_PROGRESS = "progress";
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação das tabelas
        String createUserTable = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);

        // Criação da tabela de progresso
        String CREATE_PROGRESS_TABLE = "CREATE TABLE " + TABLE_PROGRESS + "("
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_LAST_ACTIVITY_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USERNAME + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USERNAME + ") ON DELETE CASCADE" + ")";
        db.execSQL(CREATE_PROGRESS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    // Inserir usuário
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USER, null, values);
        return result != -1; // retorna true se a inserção for bem-sucedida
    }

    // Validar login
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean isLoggedIn = cursor.getCount() > 0;
        cursor.close();
        return isLoggedIn;
    }


    // Deleta um usuário específico
    public boolean deleteUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER,
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password});
        db.close();
        return result > 0; // retorna true se a exclusão foi bem-sucedida
    }

    /* Deleta todos os dados dos usuários
    public boolean deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER, null, null);
        db.close();
        return result > 0;
    } */


}