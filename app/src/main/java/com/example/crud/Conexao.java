package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String namedb = "banco.db";
    private static final String TABLE_NAME = "usuario";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "nome";
    private static final String COL_3 = "cpf";
    private static final String COL_4 = "email";
    private static final String COL_5 = "telefone";
    private static final String COL_6 = "senha";

    public Conexao(Context context) {
        super(context, namedb, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +" ("+ COL_1 + " integer primary key autoincrement," +
                COL_2 + " text, "+COL_3 +" text,"+COL_4+" text," +
                COL_5 + " text," +COL_6 +" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);

    }
    public boolean inserirUsuario(String nome, String cpf, String email, String telefone, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,nome);
        contentValues.put(COL_3,cpf);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,telefone);
        contentValues.put(COL_6,senha);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " +TABLE_NAME,null);
    }
}
