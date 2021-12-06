package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

//Orange là enemy có tốc độ không đổi, di chuyển bất kì, bắn được cộng số điểm bất kì từ 100->199 và thêm thời gian.

public class Orange extends Enemy{
    public static int direct;
    public int step = 2;
    public boolean _isdead = false;

    public Orange(int x, int y, Image img) {
        super(x,y,img);

    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void update() {
        if(_isdead == false) {
            switch (direct) {
                case 0:
                    img = Sprite.movingSprite(Sprite.orange_right1, Sprite.orange_right2, Sprite.orange_right3,
                            x, 60).getFxImage();
                    if (Map.ar[(y + 26) / Sprite.SCALED_SIZE][(x + 28) / Sprite.SCALED_SIZE] == ' '
                            && Map.ar[(y + 2) / Sprite.SCALED_SIZE][(x + 28) / Sprite.SCALED_SIZE] == ' ') {
                        x += step;
                        if (y % 32 > 16) y = (y / 32 + 1) * 32;
                    }
                    break;
                case 1:
                    img = Sprite.movingSprite(Sprite.orange_left1, Sprite.orange_left2, Sprite.orange_left3,
                            x, 60).getFxImage();
                    if (Map.ar[(y + Sprite.SCALED_SIZE) / 32][(x - 2) / Sprite.SCALED_SIZE] == ' '
                            && Map.ar[(y + 2) / Sprite.SCALED_SIZE][(x - 2) / Sprite.SCALED_SIZE] == ' ') {
                        x -= step;
                        if (y % 32 > 16) y = (y / 32 + 1) * 32;
                    }
                    break;
                case 2:
                    img = Sprite.movingSprite(Sprite.orange_left1, Sprite.orange_right2, Sprite.orange_left3,
                            y, 60).getFxImage();
                    if (Map.ar[(y - 2) / Sprite.SCALED_SIZE][(x + 26) / Sprite.SCALED_SIZE] == ' '
                            && Map.ar[(y) / Sprite.SCALED_SIZE][(x) / Sprite.SCALED_SIZE] == ' ') {
                        y -= step;
                        if (x % 32 > 16) x = (x / 32 + 1) * 32;
                    }
                    break;
                case 3:
                    img = Sprite.movingSprite(Sprite.orange_right1, Sprite.orange_left2, Sprite.orange_right3,
                            y, 60).getFxImage();
                    if (Map.ar[(y + 30) / Sprite.SCALED_SIZE][(x + 26) / Sprite.SCALED_SIZE] == ' '
                            && Map.ar[(y + 30) / Sprite.SCALED_SIZE][(x) / Sprite.SCALED_SIZE] == ' ') {
                        y += step;
                        if (x % 32 > 16) x = (x / 32 + 1) * 32;
                    }
                    break;
            }
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void kill(){
        BombermanGame.score += BombermanGame.random.nextInt(100) + 100;
        BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);
        BombermanGame.time += 20;
        BombermanGame.timerr.setText("Time " + BombermanGame.time);
        BombermanGame.numberEnemy --;
        this._isdead = true;
        Timer timer0 = new Timer();
        timer0.schedule(new TimerTask() {
            @Override
            public void run() {
                img = Sprite.orange_dead.getFxImage();
                Timer timer1 = new Timer();
                timer1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        img = Sprite.mob_dead1.getFxImage();
                        Timer timer2 = new Timer();
                        timer2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                img = Sprite.mob_dead2.getFxImage();
                                Timer timer3 = new Timer();
                                timer3.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        img = Sprite.mob_dead3.getFxImage();
                                    }
                                },75);
                            }
                        },75);
                    }
                },75);
            }
        },200);
        try {
            InputStream inp =new FileInputStream("res/media/enemy.wav");
            AudioStream au = new AudioStream(inp);
            AudioPlayer.player.start(au);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask( ) {
            @Override
            public void run() {
                img = null;
            }
        },800);
    }
}
