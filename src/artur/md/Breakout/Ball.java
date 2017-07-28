package artur.md.Breakout;

import java.awt.Color;
import java.awt.Graphics2D;

class Ball {
    private int xPos;
    private int yPos;
    private int xDirection;
    private int yDirection;

    public static final int SIZE = 10;
    private static final Color color = Color.RED;

    Ball(int xPos, int yPos, int xDir, int yDir) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xDirection = xDir;
        this.yDirection = yDir;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos= y;
    }

    public int getDX() {
        return xDirection;
    }

    public int getDY() {
        return yDirection;
    }

    public void reverseXDir() {
        xDirection = -xDirection;
    }

    public void reverseYDir() {
        yDirection = - yDirection;
    }

    public void setDX(int dx) {
        xDirection = dx;
    }

    public void setDY(int dy) {
        yDirection = dy;
    }

    public void move() {
        xPos += xDirection;
        yPos += yDirection;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(xPos, yPos, SIZE, SIZE);
    }

    @Override
    public String toString() {
        return "[" + xPos +", " + yPos + "]";
    }
}
