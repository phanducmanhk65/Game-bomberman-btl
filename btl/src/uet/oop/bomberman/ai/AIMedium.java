package uet.oop.bomberman.ai;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Enemy;

import java.util.Random;

public class AIMedium {
    public static Bomber _bomber;
    public static Enemy _e;

    public AIMedium(Bomber bomber, Enemy e) {
        _bomber = bomber;
        _e = e;
    }

    public int calculateDirection() {
        // cài đặt thuật toán tìm đường đi
        Random random = new Random();
        if(_bomber == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1) {
            int v = calculateRowDirection();
            if(v != -1)
                return v;
            else
                return calculateColDirection();

        } else {
            int h = calculateColDirection();

            if(h != -1)
                return h;
            else
                return calculateRowDirection();
        }
    }

    protected int calculateColDirection() {
        if(_bomber.getXTile() < _e.getXTile())
            return 0;
        else if(_bomber.getXTile() > _e.getXTile())
            return 1;

        return -1;
    }

    protected int calculateRowDirection() {
        if(_bomber.getYTile() < _e.getYTile())
            return 3;
        else if(_bomber.getYTile() > _e.getYTile())
            return 2;
        return -1;
    }
}
