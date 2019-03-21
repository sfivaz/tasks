package com.example.pontes_stefane_esig.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.pontes_stefane_esig.myapplication.model.Card;
import com.example.pontes_stefane_esig.myapplication.model.Listt;

import java.util.ArrayList;
import java.util.List;

public class CardDAO extends DAO {

    private final String TABLE = "card";

    public CardDAO(Context context) {
        super(context, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "points REAL, " +
                "list_id INTEGER, " +
                "FOREIGN KEY(project_id) REFERENCES list(id)" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public List<Card> getAll(Listt list) {
        String sql = "SELECT * FROM " + TABLE + " WHERE project_id = " + list.getId();
        SQLiteDatabase db = getReadableDatabase();
        //TODO prepared statement
//        String[] args = new String[]{String.valueOf(project.getId())};
//        Cursor cursor = db.rawQuery(sql, args);
        Cursor cursor = db.rawQuery(sql, null);
        List<Card> cards = new ArrayList<>();
        while (cursor.moveToNext())
            cards.add(buildCard(cursor));
        cursor.close();

        return cards;
    }

    @NonNull
    private Card buildCard(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        double points = cursor.getDouble(cursor.getColumnIndex("points"));
        long project_id = cursor.getLong(cursor.getColumnIndex("project_id"));

        return new Card(id, name, points, project_id);
    }

    public void insert(Card card) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE, null, getValues(card));
        card.setId(id);
    }

    @NonNull
    private ContentValues getValues(Card card) {
        ContentValues data = new ContentValues();
        data.put("name", card.getName());
        data.put("points", card.getPoints());
        data.put("project_id", card.getList_id());
        return data;
    }

    //TODO test this
//    @Override
//    protected ContentValues getValues(Model model) {
//        Card card = (Card) model;
//        ContentValues data = new ContentValues();
//        data.put("name", card.getName());
//        data.put("points", card.getPoints());
//        data.put("project_id", card.getList_id());
//        return data;
//    }

    public void delete(Card card) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, "id = ?", getPK(card));
    }

    @NonNull
    private String[] getPK(Card card) {
        return new String[]{String.valueOf(card.getId())};
    }

    public void update(Card card) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE, getValues(card), "id = ?", getPK(card));
    }
}
