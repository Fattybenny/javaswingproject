package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameFrame extends JFrame{
	
	private JLabel back,label01, label02, label03, label04;
	private Sound sound;
	private GameFrame m;	
	Boolean flag=true;
	public GameFrame() {		
		m=this;
		this.setTitle("飞机大战");
		this.setLayout(null);//清除布局管理器
		this.setSize(1800, 1000);
		this.setLocationRelativeTo(null);			
		this.setVisible(true);		
	    setBackground set=new setBackground();//设置背景动画
	    set.start();		
	    showlabel();//添加主界面标签选项
	    keyadapter();
		
		
	}
		
	private class setBackground extends Thread
	{
	     Image img;	    	    
	      Graphics mg;
	     
		@Override
		public void run() {
			// TODO Auto-generated method stub			
			while(true)
				
			{ 
				mg=m.getContentPane().getGraphics();	
			
			  for(int i=0;i<90;i++)
			  {	
				  if(flag==true)//当进入游戏界面就让主界面动画继续播放，虽然不会造成影响，但可以减少内存占用
				  {				
			      img=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Capture/飞机.mp4_"+i+".png"));			    
			      mg.drawImage(img, 0, 0, null);			     
			    try {
				  Thread.sleep(10);
		       	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			 	e.printStackTrace();
			    }		   			   			 			   		   		  			    
				  }
				  else continue;			
			}			 
			 
			}
		}
		
	}
		
	
	public void showlabel()
	{
				
		 ImageIcon background = new ImageIcon(this.getClass().getResource("/images/mainback.png"));
		 back = new JLabel(background);
		 back.setBounds(0,700,1800, 300);
		 this.getContentPane().add(back);
		 
		 //指示的飞机图
	   	 ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/point.png"));
	     //设置标签
		 label01 = new JLabel("开始游戏");
		 label01.setFont(new Font("acefont-family", Font.BOLD, 50));
		 label01.setForeground(Color.blue);//设置字体背景颜色
		 label01.setBounds(820, 740, 400, 120);//起点宽高
		 
		 label02 = new JLabel("选择飞机");
		 label02.setFont(new Font("acefont-family", Font.BOLD, 50));
		 label02.setBounds(820, 830, 400, 120);
		 
		 label03 = new JLabel(icon);
		 label03.setBounds(600, 740, 250, 120);
		
		 label04 = new JLabel(icon);
		 label04.setBounds(600, 830, 250, 120);
		 label04.setVisible(false);
		
		//添加到contenpane层
		this.add(label01);//文字标签		
		this.getContentPane().add(label02);//文字标签
		this.getContentPane().add(label03);//飞机图案
		this.getContentPane().add(label04);//飞机图案
		this.getContentPane().add(back);
	}
	
	public void keyadapter()
	{
		this.requestFocusInWindow();
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(sound.b[1]) {
					//声音设置
					sound = new Sound("/sounds/ClickSound.wav");	
					sound.start();
				}
                 int key = e.getKeyCode();
				
				//监听向上或向下按
				if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP) {
					label03.setVisible(!label03.isVisible());
					label04.setVisible(!label04.isVisible());
					
					if(label03.isVisible()) {
						label01.setForeground(Color.blue);
						label02.setForeground(Color.black);
					} else {
						label01.setForeground(Color.black);
						label02.setForeground(Color.blue);
					}
				}
				//监听回车按键
				if(key == KeyEvent.VK_ENTER && label03.isVisible()) {
										
					GamePanel game=new GamePanel(m);
					flag=false;						
					add(game);//直接调用就行了 不要写this 反而会错
					game.setSize(1800, 1000);					
					//移除组件
					m.getContentPane().remove(label01);
					m.remove(label02);
					m.remove(label03);
					m.remove(label04);
					m.remove(back);
					Bullet.before_time = System.currentTimeMillis();
				}
				//监听回车按键
				if(key == KeyEvent.VK_ENTER && label04.isVisible()) {
					//跳转到设置对话框
					new Dialog(m, 0);
				}
					
			}

		});
	}
	
	
	
	
	
	
	
	
	
	

}
