package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    private ListView songsListView;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        FirebaseApp.initializeApp(this);

        songsListView = findViewById(R.id.songList);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child("music/").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        ArrayList<String> songNames = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            songNames.add(item.getName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Homepage.this, android.R.layout.simple_list_item_1, songNames);
                        songsListView.setAdapter(adapter);

                        // Set listener for ListView
                        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                                //Get URL of the selected song
                                StorageReference songRef = listResult.getItems().get(position);
                                songRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //Initiate MediaPlayer and set data source to URL of selected song
                                        mediaPlayer = new MediaPlayer();
                                        try {
                                            mediaPlayer.setDataSource(uri.toString());
                                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                @Override
                                                public void onPrepared(MediaPlayer mp) {
                                                    // Play the music when Media player is ready
                                                    mediaPlayer.start();
                                                }
                                            });
                                            mediaPlayer.prepareAsync();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
                    }
                });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}