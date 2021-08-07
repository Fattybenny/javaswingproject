package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



//描述玩家飞机
public class MyPlane {
	private int myPlane_x = 800, myPlane_y =850;//玩家飞机坐标
	private Image []planePic;//存储飞机图片的数组
	private final int Speed = 7;//飞机速度
	static final int PLANE_SIZE = 100;//玩家飞机边长
	
	private boolean isPress01, isPress02, isPress03, isPress04;//记录按键状况，
	boolean stayed = true;//玩家飞机生存标识
	private Break b;//爆炸图片对象
	int id;//爆炸图片总共6张，id用于标记序号
	static int planeID;//玩家飞机编号
	
	public MyPlane()	
	{
		planePic = new Image[5];
		for(int i=1;i<=5;i++)
		{
			planePic[i-1]=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Plane0" + i + ".png"));
		}
	}
	
	//绘制玩家飞机
	public void drawMyPlane(Graphics g ,Canvas c)
	{
		if(stayed)
	   g.drawImage(planePic[planeID], myPlane_x, myPlane_y, PLANE_SIZE, PLANE_SIZE, null);//绘制玩家飞机
		
		else if(id == 0&&!stayed) {
			b = new Break(myPlane_x, myPlane_y);
			b.plane_break(g, c, id);
			id++;
		    } else {
			       b.plane_break(g, c, id);
			      id++;
		           }		
	}
	
	//玩家飞机移动键盘监听方法
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
	
	 //控制飞机移动方法
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
	
	//获取玩家飞机坐标
    public Point getX_Y() {		
		return new Point(myPlane_x, myPlane_y);	
	}

	
	
}
