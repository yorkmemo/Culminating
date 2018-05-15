package io;

public interface Audable {
    Audable play();
    Audable sleep(double seconds);
    Audable loop();
    Audable loop(int times);
    Audable stop();
    Audable pause();
    Audable volume(double factor);
    Audable pause(double seconds);
    Audable onDone(AudioEvent event);

   /* Audable fadeOut(double seconds);
    Audable fadeIn(double seconds);
    Audable increaseVolume(double factor);
    Audable rate(double rate);
    Audable volume(double volume);
    Audable increaseRate(double factor);
    Audable decreaseRate(double factor);
    Audable decreaseVolume(double factor); */


}
