package Node;

import java.awt.Color;
import java.awt.Graphics;

public class NodeStreetXY_A extends NodeStreetXY{

	protected boolean ef_flag;
	protected int ef_time;
	protected int ef_time_max;
	protected String ef_String;
	
	protected int push_time;
	protected int push_time_max;
	protected boolean push_flag;
	
	public NodeStreetXY_A() {
		ef_String = "";
		ef_time = 0;
		ef_time_max = 100;
		ef_flag = false;

		push_time = 0;
		push_time_max = 15;
		push_flag = false;
		
	}
	
	public NodeStreetXY_A(int i, int j, int w, int h) {
		// TODO Auto-generated constructor stub
		super(i, j, w, h);
		ef_String = "";
		ef_time = 0;
		ef_time_max = 100;
		ef_flag = false;
		split = 12;
		nodespeed = 1.0f;
		push_time = 0;
		push_time_max = 15;
		push_flag = false;
		
	}

	@Override
	public void show(Graphics g) {

		if(push_flag){
			g.setColor(Color.GRAY);
			g.fillRect(x-(width/2),y+(int)((maxTime-range)/split),width,(int)(range/split*2));
		}
		super.show(g);
		if(ef_flag){
			g.setColor(Color.WHITE);
			g.drawString(ef_String, x-(width/2), y+(int)((maxTime+range)/split));
		}
	}
	@Override
	public void pushButton() {
		super.pushButton();
		push_flag = true;
		push_time = 0;
	}
	@Override
	protected void removeNode(int Index) {
		super.removeNode(Index);
		ef_time = 0;
		ef_String = NScore.getLast();
	}
	@Override
	public void refresh(double time) {
		// TODO Auto-generated method stub
		super.refresh(time);
		if(ef_time <= ef_time_max){
			ef_flag = true;
			ef_time++;
		}else {
			ef_flag = false;
		}
		
		if(push_time <= push_time_max){
			push_flag = true;
			push_time++;
		}else {
			push_flag = false;
		}
		
		
		
	}
	@Override
	protected void setNodeXY(Node N, int x, int y) {
		super.setNodeXY(N, x, y);
	}
	@Override
	public void setRange(int range) {
		// TODO Auto-generated method stub
		super.setRange(range);
	}
}
