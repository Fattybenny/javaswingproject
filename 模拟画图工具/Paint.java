import java.awt.*;

import javax.swing.*;
public class Paint extends JPanel{

	//建立一个很大的数组，用来存放图形元素，
	Shape[] shape=new Shape[10000];
	public static void main(String[] args) {
		 new Paint();
	}
	  JFrame jf = new JFrame();	  
	  
	//实例化一个ButtonListener对象，实现了多种接口
	Listener lis= new Listener();
	
	public Paint() {
		  //实现一个窗体
		  jf.setTitle("画图板");
		  jf.setLocation(600,250);//窗口左上角起点
		  jf.setSize(700, 600);
		  jf.setLayout(new FlowLayout());	
		  
		   //1.setPreferredSize需要在使用布局管理器的时候使用，布局管理器会获取空间的preferredsize，因而可以生效。
		  //例如borderlayout在north中放入一个panel，panel的高度可以通过这样实现：panel.setPreferredSize(new Dimension(0, 100));
		  //这样就设置了一个高度为100的panel，宽度随窗口变化。
		  //2.setSize,setLocation,setBounds方法需要在不使用布局管理器的时候使用，也就是setLayout(null)的时候可以使用这三个方法控制布局。
		  //区分好这两个不同点之后，

		  
          //声明两个数组，包含各种指令
      		String[] command = { "开始", "清除", "直线", "曲线", "多边形", "矩形","圆形" };
      		Color[] color = { Color.BLACK, Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
   
    
    		/*在下面的两个循环中将各种按钮添加进入动作监听器中，其中addActionListener参数为btl，
   		 btl是一个Buttonlistener的对象
   		 */
   		for (int i = 0; i < command.length; i++) {
   			JButton jb = new JButton(command[i]);
   			jb.addActionListener(lis);
   			jf.add(jb);
   		}
   		for (int i = color.length - 1; i >= 0; i--) {
   			JButton jb = new JButton();
   			jb.setBackground(color[i]);//设置背景颜色
   			Dimension dm = new Dimension(20, 20);//设置大小
   			jb.setPreferredSize(dm);
   			jb.addActionListener(lis);
   			jf.add(jb);
   		}
   		
   	    //将JPanel对象添加进入jf窗体对象中，让他生效
   	 //jpanel画板的大小和颜色
		  this.setPreferredSize(new Dimension(1600, 1400));
          this.setBackground(Color.LIGHT_GRAY);
          
          
          //这里注意流程  我们实在rootpane上进行流式布局 现将各个按钮添加完，在添加这个画板 P
			jf.add(this);
			jf.setDefaultCloseOperation(3);
			jf.setVisible(true);	//设置可见
   			//Graphics gf = this.getGraphics();	//获取画笔，我们的this代表当前类的对象，正好是一个JPanel的对象
   			this.addMouseListener(lis);		//添加鼠标监听器，用于画图
   			this.addMouseMotionListener(lis);	//添加鼠标模式监听器，用于绘画曲线 			
   			lis.set_p(this);//给监听类当中的Paint 对象赋初值,方便通过p调用paint1 方法
	}
	
	//这里是重写jpanel中的paint   
	//????这里的重绘画笔与上面的gf应该不相同
	
	//一下知识点经验：当我们重写父类中的方法后，如果想在其他类中通过对象调用父类的方法时
	//应该再重新创建一个方法，名字与被重写的父类方法不同，这个方法不是重写，只是通过这个方法调用父类的方法
	
	public void paint(Graphics g)//前面有三角图案 代表是重写 (重写在当前的子类中)  
	{
		super.paint(g);//这是原先的父类方法 本来只要绘制组件的功能
                       //所以现在我们要加一个绘制图形的功能如下 
		for(int i=0;i<=shape.length;i++)
		{
			if(shape[i]!=null)
			{  
			     g.setColor(shape[i].getcolor());
				shape[i].Repaint(g);
			}
			else
				break;
		}
		System.out.println("paint");
	}
	
	public void paint1(Graphics g)
	{
		super.paint(g);
	}
	
}

