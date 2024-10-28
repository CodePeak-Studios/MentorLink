package com.example.mentorlink;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



public class Login extends AppCompatActivity {

    static String passwordHash = "";
    private EditText edtEmail;
    private EditText edtPasswort;
    private Button btnAnmelden;
    private DBHandler dbHandler;
    private User user;

    private static String ip = "192.168.178.31";// this is the host ip that your data base exists on you can use 10.0.2.2 for local host found on your pc. use if config for windows to find the ip if the database exists on your pc
    private static String port = "1433";// the port sql server runs on
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";// the driver that is required for this connection
    private static String database = "MentorLink";// the data base name
    private static String username = "mentorapp";// the user name
    private static String password = "password";// the password
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database; // the connection url string

    private Connection connection = null;
    private Button btnDB;



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

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPasswort = findViewById(R.id.editTextTextPassword);
        btnAnmelden = findViewById(R.id.buttonLogin);
        user = new User();
        dbHandler = new DBHandler(getApplicationContext());
        btnDB = findViewById(R.id.btnDB);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*try{
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/


        btnAnmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*passwordHash = get_SHA_256_SecurePassword("Design789", "sprachmeister");
                edtEmail.setText(passwordHash);
                Log.i("passwordhash", "Mein Passwort ist: " + passwordHash);*/
                /*user = dbHandler.checkPassword(edtEmail.getText().toString(), edtPasswort.getText().toString());*/

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

        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class.forName(Classes);
                    connection = DriverManager.getConnection(url, username, password);
                    Toast.makeText(getApplicationContext(), "Suuper!", Toast.LENGTH_SHORT).show();
                    btnDB.setText("Juhuu");
                }
                catch (ClassNotFoundException c)
                {
                    c.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Kaputt.", Toast.LENGTH_LONG).show();
                }
                catch (SQLException s)
                {
                    s.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Keine Verbindung", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Allgemeine Exception", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    private static String get_SHA_256_SecurePassword(String passwordToHash,
                                                     String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}