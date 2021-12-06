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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Ballom là enemy có tốc độ không đổi, di chuyển ngẫu nhiên, giết được thêm điểm.

public class Ballom  extends Enemy{
    public int step = 1;
    public boolean _isdead = false;
    private int count = 0;
    private int loop = 0;
    private int temp = 15;

    public Ballom(int x, int y, Image img) {
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
        if (this._isdead == false ){
            count ++ ;
            if (count < temp) {
                img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3,
                        x, 60).getFxImage( );
                if (Map.ar[(y + 26) / 32][(x + 28) / 32] == ' ' && Map.ar[(y) / 32][(x + 28) / 32] == ' ') {
                    x += step;
                    int temp = y % 32;
                    if (temp >= 20) y = ((y / 32) + 1) * 32;
                    else if (temp <= 10) y = (y / 32) * 32;
                }
            }
            else {
                img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                        x, 60).getFxImage( );
                if (Map.ar[(y + 26) / 32][(x - step) / 32] == ' ') {
                    x -= step;
                    int temp = y % 32;
                    if (temp >= 20) y = ((y / 32) + 1) * 32;
                    else if (temp <= 10) y = (y / 32) * 32;
                }
                loop ++;
                if (loop == temp) {
                    count = 0;
                    loop = 0;
                    Random random = new Random();
                    temp = random.nextInt(200) + 10;
                }
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
        BombermanGame.score += 120;
        BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);
        BombermanGame.numberEnemy --;
        this._isdead = true;
        Timer timer0 = new Timer();
        timer0.schedule(new TimerTask() {
            @Override
            public void run() {
                img = Sprite.balloom_dead.getFxImage();
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
