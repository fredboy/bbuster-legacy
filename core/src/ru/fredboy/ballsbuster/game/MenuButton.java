package ru.fredboy.ballsbuster.game;

public class MenuButton {

    private int type;
    private int x;
    private int y;
    private String text;

    public MenuButton(String text, int x, int y, int type) {
        setText(text);
        setX(x);
        setY(y);
        setType(type);
    }

    public MenuButton(String text, int x, int y) {
        setText(text);
        setX(x);
        setY(y);
        setType(1);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
