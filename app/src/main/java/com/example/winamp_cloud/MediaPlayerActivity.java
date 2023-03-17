package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayerHandler mediaPlayerHandler;
    private TextView title;
    private SeekBar seekBar;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        String songUrl = getIntent().getStringExtra("songUrl");
        String songTitle = getIntent().getStringExtra("songTitle");

        title = findViewById(R.id.title);
        title.setText(songTitle);

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
            }
        });

        seekBar = findViewById(R.id.seekBar);
        if (mediaPlayerHandler != null) {
            seekBar.setMax(mediaPlayerHandler.getDuration());
        }

        //Updates the SeekBar progress
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayerHandler != null) {
                    int currentPosition = mediaPlayerHandler.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayerHandler.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayerHandler.release();
    }
}
