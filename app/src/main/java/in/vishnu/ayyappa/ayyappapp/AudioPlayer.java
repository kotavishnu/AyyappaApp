package in.vishnu.ayyappa.ayyappapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class AudioPlayer extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    String TAG="AudioPlayer";
    int currentTrack = 0;
    private int [] songs ={R.raw.song1,R.raw.song2,R.raw.song1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context ctx=this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MediaPlayer.OnCompletionListener mediaListener = getOnCompletionListener();
        mediaPlayer = MediaPlayer.create(this, R.raw.song1);
        mediaPlayer.setOnCompletionListener(mediaListener);
        long finalTime = mediaPlayer.getDuration();

        ImageButton play = (ImageButton) findViewById(R.id.media_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                int timeElapsed = mediaPlayer.getCurrentPosition();
                Log.i(TAG,"Time elapsed "+timeElapsed);
            }
        });

        ImageButton pause = (ImageButton) findViewById(R.id.media_pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                int timeElapsed = mediaPlayer.getCurrentPosition();
                Log.i(TAG,"Time elapsed "+timeElapsed);
            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.media_ff);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.release();;
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    currentTrack++;
                    currentTrack=currentTrack%(songs.length);
                    Log.i(TAG,"CURRENT Track "+currentTrack);
                    mediaPlayer.setDataSource(ctx,Uri.parse("android.resource://"+ ctx.getPackageName() + "/raw/"+songs[currentTrack]));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @NonNull
    private MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer arg0) {
                    arg0.release();
                        currentTrack++;
                        currentTrack=currentTrack%(songs.length);
                        Log.i(TAG,"CURRENT Track "+currentTrack);
                        arg0 = MediaPlayer.create(getApplicationContext(), songs[currentTrack]);
                        arg0.setOnCompletionListener(this);
                        arg0.start();
                }
            };
    }

}
