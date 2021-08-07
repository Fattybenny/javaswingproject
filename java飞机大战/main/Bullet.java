package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Bullet {
	static final int BULLET_WIDTH = 15;//子弹长
	static final int BULLET_HEIGHT = 30;//子弹宽
	private int bullet_x, bullet_y;//子弹坐标
	private final int STEP = 5;//玩家飞机子弹速度
	private final int eSTEP =5;//敌机子弹速度
	private Image bullet01, bullet02;//子弹图片
	private final int TIME = 200;//玩家飞机子弹发射间隔时间
	private final int eTIME = 5000;//敌机子弹发射间隔时间
	boolean stayed = true;//子弹存在标识
	static long before_time;//过去绘制玩家飞机子弹时间
	static long[] ebefore_time = new long[10];//过去绘制敌机子弹时间
	
    public Bullet(int x, int y) {
		
		//初始化坐标
		bullet_x = x;
		bullet_y = y;
		
		bullet01 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/bullet_01.png"));//设置子弹图片
		bullet02 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/bullet_02.png"));//设置敌机子弹图片
		
		}
    
    public void drawBullet(Graphics g, Canvas c,int i) {
    	if(i == 1)
    		g.drawImage(bullet01, bullet_x, bullet_y, BULLET_WIDTH,BULLET_HEIGHT, c);//绘制子弹
    	else
    		g.drawImage(bullet02, bullet_x, bullet_y, BULLET_WIDTH, BULLET_WIDTH, c);//绘制子弹

	}
    
   // 玩家飞机子弹绘制方法，包括时间间隔的判断   
    public void drawBullet_1(ArrayList<Bullet> array, Graphics g, Canvas c) {
    	long now_time = System.currentTimeMillis();
    	if(now_time-before_time >= TIME)//判断是否发射子弹
    	{    		
    		array.add(this);//子弹对象加进数组
			before_time = now_time;//将现在时间作为过去时	
    	}
	}
    
   // 敌机子弹绘制方法，包括时间间隔的判断   
    void drawBullet_2(ArrayList<Bullet> array, Graphics g, Canvas c, int i) {
    	long now_time = System.currentTimeMillis();
    	if(now_time-ebefore_time[i] >= eTIME)//判断是否发射子弹
    	{  		
    		array.add(this);//子弹对象加进数组
			ebefore_time[i] = now_time;//将现在时间作为过去时			
    	}
	}
    
    // boss子弹绘制方法
    public void drawBullet_3(Graphics g, Canvas c) {
    	drawBullet(g, c, 2);    	
    }
    
    //控制玩家飞机子弹移动方法
   public void bulletMove(ArrayList<Bullet> array, int i) {
		
		if(bullet_y < -BULLET_HEIGHT || stayed == false)
			array.remove(i);//从数组中移除子弹对象
		else
			bullet_y -= STEP;
	}
   
   //控制敌机飞机子弹移动方法
   void bulletMove1(ArrayList<Bullet> array, int i) {
		
		if(bullet_y > GamePanel.MAP_HEIGHT || stayed == false)
			array.remove(i);//从数组中移除子弹对象
		else
			bullet_y += eSTEP;
	}
   
   //控制boss子弹移动方法
   public void bulletMove2(ArrayList<Bullet[]> arr, int i, int j) {
		if(stayed)
			switch (j) {
			case 0:
				bullet_x -= 2;
				bullet_y += 2;
				break;
			case 1:
				bullet_x -= 1;
				bullet_y += 2;
				break;
			case 2:
				bullet_y += 2;
				break;
			case 3:
				bullet_x += 1;
				bullet_y += 2;
				break;
			case 4:
				bullet_x += 2;
				bullet_y += 2;
				break;
			default:
				break;
			}
		else
			arr.get(i)[j] = null;
	}
   
   
    public Point getX_Y() {
		
		return new Point(bullet_x, bullet_y);
		
	}
   
   
   
    
}
