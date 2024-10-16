package com.example.mentorlink;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Abschlussarbeiten extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    DBHandler dbHandler;



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

        dbHandler = new DBHandler(getApplicationContext());

        //Sample-Daten, um die RV zu testen


        Abschlussarbeit meineerste = dbHandler.getAbschlussarbeit();


        Abschlussarbeit abschlussarbeit1 = new Abschlussarbeit(1, "Blockchain-Technologie", "Bli la blub", 2,1);
        Abschlussarbeit abschlussarbeit2 = new Abschlussarbeit(2, "Cybersecurity und Datenschutz", "Die Kurzbeschreibung muss noch hinzugefügt werden.", 2,1);
        Abschlussarbeit abschlussarbeit3 = new Abschlussarbeit(1, "Agile Softwareentwicklung", "Diese hier hat auch noch keine Beschreibung :O", 1,1);
        Abschlussarbeit abschlussarbeit4 = new Abschlussarbeit(1, "KI und Maschinelles Lernen", "Den hier hat jemand wohl komplett vergessen. Sagt ja gar nix aus.", 2,1);
        Abschlussarbeit abschlussarbeit5 = new Abschlussarbeit(2, "Internet of the Dinge", "Mist, das geht hier so weiter.", 3,1);


        ArrayList<Abschlussarbeit> abschlussarbeiten = new ArrayList<>();
        abschlussarbeiten.add(abschlussarbeit1);
        abschlussarbeiten.add(abschlussarbeit2);
        abschlussarbeiten.add(abschlussarbeit3);
        abschlussarbeiten.add(abschlussarbeit4);
        abschlussarbeiten.add(abschlussarbeit5);


        //RecyclerView erstellen
        RecyclerView recyclerView = findViewById(R.id.rvAbschlussarbeiten);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, abschlussarbeiten);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}