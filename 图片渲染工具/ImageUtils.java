import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import javax.imageio.ImageIO;

public class ImageUtils {
	
 boolean flag=true;
 String name="";
  
	//读取文件，获得图片像素
	public int[][] getImagePixel(String path) 
	{
		//读取文件的数据
		File file =new File(path);
		//imageIO操作将图片文件的输入输出,
		//读取文件数据
		BufferedImage buffimage=null;
		try {
		   buffimage=ImageIO.read(file);
	       }catch (IOException e) {
		     e.printStackTrace();
		     System.err.println("图像读取失败");
	                              }
	    //获取缓冲图片的大小，初始化保存像素值得二维数组		
		int w=buffimage.getWidth();
		int h=buffimage.getHeight();
	    int [][]arrPixel=new int[w][h];
		for(int i=0;i<w;i++)
		{
			for(int j=0;j<h;j++)
			{
				int pixel=buffimage.getRGB(i, j);
				arrPixel[i][j]=pixel;
			}
		}
		return arrPixel;

	}

	
	public void withdraw(Graphics g,ImageUI jp,Stack<BufferedImage> buff,Stack<BufferedImage> freshbuff)
	{
       jp.clear(g);             
       buff.clear();
       freshbuff.clear();
       flag=true;
	}
	//原图
	/*
	public void paint1(Graphics g,int[][] arrpixel)
	{

		for(int i=0;i<arrpixel.length;i++)
		{
		  for(int j=0;j<arrpixel[i].length;j++)
		  {   
			  ** 如果需要对像素颜色做更改则要将rgb全部独立出来否则可以直接用
			  int pixel=arrpixel[i][j];
			  int red=(pixel>>16&0xFF);
			  int green=(pixel>>8&0xFF);
			  int blue =(pixel>>0&0xFF);
			  Color color=new Color(red,green,blue);
			  **
			  Color color=new Color(arrpixel[i][j]);
			  //以上都在内存中执行下面开始画图，每次画一个像素都要通知GPU——>屏幕将像素颜色刷新，所以会很慢，可以有改进方法
			  g.setColor(color);
			  g.fillRect(i, j+100, 1,1);
		       
		  }
		}
	    
	}*/
//原图改进方法
	public BufferedImage paint1(Graphics g,int[][] arrpixel)
	{	    
		BufferedImage buffimage=new BufferedImage(arrpixel.length, arrpixel[0].length,BufferedImage.TYPE_INT_RGB);
	    Graphics ng=buffimage.getGraphics();
		for(int i=0;i<arrpixel.length;i++)
		{
			for(int j=0;j<arrpixel[0].length;j++)
			{
				int value=arrpixel[i][j];
				 if(i==0&&j==0)
				  { System.out.println("arrpixel:"+value);}
				 ng.setColor(new Color(value));
				 ng.fillRect(i, j, 1, 1);
				//buffimage.setRGB(i, j, value);//一次性将所有像素存储完成
			}
		}
		
		
		  g.drawImage(buffimage, 0, 0, null);
		  //              起点
		  flag=true;		
		  return buffimage;
		  
	}
	
