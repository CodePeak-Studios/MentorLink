package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import kotlinx.coroutines.flow.internal.AbstractSharedFlow;

public class Abschlussarbeiten extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    DBHandler dbHandler;
    FloatingActionButton addFab;



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

        ArrayList<Abschlussarbeit> abschlussarbeiten = dbHandler.getAlleAbschlussarbeitenNachUserIDUndStatus(1, 1);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNeuerVorschlag = new Intent(getApplicationContext(), DetailNewAbschlussarbeit.class);
                intentNeuerVorschlag.putExtra("idUser", 1);
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
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Abschlussarbeit clicked = adapter.getItem(position);

        Intent intentBestehenderVorschlag = new Intent(getApplicationContext(), DetailAbschlussarbeit.class);
        intentBestehenderVorschlag.putExtra("idUser", 1);
        intentBestehenderVorschlag.putExtra("AbschlussarbeitId", clicked.getId());
        startActivity(intentBestehenderVorschlag);
    }
}