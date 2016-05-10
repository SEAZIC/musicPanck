package Node;

import java.awt.Color;
import java.awt.Graphics;

public class Node {
	private int x;
	private int y;
	private int sizex;
	private int sizey;
	protected Color color;
private double anitime;
	public Node() {
		anitime = 0;
		x = -100;
		y = -100;
		sizex = 10;
		sizey = 10;
		color = Color.GREEN;
	}
	public void start(double t){
		anitime = t;
	}
	public void refresh(){
		anitime--;
	}
	public void refresh(double time){
		anitime -= time;
		//System.out.println("anitime"+anitime);
	}
	public void setpos(int x,int y){
		this.x = x;
		this.y = y;
	}
	public void setColor(Color C){
		this.color = C;
	}
	public void show(Graphics g){
		g.setColor(color);
		g.fillOval(x-(sizex/2), y-(sizey/2), sizex, sizey);
	}
	public double gettime(){
		return anitime;
	}
}
