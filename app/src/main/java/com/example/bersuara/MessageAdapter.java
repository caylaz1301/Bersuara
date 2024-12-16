package com.example.bersuara;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {
    private Context context;
    private ArrayList<Message> messages;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the message at this position
        Message message = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_message, parent, false);
        }

        // Find the views
        TextView textMessage = convertView.findViewById(R.id.textMessage);
        ImageView imageMessage = convertView.findViewById(R.id.imageMessage);

        // Populate the data
        if (message.hasImage()) {
            // If the message contains an image
            textMessage.setVisibility(View.GONE); // Hide text
            imageMessage.setVisibility(View.VISIBLE); // Show image
            imageMessage.setImageBitmap(message.getImage()); // Set the image
        } else {
            // If the message contains text
            textMessage.setVisibility(View.VISIBLE); // Show text
            imageMessage.setVisibility(View.GONE); // Hide image
            textMessage.setText(message.getText()); // Set the text
        }

        return convertView;
    }
}
