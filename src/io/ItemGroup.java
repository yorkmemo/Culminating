package io;

import javafx.scene.paint.Color;

import java.util.LinkedList;

class ItemGroup implements Itemable {
    private LinkedList<Item> items;
    private String type;

    ItemGroup() {
        this.type = null;
        items = new LinkedList<>();
    }

    ItemGroup(String type) {
        this.type = type;
        items = new LinkedList<>();
    }

    boolean add(Item item) {
        // System.out.println(type+"|"+item.getClass().getName());
        return (type != null && item.getType().equals(type) && items.add(item)) || (type == null && items.add(item));
    }

    int size() {
        return items.size();
    }

    LinkedList<Item> getItems() {
        return items;
    }

    @Override
    public Itemable stroke(Color color) {
        for (Item item : items) {
            item.stroke(color);
        }

        return this;
    }

    @Override
    public Itemable fill(Color color) {
        for (Item item : items) {
            item.fill(color);
        }

        return this;
    }

    @Override
    public Itemable onOver(ItemEvent handler) {
        for (Item item : items) {
            item.onOver(handler);
        }

        return this;
    }

    @Override
    public Itemable onOut(ItemEvent handler) {
        for (Item item : items) {
            item.onOut(handler);
        }

        return this;
    }

    @Override
    public Itemable onClick(ItemEvent handler) {
        for (Item item : items) {
            item.onClick(handler);
        }

        return this;
    }

    @Override
    public Itemable onEdge(ItemEvent handler) {
        for (Item item : items) {
            item.onEdge(handler);
        }

        return this;
    }

    @Override
    public Itemable onEdgeLeft(ItemEvent handler) {
        for (Item item : items) {
            item.onEdgeLeft(handler);
        }

        return this;
    }

    @Override
    public Itemable onEdgeRight(ItemEvent handler) {
        for (Item item : items) {
            item.onEdgeRight(handler);
        }

        return this;
    }

    @Override
    public Itemable onEdgeTop(ItemEvent handler) {
        for (Item item : items) {
            item.onEdgeTop(handler);
        }

        return this;
    }

    @Override
    public Itemable onEdgeBottom(ItemEvent handler) {
        for (Item item : items) {
            item.onEdgeBottom(handler);
        }

        return this;

    }

    @Override
    public Itemable onCollide(String id, ItemEvent handler) {
        for (Item item : items) {
            item.onCollide(id,handler);
        }

        return this;
    }

    @Override
    public Itemable onCollide(Itemable item, ItemEvent handler) {
        for (Item i : items) {
            item.onCollide(i,handler);
        }

        return this;
    }


    @Override
    public Itemable dir(double angle, double pps) {
        for (Item item : items) {
            item.dir(angle, pps);
        }

        return this;
    }

    @Override
    public Itemable dir(double angle) {
        for (Item item : items) {
            item.dir(angle);
        }

        return this;
    }

    @Override
    public Itemable ranColor() {
        for (Item item : items) {
            item.ranColor();
        }

        return this;
    }

    @Override
    public Itemable ranPos() {
        for (Item item : items) {
            item.ranPos();
        }

        return this;
    }

    @Override
    public Itemable ranPos(double leftX, double topY, double width, double height) {
        for (Item item : items) {
            item.ranPos(leftX, topY, width, height);
        }

        return this;
    }

    @Override
    public Itemable ranDir() {
        for (Item item : items) {
            item.ranDir();
        }

        return this;
    }

    @Override
    public Itemable ranDir(double min_angle, double max_angle) {
        for (Item item : items) {
            item.ranDir(min_angle, max_angle);
        }

        return this;
    }

    @Override
    public Itemable speed(double pps) {
        for (Item item : items) {
            item.speed(pps);
        }

        return this;
    }

    @Override
    public Itemable ranSpeed(double min_pps, double max_pps) {
        for (Item item : items) {
            item.ranSpeed(min_pps, max_pps);
        }

        return this;
    }

    @Override
    public Itemable pos(double x, double y) {
        for (Item item : items) {
            item.pos(x, y);
        }

        return this;
    }

    @Override
    public Itemable x(double x) {
        for (Item item : items) {
            item.x(x);
        }

        return this;
    }

    @Override
    public Itemable y(double y) {
        for (Item item : items) {
            item.y(y);
        }

        return this;
    }

    @Override
    public Itemable width(double width) {
        for (Item item : items) {
            item.width(width);
        }

        return this;
    }

    @Override
    public Itemable height(double height) {
        for (Item item : items) {
            item.height(height);
        }

        return this;
    }

    @Override
    public Itemable left(double pps) {
        for (Item item : items) {
            item.left(pps);
        }

        return this;
    }

