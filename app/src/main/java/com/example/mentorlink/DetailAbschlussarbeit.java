package com.example.mentorlink;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailAbschlussarbeit extends AppCompatActivity {


    private Spinner spnDetailAbschlussarbeitKategorie;
    private Spinner spnDetailAbschlussarbeitStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_abschlussarbeit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spnDetailAbschlussarbeitKategorie = findViewById(R.id.spnDetailAbschlussarbeitKategorie);
        spnDetailAbschlussarbeitStatus = findViewById(R.id.spnDetailAbschlussarbeitStatus);

        String[] arrKat = new String[] {"Wirtschaft & Soziales"};
        String[] arrStat = new String[] {"in Bearbeitung"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrKat);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDetailAbschlussarbeitKategorie.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrStat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDetailAbschlussarbeitStatus.setAdapter(adapter2);


    }
}