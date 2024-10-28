package com.example.mentorlink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_Name = "MentorLinkDB";
    private static final int DB_Version = 1;

    //Tabelle für den User (Studenten und Betreuer)
    private static final String Table_FIRST = "[MentorLink].[dbo].[User]"; //A
    private static final String col_ID = "id";
    private static final String col_VORNAME = "vorname";
    private static final String col_NACHNAME = "nachname";
    private static final String col_EMAIL = "email";
    private static final String col_PASSWORT = "passwort";
    private static final String col_TEAMSNAME = "teamsname"; //A
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
    private static final String col_STATUS_NAME = "statusname"; //A

    //Tabelle für alle Kategorien/Themengebiete der Hochschule
    private static final String Table_FOURTH = "Kategorien";
    private static final String col_ID_KATEGORIEN = "id";
    private static final String col_KATEGORIEN_NAME = "kategoriename"; //A


    private static String ip_SQL = "192.168.178.31";// this is the host ip that your data base exists on you can use 10.0.2.2 for local host found on your pc. use if config for windows to find the ip if the database exists on your pc
    private static String port_SQL = "1433";// the port sql server runs on
    private static String Classes_SQL = "net.sourceforge.jtds.jdbc.Driver";// the driver that is required for this connection
    private static String database_SQL = "MentorLink";// the data base name
    private static String username_SQL = "mentorapp";// the user name
    private static String password_SQL = "password";// the password
    private static String url_SQL = "jdbc:jtds:sqlserver://"+ip_SQL+":"+port_SQL+"/"+database_SQL; // the connection url string
    private Connection connection = null;

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

        try{ Class.forName(Classes_SQL);connection = DriverManager.getConnection(url_SQL, username_SQL, password_SQL);
        } catch(ClassNotFoundException e) {e.printStackTrace();} catch (SQLException throwables) {throwables.printStackTrace();}

        if(connection!=null)
        {
            Statement statement = null;

            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT TOP(1)" + col_ID + ", " + col_VORNAME + ", " +
                        col_NACHNAME + ", " + col_EMAIL + ", " + col_PASSWORT + ", " + col_TEAMSNAME + ", " +
                        col_ROLLE + ", " + col_AUSLASTUNG + ", " + col_FACHBEREICHE + " FROM " + Table_FIRST);
                if((resultSet.next()))
                {
                    user.setId(resultSet.getInt(1));
                    user.setVorname(resultSet.getString(2));
                    user.setNachname(resultSet.getString(3));
                    user.setMail(resultSet.getString(4));
                    user.setPasswort(resultSet.getString(5));
                    user.setTeamsUser(resultSet.getString(6));
                    user.setRolle(resultSet.getInt(7));
                    user.setAuslastung(resultSet.getInt(8));
                    user.setFachbereiche(resultSet.getString(9));;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            Log.d("User nicht gefunden", "User konnte nicht geladen werden.");
        }
        return user;
    }


    //nach ID Filtern um eine Person anzuzeigen

    public User getUserNachID(int userID) {

        User user = new User();

        try{ Class.forName(Classes_SQL);connection = DriverManager.getConnection(url_SQL, username_SQL, password_SQL);
        } catch(ClassNotFoundException e) {e.printStackTrace();} catch (SQLException throwables) {throwables.printStackTrace();}

        if(connection!=null)
        {
            Statement statement = null;

            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT TOP(1)" + col_ID + ", " + col_VORNAME + ", " +
                        col_NACHNAME + ", " + col_EMAIL + ", " + col_PASSWORT + ", " + col_TEAMSNAME + ", " +
                        col_ROLLE + ", " + col_AUSLASTUNG + ", " + col_FACHBEREICHE + " FROM " + Table_FIRST +
                        " WHERE " + col_ID + " = " + userID);
                if((resultSet.next()))
                {
                    user.setId(resultSet.getInt(1));
                    user.setVorname(resultSet.getString(2));
                    user.setNachname(resultSet.getString(3));
                    user.setMail(resultSet.getString(4));
                    user.setPasswort(resultSet.getString(5));
                    user.setTeamsUser(resultSet.getString(6));
                    user.setRolle(resultSet.getInt(7));
                    user.setAuslastung(resultSet.getInt(8));
                    user.setFachbereiche(resultSet.getString(9));;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            Log.d("User nicht gefunden", "User konnte nicht geladen werden.");
        }
        return user;
    }

    //nach ID Filtern um eine Person anzuzeigen

    public User getUserNachMail(String email) {

        User user = new User();
        String mailForQuery = "'" + email + "'";

        try{ Class.forName(Classes_SQL);connection = DriverManager.getConnection(url_SQL, username_SQL, password_SQL);
        } catch(ClassNotFoundException e) {e.printStackTrace();} catch (SQLException throwables) {throwables.printStackTrace();}

        if(connection!=null)
        {
            Statement statement = null;

            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT TOP(1)" + col_ID + ", " + col_VORNAME + ", " +
                        col_NACHNAME + ", " + col_EMAIL + ", " + col_PASSWORT + ", " + col_TEAMSNAME + ", " +
                        col_ROLLE + ", " + col_AUSLASTUNG + ", " + col_FACHBEREICHE + " FROM " + Table_FIRST +
                        " WHERE " + col_EMAIL + " = " + mailForQuery);
                if((resultSet.next()))
                {
                    user.setId(resultSet.getInt(1));
                    user.setVorname(resultSet.getString(2));
                    user.setNachname(resultSet.getString(3));
                    user.setMail(resultSet.getString(4));
                    user.setPasswort(resultSet.getString(5));
                    user.setTeamsUser(resultSet.getString(6));
                    user.setRolle(resultSet.getInt(7));
                    user.setAuslastung(resultSet.getInt(8));
                    user.setFachbereiche(resultSet.getString(9));;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            Log.d("User nicht gefunden", "User konnte nicht geladen werden.");
        }
        return user;
    }

    //alle User nach Rolle Filtern

    public ArrayList<User> getUsersNachRolle(int rolle) {

        ArrayList<User> users = new ArrayList<>();

        try{ Class.forName(Classes_SQL);connection = DriverManager.getConnection(url_SQL, username_SQL, password_SQL);
        } catch(ClassNotFoundException e) {e.printStackTrace();} catch (SQLException throwables) {throwables.printStackTrace();}

        if(connection!=null)
        {
            Statement statement = null;

            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT " + col_ID + ", " + col_VORNAME + ", " +
                        col_NACHNAME + ", " + col_EMAIL + ", " + col_PASSWORT + ", " + col_TEAMSNAME + ", " +
                        col_ROLLE + ", " + col_AUSLASTUNG + ", " + col_FACHBEREICHE + " FROM " + Table_FIRST +
                        " WHERE " + col_ROLLE + " = " + rolle);
                if((resultSet.next()))
                {
                    do {
                        User user = new User();
                        user.setId(resultSet.getInt(1));
                        user.setVorname(resultSet.getString(2));
                        user.setNachname(resultSet.getString(3));
                        user.setMail(resultSet.getString(4));
                        user.setPasswort(resultSet.getString(5));
                        user.setTeamsUser(resultSet.getString(6));
                        user.setRolle(resultSet.getInt(7));
                        user.setAuslastung(resultSet.getInt(8));
                        user.setFachbereiche(resultSet.getString(9));
                        users.add(user);
                    } while (resultSet.next());

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            Log.d("Keine User gefunden", "User konnten nicht geladen werden.");
        }
        return users;
    }

    public User checkPassword(String email, String password) {

        String hashedPassword = get_SHA_256_SecurePassword(password, "sprachmeister");
        User user = new User();
//        String mailForQuery = "'" + email + "'";

        try {
            Class.forName(Classes_SQL);
            connection = DriverManager.getConnection(url_SQL, username_SQL, password_SQL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (connection != null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                String query = "SELECT TOP(1) " + col_ID + ", " + col_VORNAME + ", " + col_NACHNAME + ", " +
                        col_EMAIL + ", " + col_PASSWORT + ", " + col_TEAMSNAME + ", " + col_ROLLE +
                        ", " + col_AUSLASTUNG + ", " + col_FACHBEREICHE + " FROM " + Table_FIRST +
                        " WHERE " + col_EMAIL + " = ? AND " + col_PASSWORT + " = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, hashedPassword);

                resultSet = preparedStatement.executeQuery();

                if ((resultSet.next())) {
                    user.setId(resultSet.getInt(1));
                    user.setVorname(resultSet.getString(2));
                    user.setNachname(resultSet.getString(3));
                    user.setMail(resultSet.getString(4));
                    user.setPasswort(resultSet.getString(5));
                    user.setTeamsUser(resultSet.getString(6));
                    user.setRolle(resultSet.getInt(7));
                    user.setAuslastung(resultSet.getInt(8));
                    user.setFachbereiche(resultSet.getString(9));
                    ;
                } else {
                    System.out.println("Benutzer nicht gefunden oder Passwort stimmt nicht überein.");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) resultSet.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    private static String get_SHA_256_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = "'" + sb.toString() + "'";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    /**********************************************************************************************
     |                                 User Update                                                |
     **********************************************************************************************/

    public void updateUser(User user) {

        try {Class.forName(Classes_SQL);connection = DriverManager.getConnection(url_SQL, username_SQL, password_SQL);
        } catch (ClassNotFoundException e) {e.printStackTrace();} catch (SQLException throwables) {throwables.printStackTrace();}

        if (connection != null) {
            PreparedStatement preparedStatement = null;
            String updateQuery = "UPDATE " + Table_FIRST + " SET " +
                    col_VORNAME + " = ?, " +
                    col_NACHNAME + " = ?, " +
                    col_EMAIL + " = ?, " +
                    col_PASSWORT + " = ?, " +
                    col_TEAMSNAME + " = ?, " +
                    col_ROLLE + " = ?, " +
                    col_AUSLASTUNG + " = ?, " +
                    col_FACHBEREICHE + " = ? " +
                    "WHERE " + col_ID + " = ?";

            try {
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, user.getVorname());
                preparedStatement.setString(2, user.getNachname());
                preparedStatement.setString(3, user.getMail());
                preparedStatement.setString(4, user.getPasswort());
                preparedStatement.setString(5, user.getTeamsUser());
                preparedStatement.setInt(6, user.getRolle());
                preparedStatement.setInt(7, user.getAuslastung());
                preparedStatement.setString(8, user.getFachbereiche());
                preparedStatement.setInt(9, user.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Verbindung zu SQL Server konnte nicht hergestellt werden.");
        }
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
                + " WHERE " + "(" + col_STUDENT + " = " + userID
                + " OR " + col_BETREUER + " = " + userID
                + " OR " + col_ZWEITGUTACHTER + " = " + userID + ")"
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

    public ArrayList<Abschlussarbeit> getAlleAktivenAbschlussarbeitenNachUserIDUndStatus(int userID) {

        ArrayList<Abschlussarbeit> abschlussarbeiten = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Table_SECOND
                + " WHERE " + "(" + col_BETREUER + " = " + userID
                + " OR " + col_ZWEITGUTACHTER + " = " + userID + ")"
                + " AND " + col_STATUS + " IS NOT " + 1;

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
        values.put(col_KURZBESCHREIBUNG, abschlussarbeit.getKurzbeschreibung());
        values.put(col_STUDENT, abschlussarbeit.getStudent());
        values.put(col_BETREUER, abschlussarbeit.getBetreuer());
        values.put(col_ZWEITGUTACHTER, abschlussarbeit.getZweitgutachter());
        values.put(col_STATUS, abschlussarbeit.getStatus());

        db.update(Table_SECOND, values, col_ID_ABSCHLUSSARBEITEN + "= " + abschlussarbeit.getId(), null);
        db.close();
    }

    /**********************************************************************************************
     |                                 Abschlussarbeiten DELETE                                    |
     **********************************************************************************************/

    public void deleteAbschlussarbeit(int abschlussarbeitId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_SECOND, "ID=" + Integer.toString(abschlussarbeitId), null);
        db.close();
    }

    /**********************************************************************************************
     |                                 Abschlussarbeiten CREATE                                    |
     **********************************************************************************************/

    public void addAbschlussarbeit(Abschlussarbeit newabschlussarbeit)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_UEBERSCHRIFT, newabschlussarbeit.getUeberschrift());
        values.put(col_KURZBESCHREIBUNG, newabschlussarbeit.getKurzbeschreibung());
        values.put(col_KATEGORIE, newabschlussarbeit.getKategorie());
        values.put(col_STATUS, newabschlussarbeit.getStatus());
        values.put(col_BETREUER, newabschlussarbeit.getBetreuer());
        values.put(col_ZWEITGUTACHTER, newabschlussarbeit.getZweitgutachter());
        values.put(col_STUDENT, newabschlussarbeit.getStudent());

        db.insert(Table_SECOND, null, values);

        db.close();
    }
}
