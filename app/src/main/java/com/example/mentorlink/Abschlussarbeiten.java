package com.example.mentorlink;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Abschlussarbeiten extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;



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

        //Sample-Daten, um die RV zu befuellen
        ArrayList<String> abschlussarbeiten = new ArrayList<>();
        abschlussarbeiten.add("Blockchain-Technologie");
        abschlussarbeiten.add("Cybersecurity und Datenschutz");
        abschlussarbeiten.add("Agile Softwareentwicklung");
        abschlussarbeiten.add("KI und Maschinelles Lernen");
        abschlussarbeiten.add("Internet of Things");

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