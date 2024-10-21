package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private Button btnStartBetreuerUebersicht;
    private Button btnStartAbschlussarbeiten;
    private Button btnStartMeineThemen;
    private Button btnStartArchiv;
    private ImageButton logoutBtn;
    private TextView tvBegruessung;

    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnStartBetreuerUebersicht = findViewById(R.id.btnStartBetreuerUebersicht);
        btnStartAbschlussarbeiten = findViewById(R.id.btnStartAbschlussarbeiten);
        btnStartMeineThemen = findViewById(R.id.btnStartMeineThemen);
        btnStartArchiv = findViewById(R.id.btnStartArchiv);
        logoutBtn = findViewById(R.id.logoutButton);
        tvBegruessung = findViewById(R.id.halloNachricht);

        dbHandler = new DBHandler(this);
        dbHandler.getWritableDatabase();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        };

        btnStartBetreuerUebersicht.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BetreuerUebersicht.class);
                startActivity(i);
            }
        });

        btnStartAbschlussarbeiten.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AktiveArbeiten.class);
                startActivity(i);
            }
        });

        btnStartMeineThemen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Abschlussarbeiten.class);
                startActivity(i);
            }
        });

        btnStartArchiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AbschlussarbeitenArchiv.class);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        User user = dbHandler.getUserNachID(4);
        String kompletterName = user.getVorname() + " " + user.getNachname();
        tvBegruessung.setText("Hallo " + kompletterName + "!");


    }
}