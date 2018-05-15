package io;


import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public abstract class Win {

    //todo: add centered text
    //todo: Polygon
    //todo:  Output and Input types
    //todo: Button
    //todo: Groups (like id)  .group("alien");
    //todo: load font file

    //todo: Sys.isMac  Sys.isWindows  Sys.isLinux

    //todo: implement Backgroundable interface (is interface needed?)
    //todo: video background !!!

    //todo: form
    //todo: load icon
    //todo: speech - recognize    Sound.recognize(ms_silence_to_stop).onDone(str->{})

    //todo: drop support of Win.background
    //todo: image texture fill in Win.bg()

    //todo: addVideo()

    //todo: Color picker in editor
    //todo:  .clone() and item

    //todo: ask for help assigned to another student and recorded


  /* Audable fadeOut(double seconds);
    Audable fadeIn(double seconds);
    Audable increaseVolume(double factor);
    Audable rate(double rate);
    Audable volume(double volume);
    Audable increaseRate(double factor);
    Audable decreaseRate(double factor);
    Audable decreaseVolume(double factor); */

    /*
    todo:User class
    todo:User.getName()
    todo:User.onLoaded()????

    todo:Mouse events have handler with object that was clicked
    todo: Audio handler with soung that is done

    todo:Net.remoteInput(studentId);
    todo:Net.remoteInput(account);
    todo:Net.message()

    todo: flyOutLeft();

    todo:Net.connect()

    todo:Win.onOpen
    todo:Win.onClose

    todo: Clock.getTime Clock.getDate
    todo: Clock.wait(10).onDone
     */

    //abstract pane so it can be used in other applications

    //todo: hit area? invisible circle?

    //DEFAULT MOUSE KEY handler class

    //todo: Map.getImage(Location loc)
    //todo: Map.getDistance(Location loc)

    //todo: StageStyle.TRANSPARENT

    //todo: Translate.text("hello")   ?
    //todo: Weather.temp(Location loc)

    //todo: custom Exceptions for files and id duplicate attempts

    //todo: background image
    //todo: background texture image

    //todo: Sound.tts("hello wolrd")   - text to speech

    //todo: remove code warnings in IDE

    //todo: itemable platorm() gravity()

    //todo: cursorPoint();
    //todo: cursorNone();
    //todo: cursorDie();

    //todo: output dialog with image
    //todo: output dialog buffer then display (so can show images and text)?  or daisy chain?

    //todo: use angles from math classes

    //todo: drag

    //todo: User class (gets a json or xml feed)

    //if method is set an id instead of a constructor

    //todo: sound - play notes?  make music assignment


    //todo: x() and y() methods

    //todo: auto trim transparent images

    //todo:  Live Connect  (abstract json services)

    //todo: next year - set fill and stroke seperately for window (not in constructors)

    //todo: increaseSpeed, decreaseSpeed

    //todo: sound

    //todo: Scrolling Image Background?

    //todo: collisions
    //todo: more keyhandlers
    //todo: grow() & shrink()


    private static Stage stage;
    private static Scene scene;
    private static DojoPane dojoPane;
    private static BorderPane borderPane;


    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String DEFAULT_TITLE = "Application";


    static Stage stage() {
        return stage;
    }

    public static void open(String title, int width, int height) {
        initialize(title, width, height);

        stage.show();
        stage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
    }

    public static void open() {
        open(DEFAULT_TITLE);
    }

    public static void open(int width, int height) {
        open(DEFAULT_TITLE, width, height);
    }

    public static void open(String title) {
        Screen screen = Screen.getPrimary();

        Rectangle2D bounds = screen.getVisualBounds();

        if (stage == null) {
            stage = new Stage();
        }

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());

      //  System.out.println(bounds.getWidth());

        open(title, (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    public static void close() {
        stage.close();
    }

    private static void initialize() {
        if (stage == null) {
            initialize(DEFAULT_TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }

    private static void initialize(String title, int width, int height) {
        if (stage == null) {
            stage = new Stage();
        }

        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);

       // stage.setAlwaysOnTop(true);

        //anchorPane.setStyle("-fx-background-color: crimson");
        borderPane = new BorderPane();

        scene = new Scene(borderPane);
        stage.setScene(scene);

        dojoPane = new DojoPane(borderPane, stage.getWidth(), stage.getHeight());

      //  borderPane.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));



        scene.setOnKeyPressed(e -> {
            dojoPane.keyPressed(e);
        });

    }

    public static double width() {
        return dojoPane.getWidth();
    }

    public static double height() {
        return dojoPane.getHeight();
    }

    public static int fps() {
        return dojoPane.fps();
    }

    public static void fps(int fps) {
        dojoPane.fps(fps);
    }

    public static void fill(Color color) {
        dojoPane.fill(color);
    }

    public static void stroke(Color color) {
        dojoPane.stroke(color);
    }

    public static void stroke(Color color, double width) {
        dojoPane.stroke(color, width);
    }

    public static void stroke(double width) {
        dojoPane.stroke(width);
    }

    public static void font(String name, double size, Color color) {
        dojoPane.font(name,size,color);
    }

    public static void font(double size, Color color) {
        dojoPane.font(size,color);
    }

    public static void font(String name, Color color) {
        dojoPane.font(name,color);
    }

    public static void font(Color color) {
        dojoPane.font(color);
    }

    public static void font(String name, double size) {
        dojoPane.font(name, size);
    }

    public static void font(double size) {
        dojoPane.font(size);
    }

    public static void font(String name) {
        dojoPane.font(name);
    }

    public static Itemable addText(String text) {
        initialize();
        return dojoPane.addText(text);
    }

    public static Itemable addCircle(double radius) {
        initialize();
        return dojoPane.addCircle(radius);
    }

    public static Itemable addRectangle(double width, double height) {
        initialize();
        return dojoPane.addRectangle(width,height);
    }


    public static Itemable addImage(String filename) {
        initialize();
        return dojoPane.addImage(filename);
    }


    public static Item id(String id) {
        return dojoPane.id(id);
    }

    public static Itemable all() {
        return dojoPane.all();
    }

    public static Itemable circles() {
        return dojoPane.circles();
    }

    public static Itemable rectangles() {
        return dojoPane.rectangles();
    }

    public static Itemable images() {
        return dojoPane.images();
    }

    public static Itemable texts() {
        return dojoPane.texts();
    }

    public static void clear() {
        dojoPane.clear();
    }

    public static void onLeftKey(DefaultEvent handler) {
        dojoPane.onLeftKey(handler);
    }

    public static void onRightKey(DefaultEvent handler) {
        dojoPane.onRightKey(handler);
    }

    public static void onUpKey(DefaultEvent handler) {
        dojoPane.onUpKey(handler);
    }

    public static void onDownKey(DefaultEvent handler) {
        dojoPane.onDownKey(handler);
    }

    public static void onSpaceKey(DefaultEvent handler) {
        dojoPane.onSpaceKey(handler);
    }

    public static void onLetterKey(CharKeyEvent handler) {
        dojoPane.onLetterKey(handler);
    }

    public static void onEscapeKey(DefaultEvent handler) {
        dojoPane.onEscapeKey(handler);
    }

    public static void onEnterKey(DefaultEvent handler) {
        dojoPane.onEnterKey(handler);
    }

    public static void onNumericKey(NumericKeyEvent handler) {
        dojoPane.onNumericKey(handler);
    }

    public static void onFrame(DefaultEvent handler) {
        dojoPane.onFrame(handler);
    }


    public static void bg(Color color) {
        dojoPane.bg(color);
    }

    public static void bg(String filename) {
        dojoPane.bg(filename);
    }


}