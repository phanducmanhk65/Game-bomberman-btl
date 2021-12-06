package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Flame extends Entity{

    public static int widen = 0;
    @Override
    public void update() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask( ) {
            @Override
            public void run() {
                img = null;
            }
        },100);
    }
    public Flame(int x,int y, Image img){
        super(x,y,img);
    }

    public static void expandFlame(int c, int a, int b){
        switch (widen) {
            case 0:
                Flame ex = new Flame(a, b, Sprite.bomb_exploded.getFxImage());
                Flame r_ex = new Flame(a + 1, b, Sprite.explosion_horizontal_right_last.getFxImage());
                Flame l_ex = new Flame(a - 1, b, Sprite.explosion_horizontal_left_last.getFxImage());
                Flame u_ex = new Flame(a, b - 1, Sprite.explosion_vertical_top_last.getFxImage());
                Flame d_ex = new Flame(a, b + 1, Sprite.explosion_vertical_down_last.getFxImage());
                BombermanGame.explosivesList.add(ex);
                if (Map.ar[b][a + 1] != '#') BombermanGame.explosivesList.add(r_ex);
                if (Map.ar[b][a - 1] != '#') BombermanGame.explosivesList.add(l_ex);
                if (Map.ar[b - 1][a] != '#') BombermanGame.explosivesList.add(u_ex);
                if (Map.ar[b + 1][a] != '#') BombermanGame.explosivesList.add(d_ex);
                break;

            case 1:
                Flame ex1 = new Flame(a, b, Sprite.bomb_exploded.getFxImage());
                BombermanGame.explosivesList.add(ex1);
                if (Map.ar[b][a + 1] != '#') {
                    if (Map.ar[b][a + 1] == '*' || (Map.ar[b][a + 1] == ' ' && Map.ar[b][a + 2] == '#')) {
                        Flame r_ex1 = new Flame(a + 1, b, Sprite.explosion_horizontal_right_last.getFxImage());
                        BombermanGame.explosivesList.add(r_ex1);
                    } else {
                        Flame r_ex1 = new Flame(a + 1, b, Sprite.explosion_horizontal.getFxImage());
                        Flame rLast_ex = new Flame(a + 2, b, Sprite.explosion_horizontal_right_last.getFxImage());
                        BombermanGame.explosivesList.add(r_ex1);
                        BombermanGame.explosivesList.add(rLast_ex);
                    }
                }
                if (Map.ar[b][a - 1] != '#') {
                    if (Map.ar[b][a - 1] == '*' || (Map.ar[b][a - 1] == ' ' && Map.ar[b][a - 2] == '#')) {
                        Flame l_ex1 = new Flame(a - 1, b, Sprite.explosion_horizontal_left_last.getFxImage());
                        BombermanGame.explosivesList.add(l_ex1);
                    } else {
                        Flame l_ex1 = new Flame(a - 1, b, Sprite.explosion_horizontal.getFxImage());
                        Flame lLast_ex = new Flame(a - 2, b, Sprite.explosion_horizontal_left_last.getFxImage());
                        BombermanGame.explosivesList.add(l_ex1);
                        BombermanGame.explosivesList.add(lLast_ex);
                    }
                }
                if (Map.ar[b - 1][a] != '#') {
                    if (Map.ar[b - 1][a] == '*' || (Map.ar[b - 1][a] == ' ' && Map.ar[b - 2][a] == '#')) {
                        Flame u_ex1 = new Flame(a, b - 1, Sprite.explosion_vertical_top_last.getFxImage());
                        BombermanGame.explosivesList.add(u_ex1);
                    } else {
                        Flame u_ex1 = new Flame(a, b - 1, Sprite.explosion_vertical.getFxImage());
                        Flame uLast_ex = new Flame(a, b - 2, Sprite.explosion_vertical_top_last.getFxImage());
                        BombermanGame.explosivesList.add(u_ex1);
                        BombermanGame.explosivesList.add(uLast_ex);
                    }
                }
                if (Map.ar[b + 1][a] != '#') {
                    if (Map.ar[b + 1][a] == '*' || (Map.ar[b + 1][a] == ' ' && Map.ar[b + 2][a] == '#')) {
                        Flame d_ex1 = new Flame(a, b + 1, Sprite.explosion_vertical_down_last.getFxImage());
                        BombermanGame.explosivesList.add(d_ex1);
                    } else {
                        Flame d_ex1 = new Flame(a, b + 1, Sprite.explosion_vertical.getFxImage());
                        Flame dLast_ex = new Flame(a, b + 2, Sprite.explosion_vertical_down_last.getFxImage());
                        BombermanGame.explosivesList.add(d_ex1);
                        BombermanGame.explosivesList.add(dLast_ex);
                    }
                }
            break;

            case 2:
                Flame ex2 = new Flame(a, b, Sprite.bomb_exploded.getFxImage());
                BombermanGame.explosivesList.add(ex2);
                if (Map.ar[b][a + 1] == '*') {
                    Flame r_ex2 = new Flame(a + 1, b, Sprite.explosion_horizontal_right_last.getFxImage());
                    BombermanGame.explosivesList.add(r_ex2);
                } else if (Map.ar[b][a + 1] == ' ') {
                    if (Map.ar[b][a + 2] == '#') {
                        Flame r_ex2 = new Flame(a + 1, b, Sprite.explosion_horizontal_right_last.getFxImage());
                        BombermanGame.explosivesList.add(r_ex2);
                    } else if (Map.ar[b][a + 2] == '*') {
                        Flame r_ex2 = new Flame(a + 1, b, Sprite.explosion_horizontal.getFxImage());
                        Flame rLast_ex = new Flame(a + 2, b, Sprite.explosion_horizontal_right_last.getFxImage());
                        BombermanGame.explosivesList.add(r_ex2);
                        BombermanGame.explosivesList.add(rLast_ex);
                    } else {
                        if (Map.ar[b][a + 3] == '#') {
                            Flame r_ex2 = new Flame(a + 1, b, Sprite.explosion_horizontal.getFxImage());
                            Flame rLast_ex = new Flame(a + 2, b, Sprite.explosion_horizontal_right_last.getFxImage());
                            BombermanGame.explosivesList.add(r_ex2);
                            BombermanGame.explosivesList.add(rLast_ex);
                        } else {
                            Flame r1_ex = new Flame(a + 1, b, Sprite.explosion_horizontal.getFxImage());
                            Flame r2_ex = new Flame(a + 2, b, Sprite.explosion_horizontal.getFxImage());
                            Flame rLast_ex = new Flame(a + 3, b, Sprite.explosion_horizontal_right_last.getFxImage());
                            BombermanGame.explosivesList.add(r1_ex);
                            BombermanGame.explosivesList.add(r2_ex);
                            BombermanGame.explosivesList.add(rLast_ex);
                        }
                    }
                }

                if (Map.ar[b][a - 1] == '*') {
                    Flame l_ex2 = new Flame(a - 1, b, Sprite.explosion_horizontal_left_last.getFxImage());
                    BombermanGame.explosivesList.add(l_ex2);
                } else if (Map.ar[b][a - 1] == ' ') {
                    if (Map.ar[b][a - 2] == '#') {
                        Flame l_ex2 = new Flame(a - 1, b, Sprite.explosion_horizontal_left_last.getFxImage());
                        BombermanGame.explosivesList.add(l_ex2);
                    } else if (Map.ar[b][a - 2] == '*') {
                        Flame l_ex2 = new Flame(a - 1, b, Sprite.explosion_horizontal.getFxImage());
                        Flame lLast_ex = new Flame(a - 2, b, Sprite.explosion_horizontal_left_last.getFxImage());
                        BombermanGame.explosivesList.add(l_ex2);
                        BombermanGame.explosivesList.add(lLast_ex);
                    } else {
                        if (Map.ar[b][a - 3] == '#') {
                            Flame l_ex2 = new Flame(a - 1, b, Sprite.explosion_horizontal.getFxImage());
                            Flame lLast_ex = new Flame(a - 2, b, Sprite.explosion_horizontal_right_last.getFxImage());
                            BombermanGame.explosivesList.add(l_ex2);
                            BombermanGame.explosivesList.add(lLast_ex);
                        } else {
                            Flame l1_ex = new Flame(a - 1, b, Sprite.explosion_horizontal.getFxImage());
                            Flame l2_ex = new Flame(a - 2, b, Sprite.explosion_horizontal.getFxImage());
                            Flame lLast_ex = new Flame(a - 3, b, Sprite.explosion_horizontal_left_last.getFxImage());
                            BombermanGame.explosivesList.add(l1_ex);
                            BombermanGame.explosivesList.add(l2_ex);
                            BombermanGame.explosivesList.add(lLast_ex);
                        }
                    }
                }

                if (Map.ar[b - 1][a] == '*') {
                    Flame u_ex2 = new Flame(a, b - 1, Sprite.explosion_vertical_top_last.getFxImage());
                    BombermanGame.explosivesList.add(u_ex2);
                } else if (Map.ar[b - 1][a] == ' ') {
                    if (Map.ar[b - 2][a] == '#') {
                        Flame u_ex2 = new Flame(a, b - 2, Sprite.explosion_vertical_top_last.getFxImage());
                        BombermanGame.explosivesList.add(u_ex2);
                    } else if (Map.ar[b - 2][a] == '*') {
                        Flame u_ex2 = new Flame(a, b - 1, Sprite.explosion_vertical.getFxImage());
                        Flame uLast_ex = new Flame(a, b - 2, Sprite.explosion_vertical_top_last.getFxImage());
                        BombermanGame.explosivesList.add(u_ex2);
                        BombermanGame.explosivesList.add(uLast_ex);
                    } else {
                        if (Map.ar[b - 3][a] == '#') {
                            Flame u_ex2 = new Flame(a, b - 1, Sprite.explosion_vertical.getFxImage());
                            Flame uLast_ex = new Flame(a, b - 2, Sprite.explosion_vertical_top_last.getFxImage());
                            BombermanGame.explosivesList.add(u_ex2);
                            BombermanGame.explosivesList.add(uLast_ex);
                        } else {
                            Flame u1_ex = new Flame(a, b - 1, Sprite.explosion_vertical.getFxImage());
                            Flame u2_ex = new Flame(a, b - 2, Sprite.explosion_vertical.getFxImage());
                            Flame uLast_ex = new Flame(a, b - 3, Sprite.explosion_vertical_top_last.getFxImage());
                            BombermanGame.explosivesList.add(u1_ex);
                            BombermanGame.explosivesList.add(u2_ex);
                            BombermanGame.explosivesList.add(uLast_ex);
                        }
                    }
                }

                if (Map.ar[b + 1][a] == '*') {
                    Flame d_ex2 = new Flame(a, b + 1, Sprite.explosion_vertical_down_last.getFxImage());
                    BombermanGame.explosivesList.add(d_ex2);
                } else if (Map.ar[b + 1][a] == ' ') {
                    if (Map.ar[b + 2][a] == '#') {
                        Flame d_ex2 = new Flame(a, b + 2, Sprite.explosion_vertical_down_last.getFxImage());
                        BombermanGame.explosivesList.add(d_ex2);
                    } else if (Map.ar[b + 2][a] == '*') {
                        Flame d_ex2 = new Flame(a, b + 1, Sprite.explosion_vertical.getFxImage());
                        Flame dLast_ex = new Flame(a, b + 2, Sprite.explosion_vertical_down_last.getFxImage());
                        BombermanGame.explosivesList.add(d_ex2);
                        BombermanGame.explosivesList.add(dLast_ex);
                    } else {
                        if (Map.ar[b + 3][a] == '#') {
                            Flame d_ex2 = new Flame(a, b + 1, Sprite.explosion_vertical.getFxImage());
                            Flame dLast_ex = new Flame(a, b + 2, Sprite.explosion_vertical_down_last.getFxImage());
                            BombermanGame.explosivesList.add(d_ex2);
                            BombermanGame.explosivesList.add(dLast_ex);
                        } else {
                            Flame d1_ex = new Flame(a, b + 1, Sprite.explosion_vertical.getFxImage());
                            Flame d2_ex = new Flame(a, b + 2, Sprite.explosion_vertical.getFxImage());
                            Flame dLast_ex = new Flame(a, b + 3, Sprite.explosion_vertical_down_last.getFxImage());
                            BombermanGame.explosivesList.add(d1_ex);
                            BombermanGame.explosivesList.add(d2_ex);
                            BombermanGame.explosivesList.add(dLast_ex);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

}
