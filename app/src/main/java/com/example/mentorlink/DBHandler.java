package com.example.mentorlink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_Name = "MentorLinkDB";
    private static final int DB_Version = 1;

    //Tabelle für den User (Studenten und Betreuer)
    private static final String Table_FIRST = "User";
    private static final String col_ID = "id";
    private static final String col_VORNAME = "vorname";
    private static final String col_NACHNAME = "nachname";
    private static final String col_EMAIL = "email";
    private static final String col_PASSWORT = "passwort";
    private static final String col_TEAMSNAME = "teamsName";
    private static final String col_ROLLE = "rolle";
    private static final String col_AUSLASTUNG = "auslastung";
    private static final String col_FACHBEREICHE = "fachbereiche";

    //Tabelle für alle Abschlussarbeiten Ideen und aktive Themen
    private static final String Table_SECOND = "Abschlussarbeiten";
    private static final String col_ID_ABSCHLUSSARBEITEN = "id";
    private static final String col_KATEGORIE = "kategorie";
    private static final String col_UEBERSCHRIFT = "ueberschrift";
    private static final String col_KURZBESCHREIBUNG = "kurzbeschreibung";
    private static final String col_STUDENT = "student";
    private static final String col_BETREUER = "betreuer";
    private static final String col_ZWEITGUTACHTER = "zweitgutachter";
    private static final String col_STATUS = "status";

    //Tabelle für alle Status-Möglichkeiten der Abschlussarbeiten
    private static final String Table_THIRD = "Status";
    private static final String col_ID_STATUS = "id";
    private static final String col_STATUS_NAME = "statusName";

    //Tabelle für alle Kategorien/Themengebiete der Hochschule
    private static final String Table_FOURTH = "Kategorien";
    private static final String col_ID_KATEGORIEN = "id";
    private static final String col_KATEGORIEN_NAME = "kategorieName";

    public DBHandler (Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + Table_FIRST);
//        db.execSQL("DROP TABLE IF EXISTS " + Table_SECOND);
//        db.execSQL("DROP TABLE IF EXISTS " + Table_THIRD);
//        db.execSQL("DROP TABLE IF EXISTS " + Table_FOURTH);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON;");

        String query = "CREATE TABLE IF NOT EXISTS " + Table_FIRST + " ("
                + col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_VORNAME + " TEXT, "
                + col_NACHNAME + " TEXT, "
                + col_EMAIL + " TEXT, "
                + col_PASSWORT + " TEXT, "
                + col_TEAMSNAME + " TEXT, "
                + col_ROLLE + " INTEGER, "
                + col_AUSLASTUNG + " INTEGER, "
                + col_FACHBEREICHE + " TEXT);";

        db.execSQL(query);

        String query2 = "CREATE TABLE IF NOT EXISTS " + Table_SECOND + " ("
                + col_ID_ABSCHLUSSARBEITEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_KATEGORIE + " INTEGER, "
                + col_UEBERSCHRIFT + " TEXT, "
                + col_KURZBESCHREIBUNG + " TEXT, "
                + col_STUDENT + " INTEGER,  "
                + col_BETREUER + " INTEGER, "
                + col_ZWEITGUTACHTER + " INTEGER, "
                + col_STATUS + " INTEGER, "
                + "FOREIGN KEY(" + col_STUDENT + ") REFERENCES " + Table_FIRST + "(" + col_ID + "), "
                + "FOREIGN KEY(" + col_BETREUER + ") REFERENCES " + Table_FIRST + "(" + col_ID + "), "
                + "FOREIGN KEY(" + col_ZWEITGUTACHTER + ") REFERENCES " + Table_FIRST + "(" + col_ID + "), "
                + "FOREIGN KEY(" + col_KATEGORIE + ") REFERENCES " + Table_FOURTH + "(" + col_ID_KATEGORIEN + "), "
                + "FOREIGN KEY(" + col_STATUS + ") REFERENCES " + Table_THIRD + "(" + col_ID_STATUS + "));";

        db.execSQL(query2);

        String query3 = "CREATE TABLE IF NOT EXISTS " + Table_THIRD + " ("
                + col_ID_STATUS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_STATUS_NAME + " TEXT);";

        db.execSQL(query3);

        String query4 = "CREATE TABLE IF NOT EXISTS " + Table_FOURTH + " ("
                + col_ID_KATEGORIEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_KATEGORIEN_NAME + " TEXT);";

        db.execSQL(query4);
    }

}
