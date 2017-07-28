package artur.md.Breakout;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Canvas extends JPanel implements ActionListener, MouseMotionListener, MouseListener, KeyListener {
    public static final int HEIGHT = 700;
    private static final int WIDTH = 700;

    private final int startDirectionX = -4;
    private final int startDirectionY = -2;

    private final BufferedImage image;
    private final Graphics2D bufferedGraphics;
    private static final Font scoreFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    private final Timer timer;

    private final Paddle player;
    private final Ball ball;
    private final List<Brick> bricks;

    private int delay = 20;

    private Canvas() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        bufferedGraphics = image.createGraphics();
        timer = new Timer(delay, this);
        player = new Paddle((WIDTH/2) - (Paddle.WIDTH/2));
        ball = new Ball(player.getX() + Paddle.WIDTH/2,
                Paddle.Y_POS - Ball.SIZE,
                0, 0);
        bricks = new ArrayList<>();
        int rows;
        int columns;
        rows = HEIGHT / Brick.HEIGHT;
        columns = WIDTH / Brick.WIDTH;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows / 2; j++) {
                bricks.add(new Brick(j * Brick.WIDTH, i * Brick.HEIGHT));
            }
        }
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        requestFocus();
    }

    private void resetBall() {
        if(player.getLives() > 0) {
            player.setLives(player.getLives() - 1);
            player.decScore(100);
            ball.setX(player.getX() + Paddle.WIDTH/2 - Ball.SIZE /2);
            ball.setY(Paddle.Y_POS - Ball.SIZE);
            ball.setDX(startDirectionX);
            ball.setDY(startDirectionY);
            timer.stop();
        }
    }


    private void checkCollisions() {
        if(player.hitBall(ball)) {
            ball.reverseYDir();
            return;
        }

        // Collision with walls
        //left or right
        if( ((ball.getX() <= 0) && (ball.getDX() < 0 )) ||((ball.getX() + Ball.SIZE >= WIDTH) && (ball.getDX() > 0)) ) {
            ball.reverseXDir();
        }
        //top
        if( (ball.getY() <= 0) && (ball.getDY() < 0 )){
            ball.reverseYDir();
        }
        //down
        if( (ball.getY() + Ball.SIZE >= HEIGHT) && (ball.getDY() > 0) ){
            resetBall();
        }


        // Collision with bricks
        for(Iterator<Brick> iter = bricks.iterator(); iter.hasNext();) {
            Brick brick = iter.next();
            if( brick.hitBy(ball) ) {
                if( (ball.getDX() < 0) && (ball.getDY() < 0) ) {
                    if( (ball.getX() < brick.getX() + Brick.WIDTH + ball.getDX()) ||
                            (-1.0 * ball.getX()/ball.getDX() < -1.0 * ball.getY()/ball.getDY()) ){
                        ball.reverseYDir();
                    } else {
                        ball.reverseXDir();
                    }
                }
                if( (ball.getDX() > 0) && (ball.getDY() > 0) ) {
                    if( (ball.getX() > brick.getX() + ball.getDX()) ||
                            (1.0 * ball.getX()/ball.getDX() > 1.0 * ball.getY()/ball.getDY()) ){
                        ball.reverseYDir();
                    } else {
                        ball.reverseXDir();
                    }
                }

                if( (ball.getDX() > 0) && (ball.getDY() < 0) ) {
                    if( (ball.getX() > brick.getX() + ball.getDX()) ||
                            (1.0 * ball.getX()/ball.getDX() + -1.0 * ball.getY()/ball.getDY() > 1) ){
                        ball.reverseYDir();
                    } else {
                        ball.reverseXDir();
                    }
                }
                if( (ball.getDX() < 0) && (ball.getDY() > 0) ) {
                    if( (ball.getX() < brick.getX() + Brick.WIDTH + ball.getDX()) ||
                            (-1.0 * ball.getX()/ball.getDX() + 1.0 * ball.getY()/ball.getDY() < 1) ){
                        ball.reverseYDir();
                    } else {
                        ball.reverseXDir();
                    }
                }

                brick.deactivate();
                player.incScore(brick.getScore());
                iter.remove();
            }
        }

    }

    private boolean gameOver() {
        return player.getLives() < 1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bufferedGraphics.clearRect(0, 0, WIDTH, HEIGHT);
        bufferedGraphics.setColor(Color.WHITE);
        bufferedGraphics.fillRect(0, 0, WIDTH, HEIGHT);
        ball.draw(bufferedGraphics);
        player.draw(bufferedGraphics);
        for(Brick brick: bricks) {
            brick.draw(bufferedGraphics);
        }
        bufferedGraphics.setFont(scoreFont);
        bufferedGraphics.setColor(Color.BLACK);
        bufferedGraphics.drawString("Lives: " + player.getLives() + " Score: " + player.getScore(),10,10);
        if(bricks.isEmpty()) {
            bufferedGraphics.drawString("You've won! Score: " + player.getScore(), (WIDTH/2)-100, (HEIGHT/2-20));
            timer.stop();
        }
        if(gameOver()) {
            bufferedGraphics.drawString("Game Over! Score: " + player.getScore(), (WIDTH/2)-100, (HEIGHT/2-20));
            timer.stop();
        }
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        checkCollisions();
        ball.move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if( !timer.isRunning()) {
            ball.setDX(startDirectionX);
            ball.setDY(startDirectionY);
        }
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Canvas canvas = new Canvas();
        frame.add(canvas);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        //speed up
        if(timer.isRunning()) {
            timer.setDelay(1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        //slow down
        if(timer.isRunning()) {
            timer.setDelay(delay);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        player.setX(mouseEvent.getX() - (Paddle.WIDTH / 2));
        if(!timer.isRunning()) {
            ball.setX(mouseEvent.getX() - Ball.SIZE / 2);
        }
        repaint();
    }


}
