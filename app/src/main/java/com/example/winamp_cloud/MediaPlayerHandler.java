package com.example.winamp_cloud;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import java.io.IOException;


public class MediaPlayerHandler {

    private MediaPlayer mediaPlayer;
    private String currentUrl;
    private boolean isPrepared;
    private boolean isReleased;
    private boolean isPaused;

    public MediaPlayerHandler(){
        mediaPlayer = new MediaPlayer();
        isPrepared = false;
        isReleased = true;
        isPaused = false;
    }

    public void start(String url) {
        if (mediaPlayer == null || isReleased) {
            mediaPlayer = new MediaPlayer();
            isReleased = false;
        } else {
            mediaPlayer.reset();
            isPaused = false;
        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isPrepared = true;
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
            currentUrl = url;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        }
    }


    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            isReleased = true;
        }
    }

    public void resume(){
        if(mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
            isPaused = false;
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            isReleased = true;
        }
    }


    public void setProgressListener(final SeekBar seekBar){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.postDelayed(runnable, 1000);
            }
        });
        handler.postDelayed(runnable, 1000);
    }

    public int getCurrentPosition(){
        if(mediaPlayer != null){
            return mediaPlayer.getCurrentPosition();
        }else{
            return 0;
        }
    }

    public int getDuration(){
        if(mediaPlayer != null){
            return mediaPlayer.getDuration();
        } else{
            return 0;
        }
    }

    public void seekTo(int position){
        if(mediaPlayer != null){
            mediaPlayer.seekTo(position);
        }
    }
}
