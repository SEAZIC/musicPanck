package Node;

import java.awt.Color;
import java.awt.Graphics;

public class NodeStreetXY extends NodeStreetBase{
	protected int width;
	protected int heght;
	
	public NodeStreetXY() {
		super();
		width = 40;
		heght = 640;
	}
	public NodeStreetXY(int x,int y){
		this();
		this.x = x;
		this.y = y;
	}
	public NodeStreetXY(int x,int y,int w,int h) {
		this(x,y);
		setWH(w, h);
	}
	public void setWH(int w,int h) {
		this.width = w;
		this.heght = h;
	}
	@Override
	public void show(Graphics g) {
		// TODO Auto-generated method stub
		super.show(g);
		g.setColor(Color.WHITE);
		g.drawLine(x-(width/2), -10, x-(width/2), heght);
		g.drawLine(x+(width/2), -10, x+(width/2), heght);
		g.drawLine(x-(width/2), y+(int)((maxTime-range)/split), x+(width/2), y+(int)((maxTime-range)/split));
		g.drawLine(x-(width/2), y+(int)((maxTime+range)/split), x+(width/2), y+(int)((maxTime+range)/split));
		
		
		
		
	}
}
