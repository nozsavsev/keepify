package com.nozsavsev.keepify;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * A thread for playing music in the background.
 * This thread creates a MediaPlayer instance and starts playing the music resource provided.
 * It also provides a method to stop the music.
 */
public class MusicPlayerThread extends Thread {
    // MediaPlayer instance for playing music
    private MediaPlayer mediaPlayer;

    /**
     * Constructor for the MusicPlayerThread.
     * It initializes the MediaPlayer instance with the provided music resource.
     *
     * @param context The context in which the MediaPlayer is created.
     * @param musicResourceId The resource ID of the music to be played.
     */
    public MusicPlayerThread(Context context, int musicResourceId) {
        mediaPlayer = MediaPlayer.create(context, musicResourceId);
    }

    /**
     * The main execution method for the thread.
     * It starts the MediaPlayer if it is not null.
     */
    @Override
    public void run() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            System.out.println("music started");
        }
    }

    /**
     * Stops the music if it is playing and releases the resources.
     * The MediaPlayer instance is set to null after stopping.
     */
    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            System.out.println("music stopped");
        }
    }
}