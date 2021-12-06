package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.Aienemy;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

// Oneal là enemy có tốc độ di chuyển thay đổi và đuổi theo bomber, giết được cộng thêm điểm.

public class Oneal  extends Enemy{
    public static int step = 2;
    public boolean _isdead = false;
    public static int direct;

    public Oneal(int x, int y, Image img) {
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
        if(_isdead == false ) {
            int xb = (x + 2) / Sprite.SCALED_SIZE ;
            int yb = (y + 2) / Sprite.SCALED_SIZE ;
            int bombermanX =  BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE  * Sprite.SCALED_SIZE;
            int bombermanY = BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE  * Sprite.SCALED_SIZE;
            int l = new Aienemy(bombermanX,bombermanY, yb * 31 + xb).getLocations();
            int i = l / 31 * Sprite.SCALED_SIZE;
            int j = l % 31  * Sprite.SCALED_SIZE;

            if( i != 0 && j != 0){
                if (x < j && Map.ar[(y+26)/Sprite.SCALED_SIZE][(x+28)/Sprite.SCALED_SIZE] == ' '
                        &&Map.ar[(y+step)/Sprite.SCALED_SIZE][(x+28)/Sprite.SCALED_SIZE] == ' ') {
                    img = Sprite.oneal_right1.getFxImage();
                    img = Sprite.movingSprite(Sprite.oneal_right2, Sprite.oneal_right3, x, 60).getFxImage( );
                    x += step;
                    if (y % 32 > 16) y = (y / 32 + 1) * 32;
                } else if (x > j && Map.ar[(y+26)/Sprite.SCALED_SIZE][(x-step)/Sprite.SCALED_SIZE] == ' '
                        && Map.ar[(y+step)/Sprite.SCALED_SIZE][(x-step)/Sprite.SCALED_SIZE]==' ') {
                    img = Sprite.oneal_left1.getFxImage();
                    img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left3, x, 60).getFxImage( );
                    x -= step;
                    if (y % 32 > 16) y = (y / 32 + 1) * 32;
                } else if (y > i ) {
                    img = Sprite.oneal_right1.getFxImage();
                    img = Sprite.movingSprite(Sprite.oneal_left2, Sprite.oneal_right3, y, 60).getFxImage( );
                    y -= step;
                    if (x % 32 > 16) x = (x / 32 + 1) * 32;
                } else if (y < i) {
                    img = Sprite.oneal_left1.getFxImage();
                    img = Sprite.movingSprite(Sprite.oneal_right2, Sprite.oneal_left3, y, 60).getFxImage( );
                    y += step;
                    if (x % 32 > 16) x = (x / 32 + 1) * 32;
                }
            }
            else {
                switch (direct) {
                    case 0:
                        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3,
                                x, 60).getFxImage();
                        if (Map.ar[(y + 26) / Sprite.SCALED_SIZE][(x + 28) / Sprite.SCALED_SIZE] == ' '
                                && Map.ar[(y + 2) / Sprite.SCALED_SIZE][(x + 28) / Sprite.SCALED_SIZE] == ' ') {
                            x += step;
                            if (y % 32 > 16) y = (y / 32 + 1) * 32;
                        }
                        break;
                    case 1:
                        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                                x, 60).getFxImage();
                        if (Map.ar[(y + 26) / Sprite.SCALED_SIZE][(x - 2) / Sprite.SCALED_SIZE] == ' '
                                && Map.ar[(y + 2) / Sprite.SCALED_SIZE][(x - 2) / Sprite.SCALED_SIZE] == ' ') {
                            x -= step;
                            if (y % 32 > 16) y = (y / 32 + 1) * 32;
                        }
                        break;
                    case 2:
                        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_right2, Sprite.oneal_left3,
                                y, 60).getFxImage();
                        if (Map.ar[(y - 2) / Sprite.SCALED_SIZE][(x + 26) / Sprite.SCALED_SIZE] == ' '
                                && Map.ar[(y) / Sprite.SCALED_SIZE][(x) / Sprite.SCALED_SIZE] == ' ') {
                            y -= step;
                            if (x % 32 > 16) x = (x / 32 + 1) * 32;
                        }
                        break;
                    case 3:
                        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_left2, Sprite.oneal_right3,
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
        BombermanGame.score += 300;
        BombermanGame.numberEnemy--;
        Timer timer0 = new Timer();
        timer0.schedule(new TimerTask() {
            @Override
            public void run() {
                img = Sprite.oneal_dead.getFxImage();
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
        this._isdead = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask( ) {
            @Override
            public void run() {
                img = null;
            }
        },800);

    }

}
