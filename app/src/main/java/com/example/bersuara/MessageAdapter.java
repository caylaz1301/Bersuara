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
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_message, parent, false);
        }

        TextView textMessage = convertView.findViewById(R.id.textMessage);
        ImageView imageMessage = convertView.findViewById(R.id.imageMessage);

        if (message.hasImage()) {
            // If the message contains an image
            textMessage.setVisibility(View.GONE); 
            imageMessage.setVisibility(View.VISIBLE); 
            imageMessage.setImageBitmap(message.getImage()); 
        } else {
            // If the message contains text
            textMessage.setVisibility(View.VISIBLE); 
            imageMessage.setVisibility(View.GONE); 
            textMessage.setText(message.getText()); 
        }

        return convertView;
    }
}
