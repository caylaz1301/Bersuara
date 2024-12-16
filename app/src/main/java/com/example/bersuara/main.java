package com.example.bersuara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class main extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private CheckBox rememberMeCheckbox;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;

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

        // Inisialisasi Firebase Auth dan SharedPreferences
        firebaseAuth = FirebaseAuth.getInstance();
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
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Simpan email jika Remember Me aktif
                        if (rememberMeCheckbox.isChecked()) {
                            sharedPreferences.edit().putString("email", email).apply();
                        } else {
                            sharedPreferences.edit().remove("email").apply();
                        }
                        Toast.makeText(main.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Intent ke halaman utama aplikasi
                        Intent intent = new Intent(main.this, account.class); // Ganti dengan activity utama Anda
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(main.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
