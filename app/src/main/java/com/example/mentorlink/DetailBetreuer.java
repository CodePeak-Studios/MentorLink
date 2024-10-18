package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailBetreuer extends AppCompatActivity implements RecyclerViewAdapterBetreuerProfil.ItemClickListener  {

    RecyclerViewAdapterBetreuerProfil adapter;
    DBHandler dbHandler;
    TextView tvProfilName;
    TextView tvProfilAuslastung;
    TextView tvProfilFachbereiche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_betreuer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int betreuerID = intent.getIntExtra("betreuerID", 0);
        betreuerID++;

        dbHandler = new DBHandler(getApplicationContext());

        tvProfilName = findViewById(R.id.tvProfilName);
        tvProfilAuslastung = findViewById(R.id.tvProfilAuslastung);
        tvProfilFachbereiche = findViewById(R.id.tvProfilFachbereiche);

        String kompletterName = dbHandler.getUserNachID(betreuerID).getVorname() + " " + dbHandler.getUserNachID(betreuerID).getNachname();
        tvProfilName.setText(kompletterName);
        tvProfilAuslastung.setText(dbHandler.getUserNachID(betreuerID).getAuslastungsString(dbHandler.getUserNachID(betreuerID).getAuslastung()));
        if (dbHandler.getUserNachID(betreuerID).getAuslastung() == 0) {
            tvProfilAuslastung.setTextColor(tvProfilAuslastung.getResources().getColor(R.color.green));
        } else if (dbHandler.getUserNachID(betreuerID).getAuslastung() == 1) {
            tvProfilAuslastung.setTextColor(tvProfilAuslastung.getResources().getColor(R.color.orange));
        } else if (dbHandler.getUserNachID(betreuerID).getAuslastung() == 2) {
            tvProfilAuslastung.setTextColor(tvProfilAuslastung.getResources().getColor(R.color.darkRot));
        }
        tvProfilFachbereiche.setText(dbHandler.getUserNachID(betreuerID).getFachbereiche());

        ArrayList<Abschlussarbeit> abschlussarbeiten = dbHandler.getAlleAbschlussarbeitenNachUserIDUndStatus(betreuerID, 1);

        RecyclerView recyclerView = findViewById(R.id.rvFreieArbeiten);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterBetreuerProfil(this, abschlussarbeiten);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

        public void onItemClick(View view, int position) {
        int betreuerID = adapter.getBetreuerID(position);
        String emailBetreuer = dbHandler.getUserNachID(betreuerID).getMail();
        String betreuerNachname = dbHandler.getUserNachID(betreuerID).getNachname();

        int abschlussarbeitID = adapter.getAbschlussarbeitID(position);
        String themaString = "Anmeldung zu Abschlussarbeit-Thema: " + dbHandler.getAbschlussarbeitNachID(abschlussarbeitID).getUeberschrift();

        String beispielMailString = "Guten Tag Herr " + betreuerNachname + ",\n\nhiermit melde ich mich zu dem folgenden Thema an:\n\n"
                + dbHandler.getAbschlussarbeitNachID(abschlussarbeitID).getUeberschrift()
                + "\n \nBitte bestätigen Sie mir die Anmeldung, damit mit der Bearbeitung gestartet werden kann.\n" +
                "Mit freundlichen Grüßen\n";


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailBetreuer} );
        intent.putExtra(Intent.EXTRA_SUBJECT, themaString);
        intent.putExtra(Intent.EXTRA_TEXT, beispielMailString);
        startActivity(Intent.createChooser(intent, "Send Mail"));
    }

}