package com.example.bersuara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class account extends AppCompatActivity {

    Button start;
    String anonymousUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountt);

        start = findViewById(R.id.start_button);

        // Get the anonymous username from the intent, or generate one if it's not provided
        anonymousUsername = getIntent().getStringExtra("anonymous_username");
        if (anonymousUsername == null || anonymousUsername.isEmpty()) {
            anonymousUsername = generateAnonymousUsername();  // Generate a random anonymous username
        }

        // Display the anonymous username in a TextView
        TextView welcomeText = findViewById(R.id.account_text);
        welcomeText.setText("Hello, " + anonymousUsername + "!");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the anonymous username to the room
                Intent intent = new Intent(getApplicationContext(), room.class);
                intent.putExtra("anonymous_username", anonymousUsername);  // Passing the username
                startActivity(intent);
            }
        });
    }

    // Method to generate an anonymous username
    private String generateAnonymousUsername() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);  // Generates a random number between 0 and 999
        return "anonymous" + randomNumber;
    }
}