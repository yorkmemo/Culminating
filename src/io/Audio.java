package io;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import static io.Sound.TICKS_PER_SECOND;

public class Audio implements Audable {

    private static String DEFAULT_AUDIO_FILE = "io/blank.wav";

    private MediaPlayer mediaPlayer;
    private Queue<Runnable> queue;
    private boolean ready;
    private int waiting = 0;
    private AudioEvent doneHandler;


    Audio(String fileName) {
        System.out.println(Audio.class.getClassLoader().getResource(fileName));

        URL url = Audio.class.getClassLoader().getResource(fileName);

        if (url == null) {
            Output.error("Sound file not found: " + fileName);
            url = Audio.class.getClassLoader().getResource(DEFAULT_AUDIO_FILE);
        }

        String file = url.toString();
        Media media = new Media(file);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(this::donePlaying);

        ready = false;
        queue = new LinkedList<>();
        mediaPlayer.setOnReady(() -> {
            ready = true;
        });

    }

    private void donePlaying() {
       // System.out.println("wales");
        mediaPlayer.stop();

        if (doneHandler != null) {
            doneHandler.handle(this);
        }
    }


    void tick() {
        if (ready) {
            waiting--;

            while (waiting <= 0 && queue.size() > 0) {
                Platform.runLater(queue.remove());
            }
        }

    }

    @Override
    public Audable play() {
        if (!ready || waiting > 0) {
            queue.add(this::play);
        } else {
         //   System.out.println("Playing: " + mediaPlayer.getMedia().getDuration().toSeconds());
            mediaPlayer.play();
        }

        return this;
    }

    @Override
    public Audable sleep(double seconds) {
        if (!ready || waiting > 0) {
            queue.add(()->sleep(seconds));
        } else {
            waiting = (int) Math.round(seconds * TICKS_PER_SECOND);
        }

        return this;
    }

    @Override
    public Audable loop() {
        if (!ready || waiting > 0) {
            queue.add(this::loop);
        } else {
            mediaPlayer.setCycleCount(-1);
        }

        return this;
    }

    @Override
    public Audable loop(int times) {
        if (!ready || waiting > 0) {
            queue.add(()->loop(times));
        } else {
            mediaPlayer.setCycleCount(times > 0 ? times : -1);
        }
        return this;
    }

    @Override
    public Audable stop() {
        if (!ready || waiting > 0) {
            queue.add(this::stop);
        } else {
            mediaPlayer.stop();
        }

        return this;
    }

    @Override
    public Audable pause() {
        if (!ready || waiting > 0) {
            queue.add(this::pause);
        } else {
            mediaPlayer.pause();
        }

        return this;
    }

    @Override
    public Audable volume(double factor) {
        if (!ready || waiting > 0) {
            queue.add(()->volume(factor));
        } else {
            mediaPlayer.setVolume(factor);
        }

        return this;
    }

    @Override
    public Audable pause(double seconds) {
        if (!ready || waiting > 0) {
            queue.add(()->pause(seconds));
        } else {
            mediaPlayer.pause();
            sleep(seconds);
            play();
        }
        return this;
    }

    @Override
    public Audable onDone(AudioEvent event) {
        doneHandler = event;
        return this;
    }
}
