package io;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.Queue;

public class Item implements Itemable {
    private static final double DEFAULT_FADE = 1;

    private static final int EDGE_IGNORE = 0;
    private static final int EDGE_BOUNCE = 1;
    private static final int EDGE_BOUNCE_FLIP = 2;
    private static final int EDGE_STOP = 3;
    private static final int EDGE_REMOVE = 4;

    private static final double DEFAULT_PPS = 30;

    private Node node;
    private DojoPane dojo;
    private double dx, dy, dr, da; //deltas x, y, rotate, alpha
    private double dir, speed;

    private ItemEvent collideEvent = null;
    private Item collideTarget = null;

    private boolean collideFlag;

    private ItemEvent edgeHandler = null;
    private ItemEvent edgeLeftHandler = null;
    private ItemEvent edgeRightHandler = null;
    private ItemEvent edgeTopHandler = null;
    private ItemEvent edgeBottomHandler = null;

    private boolean edgeLeftFlag, edgeRightFlag, edgeTopFlag, edgeBottomFlag;

    private int edge = EDGE_IGNORE;
    private Queue<Runnable> queue = new LinkedList<>();
    private int waiting = 0;

    private boolean circle, rect, shape, img, text;

    public Item(Node node) {
        this.node = node;
        node.setUserData(this);
        dojo = ((DojoPane)(node.getParent().getUserData()));
        node.setMouseTransparent(true);
        circle = node instanceof Circle;
        shape = node instanceof Shape;
        rect = node instanceof Rectangle;
        text = node instanceof Text;
        img = node instanceof ImageView;
     //   queue ;
    }


    public String getType() {
        return node.getClass().getName();
    }

