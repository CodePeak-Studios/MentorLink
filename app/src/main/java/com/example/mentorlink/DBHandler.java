package com.example.mentorlink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    /**********************************************************************************************
     |                                 User Getter                                                |
     **********************************************************************************************/

    public User getUser() {

        User user = new User();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_FIRST + " LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            user.setId(cursor.getInt(0));
            user.setVorname(cursor.getString(1));
            user.setNachname(cursor.getString(2));
            user.setMail(cursor.getString(3));
            user.setPasswort(cursor.getString(4));
            user.setTeamsUser(cursor.getString(5));
            user.setRolle(cursor.getInt(6));
            user.setAuslastung(cursor.getInt(7));
            user.setFachbereiche(cursor.getString(8));

            cursor.close();
        }
        return user;
    }

    //nach ID Filtern um eine Person anzuzeigen

    public User getUserNachID(int userID) {

        User user = new User();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_FIRST + " WHERE " + col_ID + " = " + userID + " LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            user.setId(cursor.getInt(0));
            user.setVorname(cursor.getString(1));
            user.setNachname(cursor.getString(2));
            user.setMail(cursor.getString(3));
            user.setPasswort(cursor.getString(4));
            user.setTeamsUser(cursor.getString(5));
            user.setRolle(cursor.getInt(6));
            user.setAuslastung(cursor.getInt(7));
            user.setFachbereiche(cursor.getString(8));

            cursor.close();
        }
        return user;
    }

    //alle User nach Rolle Filtern

    public ArrayList<User> getUsersNachRolle(int rolle) {

        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_FIRST + " WHERE " + col_ROLLE + " = " + rolle;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setVorname(cursor.getString(1));
                user.setNachname(cursor.getString(2));
                user.setMail(cursor.getString(3));
                user.setPasswort(cursor.getString(4));
                user.setTeamsUser(cursor.getString(5));
                user.setRolle(cursor.getInt(6));
                user.setAuslastung(cursor.getInt(7));
                user.setFachbereiche(cursor.getString(8));
                users.add(user);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return users;
    }

    //alle User nach Fachbereiche Filtern

    public ArrayList<User> getUsersNachFachbereich(String fachbereich) {

        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_FIRST + " WHERE " + col_FACHBEREICHE + " = " + fachbereich;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setVorname(cursor.getString(1));
                user.setNachname(cursor.getString(2));
                user.setMail(cursor.getString(3));
                user.setPasswort(cursor.getString(4));
                user.setTeamsUser(cursor.getString(5));
                user.setRolle(cursor.getInt(6));
                user.setAuslastung(cursor.getInt(7));
                user.setFachbereiche(cursor.getString(8));
                users.add(user);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return users;
    }

    /**********************************************************************************************
     |                                 User Update                                                |
     **********************************************************************************************/

    public void updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_VORNAME, user.getVorname());
        values.put(col_NACHNAME, user.getNachname());
        values.put(col_EMAIL, user.getMail());
        values.put(col_PASSWORT, user.getPasswort());
        values.put(col_TEAMSNAME, user.getTeamsUser());
        values.put(col_ROLLE, user.getRolle());
        values.put(col_AUSLASTUNG, user.getAuslastung());
        values.put(col_FACHBEREICHE, user.getFachbereiche());

        db.update(Table_FIRST, values, col_ID + "= " + user.getId(), null);
        db.close();
    }

    /**********************************************************************************************
     |                                 Abschlussarbeiten Getter                                   |
     **********************************************************************************************/

    public Abschlussarbeit getAbschlussarbeit() {

        Abschlussarbeit abschlussarbeit = new Abschlussarbeit();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND + " LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            abschlussarbeit.setId(cursor.getInt(0));
            abschlussarbeit.setKategorie(cursor.getInt(1));
            abschlussarbeit.setUeberschrift(cursor.getString(2));
            abschlussarbeit.setKurzbeschreibung(cursor.getString(3));
            abschlussarbeit.setStudent(cursor.getInt(4));
            abschlussarbeit.setBetreuer(cursor.getInt(5));
            abschlussarbeit.setZweitgutachter(cursor.getInt(6));
            abschlussarbeit.setStatus(cursor.getInt(7));

            cursor.close();
        }
        return abschlussarbeit;
    }

    //getAbschlussarbeit mit Parameter UserID (einzeln und mehrere zurückgeben)

    public Abschlussarbeit getAbschlussarbeitNachUserID(int userID) {

        Abschlussarbeit abschlussarbeit = new Abschlussarbeit();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND
                + " WHERE " + col_STUDENT + " = " + userID
                + " OR " + col_BETREUER + " = " + userID
                + " OR " + col_ZWEITGUTACHTER + " = " + userID + " LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            abschlussarbeit.setId(cursor.getInt(0));
            abschlussarbeit.setKategorie(cursor.getInt(1));
            abschlussarbeit.setUeberschrift(cursor.getString(2));
            abschlussarbeit.setKurzbeschreibung(cursor.getString(3));
            abschlussarbeit.setStudent(cursor.getInt(4));
            abschlussarbeit.setBetreuer(cursor.getInt(5));
            abschlussarbeit.setZweitgutachter(cursor.getInt(6));
            abschlussarbeit.setStatus(cursor.getInt(7));

            cursor.close();
        }
        return abschlussarbeit;
    }

    public ArrayList<Abschlussarbeit> getAlleAbschlussarbeitenNachUserID(int userID) {

        ArrayList<Abschlussarbeit> abschlussarbeiten = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND
                + " WHERE " + col_STUDENT + " = " + userID
                + " OR " + col_BETREUER + " = " + userID
                + " OR " + col_ZWEITGUTACHTER + " = " + userID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Abschlussarbeit abschlussarbeit = new Abschlussarbeit();
                abschlussarbeit.setId(cursor.getInt(0));
                abschlussarbeit.setKategorie(cursor.getInt(1));
                abschlussarbeit.setUeberschrift(cursor.getString(2));
                abschlussarbeit.setKurzbeschreibung(cursor.getString(3));
                abschlussarbeit.setStudent(cursor.getInt(4));
                abschlussarbeit.setBetreuer(cursor.getInt(5));
                abschlussarbeit.setZweitgutachter(cursor.getInt(6));
                abschlussarbeit.setStatus(cursor.getInt(7));
                abschlussarbeiten.add(abschlussarbeit);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return abschlussarbeiten;
    }

    //getAbschlussarbeit mit nach Parameter userID und status (mehrere Abschlussarbeit)

    public ArrayList<Abschlussarbeit> getAlleAbschlussarbeitenNachUserIDUndStatus(int userID, int status) {

        ArrayList<Abschlussarbeit> abschlussarbeiten = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND
                + " WHERE " + col_STUDENT + " = " + userID
                + " OR " + col_BETREUER + " = " + userID
                + " OR " + col_ZWEITGUTACHTER + " = " + userID
                + " AND " + col_STATUS + " = " + status;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Abschlussarbeit abschlussarbeit = new Abschlussarbeit();
                abschlussarbeit.setId(cursor.getInt(0));
                abschlussarbeit.setKategorie(cursor.getInt(1));
                abschlussarbeit.setUeberschrift(cursor.getString(2));
                abschlussarbeit.setKurzbeschreibung(cursor.getString(3));
                abschlussarbeit.setStudent(cursor.getInt(4));
                abschlussarbeit.setBetreuer(cursor.getInt(5));
                abschlussarbeit.setZweitgutachter(cursor.getInt(6));
                abschlussarbeit.setStatus(cursor.getInt(7));
                abschlussarbeiten.add(abschlussarbeit);
            } while (cursor.moveToNext());

            cursor.close();

        }
        return abschlussarbeiten;
    }


    //getAbschlussarbeit mit Parameter Status (mehrere zurückgeben)

    public ArrayList<Abschlussarbeit> getAlleAbschlussarbeitenNachStatus(int status) {

        ArrayList<Abschlussarbeit> abschlussarbeiten = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND + " WHERE " + col_STATUS + " = " + status;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Abschlussarbeit abschlussarbeit = new Abschlussarbeit();
                abschlussarbeit.setId(cursor.getInt(0));
                abschlussarbeit.setKategorie(cursor.getInt(1));
                abschlussarbeit.setUeberschrift(cursor.getString(2));
                abschlussarbeit.setKurzbeschreibung(cursor.getString(3));
                abschlussarbeit.setStudent(cursor.getInt(4));
                abschlussarbeit.setBetreuer(cursor.getInt(5));
                abschlussarbeit.setZweitgutachter(cursor.getInt(6));
                abschlussarbeit.setStatus(cursor.getInt(7));
                abschlussarbeiten.add(abschlussarbeit);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return abschlussarbeiten;
    }

    //getAbschlussarbeit mit Parameter Abschlussarbeit ID (einzelne Abschlussarbeit)

    public Abschlussarbeit getAbschlussarbeitNachID(int id) {

        Abschlussarbeit abschlussarbeit = new Abschlussarbeit();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND + " WHERE " + col_ID_ABSCHLUSSARBEITEN + " = " + id + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            abschlussarbeit.setId(cursor.getInt(0));
            abschlussarbeit.setKategorie(cursor.getInt(1));
            abschlussarbeit.setUeberschrift(cursor.getString(2));
            abschlussarbeit.setKurzbeschreibung(cursor.getString(3));
            abschlussarbeit.setStudent(cursor.getInt(4));
            abschlussarbeit.setBetreuer(cursor.getInt(5));
            abschlussarbeit.setZweitgutachter(cursor.getInt(6));
            abschlussarbeit.setStatus(cursor.getInt(7));

            cursor.close();
        }
        return abschlussarbeit;
    }

    /**********************************************************************************************
     |                                 Abschlussarbeiten Update                                   |
     **********************************************************************************************/

    public void updateAbschlussarbeit(Abschlussarbeit abschlussarbeit) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_KATEGORIE, abschlussarbeit.getKategorie());
        values.put(col_UEBERSCHRIFT, abschlussarbeit.getUeberschrift());
        values.put(col_FACHBEREICHE, abschlussarbeit.getKurzbeschreibung());
        values.put(col_STUDENT, abschlussarbeit.getStudent());
        values.put(col_BETREUER, abschlussarbeit.getBetreuer());
        values.put(col_ZWEITGUTACHTER, abschlussarbeit.getZweitgutachter());
        values.put(col_STATUS, abschlussarbeit.getStatus());

        db.update(Table_SECOND, values, col_ID_ABSCHLUSSARBEITEN + "= " + abschlussarbeit.getId(), null);
        db.close();
    }

}
