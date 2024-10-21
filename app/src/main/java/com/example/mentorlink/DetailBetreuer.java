package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
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
    ImageButton btnBack;

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

        btnBack = findViewById(R.id.btn_zurueck);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BetreuerUebersicht.class);
                startActivity(i);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), BetreuerUebersicht.class);
                startActivity(i);
            }
        };

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
                + "\n \nBitte bestätigen Sie mir die Anmeldung, damit mit der Bearbeitung gestartet werden kann.\n\n" +
                "Mit freundlichen Grüßen\n";


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailBetreuer} );
        intent.putExtra(Intent.EXTRA_SUBJECT, themaString);
        intent.putExtra(Intent.EXTRA_TEXT, beispielMailString);
        startActivity(Intent.createChooser(intent, "Send Mail"));
    }

}