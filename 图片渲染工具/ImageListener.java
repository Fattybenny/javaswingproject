import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class ImageListener extends ListenerUtils {
	
	int[][] imgarr;
	String btn="";
	String path="E:\\java\\eclipse\\eclipse javaee\\workspace\\Yu java\\66.jpg";
	private Graphics g;
	ImageUtils imageff;
    ImageUI jp;
	public Stack<BufferedImage> buffstack =new Stack<BufferedImage>();
	public Stack<BufferedImage> freshbuff=new Stack<BufferedImage>();
	int index;
    int p_x,p_y;
    int r_x,r_y;
    int d_x,d_y;
	
	
	public void setgraphics(Graphics g)
	{
		this.g=g;
	}
	
	public void setjpanel(ImageUI jp)
	{
		this.jp=jp;		
	}
	//初始化监听器
	ImageListener()
	{
		//ImageUtils imageff=new ImageUtil()ֵ,构造器中的新建的对象变量 方法完后就释放
		imageff=new ImageUtils();
		imgarr=imageff.getImagePixel(path);
		System.out.println("初始化成功"+imgarr.length);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		super.windowClosed(e);
     jp.paint(g); 
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("当前"+btn);
		//System.out.println("调用actionperformed");
	    btn=e.getActionCommand();
	    if(btn.equals("原图"))
	    {	
	    	jp.clear(g);    		    
	     BufferedImage img=	imageff.paint1(g,imgarr);
	     buffstack.push(img);
	     freshbuff.push(img);
	     System.out.println("栈顶"+buffstack.peek().getWidth());
	     System.out.println("栈内元素数"+buffstack.size());	    
	    }
	    else if(btn.equals("圆形马赛克"))
	    {
	    	jp.clear(g);
	       imageff.paint11(g,freshbuff,buffstack);
	    	
	    }
	    else if(btn.equals("方形马赛克")) 
	    {  
	    	jp.clear(g); 
	    	imageff.paint2(g, freshbuff,buffstack);		         
	    
	    }
	    else if(btn.equals("灰度")) 
	    {	
	    	jp.clear(g);   
	    imageff.paint3(g, freshbuff,buffstack);
		    	     
	    }
	    
	    else if(btn.equals("二值化")) 
	    {    
	    	jp.clear(g);
	    	imageff.paint4(g, freshbuff,buffstack);		     	    	 
	    	
	    }
	    else if(btn.equals("轮廓检测")) 
	    {   
	    	jp.clear(g);
	    	imageff.paint5(g, freshbuff,buffstack);
		    
	    	
	    }
	    else if(btn.equals("油画绘制")) 
	    {  
	    	jp.clear(g);
	    	imageff.paint6(g, freshbuff,buffstack);		     		     
	    }
	    else if(btn.equals("放大"))
	    {   
	    	jp.clear(g);
	        imageff.piant7(g, freshbuff,buffstack);	    
	        jp.paint(g);   
	    }
	    else if(btn.equals("缩小"))
	    {  
	    	jp.clear(g);
	    	imageff.paint8(g,freshbuff,buffstack );	    	
	    	jp.paint(g);
	    }
	    
	    else if(btn.equals("清空图片"))
	    {
	    jp.clear(g);
	    imageff.withdraw(g,jp,buffstack,freshbuff);
	    }
	    else if (btn.equals("撤回上一步"))
	    {
	      jp.clear(g);
	    freshbuff.push(imageff.backward(buffstack,g));	   	   
	    }	    
	    
	    else if(btn.equals("确认"))
	    {
	    	jp.clear(g);
	    	imageff.paint12(g, freshbuff, buffstack,p_x,p_y,r_x,r_y);
	    	System.out.println("截图完成");
	    	jp.nj.dispose();
	    }
	    else if(btn.equals("取消"))
	    {
	    	jp.nj.dispose();
	    	jp.paint(g);
	    }
	    
	    System.out.println("当前"+btn);
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
		p_x=e.getX();
		p_y=e.getY();
		d_x=e.getX();
		d_y=e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
		r_x=e.getX();
		r_y=e.getY();
		if(btn.equals("截图"))
	    {
			Graphics2D g2d=(Graphics2D)g;
			g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_BUTT,
					                      BasicStroke.JOIN_ROUND, 3.5f, new float[] { 10, 5 }, 0f));			
			g2d.setColor(Color.GREEN);
			g2d.drawRect(Math.min(p_x, r_x), Math.min(p_y, r_y),Math.abs(r_x-p_x) ,Math.abs(r_y-p_y));
			jp.nj.setVisible(true);			
	    }
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub		
		super.mouseDragged(e);	
		int x=e.getX();
		int y=e.getY();
		if(btn.equals("拖动原图马赛克"))
	    {
		   if(Math.abs(x-d_x)>10||Math.abs(y-d_y)>10)		
	           { imageff.paint9(g,freshbuff,buffstack,x,y);		             
	             d_x=x;d_y=y;
	           }	       
	    }
		
		if(btn.equals("拖动彩色马赛克")) 
		{
			if(Math.abs(x-d_x)>10||Math.abs(y-d_y)>10)		
	           { imageff.paint10(g,freshbuff,buffstack,x,y);		             
	             d_x=x;d_y=y;
	           }	       	
			
		}
			
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e);
	}
	

}
