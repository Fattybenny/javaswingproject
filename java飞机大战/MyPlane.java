package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



//������ҷɻ�
public class MyPlane {
	private int myPlane_x = 800, myPlane_y =850;//��ҷɻ�����
	private Image []planePic;//�洢�ɻ�ͼƬ������
	private final int Speed = 7;//�ɻ��ٶ�
	static final int PLANE_SIZE = 100;//��ҷɻ��߳�
	
	private boolean isPress01, isPress02, isPress03, isPress04;//��¼����״����
	boolean stayed = true;//��ҷɻ������ʶ
	private Break b;//��ըͼƬ����
	int id;//��ըͼƬ�ܹ�6�ţ�id���ڱ�����
	static int planeID;//��ҷɻ����
	
	public MyPlane()	
	{
		planePic = new Image[5];
		for(int i=1;i<=5;i++)
		{
			planePic[i-1]=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Plane0" + i + ".png"));
		}
	}
	
	//������ҷɻ�
	public void drawMyPlane(Graphics g ,Canvas c)
	{
		if(stayed)
	   g.drawImage(planePic[planeID], myPlane_x, myPlane_y, PLANE_SIZE, PLANE_SIZE, null);//������ҷɻ�
		
		else if(id == 0&&!stayed) {
			b = new Break(myPlane_x, myPlane_y);
			b.plane_break(g, c, id);
			id++;
		    } else {
			       b.plane_break(g, c, id);
			      id++;
		           }		
	}
	
	//��ҷɻ��ƶ����̼�������
	public void adapter(Canvas  c)//Canvas c
	{
		c.addKeyListener(new KeyAdapter() {	
									
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_UP)
					isPress01 = false;

				if(key == KeyEvent.VK_DOWN)
					isPress02 = false;

				if(key == KeyEvent.VK_LEFT)
					isPress03 = false;

				if(key == KeyEvent.VK_RIGHT)
					isPress04 = false;
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_UP)
					isPress01 = true;

				if(key == KeyEvent.VK_DOWN)
					isPress02 = true;

				if(key == KeyEvent.VK_LEFT)
					isPress03 = true;

				if(key == KeyEvent.VK_RIGHT)
					isPress04 = true;
				
			}
			
		});
	}
	
	 //���Ʒɻ��ƶ�����
	public void planeMove() {
		
		if(isPress01)
			if(myPlane_y > 0)
				myPlane_y -= Speed;

		if(isPress02)
			if(myPlane_y < GamePanel.MAP_HEIGHT-MyPlane.PLANE_SIZE)
				myPlane_y += Speed;

		if(isPress03)
			if(myPlane_x > 0)
				myPlane_x -= Speed;

		if(isPress04)
			if(myPlane_x < GamePanel.MAP_WIDTH-MyPlane.PLANE_SIZE)
				myPlane_x += Speed;
	}
	
	//��ȡ��ҷɻ�����
    public Point getX_Y() {		
		return new Point(myPlane_x, myPlane_y);	
	}

	
	
}