	//马赛克
	/*public void paint2(Graphics g,int[][] arrpixel)
	{

		for(int i=0;i<arrpixel.length;i+=20)
		{
		  for(int j=0;j<arrpixel[i].length;j+=20)
		  {   
			  int pixel=arrpixel[i][j];
			  int red=(pixel>>16&0xFF);
			  int green=(pixel>>8&0xFF);
			  int blue =(pixel>>0&0xFF);
			  Color color=new Color(red,green,blue);
			  g.setColor(color);
			  g.fillRect(i, j+100, 20,20);
		       
		  }
		}	
	}
	*/
	//方形马赛克改进算法
	public void paint2(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
	{	
		if(!freshbuff.isEmpty())
		{
		BufferedImage buff=freshbuff.peek();
		int newwidth=buff.getWidth();
		int newheight=buff.getHeight();
		BufferedImage buffimage=new BufferedImage(newwidth,newheight,BufferedImage.TYPE_INT_RGB);		
		Graphics ng=buffimage.getGraphics();
		for(int i=0;i<newwidth;i+=10)
		{
		  for(int j=0;j<newheight;j+=10)
		  {   
			    int rgb=buff.getRGB(i, j);
			    
			   /* for(int h=i;h<i+10;h++)
			    {
			    	for(int m=j;m<j+10;m++)
			    	{
			    		if(h<newwidth&&m<newheight)
			    		buffimage.setRGB(h, m, pixel);	
			    	}
			    }			    			  			   
			   */
			    ng.setColor(new Color(rgb));
			    ng.fillRect(i, j, 10, 10);
			    
		  }
		}
				
		g.drawImage(buffimage, 0, 0, null);
		 flag=true;	
		 freshbuff.push(buffimage);
		 stackbuff.push(buffimage);
		}
		
	}
//圆形马赛克
	public void paint11(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
	{
		if(!freshbuff.isEmpty())
	  {
		 BufferedImage buff=freshbuff.peek();
		BufferedImage buffimage=new BufferedImage(buff.getWidth(), buff.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics ng=buffimage.getGraphics();
		for(int i=0;i<buff.getWidth();i+=10)
		{
		  for(int j=0;j<buff.getHeight();j+=10)
		  {   
			    int pixel=buff.getRGB(i, j);
			   			        			           			           				        
			            ng.setColor(new Color(pixel));  
			    		ng.fillOval(i, j, 10, 10);
			  }
	   }			    			  			   
			   		
		g.drawImage(buffimage, 0, 0, null);
		
		flag=true;
		freshbuff.push(buffimage);
		stackbuff.push(buffimage);
	  }
}
	//灰度
	/*
		public void paint3(Graphics g,int[][] arrpixel)
		{
			for(int i=0;i<arrpixel.length;i++)
			{
				for(int j=0;j<arrpixel[i].length;j++)
				{
					int value =arrpixel[i][j];
					int red=(value>>16)&0xFF;
				    int green=(value>>8)&0xFF;
				    int blue=(value>>0)&0xFF;
				    int gray =(red+green+blue)/3;
				   // int gray =(int)(red*0.4+green*0.28+blue*0.31); 		
				   //Color color=new Color(red/2,green/2,blue/2);
				    Color color=new Color(gray,gray,gray);
				    g.setColor(color);
				    g.fillRect(i,j+100,1,1);
				}
			}
		}
		*/
//灰度改进方法
		public void paint3(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
			{ 			
			if(!freshbuff.isEmpty())
			{
			BufferedImage buff=freshbuff.peek();
			int newwidth=buff.getWidth();
			int newheight=buff.getHeight();
			BufferedImage buffimage=new BufferedImage(newwidth,newheight,BufferedImage.TYPE_INT_RGB);	
			for(int i=0;i<buff.getWidth();i++)
			{
				for(int j=0;j<buff.getHeight();j++)
				{
					int value =buff.getRGB(i, j);
					int red=(value>>16)&0xFF;
				    int green=(value>>8)&0xFF;
				    int blue=(value>>0)&0xFF;				    				 
				    int gray =(red+green+blue)/3;
				    int newvalue=(gray<<16)+(gray<<8)+(gray<<0);
					buffimage.setRGB(i, j, newvalue);//一次性将所有像素存储完成
				}
			}
			  
			  g.drawImage(buffimage, 0, 0, null);
			  //                     起点
			  flag=true;
				freshbuff.push(buffimage);
				stackbuff.push(buffimage);			
			}
			  
		}
			
		
//二值化手绘
/*public void paint4(Graphics g,int[][] arrpixel)
	{
	for(int i=0;i<arrpixel.length;i++)
	{
		for(int j=0;j<arrpixel[i].length;j++)
		{
			int value =arrpixel[i][j];
			int red=(value>>16)&0xFF;
		    int green=(value>>8)&0xFF;
		    int blue=(value>>0)&0xFF;
		    int gray=(int)(red*0.4+green*0.5+blue*0.6);
		    //以gray<100为分界线，大于这个数的全部是白色
		    if(gray<150)
		    {
		    	g.setColor(Color.black);
		    }
		    else
		    {
		    	g.setColor(Color.white);
		    }
		    g.fillRect(i, j+100,1, 1);
		}
	}
}*/
//二值化改进方法
public void paint4(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
{
	
	if(!freshbuff.isEmpty())
	{
	BufferedImage buff=freshbuff.peek();
	int newwidth=buff.getWidth();
	int newheight=buff.getHeight();
	BufferedImage buffimage=new BufferedImage(newwidth,newheight,BufferedImage.TYPE_INT_RGB);	
	for(int i=0;i<newwidth;i++)
	{
		for(int j=0;j<newheight;j++)
		{
			int value =buff.getRGB(i, j);
			int red=(value>>16)&0xFF;
		    int green=(value>>8)&0xFF;
		    int blue=(value>>0)&0xFF;
		    int gray=(int)(red*0.4+green*0.5+blue*0.6);
		   // int newvalue=(gray<<16)+(gray<<8)+(gray<<0);
		    if(gray<150)
		    {
		    	buffimage.setRGB(i, j, Color.black.getRGB());
		    }
		    else
		    {
		    	buffimage.setRGB(i, j, Color.white.getRGB());
		    }	    			
		}
	}
	
	   g.drawImage(buffimage, 0, 0, null);
	  //                     起点
	    flag=true;
		freshbuff.push(buffimage);
		stackbuff.push(buffimage);		
	}
	  
	 
}
	
//轮廓检测
//相邻之间的像素点进行比较
/*public void paint5(Graphics g ,int[][] arrpixel)
{
	for(int i=0;i<arrpixel.length-2;i++)
	{
		for(int j=0;j<arrpixel[i].length-2;j++)
		{
			int value =arrpixel[i][j];
			int red=(value>>16)&0xFF;
		    int green=(value>>8)&0xFF;
		    int blue=(value>>0)&0xFF;
		    
		    int valuen=arrpixel[i+2][j+2];
		    int redn=(valuen>>16)&0xFF;
		    int greenn=(valuen>>8)&0xFF;
		    int bluen=(valuen>>0)&0xFF;
          //int gray =(red+green+blue)/3;
          int gray =(int)(red*0.41+green*0.28+blue*0.31);
          int grayn =(int)(redn*0.41+greenn*0.28+bluen*0.31);
          
          if(Math.abs(gray-grayn)>15){
              g.setColor(Color.PINK);
          }else{
              g.setColor(Color.white);
          }
		    g.fillRect(i, j+100, 1, 1);
		    
		}
	}
}*/
//轮廓检测改进算法
public void paint5(Graphics g ,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
{
	
	if(!freshbuff.isEmpty())
	{
	BufferedImage buff=freshbuff.peek();
	int newwidth=buff.getWidth();
	int newheight=buff.getHeight();
	BufferedImage buffimage=new BufferedImage(newwidth,newheight,BufferedImage.TYPE_INT_RGB);	
	for(int i=0;i<newwidth-2;i++)
	{
		for(int j=0;j<newheight-2;j++)
		{
			int value =buff.getRGB(i, j);
			int red=(value>>16)&0xFF;
		    int green=(value>>8)&0xFF;
		    int blue=(value>>0)&0xFF;
		    
		    int valuen=buff.getRGB(i+2, j+2);
		    int redn=(valuen>>16)&0xFF;
		    int greenn=(valuen>>8)&0xFF;
		    int bluen=(valuen>>0)&0xFF;
          //int gray =(red+green+blue)/3;
          int gray =(int)(red*0.41+green*0.28+blue*0.31);
          int grayn =(int)(redn*0.41+greenn*0.28+bluen*0.31);
          
          if(Math.abs(gray-grayn)>15){
              buffimage.setRGB(i, j, Color.pink.getRGB());
          }else{
        	  buffimage.setRGB(i, j, Color.white.getRGB());
          }
         
		    
		}
	}
	
	g.drawImage(buffimage, 0, 0, null);
	flag=true;
	freshbuff.push(buffimage);
	stackbuff.push(buffimage);		
	}
	
}
//绘制油画
/*public void paint6(Graphics g ,int[][] arrpixel)
{
	Random random = new Random();
	for(int i=0;i<arrpixel.length;i+=3)
	{
		for(int j=0;j<arrpixel[i].length;j+=3)
		{
			Color color=new Color(arrpixel[i][j]);
			g.setColor(color);
		    int size = random.nextInt(5)+5; 
		    g.fillOval(i,j+100,size,size);
		}
	}
}*/
//油画改进算法
public void paint6(Graphics g ,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
{	
	if(!freshbuff.isEmpty())
	{
	BufferedImage buff=freshbuff.peek();
	int newwidth=buff.getWidth();
	int newheight=buff.getHeight();
	BufferedImage buffimage=new BufferedImage(newwidth,newheight,BufferedImage.TYPE_INT_RGB);	
	Random random = new Random();
	int size=random.nextInt(5); 
	for(int i=0;i<newwidth;i+=3)
	{
	  for(int j=0;j<newheight;j+=3)
	  {   
		    int pixel=buff.getRGB(i, j);
		    int h=i,m=j;
		    for(int k=0;k<3;k++)
		    {  			    
		      for(int p=0;p<3;p++)
		       {		    	   
		    	  if(h<newwidth&&m<newheight)
		    	  buffimage.setRGB(h,m++,pixel);			    	
		       }
		        h++;
		        m=j+size;
		    }
		   
	  }
	}
	
	g.drawImage(buffimage, 0, 0, null);
	flag=true;
	freshbuff.push(buffimage);
	stackbuff.push(buffimage);		
	}
}

//截图
public void paint12(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff,int px,int py,int rx,int ry)
{
	if(!freshbuff.isEmpty())
	{
		BufferedImage buff=freshbuff.peek();
		int newwidth=Math.abs(px-rx);
		int newheight=Math.abs(py-ry);
		System.out.println("newwid"+newwidth+"newhei"+newheight);

		BufferedImage  buffimage=new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_RGB);
		int minx=Math.min(rx, px);
		int maxx=Math.max(rx, px);
		int miny=Math.min(ry, py);
		int maxy=Math.max(ry, py);
		System.out.println("minx"+minx+"maxx"+maxx+"miny"+miny+"maxy"+maxy);
		int h=0,m=0;
		for(int i=minx;i<=maxx;i++)
		{
			for(int j=miny;j<=maxy;j++)
			{
				int rgb=buff.getRGB(i, j);					
				if(h<newwidth&&m<newheight)
			        {
					buffimage.setRGB(h, m++, rgb);
			        }//buffimage.setRGB(h, m++, rgb);							        
			}
			h++;
			m=0;
		}
		System.out.println("ok");
		//g.drawImage(buff, 0,0,newwidth, newheight, null);
     	g.drawImage(buffimage, 0, 0, null);		
		flag=true;
		freshbuff.push(buffimage);
		stackbuff.push(buffimage);		
	}
}

//放大
public void piant7(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
{
	if(!freshbuff.isEmpty())
	{
	BufferedImage buff=freshbuff.peek();
	int newwidth=buff.getWidth()+(int)((buff.getWidth()*20)/100);
	int newheight=buff.getHeight()+(int)((buff.getHeight()*20)/100);
	System.out.println(newwidth+" "+newheight);
	BufferedImage buffimage=new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_RGB);
	Graphics ng=buffimage.getGraphics();
	ng.drawImage(buff,0,0, newwidth, newheight, null);
	/*int newwidth=buff.getWidth()*2;
	int newheight=buff.getHeight()*2;
	BufferedImage buffimage=new BufferedImage(newwidth, newheight,BufferedImage.TYPE_INT_RGB );	
    for(int i=0;i<buff.getWidth();i++)
    {
    	for(int j=0;j<buff.getHeight();j++)
    		{    	   		
    		   		int rgb=buff.getRGB(i, j); 	
    		   		int count=2;
    		   		int a=i*count;int b=j*count;
    			for(int h=a;h<a+count;h++)
    			{
    				for(int m=b;m<b+count;m++)
    				{
    				  buffimage.setRGB(h, m, rgb);   					
    				}
    			}    			
    		}
    }*/	
	//System.out.println(buffimage.getWidth()+" "+buffimage.getHeight());
	g.drawImage(buffimage,0,0, null);
	flag=true;
	freshbuff.push(buffimage);
	stackbuff.push(buffimage);
	
	}
	
}
//缩小
public void paint8(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff)
{
	if(!freshbuff.isEmpty())
	{
	BufferedImage buff=freshbuff.peek();	
	int newwidth=(int)((buff.getWidth()*80)/100);
	int newheight=(int)((buff.getHeight()*80)/100);
	System.out.println(newwidth+" "+newheight);
	BufferedImage buffimage=new BufferedImage(newwidth, newheight,BufferedImage.TYPE_INT_RGB );
	Graphics ng=buffimage.getGraphics();
	ng.drawImage(buff, 0,0,newwidth, newheight, null);//一定要注明起点0,0
   /* for(int h=0;h<newwidth;h++)
    {
    	for(int m=0;m<newheight;m++)
    	{
    		int a=h*2;
    		int b=m*2;
    		if(a<buff.getWidth()&&b<buff.getHeight())
    		    {
    			int rgb=buff.getRGB(a, b);
    		    buffimage.setRGB(h, m, rgb);
    		    }
    	}
    	
    }*/	
	g.drawImage(buffimage,0,0, null);
	flag=true;
	freshbuff.push(buffimage);
	stackbuff.push(buffimage);
	}
	
}

//拖动马赛克
public void paint9(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff,int x,int y)
{    
	if(!stackbuff.isEmpty()) 
	{
	BufferedImage buff= stackbuff.peek();
	BufferedImage buffimage=new BufferedImage(buff.getWidth(), buff.getHeight(), BufferedImage.TYPE_INT_RGB);	
	if(buff.getWidth()<x||buff.getHeight()<y)
		return;
	int rgb=buff.getRGB(x, y);
	//g.setColor(new Color(rgb));
	//g.drawOval(Math.min(x, ex), Math.min(y, ey), Math.abs(x-ex), Math.abs(y-ey));	
	for(int i=0;i<buff.getWidth();i++)
	{
	  for(int j=0;j<buff.getHeight();j++)
	  {   
		    int RGB=buff.getRGB(i, j);		    		
		   buffimage.setRGB(i,j , RGB);			    		    			  			  		   
	  }
	}
	for(int i=x;i<x+20;i++)
	{
		for(int j=y;j<y+20;j++)
		{
			if(i<buffimage.getWidth()&&j<buffimage.getHeight())
			buffimage.setRGB(i, j, rgb);
		}
	}
	g.drawImage(buffimage, 0, 0,buffimage.getWidth(),buffimage.getHeight(), null);
	
	freshbuff.push(buffimage);
	stackbuff.push(buffimage);
	System.out.println("stack"+stackbuff.size());
	flag=true;	
	}
	
}
//拖动彩色马赛克
public void paint10(Graphics g,Stack<BufferedImage> freshbuff,Stack<BufferedImage> stackbuff,int x,int y)
{    
	if(!stackbuff.isEmpty()) 
	{
	BufferedImage buff= stackbuff.peek();
	if(buff.getWidth()<x||buff.getHeight()<y)
	return;
	BufferedImage buffimage=new BufferedImage(buff.getWidth(), buff.getHeight(), BufferedImage.TYPE_INT_RGB);
	//int rgb=buff.getRGB(x, y);
	//g.setColor(new Color(rgb));
	//g.drawOval(Math.min(x, ex), Math.min(y, ey), Math.abs(x-ex), Math.abs(y-ey));	
	for(int i=0;i<buff.getWidth();i++)
	{
	  for(int j=0;j<buff.getHeight();j++)
	  {   
		    int RGB=buff.getRGB(i, j);		    		
		   buffimage.setRGB(i,j , RGB);			    		    			  			  		   
	  }
	}
	Random ram=new Random();
 /*int red=(int)Color.RED.getRGB();
 int green=(int)Color.GREEN.getRGB();
 int blue=(int)Color.BLUE.getRGB();
 */
 //int rgb=(red<<16)+(green<<8)+(blue<<0);
 //System.out.println("rgb"+rgb);
//int rgb=ram.nextInt(0xffffff);
	/*for(int i=x;i<x+20;i++)
	{
		for(int j=y;j<y+20;j++)
		{
			buffimage.setRGB(i, j, rgb);
		}
	}*/
int rgb=ram.nextInt(0xffffff);
	Graphics ng=buffimage.getGraphics();
	ng.setColor(new Color(rgb));
	ng.fillOval(x, y, 20, 20);
	g.drawImage(buffimage, 0, 0,buffimage.getWidth(),buffimage.getHeight(), null);
	
	freshbuff.push(buffimage);
	stackbuff.push(buffimage);
	System.out.println("stack"+stackbuff.size());
	flag=true;	
	}
	
}

//返回上一步
public BufferedImage backward(Stack<BufferedImage> buff,Graphics g)
 {  
	 BufferedImage top=null;
	if(!buff.isEmpty())
		System.out.println("stack"+buff.size());
	{	   //BufferedImage top;
		   if(buff.size()==1)
			  {top=buff.peek();
			  g.drawImage(top,0,0,top.getWidth(),top.getHeight(),null);				 
			  }
		   else
		    {
			  if(flag==true&&buff.size()>1)
	             {
				  top=buff.pop();
			         if(buff.size()==1)
			            {			      
			            top=buff.peek();
			            g.drawImage(top,0,0,top.getWidth(),top.getHeight(),null);			           			      		           
			            }  
			         else 
			        	 {
			        	 top=buff.pop();
			        	 g.drawImage(top,0,0,top.getWidth(),top.getHeight(),null);		
			        	 }
	             }
			   if(flag==false&&buff.size()>1)
			   {
				  top=buff.pop();
				  g.drawImage(top,0,0,top.getWidth(),top.getHeight(),null);					 
			   }
	         }
		    
	  }  
		       flag=false;
	          //System.out.println("回撤完成");	    
	         System.out.println("stack"+buff.size());	        
	         return top;
	     
    }

}
