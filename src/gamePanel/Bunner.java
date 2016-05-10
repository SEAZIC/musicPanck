package gamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Bunner {

	private Image image;
	private String Meta;
	
	private String[] Meta_A;
	
	int w;
	int h;
	
	public Bunner(int w,int h,Image image,String meta) {
		this.image = image;
		this.w = w;
		this.h = h;
		this.Meta = meta;
	}
	public Bunner(int w,int h,Image image,String meta,String... MetaA) {
		this(w, h, image, meta);
		this.Meta_A = MetaA;
	}
	public void show(Graphics g,int x,int y){
		if(image != null){
			g.drawImage(image, x, y, w, h, null);
		}else{
			g.setColor(Color.BLACK);
			g.fillRect(x, y, w, h);
			g.setColor(Color.WHITE);
			g.drawString(Meta,x+((w-(Meta.length()*8))/2), y+(h/3));
			
		}
	}
	public Rectangle getRect(){
	return new Rectangle(w, h);	
	}
	public String getMeta(){
		return Meta;
	}
	public String[] getMeta_A(){
		return Meta_A;
	}
}
