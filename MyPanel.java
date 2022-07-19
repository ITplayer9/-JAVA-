package tankegram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

//import java.lang.runtime.SwitchBootstraps;
//为了监听键盘事件，实现KeyListener
//为了让Panel，不停的重绘子弹，需要将MyPanel 实现Runnable,当做一个线程来使用
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我的tank
    Hero hero;
    //定义敌人坦克，放入到Vector
    Vector<EnemyTank> enemyTanks=new Vector<>();
    //定义一个Vector，用于存放炸弹
    //说明，当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs=new Vector<>();
    int enemyTankSize = 3;

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1;
    Image image2;
    Image image3;

    public MyPanel(){
        hero=new Hero(100,100);//初始化自己的坦克
        hero.setSpeed(2);
        for(int i=0; i<enemyTankSize; i++){
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 1);
            //设置方向
            enemyTank.setDirect(3);
            //给该enemyTank，加入一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            //加入enemyTank的Vector成员
            enemyTank.shots.add(shot);
            //启动shot对象
            new Thread(shot).start();;

            //加入
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        image1 =Toolkit.getDefaultToolkit().getImage("/bomb01.png");
        image2 =Toolkit.getDefaultToolkit().getImage("/bomb02.png");
        image3 =Toolkit.getDefaultToolkit().getImage("/bomb03.jpg");
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);
        Random r=new Random();
        int x=r.nextInt(1000);
        int y=r.nextInt(750);
        //画出自己坦克-封装到方法中
        drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),0);
        //画出hero射击的子弹
            if (hero.shot != null && hero.shot.isLive) {
                g.fill3DRect(hero.shot.x, hero.shot.y, 3, 3, false);
                repaint();
            }
        //如果bombs集合中有对象，就画出
        for(int i=0;i<bombs.size();i++){
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if(bomb.life>6){
                g.drawImage(image1,bomb.x,bomb.y,1060,1060,this);
            }else if (bomb.life>3){
                g.drawImage(image2,bomb.x,bomb.y,1060,1060,this);
            } else{
                g.drawImage(image3,bomb.x,bomb.y,1060,1060,this);
            }
            //让这个渣打生命值减少
            bomb.lifeDown();
            //如果bomb life 为0 就从bombs的集合中删除
            if(bomb.life==0){
                bombs.remove(bomb);
            }

        }

        //画出敌人的坦克
        for(int i=0;i<enemyTanks.size();i++){
            //取出坦克
            EnemyTank enemyTank=enemyTanks.get(i);
            //判断当前的坦克是否存活
            if (enemyTank.isLive){//当敌人坦克是存活的，才画出该坦克
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),1);
                //画出enemyTank所有子弹
                for(int j=0;j<enemyTank.shots.size();j++){
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive){//isLive==true
                        g.fill3DRect(shot.x,shot.y, 3, 3, false);
                    }else{
                        //从vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }
    //编写画坦克方法
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        switch (type){
            case 0://我们的坦克
                g.setColor(Color.cyan);break;
            case 1:
                g.setColor(Color.yellow);break;
        }
        //不同的方向来绘制对应坦克
        //direct表示方向(0:向上 1 向右 2 向下 3 向左）
        switch(direct){
            case 0://向上
                g.fill3DRect(x,y,10,60,false);//坦克左边的轮子
                g.fill3DRect(x+30,y,10,60,false);//右边的轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间矩形，绘图
                g.fillOval(x+10,y+20,20,20);//中间的盖子
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1://向右
                g.fill3DRect(x,y,60,10,false);//坦克左边的轮子
                g.fill3DRect(x,y+30,60,10,false);//右边的轮子
                g.fill3DRect(x+10,y+10,40,20,false);//中间矩形，绘图
                g.fillOval(x+20,y+10,20,20);//中间的盖子
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://向左
                g.fill3DRect(x,y,60,10,false);//坦克左边的轮子
                g.fill3DRect(x,y+30,60,10,false);//右边的轮子
                g.fill3DRect(x+10,y+10,40,20,false);//中间矩形，绘图
                g.fillOval(x+20,y+10,20,20);//中间的盖子
                g.drawLine(x+30,y+20,x,y+20);
                break;
            case 3://向下
                g.fill3DRect(x,y,10,60,false);//坦克左边的轮子
                g.fill3DRect(x+30,y,10,60,false);//右边的轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间矩形，绘图
                g.fillOval(x+10,y+20,20,20);//中间的盖子
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
//            default:
//                System.out.println("暂时没有处理");
        }
    }
    //编写方法，判断我方法子弹是否集中敌人的坦克
    //什么时候判断 我方的子弹是否击中敌人的坦克 ？ run方法
    public  void hitTank(Shot s,EnemyTank enemyTank){
        //判断s,击中坦克
        switch (enemyTank.getDirect()){
            case 0://坦克向上
            case 3://向左
                if (s.x>enemyTank.getX()&&s.x<enemyTank.getX()+40
                && s.y>enemyTank.getY() &&s.y<enemyTank.getY()+60){
                    s.isLive=false;
                    enemyTank.isLive=false;
                    //创建一个Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);

                }
                break;
            case 1://向右
            case 2://向左
                if (s.x>enemyTank.getX()&&s.x<enemyTank.getX()+60
                &&s.y>enemyTank.getY()&&s.y>enemyTank.getY()+40){
                    s.isLive=false;enemyTank.isLive=false;
                }
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //处理wdsa 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_W){//按下W键
            //改变坦克的方向,向上走
            hero.setDirect(0);
            hero.moveUp();
        }else if(e.getKeyCode()==KeyEvent.VK_D){//D键向右走
            hero.setDirect(1);
            hero.moveRight();
        }else if (e.getKeyCode()==KeyEvent.VK_A){//A键向左走
            hero.setDirect(2);
            hero.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_S){//S键向下走
            hero.setDirect(3);
            hero.movedDown();
        }
        //如果用户按下的是J，就发射
        if(e.getKeyCode()==KeyEvent.VK_J){
            hero.shotEnemyTank();
        }
        //让面板重绘
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void run() {//每隔100毫秒，重绘区域,刷新绘图工具，子弹就移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中了敌人坦克
            if (hero.shot!=null&&hero.shot.isLive){//当前我的子弹还存活
                //遍历敌人所有的坦克
                for(int i=0;i<enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.shot,enemyTank);
                }

            }
            this.repaint();
        }
    }
}
