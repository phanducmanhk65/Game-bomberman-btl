package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity{
    public static boolean isBomb = false;
    public static int canPutBomb = 1;
    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public Bomb (int x, int y, Image img ) {
        super(x,y,img);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void exploding() {
        BombermanGame.score += 20;
        BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                img = Sprite.bomb.getFxImage();
                Timer timer1 = new Timer();
                timer1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        img = Sprite.bomb_1.getFxImage();
                        Timer timer2 = new Timer();
                        timer2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                img = Sprite.bomb_2.getFxImage();
                            }
                        },400);
                    }
                },400);
            }
        },1200);

    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

}
