package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailAbschlussarbeit extends AppCompatActivity {


    private Spinner spnDetailAbschlussarbeitKategorie;
    private Spinner spnDetailAbschlussarbeitStatus;
    private Button btnSearchZweitgutachter;
    private Button btnSearchStudent;
    private TextView tvDetailAbschlussarbeitZweitgutachterMail;
    private TextView tvDetailAbschlussarbeitZweitgutachterName;
    private TextView tvDetailAbschlussarbeitStudentMail;
    private TextView tvDetailAbschlussarbeitStudentName;



    private DBHandler dbHandler;

    private int intentIdUser;
    private int intentAbschlussarbeit;



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
        btnSearchZweitgutachter = findViewById(R.id.btnSearchZweitgutachterDetailAbschlussarbeiten);
        btnSearchStudent = findViewById(R.id.btnSearchStudentDetailAbschlussarbeiten);
        dbHandler = new DBHandler(getApplicationContext());
        tvDetailAbschlussarbeitZweitgutachterMail = findViewById(R.id.tvDetailAbschlussarbeitZweitgutachterMail);
        tvDetailAbschlussarbeitZweitgutachterName = findViewById(R.id.tvDetailAbschlussarbeitZweitgutachterName);
        tvDetailAbschlussarbeitStudentMail = findViewById(R.id.tvDetailAbschlussarbeitStudentMail);
        tvDetailAbschlussarbeitStudentName = findViewById(R.id.tvDetailAbschlussarbeitStudentName);





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

        Intent intentVonVorschlaege = getIntent();

        intentIdUser = intentVonVorschlaege.getIntExtra("idUser", -1);
        intentAbschlussarbeit = intentVonVorschlaege.getIntExtra("AbschlussarbeitId", -1);


        btnSearchZweitgutachter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user = dbHandler.getUserNachMail(tvDetailAbschlussarbeitZweitgutachterMail.getText().toString());
                    tvDetailAbschlussarbeitZweitgutachterName.setText(user.getVorname() + " " + user.getNachname());
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage() +
                            "\n Der User konnte anscheinend nicht aus der DB zugeordnet werden.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}