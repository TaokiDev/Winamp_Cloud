package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MusicPlayer extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String urlSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        urlSong = getIntent().getStringExtra("urlSong");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try{
            mediaPlayer.setDataSource(urlSong);
            mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
               stopSong();
            }
        });

        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        });

        Button pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });

        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopSong();
            }
        });
    }

    private void stopSong() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(urlSong);
            mediaPlayer.prepare();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
    }
}