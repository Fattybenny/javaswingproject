package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//�����ɻ���ը
public class Break {
	private Image plane_b[];//�ɻ���ըͼƬ����
	private int x, y;//����
	public Break(int x, int y) {
		//ʵ���ɻ���ըͼƬ����
		plane_b = new Image[6];
		for(int i = 0; i < plane_b.length; i++) {
			plane_b[i] = Toolkit.getDefaultToolkit().getImage(getClass()
					.getResource("/images/bomb_enemy_" + i + ".png"));
		}
		//��ʼ������
		this.x = x;
		this.y = y;
	}
	
	//�л���ը����
	public void enemy_break(Graphics g, Canvas c, int i) {		
		g.drawImage(plane_b[i], x, y, EnemyPlane.ENEMY_SIZE, EnemyPlane.ENEMY_SIZE, c);
	}
	
	//��ҷɻ���ը����
	public void plane_break(Graphics g, Canvas c, int i) {
	
			g.drawImage(plane_b[i], x, y, MyPlane.PLANE_SIZE, MyPlane.PLANE_SIZE, c);
	}
	//boss�ɻ���ը
	void boss_break(Graphics g, Canvas c, int i) {
		if(i < 30)//����ըͼ��ѭ����ʾ����
			g.drawImage(plane_b[i/5], x, y, BossPlane.BOSS_WIDTH, BossPlane.BOSS_HEIGHT, c);
	}
	
	
}
