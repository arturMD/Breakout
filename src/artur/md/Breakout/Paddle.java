package artur.md.Breakout;

import java.awt.Color;
import java.awt.Graphics2D;

class Paddle {
    public static final int Y_POS = Canvas.HEIGHT - 20;
    public static final int WIDTH = 50;
    private static final int HEIGHT = 10;
    private static final Color color = Color.BLACK;


    private int xPos;
    private int lives;
    private int score;

    Paddle(int startX) {
        xPos = startX;
        score = 0;
        lives = 5;
    }

    public int getX() {
        return xPos;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Checks if ball was hit by paddle
     */
    public boolean hitBall(Ball ball) {
        if(ball.getY() + Ball.SIZE > Y_POS) {
            if( (ball.getX()  <= xPos + WIDTH) && (ball.getX() >= xPos - Ball.SIZE) && (ball.getDY() > 0) ){
                return true;
            }
        }
        return false;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void incScore(int n) {
        score += n;
    }

    public void decScore(int n) {
        score -= n;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(xPos, Y_POS, WIDTH, HEIGHT);
    }


}
