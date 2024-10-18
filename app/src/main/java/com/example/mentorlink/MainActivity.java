package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private Button btnStartBetreuerUebersicht;
    private Button btnStartAbschlussarbeiten;
    private Button btnStartMeineThemen;
    private Button btnStartArchiv;

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

        dbHandler = new DBHandler(this);
        dbHandler.getWritableDatabase();

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



    }
}