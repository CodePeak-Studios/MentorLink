package com.example.mentorlink;

import android.content.Intent;
import android.content.res.ColorStateList;
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
    private ImageButton profilseiteBtn;
    private TextView tvBegruessung;
    private int userId;
    private User user;

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
        profilseiteBtn = findViewById(R.id.profilseite_btn);

        dbHandler = new DBHandler(this);
        dbHandler.getWritableDatabase();

        //User aus Intent der Login-Seite laden
        Intent intentUserId = getIntent();
        userId = intentUserId.getIntExtra("aktiverUser", -1);
        user = dbHandler.getUserNachID(userId);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("aktiverUser", userId);
                startActivity(i);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        btnStartBetreuerUebersicht.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BetreuerUebersicht.class);
                i.putExtra("aktiverUser", userId);
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

        profilseiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Profilseite.class);
                i.putExtra("aktiverUser", userId);
                startActivity(i);
            }
        });

        if (user.getRolle() == 1) {
            btnStartAbschlussarbeiten.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), AktiveArbeiten.class);
                    i.putExtra("aktiverUser", userId);
                    startActivity(i);
                }
            });

            btnStartMeineThemen.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Abschlussarbeiten.class);
                    i.putExtra("aktiverUser", userId);
                    startActivity(i);
                }
            });

            btnStartArchiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), AbschlussarbeitenArchiv.class);
                    i.putExtra("aktiverUser", userId);
                    startActivity(i);
                }
            });
        } else {
            int color = getResources().getColor(R.color.grey);
            btnStartMeineThemen.setBackgroundTintList(ColorStateList.valueOf(color));
            btnStartMeineThemen.setAlpha(0.6f);
            btnStartArchiv.setBackgroundTintList(ColorStateList.valueOf(color));
            btnStartArchiv.setAlpha(0.6f);
            btnStartAbschlussarbeiten.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), AktiveArbeiten.class);
                    i.putExtra("aktiverUser", userId);
                    startActivity(i);
                }
            });
        }



        String kompletterName = user.getVorname() + " " + user.getNachname();
        tvBegruessung.setText("Moin " + kompletterName + "!");


    }
}