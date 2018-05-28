package io;

import javafx.scene.Node;

public class Collision {
    boolean flag;
    Itemable target;
    ItemEvent event;

    public Collision(Itemable target, ItemEvent event) {
        this.target = target;
        this.event = event;
    }

    public boolean isFlagged() {
        return flag;
    }

    public Itemable getTarget() {
        return target;
    }

    public ItemEvent getEvent() {
        return event;
    }

    public void check(Node node) {

        if (target instanceof ItemGroup) {
            ItemGroup group = (ItemGroup)target;

            System.out.println(((ItemGroup) target).size());
            group.getItems().forEach((item) -> {
                if (item.getNode().getBoundsInParent().intersects(node.getBoundsInParent())) {
                    if (!flag) {
                        event.handle((Item)node.getUserData());
                        flag = true;
                    }
                } else {
                    flag = false;
                }
            });


        } else if (target instanceof Item) {
            Item item = (Item)target;
            if (item.getNode().getBoundsInParent().intersects(node.getBoundsInParent())) {
                if (!flag) {
                    event.handle((Item)node.getUserData());
                    flag = true;
                }
            } else {
                flag = false;
            }
        }
    }

}
