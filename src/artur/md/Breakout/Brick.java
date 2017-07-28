package artur.md.Breakout;

import java.awt.Color;
import java.awt.Graphics2D;

class Brick {
    public static final int HEIGHT = 20;
    public static final int WIDTH = 50;

    private final Color color = Color.BLUE;

    private int xPos;
    private int yPos;
    private boolean active;
    private final int score;

    Brick(int x, int y) {
        xPos = x;
        yPos = y;
        active = true;
        score = 50;
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
        yPos = y;
    }

    public boolean hitBy(Ball ball) {
        if( (ball.getY() < yPos + HEIGHT) && (ball.getY() >= yPos) ) {
            if( (ball.getX() < xPos + WIDTH) && (ball.getX() >= xPos) ) {
                return true;
            }
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics2D g) {
        if(active) {
            g.setColor(color);
            g.fillRect(xPos, yPos, WIDTH - 1, HEIGHT - 1);
        }
    }

    @Override
    public String toString() {
        return "[" + xPos + ", " + yPos + "]";
    }

}
