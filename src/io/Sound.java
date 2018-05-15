package io;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Sound {
    static int TICKS_PER_SECOND = 15;

    private static Map<String, Audio> soundMap = new HashMap<String, Audio>();
    private static AllAudio all = new AllAudio(soundMap);
    private static Timeline timeline;

    private static void initialize() {
        if (timeline == null) {
            timeline = new Timeline();
            timeline.setCycleCount(Animation.INDEFINITE);

            double duration = Math.round(1000 / TICKS_PER_SECOND);

            timeline.getKeyFrames().add(new KeyFrame(new Duration(duration), e -> tick()));
            timeline.play();
        }
    }

    public static Audable file(String filename) {
        initialize();

        if (!soundMap.containsKey(filename)) {
            soundMap.put(filename, new Audio(filename));
        }

        return soundMap.get(filename);
    }

    private static void tick() {
        for(Map.Entry<String, Audio> entry : soundMap.entrySet()) {
            Audio audio = entry.getValue();
            audio.tick();
        }
    }


    public static Audable all() {
        return all;
    }

    public static Tts tts(String text) {
            Tts tts = new Tts();

            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(() -> {
                //use ProcessBuilder here to make the process
                if (!Sys.isWindows() && !Sys.isMac()) {
                    Output.error("Text to Speech is not supported on this system");
                } else {

                    Process p = null;

                    try {
                        if (Sys.isMac()) {
                            p = Runtime.getRuntime().exec("Say " + text);

                            try {
                                p.waitFor();

                                if (tts.hadDoneEvent()) {
                                    tts.done();
                                }

                                //  System.out.println("Say is done.");
                            } catch (InterruptedException e) {
                                Output.error("Text to Speech error");
                            }
                        } else {
                            Runtime.getRuntime().exec("wscript.exe " + Sys.HOME + "/say.vbs " + text);
                       //     Runtime.getRuntime().exec("wscript.exe \"C:\\Program Files\\tts\\say.vbs\" Howdie Cowboy");
                        }


                    } catch (IOException e) {
                        Output.error("Text to Speech is not configured");
                    }
                }

            });

            return tts;

         //   Process p = Runtime.getRuntime().exec("Say " + text);

           // p.waitFor();


    }


}
