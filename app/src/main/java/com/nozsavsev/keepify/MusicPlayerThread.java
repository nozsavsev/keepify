package com.nozsavsev.keepify;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayerThread extends Thread {
    private MediaPlayer mediaPlayer;

    public MusicPlayerThread(Context context, int musicResourceId) {
        mediaPlayer = MediaPlayer.create(context, musicResourceId);
    }

    @Override
    public void run() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
System.out.println("music staretd");
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            System.out.println("music stopped");
        }
    }
}