package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayerHandler mediaPlayerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        String songUrl = getIntent().getStringExtra("songUrl");

        mediaPlayerHandler = new MediaPlayerHandler();
        mediaPlayerHandler.start(songUrl);

        ImageButton playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerHandler.start(songUrl);
            }
        });

        ImageButton pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerHandler.pause();
            }
        });

        ImageButton stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerHandler.stop();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayerHandler.release();
    }
}