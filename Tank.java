package tankegram;

import java.awt.event.KeyEvent;

public class Tank {
    private int x;
    private int y;
    private int direct;//坦克方向 0 上 1 右 2 下 3 左
    private int speed=5;//坦克速度

    public int getDirect(){
        return direct;
    }
    public void setDirect(int direct){
        this.direct=direct;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    //上右下左移动方法
    public void moveUp(){
        y-=speed;
    }
    public void moveRight(){
        x+=speed;
    }
    public void movedDown(){
        y+=speed;
    }
    public void moveLeft(){
        x-=speed;
    }

    public Tank(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
