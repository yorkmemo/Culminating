package io;

import javafx.scene.paint.Color;

public interface Itemable {
    //todo: return Itemable so commands can be chained

    Itemable stroke(Color color);
    Itemable fill(Color color);
    Itemable onOver(ItemEvent handler);
    Itemable onOut(ItemEvent handler);
    Itemable onClick(ItemEvent handler);
    Itemable onEdge(ItemEvent handler);
    Itemable onEdgeLeft(ItemEvent handler);
    Itemable onEdgeRight(ItemEvent handler);
    Itemable onEdgeTop(ItemEvent handler);
    Itemable onEdgeBottom(ItemEvent handler);
    Itemable onCollide(String id, ItemEvent handler);
    Itemable dir(double angle, double pps);
    Itemable dir(double angle);
    Itemable ranColor();
    Itemable ranPos();
    Itemable ranPos(double leftX, double topY, double width, double height);
    Itemable ranDir();
    Itemable ranDir(double min_angle, double max_angle);
    Itemable speed(double pps);
    Itemable ranSpeed(double min_pps, double max_pps);
    Itemable pos(double x, double y);
    Itemable x(double x);
    Itemable y(double y);
    Itemable width(double width);
    Itemable height(double height);
    Itemable left(double pps);
    Itemable left();
    Itemable right(double pps);
    Itemable right();
    Itemable up(double pps);
    Itemable up();
    Itemable down(double pps);
    Itemable down();
    Itemable hide();
    Itemable show();
    Itemable stop();
    Itemable text(String text);
    Itemable text(char ch);
    Itemable text(int num);
    Itemable text(double num);
    Itemable vBounce();
    Itemable hBounce();
    Itemable rotate(double degrees);
    Itemable angle(double degrees);
    Itemable opacity(double factor);
    Itemable fadeIn();
    Itemable fadeOut();
    Itemable fadeIn(double seconds);
    Itemable fadeOut(double seconds);
    Itemable sleep(double seconds);
    Itemable flipH();
    Itemable unFlipH();
    Itemable flipV();
    Itemable unFlipV();
    Item id(String id);

    void remove();

    Itemable edgeBounce();
    Itemable edgeBounceFlip();
    Itemable edgeStop();
    Itemable edgeRemove();
    Itemable edgeIgnore();



}
