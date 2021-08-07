package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class BossPlane {
	
	static final int BOSS_WIDTH = 250;
	static final int BOSS_HEIGHT = 180;
	private int bossPlane_x, bossPlane_y;//boss�ɻ�����
	private Image ima;//boss�ɻ�ͼƬ
	boolean stayed;//�ɻ����ڱ�ʶ
	private Break b;//��ըͼƬ����
	int id;//��ըͼƬID
	private final int step = 1;//boss�ƶ��ٶ�
	private int position = -1, point;//�ɻ�λ�ñ��
	
	public BossPlane()
	{
		//�����ʼ��
		bossPlane_x = 175;
		bossPlane_y = -500;
		ima = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/boss.png"));
		stayed = true;
	}
	
	public void drawBoss(Graphics g, Canvas c) {
		if(stayed)
			g.drawImage(ima, bossPlane_x, bossPlane_y, BOSS_WIDTH, BOSS_HEIGHT, c);
		else if(id == 0) {
			b = new Break(bossPlane_x, bossPlane_y);
			b.boss_break(g, c, id);
			id++;
		} else {
			b.boss_break(g, c, id);
			id++;
		}
	}
	
	public void bossMove() {
		if(bossPlane_y < 80)
			bossPlane_y += step;
		else
			switch (position) {//�÷ɻ���������
			case 0://��������
				bossPlane_y += step;
				bossPlane_x += step;
				if(bossPlane_x == GamePanel.MAP_WIDTH-BOSS_WIDTH)
					position++;
				break;
			case 1://��������
				if((point/700)%2 == 0) {
					bossPlane_y += 3;
					bossPlane_x -= step;
				} else {
					bossPlane_y += 2;
					bossPlane_x -= 3;
				}
				if(bossPlane_y >= GamePanel.MAP_HEIGHT-BOSS_HEIGHT)
					position++;
				break;
			case 2://��������
				if((point/700)%2 == 0) {
					bossPlane_y -= step;
					bossPlane_x -= step;
				} else {
					bossPlane_y -= 3;
					bossPlane_x -= step;
				}
				if(bossPlane_x <= 0)
					position++;
				break;
			case 3://��������
				bossPlane_x += step;
				if(bossPlane_x == 175)
					position = -1;
				break;
			default://�����ƶ�
				point++;
				if(point%300 == 0)
					position = 0;
				break;
			}
	}
 public Point getX_Y() {
		
		return new Point(bossPlane_x, bossPlane_y);
		
	}
	
}
