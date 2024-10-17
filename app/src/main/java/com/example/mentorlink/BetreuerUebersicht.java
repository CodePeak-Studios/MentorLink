package com.example.mentorlink;

import android.content.Intent;
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

public class BetreuerUebersicht extends AppCompatActivity implements RecyclerViewAdapterBetreuerUebersicht.ItemClickListener {

    RecyclerViewAdapterBetreuerUebersicht adapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_betreuer_uebersicht);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(getApplicationContext());

        ArrayList<User> users = dbHandler.getUsersNachRolle(1);


        RecyclerView recyclerView = findViewById(R.id.rvBetreuerUebersicht);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterBetreuerUebersicht(this, users);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    //OnItemClick um auf die DetailBetreuer Seite zu kommen, wenn man auf einen User klickt.
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailBetreuer.class);
        intent.putExtra("betreuerID", position);
        startActivity(intent);
    }


}