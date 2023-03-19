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
    private TextView title, currentDuration, totalDuration;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        String songUrl = getIntent().getStringExtra("songUrl");
        String songTitle = getIntent().getStringExtra("songTitle");

        title = findViewById(R.id.title);
        title.setText(songTitle);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayerHandler = new MediaPlayerHandler();
        mediaPlayerHandler.start(songUrl);

        mediaPlayerHandler.setProgressListener(seekBar, handler);

        ImageButton playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlaying){
                    mediaPlayerHandler.pause();
                    playButton.setImageResource(R.drawable.ic_action_playback_play);
                }else{
                    mediaPlayerHandler.resume();
                    playButton.setImageResource(R.drawable.ic_action_playback_pause);
                }
                isPlaying = !isPlaying;
            }
        });

        handler.postDelayed(updateSeekBarRunnable, 1000);

    }



    @Override
    protected void onDestroy () {
        super.onDestroy();
        handler.removeCallbacks(updateSeekBarRunnable);
        mediaPlayerHandler.stop();
        mediaPlayerHandler.release();
    }

    private Runnable updateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = mediaPlayerHandler.getCurrentPosition();
            int totalDuration = mediaPlayerHandler.getDuration();

            seekBar.setMax(totalDuration);
            seekBar.setProgress(currentPosition);

            handler.postDelayed(this, 1000);
        }
    };

}