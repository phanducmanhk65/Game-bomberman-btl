package uet.oop.bomberman.entities;

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

public class Bomber extends Entity {
    public static int num = 0;
    public static int step = 2;
    public boolean _alive = true;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(){
        move();
    }
    public void move() {
        if (num == 1 && Map.ar[(y + 26) / Sprite.SCALED_SIZE][(x + 28) / Sprite.SCALED_SIZE] == ' '
                && Map.ar[(y + 2) / Sprite.SCALED_SIZE][(x + 2) / Sprite.SCALED_SIZE] == ' ') {
            img = Sprite.player_right.getFxImage();
            img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, x, 15).getFxImage();
            x += step;
            try {
                InputStream inp = new FileInputStream("res/media/soundmove.wav");
                AudioStream au = new AudioStream(inp);
                AudioPlayer.player.start(au);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (y % 32 > 16) y = (BombermanGame.bomberman.getYTile() + 1) * 32;
            BombermanGame.score += 1;
            BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);
        }
        if (num == 2 && x >= 0 && Map.ar[(y + 26) / Sprite.SCALED_SIZE][(x - 2) / Sprite.SCALED_SIZE] == ' '
                && Map.ar[(y + 2) / Sprite.SCALED_SIZE][(x - 2) / Sprite.SCALED_SIZE] == ' ') {
            img = Sprite.player_left.getFxImage();
            img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, x, 15).getFxImage();
            x -= step;
            try {
                InputStream inp = new FileInputStream("res/media/soundmove.wav");
                AudioStream au = new AudioStream(inp);
                AudioPlayer.player.start(au);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (y % 32 > 16) y = (BombermanGame.bomberman.getYTile() + 1) * 32;
            BombermanGame.score += 1;
            BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);
        }
        if (num == 3 && y >= 0 && Map.ar[(y) / Sprite.SCALED_SIZE][(x) / Sprite.SCALED_SIZE] == ' '
                && Map.ar[(y - 2) / Sprite.SCALED_SIZE][(x + 26) / Sprite.SCALED_SIZE] == ' ') {
            img = Sprite.player_up.getFxImage();
            img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, y, 15).getFxImage();
            y -= step;
            try {
                InputStream inp = new FileInputStream("res/media/soundmove.wav");
                AudioStream au = new AudioStream(inp);
                AudioPlayer.player.start(au);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (x % 32 > 16) x = (BombermanGame.bomberman.getXTile() + 1) * 32;
            BombermanGame.score += 1;
            BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);

        }
        if (num == 4 && y <= 12 * Sprite.SCALED_SIZE && Map.ar[(y + 30) / Sprite.SCALED_SIZE][((x + 26) / Sprite.SCALED_SIZE)] == ' '
                && Map.ar[(y + 30) / Sprite.SCALED_SIZE][((x) / Sprite.SCALED_SIZE)] == ' ') {
            img = Sprite.player_down.getFxImage();
            img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, y, 15).getFxImage();
            y += step;
            try {
                InputStream inp = new FileInputStream("res/media/soundmove.wav");
                AudioStream au = new AudioStream(inp);
                AudioPlayer.player.start(au);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (x % 32 > 16) x = (BombermanGame.bomberman.getXTile() + 1) * 32;
            BombermanGame.score += 1;
            BombermanGame.scoreText.setText("SCORE : " + BombermanGame.score);
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void kill(){
        //_alive = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                img = Sprite.player_dead1.getFxImage();
                Timer timer1 = new Timer();
                timer1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        img = Sprite.player_dead2.getFxImage();
                        Timer timer2 = new Timer();
                        timer2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                img = Sprite.player_dead3.getFxImage();
                                Timer timer3 = new Timer();
                                timer3.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        img = Sprite.grass.getFxImage();
                                    }
                                },100);
                            }
                        },100);
                    }
                },100);
            }
        },100);
        /*try {
            InputStream inp =new FileInputStream("res/media/move.wav");
            AudioStream au = new AudioStream(inp);
            AudioPlayer.player.start(au);
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }

    @Override
    public boolean collide(Entity e){
        if (e instanceof Flame){
            if (x == e.getX()) _alive = false;
        }

        if (e instanceof Grass){
            return true;
        }
        if (e instanceof Wall){
            return true;
        }
        if (e instanceof Brick){
            return true;
        }
        return false;
    }
    public void setAgain(){
        x = 1 * Sprite.SCALED_SIZE;
        y = 1 * Sprite.SCALED_SIZE;
        img = Sprite.player_right.getFxImage();
        step = 2;
    }
}
