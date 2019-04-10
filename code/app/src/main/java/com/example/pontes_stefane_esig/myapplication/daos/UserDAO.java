package com.example.pontes_stefane_esig.myapplication.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.pontes_stefane_esig.myapplication.models.Listt;
import com.example.pontes_stefane_esig.myapplication.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO use prepared statements
//TODO after update and insert
public class UserDAO extends DAO {

    private final String SELECT_STATEMENT = "SELECT * FROM " + TB_USER_NAME;
    private final String SELECT_WHERE = SELECT_STATEMENT + " WHERE id = %d";
    private final String SELECT_ALL = SELECT_STATEMENT + " WHERE isArchived = 0";
    private Context context;

    public UserDAO(Context context) {
        super(context);
        this.context = context;
    }

    public User get(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format(SELECT_WHERE, id);
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        User user = buildUser(cursor);
        cursor.close();
        return user;
    }

    public List<User> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        List<User> users = new ArrayList<>();
        while (cursor.moveToNext())
            users.add(buildUser(cursor));
        cursor.close();
        return users;
    }

    @NonNull
    private User buildUser(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String first_name = cursor.getString(cursor.getColumnIndex("first_name"));
        String last_name = cursor.getString(cursor.getColumnIndex("last_name"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String password = cursor.getString(cursor.getColumnIndex("password"));

        return new User(id, first_name, last_name, email, password);
    }

    public void insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(DAO.TB_USER_NAME, null, getValues(user));
        user.setId(id);
    }

    @NonNull
    private ContentValues getValues(User user) {
        ContentValues data = new ContentValues();
        data.put("first_name", user.getFirstName());
        data.put("last_name", user.getLastName());
        data.put("email", user.getEmail());
        data.put("password", user.getPassword());
        data.put("isArchived", user.isArchived());
        return data;
    }

    public void delete(User user) {
        SQLiteDatabase db = getWritableDatabase();
        user.setArchived(true);
        db.update(DAO.TB_USER_NAME, getValues(user), "id = ?", getPK(user));
    }

    @NonNull
    private String[] getPK(User user) {
        return new String[]{String.valueOf(user.getId())};
    }

    public void update(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(DAO.TB_USER_NAME, getValues(user), "id = ?", getPK(user));
    }

    public boolean checkUser(User user) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = SELECT_STATEMENT + " WHERE email = '%s' AND password = '%s'";
        sql = String.format(sql, user.getEmail(), user.getPassword());
        Cursor cursor = db.rawQuery(sql, null);
        boolean result = false;
        if (cursor.moveToNext())
            result = true;
        cursor.close();
        return result;
    }
}
