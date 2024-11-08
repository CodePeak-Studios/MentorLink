package com.example.mentorlink;

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

public class AbschlussarbeitenArchiv extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    DBHandler dbHandler;
    ImageButton btnHome;
    int userId;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_abschlussarbeiten_archiv);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intentVonStartseite = getIntent();
        userId = intentVonStartseite.getIntExtra("aktiverUser", -1);

        dbHandler = new DBHandler(getApplicationContext());
        btnHome = findViewById(R.id.btn_home);

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


        ArrayList<Abschlussarbeit> abschlussarbeiten = dbHandler.getAlleAbschlussarbeitenNachUserIDUndStatus(userId, 10, 11);

        //RecyclerView erstellen
        RecyclerView recyclerView = findViewById(R.id.rvAbschlussarbeitenArchiv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, abschlussarbeiten);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {

        Abschlussarbeit clicked = adapter.getItem(position);

        Intent intentBestehenderVorschlag = new Intent(getApplicationContext(), DetailAbschlussarbeit.class);
        intentBestehenderVorschlag.putExtra("aktiverUser", userId);
        intentBestehenderVorschlag.putExtra("AbschlussarbeitId", clicked.getId());
        intentBestehenderVorschlag.putExtra("kommtAusArchiv", true);
        startActivity(intentBestehenderVorschlag);
    }


}