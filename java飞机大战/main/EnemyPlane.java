package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;


public class EnemyPlane {
	static final int ENEMY_SIZE = 80;//敌机边长
	private Image enemyPic[];//敌机图片数组
	private int enemy_x, enemy_y;//敌机坐标
	private int enemy_y0;//敌机初始y坐标
	private final int Speed = 2;//敌机移动速度
	boolean stayed = true;//敌机生存标识
	private Break b;//爆炸图片对象
	private int id=0;//爆炸图片ID
	
	public EnemyPlane(int y)
	{
		//初始化敌机坐标
		enemy_x = (int) (Math.random()*2000);
		enemy_y0 = enemy_y = y;
		enemyPic = new Image[5];
		for(int i = 1; i <= enemyPic.length; i++) {
			enemyPic[i-1] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/bullet0" + i + ".png"));
		}
	}
	
	//绘画敌机
	public void drawEnemy(Graphics g,Canvas c, int i)
	{
		if(stayed)
			g.drawImage(enemyPic[i], enemy_x, enemy_y, ENEMY_SIZE, ENEMY_SIZE, c);
		
		else if(id == 0&&!stayed) {
			b = new Break(enemy_x, enemy_y);
			b.enemy_break(g, c, id);
			id++;
		}
		//当前飞机已经stayed==true，但是还会继续执行这一步将上一次爆照没画完的图片画完
		if(b != null && id != 0)
			if(id == 5){
				b.enemy_break(g, c, id);
				id = 0;
			} else {
				b.enemy_break(g, c, id);
				id++;
			}
		
	}
	
	//敌机移动
	public void enemyMove() {
		if(enemy_y > GamePanel.MAP_HEIGHT || stayed == false)
		{
			if(GamePanel.time >= 2500) {//50秒过后敌机不在出现
				enemy_x = 0;
				enemy_y = GamePanel.MAP_HEIGHT+MyPlane.PLANE_SIZE;
			}	
			else {
				enemy_x = (int) (Math.random()*2000);
				enemy_y = enemy_y0;
				stayed = true;//敌机设置为生存状态
			}
		}else
			enemy_y += Speed;			
	}
	
	//获取敌机坐标
    Point getX_Y() {
		return new Point(enemy_x, enemy_y);		
	}
	
}
