package BackResoce;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class StringSorce {

	private ArrayList<String> resorce;
	private LinkedList<String> logs;
	private int logmax;
	
	private int x;
	private int y;
	
	protected float r,g,b ;
	
	public StringSorce() {

		resorce = new ArrayList<String>();
		logs = new LinkedList<String>();
		resorce.add("a");
		logmax = 1;
		x = 0;
		y = 0;
		r = 0.0f;
		g = 1.0f;
		b = 0.0f;
	}

	public void setList(ArrayList<String> resorce,int logmax){
		this.resorce = resorce;
		this.logmax = logmax;
	}
	
	public void setInit(int x,int y){
		setXY(x, y);
	}
	public void setXY(int x,int y){
		this.x = x;
		this.y = y;
	}
	public void addXY(int addx,int addy){
		this.x += addx;
		this.y += addy;
	}
	
	public int getY(){
		return y;
	}
	
	public String getResorce(){
		Random rn = new Random();
		String S = resorce.get(rn.nextInt(resorce.size()));
		logs.addFirst(S);
		if(logs.size()>logmax){
			logs.remove(logs.size()-1);
		}
		return S;
	}
	public void show(Graphics g){
		for(int cnt = 0;cnt < logs.size();cnt++){
			//g.setColor(new Color(0,255-(int)(255*(cnt+1)/logs.size()),0));
			g.setColor(new Color(this.r,this.g,this.b,(1.0f-0.99f*(float)(cnt+1)/logs.size())));
			
			g.drawString(logs.get(cnt), x, y-(cnt*20));
		}
	}
}
