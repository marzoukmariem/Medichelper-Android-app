package com.esprit.intro.miniprojet;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
public class Databaseceate extends SQLiteOpenHelper {
    public  static final String Table_match="Rendez_vous";
    public  static final String Column_1="titre";
    public  static final String Column_2="date";
    public  static final String Column_3="approuve";
    public  static final String Column_4="id_patient";
    public  static final String Column_5="nompatient";
    public  static final String Column_6="prenompatient";
    public  static final String Column_7="detailrdv";
    public  static final String Column_8="idrdv";
    public  static final String Column_9="dateann";
    public  static final String Column_10="urlimage";
    public  static final String Column_11="numtel";
    public  static final String Column_12="proff";
  public  static final String Column_Id="ID";
    public  static final String DataBase_drop="DROP TABLE ";
    public  static final String v=";";
    public  static final int DataBase_version=1;

    public  static final String DataBase_crate="CREATE TABLE " + Table_match + " (" +
            Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Column_1 + " TEXT," + Column_2 + " TEXT," + Column_3+ " TEXT," +Column_4+ " INTEGER," +Column_5+ " TEXT," +Column_6+ " TEXT," + Column_7 + " TEXT," + Column_8 + " TEXT," + Column_9+ " TEXT," + Column_10 + "  TEXT," + Column_11 + "  TEXT," + Column_12 + "  TEXT)";

    public Databaseceate(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase_crate+";");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBase_drop + Table_match +v);
        onCreate(db);
    }
}
