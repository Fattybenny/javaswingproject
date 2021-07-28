import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageUI extends JPanel {

	String []bt= {"原图","方形马赛克","灰度","二值化","轮廓检测","油画绘制","拖动原图马赛克","拖动彩色马赛克","截图","放大","缩小","撤回上一步","清空图片"};
	 ImageListener listener=new ImageListener();
	 JFrame nj=new JFrame();
	public void initUI()
	{
		JFrame jf=new JFrame();
		jf.setSize(1500,780);
		jf.setTitle("图片处理");
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		
		
		//按钮功能区面板
		JPanel jp1=new JPanel();
		jp1.setBackground(new Color(0xafeeee));
		Dimension dm=new Dimension(200,780);
		jp1.setPreferredSize(dm);
		Dimension btdm=new Dimension(190,40);		
		for(int i=0;i<bt.length;i++)
		{
			JButton jb =new JButton(bt[i]);
			jb.setPreferredSize(btdm);
			jb.setBackground(Color.white);
			Font font= new Font("黑体",Font.BOLD,20);
			jb.setFont(font);
			jb.setForeground(new Color(0x000080));
			jb.setBackground(new Color(0xf0fff0));
			jb.addActionListener(listener);
			jp1.add(jb);
		}
		
	    //绘图区jframe默认边框布局		
		//Dimension dm2=new Dimension(2000,2000);		 
		//this.setPreferredSize(dm2);
		this.setBackground(new Color(0xffffe0));
		jf.add(jp1,BorderLayout.EAST); 
		jf.add(this,BorderLayout.CENTER);

		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.addKeyListener(listener);
		jf.setVisible(true);

		//从jp2 获得画笔
		Graphics g=jf.getRootPane().getGraphics();
		listener.setgraphics(g);
		listener.setjpanel(this);
		
		//JFrame nj=new JFrame();	
		nj.setTitle("提示");
		nj.setSize(300, 150);
		//nj.setDefaultCloseOperation(3);
		nj.setLocationRelativeTo(jf);
		nj.setResizable(false);
		JLabel txt =new JLabel("是否确认截图",JLabel.CENTER);
		Font fnt= new Font("黑体",Font.BOLD,30);
		txt.setFont(fnt);
		nj.setLayout(new FlowLayout());
		nj.add(txt);
		Font fnt2=new Font("黑体", Font.CENTER_BASELINE, 15);
		JButton bt =new JButton("确认");
		bt.addActionListener(listener);
		bt.setPreferredSize(new Dimension(100, 30));
		bt.setFont(fnt2);
		nj.add(bt);
		JButton bt2=new JButton("取消");
		bt2.addActionListener(listener);
		bt2.setPreferredSize(new Dimension(100, 30));
		bt2.setFont(fnt2);
		nj.add(bt2);
	    nj.addWindowListener(listener);
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		 super.paint(g);
		   //Stack<BufferedImage> buff=listener.buffstack;
		 System.out.println("fresh"+!listener.freshbuff.isEmpty());
		    if( !listener.freshbuff.isEmpty())
		     { BufferedImage top=(BufferedImage)listener.freshbuff.peek();
			  g.drawImage(top,0,0,top.getWidth(),top.getHeight() ,null);		
			  System.out.println("调用paint");
	         }

	}
	
	public void clear(Graphics g)
	{
		super.paint(g);
	}
	
	public static void main(String[] args) {
		new ImageUI().initUI();
		
	}	
}
