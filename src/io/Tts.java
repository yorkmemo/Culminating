package io;

public class Tts {
    private DefaultEvent doneHandler = null;

    boolean hadDoneEvent() {
        return doneHandler != null;
    }

    void done() {
        doneHandler.handle(null);
    }


    public void onDone(DefaultEvent event) {
        doneHandler = event;
    }
}
