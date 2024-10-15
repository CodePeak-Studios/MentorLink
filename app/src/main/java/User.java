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

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getMail() {
        return mail;
    }

    /**********************************************************************************************
     |                                          Setter                                             |
     **********************************************************************************************/

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTeamsUser() {
        return teamsUser;
    }

    public void setTeamsUser(String teamsUser) {
        this.teamsUser = teamsUser;
    }

    public int getRolle() {
        return rolle;
    }

    public void setRolle(int rolle) {
        this.rolle = rolle;
    }

    public int getAuslastung() {
        return auslastung;
    }

    public void setAuslastung(int auslastung) {
        this.auslastung = auslastung;
    }

    public String getFachbereiche() {
        return fachbereiche;
    }

    public void setFachbereiche(String fachbereiche) {
        this.fachbereiche = fachbereiche;
    }
}
