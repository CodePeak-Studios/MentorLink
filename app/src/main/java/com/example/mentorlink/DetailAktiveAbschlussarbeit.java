package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailAktiveAbschlussarbeit extends AppCompatActivity implements IKonstanten {


    private Spinner spnDetailAbschlussarbeitKategorie;
    private Spinner spnDetailAbschlussarbeitStatus;
    private Button btnSearchZweitgutachter;
    private Button btnSearchStudent;
    private TextView edtDetailAbschlussarbeitZweitgutachterMail;
    private TextView tvDetailAbschlussarbeitZweitgutachterName;
    private TextView edtDetailAbschlussarbeitStudentMail;
    private TextView tvDetailAbschlussarbeitStudentName;
    private Button btnDetailAbschlussarbeitSpeichern;
    private Button btnDetailAbschlussarbeitLoeschen;
    private EditText edtDetailAbschlussarbeitKurzbeschreibung;

    private TextView tvDetailAbschlussarbeitUeberschrift;
    private TextView tvDetailAbschlussarbeitBetreuer;




    private DBHandler dbHandler;

    private int intentIdUser;
    private int intentAbschlussarbeit;
    private Abschlussarbeit geladeneAbschlussarbeit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_abschlussarbeit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spnDetailAbschlussarbeitKategorie = findViewById(R.id.spnDetailAbschlussarbeitKategorie);
        spnDetailAbschlussarbeitStatus = findViewById(R.id.spnDetailAbschlussarbeitStatus);
        btnSearchZweitgutachter = findViewById(R.id.btnSearchZweitgutachterDetailAbschlussarbeiten);
        btnSearchStudent = findViewById(R.id.btnSearchStudentDetailAbschlussarbeiten);
        dbHandler = new DBHandler(getApplicationContext());
        edtDetailAbschlussarbeitZweitgutachterMail = findViewById(R.id.edtDetailAbschlussarbeitZweitgutachterMail);
        tvDetailAbschlussarbeitZweitgutachterName = findViewById(R.id.tvDetailAbschlussarbeitZweitgutachterName);
        edtDetailAbschlussarbeitStudentMail = findViewById(R.id.edtDetailAbschlussarbeitStudentMail);
        tvDetailAbschlussarbeitStudentName = findViewById(R.id.tvDetailAbschlussarbeitStudentName);
        btnDetailAbschlussarbeitSpeichern = findViewById(R.id.btnSpeichernDetailAbschlussarbeit);
        btnDetailAbschlussarbeitLoeschen = findViewById(R.id.btnLoeschenDetailAbschlussarbeit);
        edtDetailAbschlussarbeitKurzbeschreibung = findViewById(R.id.edtDetailAbschlussarbeitKurzbeschreibung);
        tvDetailAbschlussarbeitUeberschrift = findViewById(R.id.edtDetailAbschlussarbeitUeberschrift);
        tvDetailAbschlussarbeitBetreuer = findViewById(R.id.tvDetailAbschlussarbeitBetreuerName);


        Intent intentVonVorschlaege = getIntent();

        intentIdUser = intentVonVorschlaege.getIntExtra("idUser", -1);
        intentAbschlussarbeit = intentVonVorschlaege.getIntExtra("AbschlussarbeitId", -1);

        try {
            geladeneAbschlussarbeit = dbHandler.getAbschlussarbeitNachID(intentAbschlussarbeit);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Es gab einen Fehler beim Laden des Vorschlages.\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        this.loadSpinner();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), AktiveArbeiten.class);
                startActivity(i);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        tvDetailAbschlussarbeitUeberschrift.setText(geladeneAbschlussarbeit.getUeberschrift());
        edtDetailAbschlussarbeitKurzbeschreibung.setText(geladeneAbschlussarbeit.getKurzbeschreibung());
        spnDetailAbschlussarbeitKategorie.setSelection(geladeneAbschlussarbeit.getKategorie()-1);
        spnDetailAbschlussarbeitStatus.setSelection(geladeneAbschlussarbeit.getStatus()-1);
        tvDetailAbschlussarbeitBetreuer.setText(dbHandler.getUserNachID(geladeneAbschlussarbeit.getBetreuer()).getVorname()
                + " " + dbHandler.getUserNachID(geladeneAbschlussarbeit.getBetreuer()).getNachname());
        edtDetailAbschlussarbeitZweitgutachterMail.setText((dbHandler.getUserNachID(geladeneAbschlussarbeit.getZweitgutachter())).getMail());
        edtDetailAbschlussarbeitStudentMail.setText((dbHandler.getUserNachID(geladeneAbschlussarbeit.getStudent())).getMail());
        tvDetailAbschlussarbeitZweitgutachterName.setText(dbHandler.getUserNachID(geladeneAbschlussarbeit.getZweitgutachter()).getVorname()
                + " " + dbHandler.getUserNachID(geladeneAbschlussarbeit.getZweitgutachter()).getNachname());
        tvDetailAbschlussarbeitStudentName.setText(dbHandler.getUserNachID(geladeneAbschlussarbeit.getStudent()).getVorname()
                + " " + dbHandler.getUserNachID(geladeneAbschlussarbeit.getStudent()).getNachname());


        btnSearchZweitgutachter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = dbHandler.getUserNachMail(edtDetailAbschlussarbeitZweitgutachterMail.getText().toString());
                    tvDetailAbschlussarbeitZweitgutachterName.setText(user.getVorname() + " " + user.getNachname());
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage() +
                                    "\n Der User konnte anscheinend nicht aus der DB zugeordnet werden.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSearchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = dbHandler.getUserNachMail(edtDetailAbschlussarbeitStudentMail.getText().toString());
                    tvDetailAbschlussarbeitStudentName.setText(user.getVorname() + " " + user.getNachname());
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage() +
                                    "\n Der User konnte anscheinend nicht aus der DB zugeordnet werden.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        //Loescht eine Abschlussarbeit
        btnDetailAbschlussarbeitLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dbHandler.deleteAbschlussarbeit(geladeneAbschlussarbeit.getId());
                Intent intent = new Intent(getApplicationContext(), AktiveArbeiten.class);
                startActivity(intent);
            }
        });



        //Pruefen, ob benoetigte Felder ausgefuellt wurden
        btnDetailAbschlussarbeitSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDetailAbschlussarbeitKurzbeschreibung.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Es wurden nicht alle benoetigten Felder ausgefüllt.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Abschlussarbeit updateAbschlussarbeit = new Abschlussarbeit();
                    updateAbschlussarbeit.setId(geladeneAbschlussarbeit.getId());
                    updateAbschlussarbeit.setUeberschrift(geladeneAbschlussarbeit.getUeberschrift());
                    updateAbschlussarbeit.setBetreuer(geladeneAbschlussarbeit.getBetreuer());
                    updateAbschlussarbeit.setKurzbeschreibung(edtDetailAbschlussarbeitKurzbeschreibung.getText().toString());
                    if(!(edtDetailAbschlussarbeitZweitgutachterMail.getText().toString().isEmpty()))
                    {
                        updateAbschlussarbeit.setZweitgutachter(dbHandler.getUserNachMail(edtDetailAbschlussarbeitZweitgutachterMail.getText().toString()).getId());
                    }
                    else
                    {
                        if(!(geladeneAbschlussarbeit.getZweitgutachter() == 0)){updateAbschlussarbeit.setZweitgutachter(geladeneAbschlussarbeit.getZweitgutachter());}
                        else {updateAbschlussarbeit.setZweitgutachter(0);}
                    }
                    if(!(edtDetailAbschlussarbeitStudentMail.getText().toString().isEmpty()))
                    {
                        updateAbschlussarbeit.setStudent(dbHandler.getUserNachMail(edtDetailAbschlussarbeitStudentMail.getText().toString()).getId());
                    }
                    else
                    {
                        if(!(geladeneAbschlussarbeit.getStudent() == 0)){updateAbschlussarbeit.setStudent(geladeneAbschlussarbeit.getStudent());}
                        else {updateAbschlussarbeit.setStudent(0);}
                    }

                    updateAbschlussarbeit.setKategorie(spnDetailAbschlussarbeitKategorie.getSelectedItem().toString());
                    updateAbschlussarbeit.setStatus(spnDetailAbschlussarbeitStatus.getSelectedItem().toString());
                    dbHandler.updateAbschlussarbeit(updateAbschlussarbeit);
                    Intent intent = new Intent(getApplicationContext(), AktiveArbeiten.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadSpinner()
    {
        //Spinner werden mit vorgegebene Werten aus dem Interface belegt
        String[] arrKat = new String[]
                {
                        IKonstanten.KAT_ARCBAU,
                        IKonstanten.KAT_DESMED,
                        IKonstanten.KAT_GESSOZ,
                        IKonstanten.KAT_ITTEC,
                        IKonstanten.KAT_MARKOM,
                        IKonstanten.KAT_PERREC,
                        IKonstanten.KAT_PAEPSY,
                        IKonstanten.KAT_TOUHOS,
                        IKonstanten.KAT_WIRMAN
                };
        String[] arrStat = new String[]
                {
                        IKonstanten.STAT_OFFEN,
                        IKonstanten.STAT_IN_ABSTIMMUNG,
                        IKonstanten.STAT_ANGEMELDET,
                        IKonstanten.STAT_IN_BEARBEITUNG,
                        IKonstanten.STAT_ABGEGEBEN,
                        IKonstanten.STAT_IN_KORREKTUR,
                        IKonstanten.STAT_KOLLOQ_TERMINIERT,
                        IKonstanten.STAT_KOLLOQ_BEENDET,
                        IKonstanten.STAT_RECHNUNG_GESTELLT,
                        IKonstanten.STAT_RECHNUNG_BEGLICHEN
                };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrKat);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDetailAbschlussarbeitKategorie.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrStat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDetailAbschlussarbeitStatus.setAdapter(adapter2);
    }
}