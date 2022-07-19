package tankegram;

import java.util.Vector;

/*
    敌人的坦克
 */
public class EnemyTank extends Tank{
    //在敌人坦克类，使用Vector 保存多个shot
    Vector<Shot> shots=new Vector<>();
    boolean isLive=true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }
}