    void tick() {
        waiting--;

        while (waiting <= 0 && queue.size() > 0) {
            Platform.runLater(queue.remove());
        }

        if (dx != 0) {
            if (circle) ((Circle)node).setCenterX(((Circle)node).getCenterX() + dx);
            else if (rect) ((Rectangle)node).setX(((Rectangle)node).getX() + dx);
            else node.setLayoutX(node.getLayoutX() + dx);
        }

        if (dy != 0) {
            if (circle) ((Circle)node).setCenterY(((Circle)node).getCenterY() + dy);
            else if (rect) ((Rectangle)node).setY(((Rectangle)node).getY() + dy);
            else node.setLayoutY(node.getLayoutY() + dy);
        }

        if (collideEvent != null && collideTarget != null) {
         //   System.out.println("-"+node.getBoundsInParent());
          //  System.out.println("+"+collideTarget.node.getBoundsInParent());
            if (collideTarget.node.getBoundsInParent().intersects(node.getBoundsInParent())) {
                if (!collideFlag) {
                    collideEvent.handle(this);
                    collideFlag = true;
                }
            } else {
                collideFlag = false;
            }
        }

       // System.out.println(node.getClass().getName() + "|" + edge);

        if ((edge != EDGE_IGNORE || edgeHandler != null || edgeLeftHandler != null || edgeRightHandler != null || edgeTopHandler != null || edgeBottomHandler != null) && (dx != 0 || dy != 0)) {

           // Bounds screen = Win.edges();
            //double w = Win.getWidth();

            if (node.getParent() == null) return;  //node has been removed

            double w = node.getParent().getBoundsInParent().getWidth();
            double h = node.getParent().getBoundsInParent().getHeight();

          //  Bounds bounds = node.getBoundsInParent();


          //  double x = node.getBoundsInParent().getMaxX();

            if (node instanceof Circle) {
               // System.out.println(node.getClass().getName() + "|" + bounds.getMinY() + "|" + bounds.getMaxY() + "|" + node.getLayoutY()+ "|" + ((Circle)node).getCenterY());
            }

            if (node.getBoundsInParent().getMinX() < 0) {

                if (edgeHandler != null && !edgeLeftFlag) {
                    edgeHandler.handle(this);
                    edgeLeftFlag = true;
                }

                if (edgeLeftHandler != null && !edgeLeftFlag) {
                    edgeLeftHandler.handle(this);
                    edgeLeftFlag = true;
                }

                if (edge == EDGE_BOUNCE || edge == EDGE_BOUNCE_FLIP) {
                    dir(180 - dir);
                    if (circle) {
                        ((Circle)node).setCenterX(((Circle)node).getRadius());
                    } else {
                        if (edge == EDGE_BOUNCE_FLIP) {
                            forceFlipHorizontally();
                        }
                        node.setLayoutX(0);

                        while (node.getBoundsInParent().getMinX() < 0) {
                            node.setLayoutX(node.getLayoutX()+1);
                        }
                    }
                } else if (edge == EDGE_STOP) {

                    if (circle) {
                        ((Circle) node).setCenterX(((Circle) node).getRadius());
                    } else if (rect) {
                        ((Rectangle) node).setX(0);
                    } else {
                        node.setLayoutX(0);
                       // System.out.println("stopped ... ");
                    }

                    speed(0);
                } else if (edge == EDGE_REMOVE) {
                    this.remove();
                    return;
                }
            } else {
                edgeLeftFlag = false;
            }

            if (node.getBoundsInParent().getMaxX() > w) {
                if (edgeHandler != null && !edgeRightFlag) {
                    edgeHandler.handle(this);
                    edgeRightFlag = true;
                }

                if (edgeRightHandler != null && !edgeRightFlag) {
                    edgeRightHandler.handle(this);
                    edgeRightFlag = true;
                }

                if (edge == EDGE_BOUNCE || edge == EDGE_BOUNCE_FLIP) {
                    dir(180 - dir);
                    if (circle) {
                        ((Circle)node).setCenterX(w - ((Circle)node).getRadius());
                    } else {
                        if (edge == EDGE_BOUNCE_FLIP) {
                            forceFlipHorizontally();
                        }
                        node.setLayoutX(w - node.getLayoutBounds().getWidth());

                        while (node.getBoundsInParent().getMaxX() >= w) {
                            node.setLayoutX(node.getLayoutX()-1);
                        }
                    }
                } else if (edge == EDGE_STOP) {
                    if (circle) {
                        ((Circle)node).setCenterX(w - ((Circle)node).getRadius());
                    } else if (rect) {
                        ((Rectangle) node).setX(w - node.getLayoutBounds().getWidth());
                    } else {
                        node.setLayoutX(w - node.getLayoutBounds().getWidth());
                    }
                    speed(0);
                } else if (edge == EDGE_REMOVE) {
                    this.remove();
                    return;
                }
            } else {
                edgeRightFlag = false;
            }

            if (text) {
               // System.out.println(bounds.getMinY() + "|" + ((Text)node).getBaselineOffset());
            }


            if (node.getBoundsInParent().getMinY() < 0) {
                if (edgeHandler != null && !edgeTopFlag) {
                    edgeHandler.handle(this);
                    edgeTopFlag = true;
                }

                if (edgeTopHandler != null && !edgeTopFlag) {
                    edgeTopHandler.handle(this);
                    edgeTopFlag = true;
                }

                if (edge == EDGE_BOUNCE || edge == EDGE_BOUNCE_FLIP) {
                    dir(360 - dir);
                    if (circle) {
                        ((Circle)node).setCenterY(((Circle)node).getRadius());
                    } else if (rect) {
                        ((Rectangle)node).setY(0);
                    } else {
                        node.setLayoutY(0);
                        if (edge == EDGE_BOUNCE_FLIP) {
                            forceFlipVertically();
                        }
                    }
                } else if (edge == EDGE_STOP) {
                    if (circle) {
                        ((Circle)node).setCenterY(((Circle)node).getRadius());
                    } else if (rect) {
                        ((Rectangle) node).setY(0);
                    } else {
                        System.out.println("stopped ... " + node.getLayoutY());
                        node.setLayoutY(0);

                    }
                    speed(0);
                } else if (edge == EDGE_REMOVE) {
                    this.remove();
                    return;
                }
            } else {
                edgeTopFlag = false;
            }

            if (node.getBoundsInParent().getMaxY() > h) {
                if (edgeHandler != null && !edgeBottomFlag) {
                    edgeHandler.handle(this);
                    edgeBottomFlag = true;
                }

                if (edgeBottomHandler != null && !edgeBottomFlag) {
                    edgeBottomHandler.handle(this);
                    edgeBottomFlag = true;
                }

                if (edge == EDGE_BOUNCE || edge == EDGE_BOUNCE_FLIP) {
                    dir(360 - dir);

                    if (circle) {
                       // System.out.println(h - node.getLayoutBounds().getHeight());
                        ((Circle)node).setCenterY(h - ((Circle)node).getRadius());
                    } else if (rect) {
                        ((Rectangle)node).setY(h-node.getLayoutBounds().getHeight());
                    } else if (text) {
                        int pixels = 1;

                        while (node.getBoundsInParent().getMaxY() > h + pixels) pixels++;

                        node.setLayoutY(h - pixels);

                        if (edge == EDGE_BOUNCE_FLIP) {
                            forceFlipVertically();
                        }
                    } else {
                        node.setLayoutY(h - node.getLayoutBounds().getHeight());

                        if (edge == EDGE_BOUNCE_FLIP) {
                            forceFlipVertically();
                        }
                    }
                } else if (edge == EDGE_STOP) {
                    if (circle) {
                        // System.out.println(h - node.getLayoutBounds().getHeight());
                        ((Circle)node).setCenterY(h - ((Circle)node).getRadius());
                    } else if (rect) {
                        ((Rectangle) node).setY(h - node.getLayoutBounds().getHeight());
                    } else {
                        node.setLayoutY(h - node.getLayoutBounds().getHeight());
                    }
                    speed(0);
                } else if (edge == EDGE_REMOVE) {
                    this.remove();
                    return;
                }
            } else {
                edgeBottomFlag = false;
            }
        }

        if (dr != 0) {
            double r = (node.getRotate() + dr) % 360;
            node.setRotate(r);
        }

        if (node.getOpacity() + da > 1) {
            node.setOpacity(1);
        } else if (node.getOpacity() + da < 0) {
            node.setOpacity(0);
        } else {
            node.setOpacity(node.getOpacity() + da);
        }
    }

