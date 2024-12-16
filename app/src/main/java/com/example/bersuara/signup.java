package com.example.bersuara;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity {

    private EditText emailEditText, studentIdEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Inisialisasi view
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        studentIdEditText = findViewById(R.id.editTextNumber);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword2);
        registerButton = findViewById(R.id.button);

        // Inisialisasi Firestore
        firestore = FirebaseFirestore.getInstance();

        // Tombol registrasi
        registerButton.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {
        String email = emailEditText.getText().toString().trim();
        String studentId = studentIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || !email.endsWith("@student.president.ac.id")) {
            emailEditText.setError("Please use your student email");
            return;
        }

        if (TextUtils.isEmpty(studentId)) {
            studentIdEditText.setError("Student ID is required");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // Simpan data ke Firestore
        User user = new User(email, studentId, password);
        firestore.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), main.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(signup.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
