package tankegram;

import javax.swing.*;

public class HspTankGame01 extends JFrame {
    //定义一个全局函数
    MyPanel mp=null;
    public static void main(String[] args) {
        HspTankGame01 hspTankGame01 = new HspTankGame01();
    }
    public HspTankGame01() {
        mp = new MyPanel();
        //将mp放入到Thread，并启动
        Thread thread =new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1000, 750);//监听mp的键盘事件
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
