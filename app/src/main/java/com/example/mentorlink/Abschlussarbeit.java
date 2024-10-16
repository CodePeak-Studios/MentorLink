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

    public Abschlussarbeit(int kategorie, String ueberschrift, String kurzbeschreibung, int betreuer, int status)
    {
        this.kategorie = kategorie;
        this.ueberschrift = ueberschrift;
        this.kurzbeschreibung = kurzbeschreibung;
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

    /**********************************************************************************************
     |                                       Funktionen                                            |
     **********************************************************************************************/

    public String getKategorieName(int kategorie)
    {
        String kategorieName;

        switch(status)
        {
            case 1:
                kategorieName = "Architektur & Bau";
                break;
            case 2:
                kategorieName = "Design & Medien";
                break;
            case 3:
                kategorieName = "Gesundheit & Soziales";
                break;
            case 4:
                kategorieName = "IT & Technik";
                break;
            case 5:
                kategorieName = "Marketing & Kommunikation";
                break;
            case 6:
                kategorieName = "Personal & Recht";
                break;
            case 7:
                kategorieName = "Pädagogik & Psychologie";
                break;
            case 8:
                kategorieName = "Tourismus & Hospitality";
                break;
            case 9:
                kategorieName = "Wirtschaft & Management";
                break;
            default:
                kategorieName = "Kategoriefehler";
        }
        return kategorieName;
    }


    public String getStatusName(int status)
    {
        String statusName;

        switch(status)
        {
            case 1:
                statusName = "Offen";
                break;
            case 2:
                statusName = "in Abstimmung";
                break;
            case 3:
                statusName = "Angemeldet";
                break;
            case 4:
                statusName = "in Bearbeitung";
                break;
            case 5:
                statusName = "Abgegeben";
                break;
            case 6:
                statusName = "in Korrektur";
                break;
            case 7:
                statusName = "Kolloquium terminiert";
                break;
            case 8:
                statusName = "Kolloquium beendet - in Abrechnung";
                break;
            case 9:
                statusName = "Rechnung gestellt";
                break;
            case 10:
                statusName = "Rechnung beglichen";
                break;
            default:
                statusName = "Statusfehler";
        }
        return statusName;
    }
}
