package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profilseite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageButton btn_zurueck;
    private TextView tvProfilName;
    private TextView tvProfilEmail;
    private TextView tvProfilFachbereiche;
    private EditText etFachbereiche;
    private Button editFachbereicheBtn;
    private Button fachbereicheSpeichernBtn;

    private int userId;
    private User user;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profilseite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_zurueck = findViewById(R.id.btn_zurueck);
        tvProfilName = findViewById(R.id.tvProfilseiteName);
        tvProfilEmail = findViewById(R.id.tvProfilseiteEmail);
        tvProfilFachbereiche = findViewById(R.id.tvProfilseiteFachbereiche);
        etFachbereiche = findViewById(R.id.editFachbereicheInput);
        editFachbereicheBtn = findViewById(R.id.editFachbereichBtn);
        fachbereicheSpeichernBtn = findViewById(R.id.fachbereicheSpeichernBtn);

        btn_zurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profilseite.this, MainActivity.class);
                intent.putExtra("aktiverUser", userId);
                startActivity(intent);
            }
        });

        dbHandler = new DBHandler(this);

        Intent intentUserId = getIntent();
        userId = intentUserId.getIntExtra("aktiverUser", -1);
        user = dbHandler.getUserNachID(userId);

        String kompletterName = user.getVorname() + " " + user.getNachname();
        tvProfilName.setText(kompletterName);
        tvProfilEmail.setText(user.getMail());
        tvProfilFachbereiche.setText(user.getFachbereiche());

        Spinner dropdown = findViewById(R.id.spinnerAuslastung);
        String[] items = new String[]{"Frei", "Beschäftigt", "Ausgelastet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);

        setSpinnerAufAktuelleAuslastung(dropdown, user.getAuslastung());

        dropdown.setOnItemSelectedListener(this);

        editFachbereicheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProfilFachbereiche.setVisibility(View.GONE);
                editFachbereicheBtn.setVisibility(View.GONE);
                etFachbereiche.setVisibility(View.VISIBLE);
                etFachbereiche.setText(user.getFachbereiche());
                etFachbereiche.setHint("Beschreibe deine Fachbereiche");
                fachbereicheSpeichernBtn.setVisibility(View.VISIBLE);
            }
        });

        fachbereicheSpeichernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.updateFachbereiche(String.valueOf(etFachbereiche.getText()), userId);
                Toast.makeText(getApplicationContext(), "Fachbereiche erfolgreich gespeichert.", Toast.LENGTH_LONG).show();

                etFachbereiche.setVisibility(View.GONE);
                fachbereicheSpeichernBtn.setVisibility(View.GONE);
                tvProfilFachbereiche.setVisibility(View.VISIBLE);
                user = dbHandler.getUserNachID(userId);
                tvProfilFachbereiche.setText(user.getFachbereiche());
                editFachbereicheBtn.setVisibility(View.VISIBLE);

            }
        });
    }

    private void setSpinnerAufAktuelleAuslastung(Spinner spinner, int aktuelleAuslastung) {
        switch (aktuelleAuslastung) {
            case 0:
                spinner.setSelection(0);
                break;
            case 1:
                spinner.setSelection(1);
                break;
            case 2:
                spinner.setSelection(2);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        int neueAuslastung;
        switch (choice) {
            case "Frei":
                neueAuslastung = 0;
                break;
            case "Beschäftigt":
                neueAuslastung = 1;
                break;
            case "Ausgelastet":
                neueAuslastung = 2;
                break;
            default:
                return;
        }
        if (neueAuslastung != user.getAuslastung()) {
            dbHandler.updateAuslastung(neueAuslastung, userId);
            user.setAuslastung(neueAuslastung);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        int aktuelleAuslastung = user.getAuslastung();
        switch (aktuelleAuslastung) {
            case 0:
                parent.setSelection(0);
                break;
            case 1:
                parent.setSelection(1);
                break;
            case 2:
                parent.setSelection(2);
                break;
            default:
                break;
        }
    }
}