    private double getRealAngle(double degrees) {
        double deg = degrees;

        while (deg < 0) deg += 360;

        deg = deg % 360;

        return deg;
    }

    public Itemable onOver(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()->onOver(handler));
        } else {
            if (handler == null) {
                if (node.getOnMouseExited() == null && node.getOnMouseClicked() == null) {
                    node.setMouseTransparent(true);
                }
                node.setOnMouseEntered(null);
            } else {
                node.setMouseTransparent(false);
                node.setOnMouseEntered(e->{handler.handle(this);});
            }

        }
        return this;
    }

    public Itemable onOut(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()->onOut(handler));
        } else {
            if (handler == null) {
                if (node.getOnMouseEntered() == null && node.getOnMouseClicked() == null) {
                    node.setMouseTransparent(true);
                }
                node.setOnMouseExited(null);
            } else {
                node.setMouseTransparent(false);
                node.setOnMouseExited(e->{handler.handle(this);});
            }

        }
        return this;
    }

    public Itemable onClick(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()->onClick(handler));
        } else {
            if (handler == null) {
                if (node.getOnMouseEntered() == null && node.getOnMouseExited() == null) {
                    node.setMouseTransparent(true);
                }
                node.setOnMousePressed(null);
            } else {
                node.setMouseTransparent(false);
                node.setOnMousePressed(e->{handler.handle(this);});
            }

        }
        return this;
    }

    @Override
    public Itemable onEdge(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()-> onEdge(handler));
        } else {
            edgeHandler = handler;
        }
        return this;
    }

    @Override
    public Itemable onEdgeLeft(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()-> onEdgeLeft(handler));
        } else {
            edgeLeftHandler = handler;
        }
        return this;
    }

    @Override
    public Itemable onEdgeRight(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()-> onEdgeRight(handler));
        } else {
            edgeRightHandler = handler;
        }
        return this;
    }

    @Override
    public Itemable onEdgeTop(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()-> onEdgeTop(handler));
        } else {
            edgeTopHandler = handler;
        }
        return this;
    }

    @Override
    public Itemable onEdgeBottom(ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()-> onEdgeBottom(handler));
        } else {
            edgeBottomHandler = handler;
        }
        return this;
    }

    @Override
    public Itemable onCollide(String id, ItemEvent handler) {
        if (waiting > 0) {
            queue.add(()-> onCollide(id, handler));
        } else {
            collideEvent = handler;
            collideTarget = (Item)dojo.id(id);
        }
        return this;
    }


    public Itemable stroke(Color color) {
        if (waiting > 0) {
            queue.add(()->stroke(color));
        } else {

            if (shape) {
                ((Shape) node).setStroke(color);
            }
        }

        return this;
    }

    public Itemable fill(Color color) {
        // System.out.println(fill);
        if (waiting > 0) {
            queue.add(()->fill(color));
        } else {
            if (shape) {
                ((Shape) node).setFill(color);
            }
        }

        return this;
    }

    private void updateDeltas() {
        dx = Math.cos(Math.toRadians(dir)) * speed;
        dy = Math.sin(Math.toRadians(dir)) * speed;
       // System.out.println("dx="+dx+",dy="+dy);
    }

    private double calculateSpeed(double pps) {
        return pps / dojo.fps();
    }

    public Itemable dir(double angle, double pps) {
        if (waiting > 0) {
            queue.add(()-> dir(angle, pps));
        } else {
            speed = pps;
            dir(angle);
        }
        return this;
    }

    public Itemable dir(double angle) {
        if (waiting > 0) {
            queue.add(()-> dir(angle));
        } else {
            dir = getRealAngle(angle);

            updateDeltas();
        }

        return this;
    }

    @Override
    public Itemable ranColor() {
        if (waiting > 0) {
            queue.add(()->ranColor());
        } else {
            if (shape) {
                ((Shape) node).setFill(Color.rgb((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
            }
        }

        return this;
    }

    @Override
    public Itemable ranPos() {
        if (waiting > 0) {
            queue.add(this::ranPos);
        } else {
            ranPos(0,0, dojo.getWidth(), dojo.getHeight());
        }

        return this;
    }

    @Override
    public Itemable ranPos(double leftX, double topY, double width, double height) {
        if (waiting > 0) {
            queue.add(this::ranPos);
        } else {
            double w, h;

            if (node instanceof Circle) {
                w = ((Circle)node).getRadius() * 2;
                h = w;

//                ((Circle)node).setCenterX(((Circle)node).getCenterX() + dx);
            } else {
                w = node.getLayoutBounds().getWidth();
                h = node.getLayoutBounds().getHeight();
            }

            if (w > width) {
                if (node instanceof Circle) {
                    ((Circle) node).setCenterX(leftX + width/2);
                } else {
                    node.setLayoutX(leftX + width/2 - w / 2);
                }
            } else {
                if (node instanceof Circle) {
                    ((Circle) node).setCenterX(Math.random() * (width - w) + leftX + w/2);
                } else {
                    node.setLayoutX(Math.random() * (width - w) + leftX);
                }
            }

            if (h > height) {
                if (node instanceof Circle) {
                    ((Circle) node).setCenterY(topY + height/2);
                } else {
                    node.setLayoutY(topY + height/2 - h / 2);
                }
            } else {
                if (node instanceof Circle) {
                    ((Circle) node).setCenterY(Math.random() * (height - h) + topY + h/2);
                } else {
                    node.setLayoutY(Math.random() * (height - h) + topY);
                }
            }

        }

        return this;
    }


    @Override
    public Itemable ranDir() {
        dir(Math.random() * 360);
        return this;
    }

    @Override
    public Itemable ranDir(double min_angle, double max_angle) {
        min_angle = getRealAngle(min_angle);
        max_angle = getRealAngle(max_angle);

        double min = Math.min(min_angle,max_angle);
        double max = Math.max(min_angle,max_angle);

        dir(Math.random() * (max - min + 1) + min);

        return this;
    }

    public Itemable speed(double pps) {
        if (waiting > 0) {
            queue.add(()->speed(pps));
        } else {
            speed = calculateSpeed(pps);
            updateDeltas();
        }
        return this;
    }

    @Override
    public Itemable ranSpeed(double min_pps, double max_pps) {
        double min = Math.min(min_pps,max_pps);
        double max = Math.max(min_pps,max_pps);

        speed(Math.random() * (max - min + 1) + min);
        return this;
    }

    @Override
    public Itemable pos(double x, double y) {
        if (waiting > 0) {
            queue.add(()->pos(x,y));
        } else {
            if (circle) {
                ((Circle) node).setCenterX(x);
                ((Circle) node).setCenterY(y);
            } else if (rect) {
                ((Rectangle) node).setX(x);
                ((Rectangle) node).setY(y);
            } else {
                node.setLayoutX(x);
                node.setLayoutY(y);
            }
        }
        return this;
    }

    @Override
    public Itemable x(double x) {
        if (waiting > 0) {
            queue.add(()-> x(x));
        } else {
            if (circle) {
                ((Circle) node).setCenterX(x);
            } else if (rect) {
                ((Rectangle) node).setX(x);
            } else {
                node.setLayoutX(x);
            }
        }
        return this;
    }

    @Override
    public Itemable y(double y) {
        if (waiting > 0) {
            queue.add(()-> y(y));
        } else {
            if (circle) {
                ((Circle) node).setCenterY(y);
            } else if (rect) {
                ((Rectangle) node).setY(y);
            } else {
                node.setLayoutY(y);
            }
        }
        return this;
    }

    @Override
    public Itemable width(double width) {
        if (waiting > 0) {
            queue.add(()->width(width));
        } else {
            if (circle) {
                ((Circle) node).setRadius(width/2);
            } else if (rect) {
                ((Rectangle) node).setWidth(width);
            } else if (img) {
                ((ImageView) node).setFitWidth(width);
            } else if (text) {
                double cur = ((Text) node).getLayoutBounds().getWidth();
                double fs = ((Text) node).getFont().getSize();

                while (cur < width) {
                    fs += 0.1;
                    ((Text) node).setFont(Font.font(fs));
                    cur = ((Text) node).getLayoutBounds().getWidth();
                }

                while (cur > width) {
                    fs -= 0.1;
                    ((Text) node).setFont(Font.font(fs));
                    cur = ((Text) node).getLayoutBounds().getWidth();
                }

            } else {
                //? todo: other nodes?
            }
        }
        return this;
    }

    @Override
    public Itemable height(double height) {
        if (waiting > 0) {
            queue.add(()->height(height));
        } else {
            if (circle) {
                ((Circle) node).setRadius(height / 2);
            } else if (rect) {
                ((Rectangle) node).setHeight(height);
            } else if (img) {
                ((ImageView) node).setFitHeight(height);
            } else if (text) {
                double cur = ((Text) node).getLayoutBounds().getHeight();
                double fs = ((Text) node).getFont().getSize();

                while (cur < height) {
                    fs += 0.1;
                    ((Text) node).setFont(Font.font(fs));
                    cur = ((Text) node).getLayoutBounds().getHeight();
                }

                while (cur > height) {
                    fs -= 0.1;
                    ((Text) node).setFont(Font.font(fs));
                    cur = ((Text) node).getLayoutBounds().getHeight();
                }

            } else {
                //? todo: other nodes?
            }
        }
        return this;
    }

    public Itemable left(double pps) {
        if (waiting > 0) {
            queue.add(()->left(pps));
        } else {
            this.speed = calculateSpeed(pps);
            dir(180);
        }
        return this;
    }

    public Itemable left() {
        if (waiting > 0) {
            queue.add(()->left());
        } else {
            if (speed <= 0) {
                speed = calculateSpeed(DEFAULT_PPS);
            }
            dir(180);
        }
        return this;
    }

    public Itemable right(double pps) {
        if (waiting > 0) {
            queue.add(()->right(pps));
        } else {
            this.speed = calculateSpeed(pps);
            dir(0);
        }
        return this;
    }

    public Itemable right() {

        if (waiting > 0) {
            queue.add(this::right);
        } else {
            if (speed <= 0) {
                speed = calculateSpeed(DEFAULT_PPS);
            }
            dir(0);
        }
        return this;
    }

    @Override
    public Itemable up(double pps) {
        if (waiting > 0) {
            queue.add(()->up(pps));
        } else {
            this.speed = calculateSpeed(pps);
            dir(270);
        }
        return this;
    }

    @Override
    public Itemable up() {
        if (waiting > 0) {
            queue.add(()->up());
        } else {
            if (speed <= 0) {
                speed = calculateSpeed(DEFAULT_PPS);
            }
            dir(270);
        }
        return this;
    }

    @Override
    public Itemable down(double pps) {
        if (waiting > 0) {
            queue.add(()->down(pps));
        } else {
            this.speed = calculateSpeed(pps);
            dir(90);
        }
        return this;
    }

    @Override
    public Itemable down() {
        if (waiting > 0) {
            queue.add(()->down());
        } else {
            if (speed <= 0) {
                speed = calculateSpeed(DEFAULT_PPS);
            }
            dir(90);
        }
        return this;
    }

    @Override
    public Itemable hide() {
        if (waiting > 0) {
            queue.add(()->hide());
        } else {
            node.setVisible(false);
        }
        return this;
    }

    @Override
    public Itemable show() {
        if (waiting > 0) {
            queue.add(()->show());
        } else {
            node.setVisible(true);
        }
        return this;
    }

    @Override
    public Itemable stop() {
        if (waiting > 0) {
            queue.add(()->stop());
        } else {
            speed(0);
            rotate(0);
        }
        return this;
    }

    @Override
    public Itemable text(String text) {
        if (waiting > 0) {
            queue.add(()->text(text));
        } else {
            if (node instanceof Text) {
                ((Text)node).setText(text);
            } else {
                Output.error("The text() method can only be used on Text");
            }
        }
        return this;
    }

    @Override
    public Itemable text(char ch) {
        if (waiting > 0) {
            queue.add(()->text(ch));
        } else {
            if (node instanceof Text) {
                ((Text)node).setText(ch+"");
            } else {
                Output.error("The text() method can only be used on Text");
            }
        }
        return this;
    }

    @Override
    public Itemable text(int num) {
        if (waiting > 0) {
            queue.add(()->text(num));
        } else {
            if (node instanceof Text) {
                ((Text)node).setText(Integer.toString(num));
            } else {
                Output.error("The text() method can only be used on Text");
            }
        }
        return this;
    }

    @Override
    public Itemable text(double num) {
        if (waiting > 0) {
            queue.add(()->text(num));
        } else {
            if (node instanceof Text) {
                ((Text)node).setText(Double.toString(num));
            } else {
                Output.error("The text() method can only be used on Text");
            }
        }
        return this;
    }

    @Override
    public Itemable vBounce() {
        if (waiting > 0) {
            queue.add(()-> vBounce());
        } else {
            dir(360 - dir);
        }
        return this;
    }

    @Override
    public Itemable hBounce() {
        if (waiting > 0) {
            queue.add(()-> hBounce());
        } else {
            dir(180 - dir);
        }
        return this;
    }


    public Itemable rotate(double degrees) {
        if (waiting > 0) {
            queue.add(()->rotate(degrees));
        } else {
            dr = degrees;
        }
        return this;
    }

    @Override
    public Itemable fadeIn() {
        if (waiting > 0) {
            queue.add(()->fadeIn());
        } else {
            node.setVisible(true);
            node.setOpacity(0);
            da = 1 / (dojo.fps() * DEFAULT_FADE);
            sleep(DEFAULT_FADE);
        }
        return this;
    }

    @Override
    public Itemable fadeOut() {
        if (waiting > 0) {
            queue.add(()->fadeOut());
        } else {
            da = -1 / (dojo.fps() * DEFAULT_FADE);
            sleep(DEFAULT_FADE);
        }

        return this;
    }


    @Override
    public Itemable fadeIn(double seconds) {
        if (waiting > 0) {
            queue.add(()->fadeIn(seconds));
        } else {
            node.setOpacity(0);
            da = 1 / (dojo.fps() * seconds);
            sleep(seconds);
        }
        return this;
    }

    @Override
    public Itemable fadeOut(double seconds) {
        if (waiting > 0) {
            queue.add(()->fadeOut(seconds));
        } else {
            node.setOpacity(1);
            da = -1 / (dojo.fps() * seconds);
            sleep(seconds);
        }

        return this;
    }

    @Override
    public Itemable sleep(double seconds) {
        if (waiting > 0) {
            queue.add(()->sleep(seconds));
        } else {
            waiting = (int) Math.round(seconds * dojo.fps());
        }

        return this;
    }

    @Override
    public Itemable flipH() {
        if (waiting > 0) {
            queue.add(()-> flipH());
        } else {
             node.setScaleX(-1);
        }
        return this;
    }

    @Override
    public Itemable unFlipH() {
        if (waiting > 0) {
            queue.add(()-> unFlipH());
        } else {
            node.setScaleX(1);
        }
        return this;
    }

    @Override
    public Itemable flipV() {
        if (waiting > 0) {
            queue.add(()-> flipV());
        } else {
            node.setScaleY(-1);
        }
        return this;
    }

    @Override
    public Itemable unFlipV() {
        if (waiting > 0) {
            queue.add(()-> unFlipV());
        } else {
            node.setScaleY(1);
        }
        return this;
    }

    private void forceFlipHorizontally() {

        if (node.getScaleX() < 0) {
            node.setScaleX(1);
        } else {
            node.setScaleX(-1);
        }

    }


    private void forceFlipVertically() {

            if (node.getScaleY() < 0) {
                node.setScaleY(1);
            } else {
                node.setScaleY(-1);
            }

    }


    @Override
    public Item id(String id) {
        dojo.assignId(this, id);

        return this;
    }

    public String getId() {
        return node.getId();
    }


    public double getLeftX() {
        return node.getBoundsInParent().getMinX();
    }


    public double getRightX() {
        return node.getBoundsInParent().getMaxX();
    }


    public double getTopY() {
        return node.getBoundsInParent().getMinY();
    }


    public double getBottomY() {
        return node.getBoundsInParent().getMaxY();
    }


    public double getCenterX() {
        return (node.getBoundsInParent().getMinX() + node.getBoundsInParent().getMaxX()) / 2.0;
    }


    public double getCenterY() {
        return (node.getBoundsInParent().getMinY() + node.getBoundsInParent().getMaxY()) / 2.0;
    }

    @Override
    public Itemable angle(double degrees) {
        if (waiting > 0) {
            queue.add(()-> angle(degrees));
        } else {
            node.setRotate(degrees);
        }

        return this;
    }

    @Override
    public Itemable opacity(double factor) {
        if (waiting > 0) {
            queue.add(()-> opacity(factor));
        } else {
            node.setOpacity(factor > 1 ? 1 : factor < 0 ? 0 : factor);
        }

        return this;
    }

    @Override
    public void remove() {
        if (waiting > 0) {
            queue.add(this::remove);
        } else {
            dojo.remove(this);
        }

    }

    Node getNode() {
        return node;
    }


    @Override
    public Itemable edgeBounce() {
        if (waiting > 0) {
            queue.add(()-> edgeBounce());
        } else {
            edge = EDGE_BOUNCE;
        }
        return this;
    }

    @Override
    public Itemable edgeBounceFlip() {
        if (waiting > 0) {
            queue.add(()-> edgeBounceFlip());
        } else {
            edge = EDGE_BOUNCE_FLIP;
        }
        return this;
    }

    @Override
    public Itemable edgeStop() {
        if (waiting > 0) {
            queue.add(()-> edgeStop());
        } else {
            edge = EDGE_STOP;
        }
        return this;
    }

    @Override
    public Itemable edgeRemove() {
        if (waiting > 0) {
            queue.add(()-> edgeRemove());
        } else {
            edge = EDGE_REMOVE;
        }
        return this;
    }

    @Override
    public Itemable edgeIgnore() {
        if (waiting > 0) {
            queue.add(()-> edgeRemove());
        } else {
            edge = EDGE_IGNORE;
        }
        return this;
    }



}