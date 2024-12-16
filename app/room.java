package com.example.bersuara;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.Manifest;

public class room extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    EditText editTextMessage;
    ImageButton buttonSend, buttonTakePhoto;
    ListView listViewMessages;
    ArrayList<Message> messages;
    MessageAdapter messageAdapter;
    int REQUEST_CODE = 99;
    String anonymousUsername;  // Variable to hold the anonymous username

    // Array of banned words
    String[] bannedWords = {"anjing", "babi", "bangsat", "pepek", "tai", "ngentot", "memek", "kontol", "fuck", "bitch", "ngewe", "goblog", "goblok", "bajingan", "pepek", "lonte", "asu", "edan" };

    // Notification Manager
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "banned_words_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Initialize UI elements
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonTakePhoto = findViewById(R.id.buttonTakePhoto);
        listViewMessages = findViewById(R.id.listViewMessages);

        anonymousUsername = getIntent().getStringExtra("anonymous_username");

        // Initialize message list and adapter
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messages);
        listViewMessages.setAdapter(messageAdapter);

        // Initialize Notification Manager
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();

        // Send button click listener
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    if (containsBannedWords(messageText)) {
                        // Show warning notification if message contains banned words
                        showBannedWordsNotification();
                        Toast.makeText(room.this, "Pesan Anda mengandung kata-kata tidak pantas!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add the text message to the list, prepending the anonymous username
                        messages.add(new Message(anonymousUsername + ": " + messageText, null));  // Text message without image
                        messageAdapter.notifyDataSetChanged();
                        editTextMessage.setText(""); // Clear input field
                    }
                } else {
                    Toast.makeText(room.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Take photo button click listener
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Check for camera permission
        checkCameraPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the captured image as a Bitmap
            Bitmap picTaken = (Bitmap) data.getExtras().get("data");

            // Add the image message to the list
            messages.add(new Message(null, picTaken));  // Image message without text
            messageAdapter.notifyDataSetChanged();  // Notify the adapter to refresh the list
        } else {
            Toast.makeText(this, "Camera Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }
    }

    // Method to check if the message contains banned words
    private boolean containsBannedWords(String message) {
        for (String word : bannedWords) {
            if (message.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    // Method to show notification for banned words
    private void showBannedWordsNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo2) // Replace with your app's icon
                .setContentTitle("Peringatan!")
                .setContentText("Pesan Anda mengandung kata-kata tidak pantas.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND) // Default sound notification
                .setAutoCancel(true)
                .build();

        // Display the notification
        notificationManager.notify(1, notification);
    }

    // Create a notification channel for Android 8.0 and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Banned Words Warning";
            String description = "Notifikasi untuk peringatan kata-kata tidak pantas";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }
}