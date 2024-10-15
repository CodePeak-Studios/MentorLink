package com.example.mentorlink;

public class Abschlussarbeit
{
    private int id;
    private int kategorie;
    private String ueberschrift;
    private String kurzbeschreibung;
    private int student;
    private int betreuer;
    private int zweitgutachter;
    private int status;

    /**********************************************************************************************
     |                                      Konstruktoren                                          |
     **********************************************************************************************/

    public Abschlussarbeit()
    {

    }

    public Abschlussarbeit(int kategorie, String ueberschrift, int betreuer, int status)
    {
        this.kategorie = kategorie;
        this.ueberschrift = ueberschrift;
        this.betreuer = betreuer;
        this.status = status;
    }

    /**********************************************************************************************
     |                                         Getter                                              |
     **********************************************************************************************/

    public int getId() {
        return id;
    }

    public int getKategorie() {
        return kategorie;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public String getKurzbeschreibung() {
        return kurzbeschreibung;
    }

    public int getStudent() {
        return student;
    }

    public int getBetreuer() {
        return betreuer;
    }

    public int getZweitgutachter() {
        return zweitgutachter;
    }

    public int getStatus() {
        return status;
    }



    /**********************************************************************************************
     |                                         Setter                                              |
     **********************************************************************************************/

    public void setId(int id) {
        this.id = id;
    }

    public void setKategorie(int kategorie) {
        this.kategorie = kategorie;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public void setKurzbeschreibung(String kurzbeschreibung) {
        this.kurzbeschreibung = kurzbeschreibung;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public void setBetreuer(int betreuer) {
        this.betreuer = betreuer;
    }

    public void setZweitgutachter(int zweitgutachter) {
        this.zweitgutachter = zweitgutachter;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
