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

public class DetailNewAbschlussarbeit extends AppCompatActivity {

    private Spinner spnDetailNewAbschlussarbeitKategorie;
    private Spinner spnDetailNewAbschlussarbeitStatus;
    private Button btnDetailNewAbschlussarbeitSearchZweitgutachter;
    private Button btnDetailNewAbschlussarbeitSearchStudent;
    private TextView edtDetailNewAbschlussarbeitZweitgutachterMail;
    private TextView tvDetailNewAbschlussarbeitZweitgutachterName;
    private TextView edtDetailNewAbschlussarbeitStudentMail;
    private TextView tvDetailNewAbschlussarbeitStudentName;
    private Button btnDetailNewAbschlussarbeitSpeichern;
    private Button btnDetailNewAbschlussarbeitLoeschen;
    private EditText edtDetailNewAbschlussarbeitKurzbeschreibung;
    private EditText edtDetailNewAbschlussarbeitUeberschrift;

    private TextView tvDetailNewAbschlussarbeitUeberschrift;
    private TextView tvDetailNewAbschlussarbeitBetreuer;

    private DBHandler dbHandler;

    private int intentIdUser;
    private int intentAbschlussarbeit;
    private Abschlussarbeit geladeneAbschlussarbeit;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_new_abschlussarbeit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        spnDetailNewAbschlussarbeitKategorie = findViewById(R.id.spnDetailNewAbschlussarbeitKategorie);
        spnDetailNewAbschlussarbeitStatus = findViewById(R.id.spnDetailNewAbschlussarbeitStatus);
        btnDetailNewAbschlussarbeitSearchZweitgutachter = findViewById(R.id.btnSearchZweitgutachterDetailNewAbschlussarbeiten);
        btnDetailNewAbschlussarbeitSearchStudent = findViewById(R.id.btnSearchStudentDetailNewAbschlussarbeiten);
        dbHandler = new DBHandler(getApplicationContext());
        edtDetailNewAbschlussarbeitZweitgutachterMail = findViewById(R.id.edtDetailNewAbschlussarbeitZweitgutachterMail);
        tvDetailNewAbschlussarbeitZweitgutachterName = findViewById(R.id.tvDetailNewAbschlussarbeitZweitgutachterName);
        edtDetailNewAbschlussarbeitStudentMail = findViewById(R.id.edtDetailNewAbschlussarbeitStudentMail);
        tvDetailNewAbschlussarbeitStudentName = findViewById(R.id.tvDetailNewAbschlussarbeitStudentName);
        btnDetailNewAbschlussarbeitSpeichern = findViewById(R.id.btnSpeichernDetailNewAbschlussarbeit);
        edtDetailNewAbschlussarbeitKurzbeschreibung = findViewById(R.id.edtDetailNewAbschlussarbeitKurzbeschreibung);
        tvDetailNewAbschlussarbeitBetreuer = findViewById(R.id.tvDetailNewAbschlussarbeitBetreuerName);
        edtDetailNewAbschlussarbeitUeberschrift = findViewById(R.id.edtDetailNewAbschlussarbeitUeberschrift);

        //TODO Hardcoded User, hier muss der angemeldete hin

        Intent intentvonVorschlaege = getIntent();
        userId = intentvonVorschlaege.getIntExtra("aktiverUser", -1);

        tvDetailNewAbschlussarbeitBetreuer.setText(dbHandler.getUserNachID(userId).getVorname()
                + " " + dbHandler.getUserNachID(userId).getNachname());

        this.loadSpinner();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), Abschlussarbeiten.class);
                startActivity(i);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


        btnDetailNewAbschlussarbeitSearchZweitgutachter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = dbHandler.getUserNachMail(edtDetailNewAbschlussarbeitZweitgutachterMail.getText().toString());
                    tvDetailNewAbschlussarbeitZweitgutachterName.setText(user.getVorname() + " " + user.getNachname());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage() +
                                    "\n Der User konnte anscheinend nicht aus der DB zugeordnet werden.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDetailNewAbschlussarbeitSearchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = dbHandler.getUserNachMail(edtDetailNewAbschlussarbeitStudentMail.getText().toString());
                    tvDetailNewAbschlussarbeitStudentName.setText(user.getVorname() + " " + user.getNachname());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage() +
                                    "\n Der User konnte anscheinend nicht aus der DB zugeordnet werden.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //Pruefen, ob benoetigte Felder ausgefuellt wurden
        btnDetailNewAbschlussarbeitSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDetailNewAbschlussarbeitKurzbeschreibung.getText().toString().isEmpty() ||
                        edtDetailNewAbschlussarbeitUeberschrift.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Es wurden nicht alle benoetigten Felder ausgefüllt.", Toast.LENGTH_LONG).show();
                } else {
                    Abschlussarbeit newAbschlussarbeit = new Abschlussarbeit();
                    newAbschlussarbeit.setUeberschrift(edtDetailNewAbschlussarbeitUeberschrift.getText().toString());
                    newAbschlussarbeit.setBetreuer(dbHandler.getUserNachID(userId).getId()); //TODO Hardcoded User, hier muss der angemeldete hin
                    newAbschlussarbeit.setKurzbeschreibung(edtDetailNewAbschlussarbeitKurzbeschreibung.getText().toString());
                    if (!(edtDetailNewAbschlussarbeitZweitgutachterMail.getText().toString().isEmpty())) {
                        newAbschlussarbeit.setZweitgutachter(dbHandler.getUserNachMail(edtDetailNewAbschlussarbeitZweitgutachterMail.getText().toString()).getId());
                    } else {
                        /*if(!(geladeneAbschlussarbeit.getZweitgutachter() == 0)){newAbschlussarbeit.setZweitgutachter(geladeneAbschlussarbeit.getZweitgutachter());}
                        else {newAbschlussarbeit.setZweitgutachter(0);}*/
                    }
                    if (!(edtDetailNewAbschlussarbeitStudentMail.getText().toString().isEmpty())) {
                        newAbschlussarbeit.setStudent(dbHandler.getUserNachMail(edtDetailNewAbschlussarbeitStudentMail.getText().toString()).getId());
                    } else {
                        /*if(!(geladeneAbschlussarbeit.getStudent() == 0)){newAbschlussarbeit.setStudent(geladeneAbschlussarbeit.getStudent());}
                        else {newAbschlussarbeit.setStudent(0);}*/
                    }

                    newAbschlussarbeit.setKategorie(spnDetailNewAbschlussarbeitKategorie.getSelectedItem().toString());
                    newAbschlussarbeit.setStatus(spnDetailNewAbschlussarbeitStatus.getSelectedItem().toString());
                    dbHandler.addAbschlussarbeit(newAbschlussarbeit);
                    Intent intent = new Intent(getApplicationContext(), Abschlussarbeiten.class);
                    intent.putExtra("aktiverUser", userId);
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
        spnDetailNewAbschlussarbeitKategorie.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrStat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDetailNewAbschlussarbeitStatus.setAdapter(adapter2);
    }

}