    @Override
    public Itemable left() {
        for (Item item : items) {
            item.left();
        }

        return this;
    }

    @Override
    public Itemable right(double pps) {
        for (Item item : items) {
            item.right(pps);
        }

        return this;
    }

    @Override
    public Itemable right() {
        for (Item item : items) {
            item.right();
        }

        return this;
    }

    @Override
    public Itemable up(double pps) {
        for (Item item : items) {
            item.up(pps);
        }

        return this;
    }

    @Override
    public Itemable up() {
        for (Item item : items) {
            item.up();
        }

        return this;

    }

    @Override
    public Itemable down(double pps) {
        for (Item item : items) {
            item.down(pps);
        }

        return this;
    }

    @Override
    public Itemable down() {
        for (Item item : items) {
            item.down();
        }

        return this;
    }

    @Override
    public Itemable hide() {
        for (Item item : items) {
            item.hide();
        }

        return this;
    }

    @Override
    public Itemable show() {
        for (Item item : items) {
            item.show();
        }

        return this;
    }

    @Override
    public Itemable stop() {
        for (Item item : items) {
            item.stop();
        }

        return this;

    }

    @Override
    public Itemable text(String text) {
        for (Item item : items) {
            item.text(text);
        }

        return this;
    }

    @Override
    public Itemable text(char ch) {
        for (Item item : items) {
            item.text(ch);
        }

        return this;
    }

    @Override
    public Itemable text(int num) {
        for (Item item : items) {
            item.text(num);
        }

        return this;
    }

    @Override
    public Itemable text(double num) {
        for (Item item : items) {
            item.text(num);
        }

        return this;
    }

    @Override
    public Itemable vBounce() {
        for (Item item : items) {
            item.vBounce();
        }

        return this;
    }

    @Override
    public Itemable hBounce() {
        for (Item item : items) {
            item.hBounce();
        }

        return this;
    }

    @Override
    public Itemable rotate(double degrees) {
        for (Item item : items) {
            item.rotate(degrees);
        }

        return this;
    }

    @Override
    public Itemable fadeIn() {
        for (Item item : items) {
            item.fadeIn();
        }

        return this;
    }

    @Override
    public Itemable fadeOut() {
        for (Item item : items) {
            item.fadeOut();
        }

        return this;
    }

    @Override
    public Itemable fadeIn(double seconds) {
        for (Item item : items) {
            item.fadeIn(seconds);
        }

        return this;
    }

    @Override
    public Itemable fadeOut(double seconds) {
        for (Item item : items) {
            item.fadeOut(seconds);
        }

        return this;
    }

    @Override
    public Itemable sleep(double seconds) {
        for (Item item : items) {
            item.sleep(seconds);
        }

        return this;
    }

    @Override
    public Itemable flipH() {
        for (Item item : items) {
            item.flipH();
        }

        return this;
    }

    @Override
    public Itemable unFlipH() {
        for (Item item : items) {
            item.unFlipH();
        }

        return this;
    }

    @Override
    public Itemable flipV() {
        for (Item item : items) {
            item.flipV();
        }

        return this;
    }

    @Override
    public Itemable unFlipV() {
        for (Item item : items) {
            item.unFlipV();
        }

        return this;
    }


    @Override
    public Item id(String id) {
        if (items.size() > 0) {

            for (Item temp : items) {
                if (temp.getId().equals(id)) {
                    return temp;
                }
            }
        }

        Output.error("Identifier not found: " + id);

        return (new NullItem()).item;
    }

    @Override
    public Itemable angle(double degrees) {
        for (Item item : items) {
            item.angle(degrees);
        }

        return this;
    }

    @Override
    public Itemable opacity(double factor) {
        for (Item item : items) {
            item.opacity(factor);
        }

        return this;
    }

    @Override
    public void remove() {
        for (Item item : items) {
            item.remove();
        }

        while (!items.isEmpty()) items.pop();
    }

    public void remove(Item item) {
        for (Item itemCheck : items) {
            if (itemCheck == item) {
                items.remove(item);
                item.remove();
                return;
            }
        }
    }

    @Override
    public Itemable edgeBounce() {
        for (Item item : items) {
            item.edgeBounce();
        }

        return this;
    }

    @Override
    public Itemable edgeBounceFlip() {
        for (Item item : items) {
            item.edgeBounceFlip();
        }

        return this;
    }

    @Override
    public Itemable edgeStop() {
        for (Item item : items) {
            item.edgeStop();
        }

        return this;
    }

    @Override
    public Itemable edgeRemove() {
        for (Item item : items) {
            item.edgeRemove();
        }

        return this;
    }

    @Override
    public Itemable edgeIgnore() {
        for (Item item : items) {
            item.edgeIgnore();
        }

        return this;
    }
}
