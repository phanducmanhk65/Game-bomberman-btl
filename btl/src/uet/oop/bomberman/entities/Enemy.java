package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class Enemy extends Entity{
    public Enemy(int x , int y, Image img){
        super(x,y,img);
    }
    @Override
    public void update() {
    }

    /*
    Các enemy đã cài đặt bao gồm:
    + Ballom: tốc độ không đổi, di chuyển ngẫu nhiên, giết được thêm điểm.
    + Bear: tốc độ không đổi, di chuyển ngẫu nhiên, thoắt ẩn thoắt hiện bắn được thêm điểm.
    + Doll: tốc độ nhanh, di chuyển tngẫu nhiên, giết được thêm điểm và tăng tốc độ bomber.
    + Ghost: tốc độ thay đổi, di chuyển ngẫu nhiên, giết được thêm điểm, bắn được thêm lần đặt bom hoặc không.
    + Kondoria: tốc độ thay đổi, di chuyển tránh bomber, giết được thêm điểm từ 150->249 và thêm máu.
    + Minvo: tốc độ thay đổi, chỉ di chuyển theo chiều dọc, bắn được thêm điểm và thêm lần đặt bom.
    + Oneal: tốc độ di chuyển thay đổi và đuổi theo bomber, giết được cộng thêm điểm.
    + Orange: tốc độ không đổi, di chuyển bất kì, bắn được cộng số điểm bất kì từ 100->199 và thêm thời gian.
    + Red: tốc độ không đổi, di chuyển tránh bomber, bắn được thêm điểm, bắn có thể giúp tăng tốc hoặc không.
     */

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }
    public abstract void kill();

}
