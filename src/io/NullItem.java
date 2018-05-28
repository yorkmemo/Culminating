package io;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NullItem implements Itemable {
    Item item = new Item(new Text(""));
    @Override

    public Itemable stroke(Color color) {
        return this;
    }

    @Override
    public Itemable fill(Color color) {
        return this;
    }

    @Override
    public Itemable onOver(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onOut(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onClick(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onEdge(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onEdgeLeft(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onEdgeRight(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onEdgeTop(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onEdgeBottom(ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onCollide(String id, ItemEvent handler) {
        return this;
    }

    @Override
    public Itemable onCollide(Itemable item, ItemEvent handler) {
        return this;
    }


    @Override
    public Itemable dir(double angle, double pps) {
        return this;
    }

    @Override
    public Itemable dir(double angle) {
        return this;
    }

    @Override
    public Itemable ranColor() {
        return this;
    }

    @Override
    public Itemable ranPos() {
        return this;
    }

    @Override
    public Itemable ranPos(double leftX, double topY, double width, double height) {
        return this;
    }

    @Override
    public Itemable ranDir() {
        return this;
    }

    @Override
    public Itemable ranDir(double min_angle, double max_angle) {
        return this;
    }

    @Override
    public Itemable speed(double pps) {
        return this;
    }

    @Override
    public Itemable ranSpeed(double min_pps, double max_pps) {
        return this;
    }

    @Override
    public Itemable pos(double x, double y) {
        return this;
    }

    @Override
    public Itemable x(double x) {
        return this;
    }

    @Override
    public Itemable y(double y) {
        return this;
    }

    @Override
    public Itemable width(double width) {
        return this;
    }

    @Override
    public Itemable height(double height) {
        return this;
    }

    @Override
    public Itemable left(double pps) {
        return this;
    }

    @Override
    public Itemable left() {
        return this;
    }

    @Override
    public Itemable right(double pps) {
        return this;
    }

    @Override
    public Itemable right() {
        return this;
    }

    @Override
    public Itemable up(double pps) {
        return this;
    }

    @Override
    public Itemable up() {
        return this;
    }

    @Override
    public Itemable down(double pps) {
        return this;
    }

    @Override
    public Itemable down() {
        return this;
    }

    @Override
    public Itemable hide() {
        return this;
    }

    @Override
    public Itemable show() {
        return this;
    }

    @Override
    public Itemable stop() {
        return this;
    }

    @Override
    public Itemable text(String text) {
        return this;
    }

    @Override
    public Itemable text(char ch) {
        return this;
    }

    @Override
    public Itemable text(int num) {
        return this;
    }

    @Override
    public Itemable text(double num) {
        return this;
    }

    @Override
    public Itemable vBounce() {
        return this;
    }

    @Override
    public Itemable hBounce() {
        return this;
    }

    @Override
    public Itemable rotate(double degrees) {
        return this;
    }

    @Override
    public Itemable fadeIn() {
        return this;
    }

    @Override
    public Itemable fadeOut() {
        return this;
    }

    @Override
    public Itemable fadeIn(double seconds) {
        return this;
    }

    @Override
    public Itemable fadeOut(double seconds) {
        return this;
    }

    @Override
    public Itemable sleep(double seconds) {
        return this;
    }

    @Override
    public Itemable flipH() {
        return this;
    }

    @Override
    public Itemable unFlipH() {
        return this;
    }

    @Override
    public Itemable flipV() {
        return this;
    }

    @Override
    public Itemable unFlipV() {
        return this;
    }


    @Override
    public Item id(String name) {
        return item;
    }

    @Override
    public Itemable angle(double degrees) {
        return this;
    }

    @Override
    public Itemable opacity(double factor) {
        return this;
    }

    @Override
    public void remove() {

    }

    @Override
    public Itemable edgeBounce() {
        return this;
    }

    @Override
    public Itemable edgeBounceFlip() {
        return this;
    }

    @Override
    public Itemable edgeStop() {
        return this;
    }

    @Override
    public Itemable edgeRemove() {
        return this;
    }

    @Override
    public Itemable edgeIgnore() {
        return this;
    }
}
