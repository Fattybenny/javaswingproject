public class Listener implements ActionListener,MouseListener,MouseMotionListener {
	//一定要将接口的所有方法重写
	private int index=0;
	private Graphics gr;
	private String command="";	
	private Paint p;
	private Color color=Color.black;
	int x0=0,y0=0,//曲线的起始坐标
	    x1=0,y1=0,x2=0,y2=0,//其他图形
	    x3=0,x4=0,y3=0,y4=0,
	    start_x=0,start_y=0;//多边形起点

	//初始化数据
	//基本数据类型向形参传递的是副本，引用数据类型（类，接口，数组）向形参传递的地址，如下传递jp	
	public void set_gr(Graphics G) {
		this.gr = G;
	}	

	public void set_p(Paint p)
	{
		this.p=p;
	}
			
	/*
	 * getSource得到的组件的名称，而getActionCommand得到的是标签。
           如：Button bt=new Button("buttons");
             用getSource得到的是bt 而用getActionCommand得到的是:buttons
	 */
	/*
	 * 这里必须先点击被命名的按钮(即要画什么图形)，将"zhiling"初始化，
	 * 否则后面的监听回应方法都不能执行，如果不点击颜色按钮(只设置了按钮的背景颜色，没有settext),
	 * 则不会为画笔setcolor
	 * 默认黑色
	 */
	public void actionPerformed(ActionEvent e) 
	{
		
		// TODO Auto-generated method stub
		//在画板上描绘图案的时候用的是同一个画笔，只不过每次使用之前都把画笔的颜色修改了		
		 		if (e.getActionCommand()=="") {
			  //j暂存颜色按钮  
			JButton j = (JButton)e.getSource();
			//将画笔设置为按钮的背景颜色
			color=j.getBackground();
		}
		else {
			command = e.getActionCommand();
			if("清除".equals(command))
			{
				Graphics cleang=p.getGraphics();
				//JFrame和JPanel的repaint方法是同一个，都是继承自JComponent的，对该方法的调用执行的都是同样的代码的。
				
					p.paint1(cleang);
			        p.shape=new Shape[10000];
			        index=0;
			}
		}
	}
	
	//鼠标按键在组件上按下(长按)并拖动时(在拖动的过程中)调用。   (处理鼠标拖动事件)
			public void mouseDragged(MouseEvent e) {
				//System.out.println("Drag");				
				if("曲线".equals(command)) {
					x1 = x0; y1 = y0; //鼠标按下的时候就获得了起始坐标
					x0 = e.getX(); y0 = e.getY();
					gr.drawLine(x1,y1,x0,y0);
					p.shape[index++]= new Shape(x1,y1,x0,y0,command,gr.getColor());
				}
			}
	        
	       
			// Override鼠标监听//点击鼠标点击时间，用来绘画多边形
			public void mouseClicked(MouseEvent e) //是点击和松开两步完成后才被再调用(前提是点击和松开的位置是相同点) 在此之前，pressed 和released都已经被调用
			                                           
			{        
				System.out.println("鼠标click事件发生");
				// TODO Auto-generated method stub
				if("多边形".equals(command)) 
				{
					
					if(x4==0&&y4==0)
					    {
						
						x4 = e.getX(); y4 = e.getY();
						start_x = x4; start_y = y4;
						//记录多边形的起点位置
					      }
					
					else {
						//System.out.println("Here is duobianxing ");
						x3 = x4; y3 = y4;//这里的x3,y3为上一条边的终点
						x4 = e.getX(); y4 = e.getY();
						gr.drawLine(x3, y3, x4, y4);//将这点与上一条边的终点连起来
						p.shape[index++]= new Shape(x3,y3,x4,y4,command,gr.getColor());
					     }
					if(e.getClickCount()==2)
					{
					//System.out.println("双击");
					x4 =0; y4=0;//这里设置为零是为了能都再次执行if语句 
					gr.drawLine(start_x, start_y, e.getX(), e.getY());//将双击的终点位置和开始记录的起点位置相连
					p.shape[index++]= new Shape(start_x, start_y, e.getX(), e.getY(),command,gr.getColor());
					}	
				}
			}
				
				 //当鼠标按下的时候，获取起点的坐标
				 public void mousePressed(MouseEvent e)
				{
					  gr=p.getGraphics();
					  gr.setColor(color);
			       System.out.println("pressed事件发生");
					
					if("曲线".equals(command)){
						x0 = e.getX();
						y0 = e.getY();
					}
					// TODO Auto-generated method stub
					x1 = e.getX();
					y1 = e.getY();
					
				}
				
				
				//当鼠标松开的时候，获取终点的坐标
				public void mouseReleased(MouseEvent e)
				{
					System.out.println("released事件发生");
					// TODO Auto-generated method stub
					x2 = e.getX();
					y2 = e.getY();
				
					if("直线".equals(command)){
						gr.drawLine(x1, y1, x2, y2);
						p.shape[index++]= new Shape(x1,y1,x2,y2,command,gr.getColor());
						
					}
					else if("矩形".equals(command)) {
						gr.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
						p.shape[index++]= new Shape(x1,y1,x2,y2,command,gr.getColor());
					}
					else if("圆形".equals(command)) {
						//Math.min(x1, x2), Math.min(y1, y2),
						//drawoval 和drawrect 都是以某个点 向着坐标点增大的方向画图
						//Math.abs(x1-x2)求绝对值
						gr.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
						p.shape[index++]= new Shape(x1,y1,x2,y2,command,gr.getColor()); 
											}
				}
				//鼠标进入窗口
				public void mouseEntered(MouseEvent e)
				{
					
				}
                 //鼠标退出窗口
			    public void mouseExited(MouseEvent e)
			    {
			    	
			    }
				//鼠标在移动的过程中调用(没有点击)
			    public void mouseMoved(MouseEvent e)
		         {
		        	 
		         }
		         
				
			}

