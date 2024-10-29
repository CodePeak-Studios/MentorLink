package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Abschlussarbeiten extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    DBHandler dbHandler;
    FloatingActionButton addFab;
    ImageButton btnHome;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_abschlussarbeiten);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addFab = findViewById(R.id.fab_addVorschlag);
        dbHandler = new DBHandler(getApplicationContext());
        btnHome = findViewById(R.id.btn_home);

        Intent intent = getIntent();
        userId = intent.getIntExtra("aktiverUser", userId);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("aktiverUser", userId);
                startActivity(i);
            }
        });


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


        ArrayList<Abschlussarbeit> abschlussarbeiten = dbHandler.getAlleAbschlussarbeitenNachUserIDUndStatus(userId, 1);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNeuerVorschlag = new Intent(getApplicationContext(), DetailNewAbschlussarbeit.class);
                intentNeuerVorschlag.putExtra("aktiverUser", userId);
                startActivity(intentNeuerVorschlag);
            }
        });

        //RecyclerView erstellen
        RecyclerView recyclerView = findViewById(R.id.rvAbschlussarbeiten);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, abschlussarbeiten);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

    }

    @Override
    public void onItemClick(View view, int position) {

        Abschlussarbeit clicked = adapter.getItem(position);

        Intent intentBestehenderVorschlag = new Intent(getApplicationContext(), DetailAbschlussarbeit.class);
        //TODO Hier muss der aktuelle User als Parameter übergeben werden
        intentBestehenderVorschlag.putExtra("aktiverUser", userId);
        intentBestehenderVorschlag.putExtra("AbschlussarbeitId", clicked.getId());
        startActivity(intentBestehenderVorschlag);
    }
}