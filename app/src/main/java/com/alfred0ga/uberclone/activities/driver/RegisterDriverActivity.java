package com.alfred0ga.uberclone.activities.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.includes.MyToolbar;
import com.alfred0ga.uberclone.models.Driver;
import com.alfred0ga.uberclone.providers.AuthProvider;
import com.alfred0ga.uberclone.providers.DriverProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {
    private Button btnRegister;
    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etBrand;
    private TextInputEditText etPlate;
    private TextInputEditText etPass;
    private AlertDialog mDialog;

    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolbar.show(this, "Registro de conductor", true);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etBrand = findViewById(R.id.et_brand);
        etPlate = findViewById(R.id.et_plate);
        etPass = findViewById(R.id.et_pass);
        btnRegister = findViewById(R.id.btn_register);

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .build();

        btnRegister.setOnClickListener(v -> clickRegister());
    }

    private void clickRegister() {
        final String name = etName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String brand = etBrand.getText().toString().trim();
        final String plate = etPlate.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !brand.isEmpty() && !plate.isEmpty() && !pass.isEmpty()) {
            if (pass.length() >= 6) {
                mDialog.show();
                register(name, email, brand, plate, pass);
            } else {
                Toast.makeText(this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(final String name, String email, String brand, String plate, String pass) {
        mAuthProvider.register(email, pass).addOnCompleteListener(task -> {
            mDialog.hide();
            if (task.isSuccessful()) {
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Driver driver = new Driver(id, name, email, brand, plate);
                create(driver);
            } else {
                Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void create(Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Toast.makeText(this, "El registro se realizo exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterDriverActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}