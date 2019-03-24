package com.example.pontes_stefane_esig.myapplication.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO extends SQLiteOpenHelper {

    static final String TB_PROJECT_NAME = "project";
    static final String TB_LISTT_NAME = "listt";
    static final String TB_CARD_NAME = "card";

    private final String CREATE_TABLE_PROJECT_STATEMENT =
            "CREATE TABLE " + TB_PROJECT_NAME + " (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL" +
                    ")";
    private final String CREATE_TABLE_LISTT_STATEMENT =
            "CREATE TABLE " + TB_LISTT_NAME + " (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "position INT NOT NULL, " +
                    "project_id INTEGER," +
                    "FOREIGN KEY(project_id) REFERENCES " + TB_PROJECT_NAME + "(id)" +
                    ")";
    private final String CREATE_TABLE_CARD_STATEMENT =
            "CREATE TABLE " + TB_CARD_NAME + " (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "points REAL NOT NULL, " +
                    "position INT NOT NULL, " +
                    "listt_id INTEGER, " +
                    "FOREIGN KEY(listt_id) REFERENCES " + TB_LISTT_NAME + "(id)" +
                    ")";
    private final String DROP_STATEMENT = "DROP TABLE IF EXISTS ";

    DAO(Context context) {
        super(context, "trello", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creating tables");
        db.execSQL(CREATE_TABLE_PROJECT_STATEMENT);
        db.execSQL(CREATE_TABLE_LISTT_STATEMENT);
        db.execSQL(CREATE_TABLE_CARD_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("updating tables");
        db.execSQL(DROP_STATEMENT + TB_CARD_NAME);
        db.execSQL(DROP_STATEMENT + TB_LISTT_NAME);
        db.execSQL(DROP_STATEMENT + TB_PROJECT_NAME);
        onCreate(db);
    }
}
