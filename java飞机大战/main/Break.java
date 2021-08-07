package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//描述飞机爆炸
public class Break {
	private Image plane_b[];//飞机爆炸图片数组
	private int x, y;//坐标
	public Break(int x, int y) {
		//实例飞机爆炸图片数组
		plane_b = new Image[6];
		for(int i = 0; i < plane_b.length; i++) {
			plane_b[i] = Toolkit.getDefaultToolkit().getImage(getClass()
					.getResource("/images/bomb_enemy_" + i + ".png"));
		}
		//初始化坐标
		this.x = x;
		this.y = y;
	}
	
	//敌机爆炸绘制
	public void enemy_break(Graphics g, Canvas c, int i) {		
		g.drawImage(plane_b[i], x, y, EnemyPlane.ENEMY_SIZE, EnemyPlane.ENEMY_SIZE, c);
	}
	
	//玩家飞机爆炸绘制
	public void plane_break(Graphics g, Canvas c, int i) {
	
			g.drawImage(plane_b[i], x, y, MyPlane.PLANE_SIZE, MyPlane.PLANE_SIZE, c);
	}
	//boss飞机爆炸
	void boss_break(Graphics g, Canvas c, int i) {
		if(i < 30)//将爆炸图案循环显示三次
			g.drawImage(plane_b[i/5], x, y, BossPlane.BOSS_WIDTH, BossPlane.BOSS_HEIGHT, c);
	}
	
	
}
