package com.example.mentorlink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AktiveArbeiten extends AppCompatActivity implements RecyclerViewAdapterAktiveArbeiten.ItemClickListener {

    RecyclerViewAdapterAktiveArbeiten adapter;
    DBHandler dbHandler;
    ImageButton btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aktive_arbeiten);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dbHandler = new DBHandler(getApplicationContext());
        btnHome = findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        ArrayList<Abschlussarbeit> abschlussarbeiten = dbHandler.getAlleAktivenAbschlussarbeitenNachUserIDUndStatus(1);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        //RecyclerView erstellen
        RecyclerView recyclerView = findViewById(R.id.rvAktiveArbeiten);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterAktiveArbeiten(this, abschlussarbeiten);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {

        Abschlussarbeit clicked = adapter.getItem(position);

        Intent intentAktiveAbschlussarbeit = new Intent(getApplicationContext(), DetailAktiveAbschlussarbeit.class);
        //TODO Hier muss der aktuelle User als Parameter übergeben werden
        intentAktiveAbschlussarbeit.putExtra("idUser", 1);
        intentAktiveAbschlussarbeit.putExtra("AbschlussarbeitId", clicked.getId());
        startActivity(intentAktiveAbschlussarbeit);
    }

}