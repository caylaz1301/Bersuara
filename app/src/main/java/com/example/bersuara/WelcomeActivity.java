package com.example.bersuara;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private TextView textWelcomeTop;
    private TextView textWelcomeBottom;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        // Initialize components
        textWelcomeTop = findViewById(R.id.textWelcomeTop);
        textWelcomeBottom = findViewById(R.id.textWelcomeBottom);
        startButton = findViewById(R.id.start_button);

        textWelcomeTop.setVisibility(View.INVISIBLE);
        textWelcomeBottom.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.GONE);

        // Initialize sensor manager for shake detection
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        // Initialize acceleration values
        acceleration = 5f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, main.class);
            startActivity(intent);
            finish();
        });
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt((x * x) + (y * y) + (z * z));
            float delta = currentAcceleration - lastAcceleration;
            acceleration = acceleration * 0.9f + delta;

            if (acceleration > 7) {
                startAnimations();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void startAnimations() {
        textWelcomeTop.setVisibility(View.VISIBLE);
        textWelcomeBottom.setVisibility(View.VISIBLE);

        Animation animTop = AnimationUtils.loadAnimation(this, R.anim.move_top_to_center);
        Animation animBottom = AnimationUtils.loadAnimation(this, R.anim.move_bottom_to_center);

        textWelcomeTop.startAnimation(animTop);
        textWelcomeBottom.startAnimation(animBottom);

        // After animations, show reflection effect and button
        animBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textWelcomeBottom.setAlpha(0.5f); // Reflection effect
                startButton.setVisibility(View.VISIBLE); 
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}
