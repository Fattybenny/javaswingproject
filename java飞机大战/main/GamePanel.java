package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//游戏开始界面
public class GamePanel extends JPanel{
	
	private Image bg1, bg2;//两张游戏背景图片
	private int bg1_y;//bg1图片的y坐标
	private int bg2_y;//bg2图片的y坐标
	
	private final int SCREEN_WIDTH = 1800;//屏幕宽
	private final int SCREEN_HEIGHT = 1000;//屏幕高
	static final int MAP_WIDTH = 1600;//地图面板长
	static final int MAP_HEIGHT = 1000;//地图面板宽
	
	private GameFrame gframe;
	private MapPanel jp;//地图面板	
	static long sum;//分数
	static int time;//计时
	static int live;//玩家飞机生命
	static int bosslive;//boss飞机生命
	static long before_time2 = System.currentTimeMillis();//过去绘制boss子弹时间
	private boolean isRunning = false;//线程是否循环的标志
	private JButton jb1, jb2, jb3;//按钮
	private JLabel jl;//标签
	private Image x;
	
	//双缓冲	
	private BufferedImage iBuffer;	
	private Graphics gBuffer;
	
	public GamePanel(GameFrame gframe)
	{
		bg1_y = 0;//bg1图片的y坐标
		bg2_y = -SCREEN_HEIGHT;//bg2图片的y坐标
		
		this.gframe=gframe;
		setLayout(null);	    		
		setPanel();		
		live = 100;
		bosslive= 2000;	
	}	
	public void setPanel()
	{
		sum = 0;//设置分数显示为零		
		//设置按钮
		jb1 = new JButton("开始(P)");
		jb1.setBounds(50, 60, 100, 50);
		add(jb1);
		
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop_start();			
			}

		});
		
		jb2 = new JButton("重新开始");
		jb2.setBounds(50, 140, 100, 50);
		add(jb2);
		
		jb2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(jp);
				jp = new MapPanel();
				jp.setBounds(200, 0, 1600, 1000);
				add(jp);
				//让游戏暂停
				isRunning = false;
				jb1.setText("开始");//改变按钮文字
				Bullet.before_time = System.currentTimeMillis();
				
				jb1.setEnabled(true);//设置暂停初始按钮为可按
				sum = 0;//设置分数显示0
				
				live = 100;//设置生命初值100
				bosslive = 2000;//设置boss生命初值2000
				
				time = 0;//赋予时间
			}
		});
		
		jb3 = new JButton("返回主菜单");
		jb3.setBounds(50, 220, 100, 50);
		add(jb3);
		jb3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = Main.f1.getLocation();
				Main.f1.dispose();
				isRunning = false;
				Main.main(null);
				Main.f1.setLocation(p);
			}
		});
		
		//设置标签，用于存放分数
				jl = new JLabel("0", JLabel.CENTER);
				jl.setBounds(40, 300, 120, 50);
				jl.setFont(new Font("acefont-family", Font.BOLD, 30));
				jl.setForeground(Color.red);
				this.add(jl);
				
				
		    ImageIcon imcon=new ImageIcon(this.getClass().getResource("/images/组件背景.png"));									
			JLabel jback=new JLabel(imcon);
			jback.setBounds(0, 0, 200, 1000);
			this.add(jback);
				
			jp = new MapPanel();		
			jp.setBounds(200, 0, 1600, 1000);
			this.add(jp);//添加到当前jpanel中
				
	}
	
	
	//描述地图面板
	
		private class MapPanel extends Canvas implements Runnable {
	
		private ArrayList<Bullet> mybulletarray;//玩家飞机子弹数组
		private ArrayList<Bullet> enemybulletarray;//敌机子弹数组
		private ArrayList<Bullet[]> bossbulletarray;//boss子弹数组
		
		private MyPlane myplane;
		private BossPlane bossplane;//boss飞机对象
		private EnemyPlane[] enemyarray;//敌机数组
		private Bullet b;
		
		private Collision c;//碰撞判断				
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
              while(isRunning) {          	             	  
            	  draw();//刷新界面              	 
			  try {
				Thread.sleep(10);//延时0.01s
			   } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }	
			   time += 1;
           	 
		  }
		}
		
		
		 MapPanel()
		{			
			bg1  = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/mapback.png"));
			bg2=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/mapback2.png"));
			//创建玩家飞机对象
		    myplane = new MyPlane();
		    
			myplane.adapter(this);//传入canvas其实是个画布添加监听器
			
			mybulletarray=new ArrayList<Bullet>();
			
			//创建boss飞机对象
			bossplane = new BossPlane();
			bossbulletarray=new ArrayList<Bullet[]>();
			
			//创建并初始化敌机数组
			enemyarray= new EnemyPlane[10];
			enemybulletarray=new ArrayList<Bullet>();
			for(int i=0;i<enemyarray.length;i++)
			{
				enemyarray[i]=new EnemyPlane((-i-1)*EnemyPlane.ENEMY_SIZE-Bullet.BULLET_HEIGHT);
			}
			
			c=new Collision();//创建碰撞对象
			
			pauselistener();//给当前游戏界面添加监听器，按空格暂停
			
	
		}
		
		
		
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			//super.paint(g);			
			jl.setText("" + sum);//显示分数		
			if(iBuffer == null)
			{
				iBuffer=new BufferedImage(1600, 1000, BufferedImage.TYPE_INT_RGB);				
				gBuffer = iBuffer.getGraphics();
			}					
			gBuffer.drawImage(bg1, 0, bg1_y, 1600, 1000, this);
			gBuffer.drawImage(bg2, 0, bg2_y, 1600, 1000, this);
			
		
			myplane.drawMyPlane(gBuffer,this);//绘制玩家飞机
			//boss子弹循环绘制
			for(int i = 0; i < bossbulletarray.size()&&bossbulletarray.size()>0; i++)
				for(int j = 0; j < 5; j++)
					if(bossbulletarray.get(i)[j]!=null)
						bossbulletarray.get(i)[j].drawBullet(gBuffer,this, 2);	
			
			if(time >= 2500) 				
			  {
				//25秒后出现boss： 2500*0.01
				if(bossplane.getX_Y().getY() >= 80 && System.currentTimeMillis()-before_time2 >= 2500 && bossplane.stayed) 
				{
					//boss发射子弹时间限制
					//绘制boss子弹
					Bullet[] bt = new Bullet[5];
					for(int i = 0; i < 5; i++) {
						bt[i] = new Bullet((int)(bossplane.getX_Y().getX())+BossPlane.BOSS_WIDTH/2-5, (int)(bossplane.getX_Y().getY())+BossPlane.BOSS_HEIGHT-30);
						bt[i].drawBullet_3(gBuffer, this);
					}
					bossbulletarray.add(bt);
					before_time2 = System.currentTimeMillis();
				}
				if(!bossplane.stayed && bossplane.id == 5) {
					jb1.setEnabled(false);//设置暂停始按钮为不可
					isRunning = false;//设置线程不循
					new Dialog(gframe, 2);//挑战成功对话
				}
				bossplane.drawBoss(gBuffer, this);//绘制boss飞机			
		      }
			
			if(myplane.stayed) {	
				//创建玩家飞机子弹对象
				b = new Bullet((int)(myplane.getX_Y().getX())+MyPlane.PLANE_SIZE/2-Bullet.BULLET_WIDTH/2, (int)(myplane.getX_Y().getY())-Bullet.BULLET_HEIGHT);
				b.drawBullet_1(mybulletarray, gBuffer, this);//子弹绘制
				for(int i = 0; i<mybulletarray.size() && mybulletarray.size()>1; i++) {
					mybulletarray.get(i).drawBullet(gBuffer, this, 1);//子弹绘制
				}
			} else if(!myplane.stayed&&myplane.id == 5){
				jb1.setEnabled(false);//设置暂停始按钮为不可
				isRunning = false;//设置线程不循
				new Dialog(gframe, 1);//打开对话
			}
			
			//绘制敌机、敌机子弹
			for(int i = 0; i < enemyarray.length; i++)
			{
				enemyarray[i].drawEnemy(gBuffer, this, i%5);
				if(enemyarray[i].stayed && enemyarray[i].getX_Y().getY()>0) {					
					int t = (int)(enemyarray[i].getX_Y().getY())+EnemyPlane.ENEMY_SIZE;
					if(t < MAP_HEIGHT) {
						//创建敌机子弹对象
						b = new Bullet((int)(enemyarray[i].getX_Y().getX())+EnemyPlane.ENEMY_SIZE/2-Bullet.BULLET_WIDTH/2, t);
						b.drawBullet_2(enemybulletarray, gBuffer, this, i);//子弹绘制
					}
					
				}
				for(int j = 0; j<enemybulletarray.size(); j++) {
					enemybulletarray.get(j).drawBullet(gBuffer, this, 2);//子弹绘制
				}
			}
			
			
			if(bosslive >= 0 && bossplane.stayed) {
				//绘制boss
				x = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/xue_" + ((2000-bosslive)/100+1) + ".png"));
				gBuffer.drawImage(x, (int)(bossplane.getX_Y().getX()), (int)(bossplane.getX_Y().getY()-10), 250, 10, this);
			}
			
			if(live >= 0) {
				//绘制玩家飞机
				x = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/xue_" + ((100-live)/5+1) + ".png"));
				gBuffer.drawImage(x, 7, 7, 200, 20, this);
			}
					
			Graphics canvasg=this.getGraphics();
			canvasg.drawImage(iBuffer, 0, 0, null);//把缓冲图像载入屏
		
		}
		private void draw() {
			// TODO Auto-generated method stub
			
			mapMove();
			
			myplane.planeMove();//控制玩家飞机移动
			
			if(time >= 2500) {
				c.boss_plane(bossplane, myplane);
				bossplane.bossMove();//boss飞机移动
			}
			
			//玩家飞机子弹移动	
			for(int i = 0; i < mybulletarray.size(); i++)
				mybulletarray.get(i).bulletMove(mybulletarray, i);
			
			//敌机子弹移动
			for(int i = 0; i < enemybulletarray.size(); i++)
			{
				c.bullet_plane(enemybulletarray.get(i), myplane);//玩家飞机和敌机子弹碰
				enemybulletarray.get(i).bulletMove1(enemybulletarray, i);
				
			}
			
			//敌机移动
			for(int i = 0; i < enemyarray.length; i++)
			{
				enemyarray[i].enemyMove();
				c.plane_enemy(myplane, enemyarray[i]);//敌机与玩家飞机碰撞检测
			}
			
			//玩家飞机子弹和敌机与boss碰撞判断
			for(int i = 0; i < mybulletarray.size(); i++)
				for(int j = 0; j < enemyarray.length; j++) {
					c.bullet_enemy(mybulletarray.get(i), enemyarray[j]);
					c.bullet_boss(mybulletarray.get(i), bossplane);
				}
			
			//boss子弹移动和子弹与玩家飞机碰撞判断
			for(int i = 0; i < bossbulletarray.size(); i++)
				for(int j = 0; j < 5; j++) {
					if(bossbulletarray.get(i)[j]!=null) {
						c.bullet_plane(bossbulletarray.get(i)[j], myplane);
						bossbulletarray.get(i)[j].bulletMove2(bossbulletarray, i, j);
					}
				}
				
			repaint();//重画
		}
			
	@Override
	public void update(Graphics g) {
			paint(g);
			}
		
		
          private void mapMove() {			
			//背景地图移动
			bg1_y += 1;
			bg2_y += 1;
			if(bg1_y == SCREEN_HEIGHT)
				bg1_y = -SCREEN_HEIGHT;
			if(bg2_y == SCREEN_HEIGHT)
				bg2_y = -SCREEN_HEIGHT;		
		}
		
		
		private void pauselistener()
		{
			this.addKeyListener(new KeyAdapter() {				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					super.keyPressed(e);
					if(e.getKeyCode() == KeyEvent.VK_SPACE)
					stop_start();
					
				}
			});
		}
	
	}
	
	//控制线程开始与暂停
	public void stop_start()
	{
		if(isRunning == true)
		{
			isRunning = false;//设置线程不循环
			jb1.setText("开始");//改变按钮文字			
			
		} else {
			isRunning = true;//设置线程循环
			Thread d = new Thread(jp);//创建线程
			d.start();
			jb1.setText("暂停(P)");//改变按钮文字
			jp.requestFocusInWindow();
		}
	}
	
	
}
