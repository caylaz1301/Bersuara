package com.example.bersuara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class main extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private CheckBox rememberMeCheckbox;
    private SharedPreferences sharedPreferences;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi view
        emailEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_btn);
        registerButton = findViewById(R.id.register);
        rememberMeCheckbox = findViewById(R.id.remember);

        // Inisialisasi Firestore dan SharedPreferences
        firestore = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        // Cek jika Remember Me aktif
        checkRememberMe();

        // Tombol login
        loginButton.setOnClickListener(v -> handleLogin());

        // Tombol register
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(main.this, signup.class);
            startActivity(intent);
        });
    }

    private void checkRememberMe() {
        String savedEmail = sharedPreferences.getString("email", null);
        if (savedEmail != null) {
            emailEditText.setText(savedEmail);
            rememberMeCheckbox.setChecked(true);
        }
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        // Query ke Firestore untuk mengecek email dan password
        firestore.collection("users")
                .whereEqualTo("email", email) // Cari data dengan email yang cocok
                .whereEqualTo("password", password) // Cocokkan password
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // Jika data ditemukan, login berhasil
                        if (rememberMeCheckbox.isChecked()) {
                            sharedPreferences.edit().putString("email", email).apply();
                        } else {
                            sharedPreferences.edit().remove("email").apply();
                        }

                        Toast.makeText(main.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(main.this, account.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Jika data tidak ditemukan atau password salah
                        Toast.makeText(main.this, "Login Failed: Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(main.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
