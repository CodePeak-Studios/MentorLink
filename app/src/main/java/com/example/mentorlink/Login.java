package com.example.mentorlink;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Login extends AppCompatActivity {

    static String passwordHash = "";
    private EditText edtEmail;
    private EditText edtPasswort;
    private Button btnAnmelden;
    private DBHandler dbHandler;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPasswort = findViewById(R.id.editTextTextPassword);
        btnAnmelden = findViewById(R.id.buttonLogin);
        user = new User();
        dbHandler = new DBHandler(getApplicationContext());


        btnAnmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*passwordHash = get_SHA_256_SecurePassword("Design789", "sprachmeister");
                edtEmail.setText(passwordHash);
                Log.i("passwordhash", "Mein Passwort ist: " + passwordHash);*/
                user = dbHandler.checkPassword(edtEmail.getText().toString(), edtPasswort.getText().toString());

                if(user == null)
                {
                    Toast.makeText(getApplicationContext(), "Anmeldung fehlgeschlagen", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("aktiverUser", user.getId());
                    startActivity(intent);
                }
            }
        });

    }

}