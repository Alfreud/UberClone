package com.alfred0ga.uberclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.activities.client.MapClientActivity;
import com.alfred0ga.uberclone.activities.driver.MapDriverActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btnDriver;
    private Button btnClient;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        btnDriver = findViewById(R.id.btn_driver);
        btnClient = findViewById(R.id.btn_client);

        btnDriver.setOnClickListener(v -> {
            editor.putString("user", "driver");
            editor.apply();
            goToSelectAuth();
        });
        btnClient.setOnClickListener(v -> {
            editor.putString("user", "client");
            editor.apply();
            goToSelectAuth();
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = mPreferences.getString("user", "");
            if (user.equals("client")) {
                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionsAuthActivity.class);
        startActivity(intent);
    }
}