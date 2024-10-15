package com.example.mentorlink;

public class User
{

    private int id;
    private String vorname;
    private String nachname;
    private String passwort;
    private String mail;
    private String teamsUser;
    private int rolle;
    private int auslastung;
    private String fachbereiche;


     /**********************************************************************************************
     |                                      Konstruktoren                                          |
     **********************************************************************************************/

     public User()
     {

     }

     public User(String vorname, String nachname, String mail, String passwort, int rolle, int auslastung)
     {
         this.vorname = vorname;
         this.nachname = nachname;
         this.mail = mail;
         this.passwort = passwort;
         this.rolle = rolle;
         this.auslastung = auslastung;
     }

    /**********************************************************************************************
     |                                          Getter                                             |
     **********************************************************************************************/

    public int getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getMail() {
        return mail;
    }

    public String getTeamsUser() {
        return teamsUser;
    }

    public int getRolle() {
        return rolle;
    }

    public int getAuslastung() {
        return auslastung;
    }

    public String getFachbereiche() {
        return fachbereiche;
    }

    /**********************************************************************************************
     |                                          Setter                                             |
     **********************************************************************************************/

    public void setId(int id) {
        this.id = id;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTeamsUser(String teamsUser) {
        this.teamsUser = teamsUser;
    }

    public void setRolle(int rolle) {
        this.rolle = rolle;
    }

    public void setAuslastung(int auslastung) {
        this.auslastung = auslastung;
    }

    public void setFachbereiche(String fachbereiche) {
        this.fachbereiche = fachbereiche;
    }
}
