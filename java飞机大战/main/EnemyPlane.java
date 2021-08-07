package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;


public class EnemyPlane {
	static final int ENEMY_SIZE = 80;//�л��߳�
	private Image enemyPic[];//�л�ͼƬ����
	private int enemy_x, enemy_y;//�л�����
	private int enemy_y0;//�л���ʼy����
	private final int Speed = 2;//�л��ƶ��ٶ�
	boolean stayed = true;//�л������ʶ
	private Break b;//��ըͼƬ����
	private int id=0;//��ըͼƬID
	
	public EnemyPlane(int y)
	{
		//��ʼ���л�����
		enemy_x = (int) (Math.random()*2000);
		enemy_y0 = enemy_y = y;
		enemyPic = new Image[5];
		for(int i = 1; i <= enemyPic.length; i++) {
			enemyPic[i-1] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/bullet0" + i + ".png"));
		}
	}
	
	//�滭�л�
	public void drawEnemy(Graphics g,Canvas c, int i)
	{
		if(stayed)
			g.drawImage(enemyPic[i], enemy_x, enemy_y, ENEMY_SIZE, ENEMY_SIZE, c);
		
		else if(id == 0&&!stayed) {
			b = new Break(enemy_x, enemy_y);
			b.enemy_break(g, c, id);
			id++;
		}
		//��ǰ�ɻ��Ѿ�stayed==true�����ǻ������ִ����һ������һ�α���û�����ͼƬ����
		if(b != null && id != 0)
			if(id == 5){
				b.enemy_break(g, c, id);
				id = 0;
			} else {
				b.enemy_break(g, c, id);
				id++;
			}
		
	}
	
	//�л��ƶ�
	public void enemyMove() {
		if(enemy_y > GamePanel.MAP_HEIGHT || stayed == false)
		{
			if(GamePanel.time >= 2500) {//50�����л����ڳ���
				enemy_x = 0;
				enemy_y = GamePanel.MAP_HEIGHT+MyPlane.PLANE_SIZE;
			}	
			else {
				enemy_x = (int) (Math.random()*2000);
				enemy_y = enemy_y0;
				stayed = true;//�л�����Ϊ����״̬
			}
		}else
			enemy_y += Speed;			
	}
	
	//��ȡ�л�����
    Point getX_Y() {
		return new Point(enemy_x, enemy_y);		
	}
	
}
