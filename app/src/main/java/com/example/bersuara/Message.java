package com.example.bersuara;

import android.graphics.Bitmap;

public class Message {
    private String text;
    private Bitmap image;

    // Constructor for a message with text and/or image
    public Message(String text, Bitmap image) {
        this.text = text;
        this.image = image;
    }

    // Getters
    public String getText() {
        return text;
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean hasImage() {
        return image !=null;
        }
}
