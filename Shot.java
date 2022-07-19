package tankegram;

public class Shot implements Runnable {
    int x;//子弹的x坐标
    int y;//y坐标
    int direct = 0;//坐标
    int speed = 5;//速度
    boolean isLive = true;//子弹是否存活

    //构造器
    public Shot(int x, int y, int direct) {
        this.direct = direct;
        this.y = y;
        this.x = x;
    }

    @Override
    public void run() {//射击循环
        while (true) {
            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变下x,y坐标
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2://向左
                    x -= speed;
                    break;
                case 3://向下
                    y += speed;
                    break;
            }
            //当子弹移动到面板边界时，就应该销毁(把启动的子弹的线程销毁）
            //当子弹碰到敌人坦克时，也应该结束线程

            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750&& isLive)) {
                System.out.println("子弹线程退出");
                isLive = false;
                break;
            }
        }

    }
}