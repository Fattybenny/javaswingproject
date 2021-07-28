import java.awt.Color;
import java.awt.Graphics;

public class Shape{
private int x1,y1,x2,y2;
private String name;
private Graphics g;
private Color color;

public Shape(int x1,int y1,int x2,int y2,String name,Color color)
{
	this.x1=x1;
	this.x2=x2;
	this.y1=y1;
	this.y2=y2;
	this.name=name;
	this.color=color;
}

public Color getcolor()
{
	return color;
}

public void Repaint(Graphics g)
{
	switch(name)
	{
	case "直线":
		g.drawLine(x1, y1, x2, y2);
		break;
	case "矩形":
		g.drawRect(Math.min(x1, x2), Math.min(y1 ,y2), Math.abs(x1-x2), Math.abs(y1-y2));
		break;
	case "圆形":
		g.drawOval(Math.min(x1, x2), Math.min(y1 ,y2), Math.abs(x1-x2), Math.abs(y1-y2));
		break;
	case "曲线":
		g.drawLine(x1, y1, x2, y2);
		break;
	case "多边形":
		g.drawLine(x1, y1, x2, y2);
		break;
	}
}
}
	
