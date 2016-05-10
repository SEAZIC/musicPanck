package BackResoce;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class downString {

	private ArrayList<StringSorce> stay;
	private ArrayList<StringSorce> move;
	
	private int time;
	private int timemax;
	
	private int maxx;
	private int maxy;
	
	private boolean enable;
	
	public downString() {
		stay = new ArrayList<StringSorce>();
		move = new ArrayList<StringSorce>();
		time = 0;
		timemax = -1;
		maxy = 0;
		enable = true;
	}
	public void addS(StringSorce S){
		stay.add(S);
	}
	public void set_max_xy(int x,int y){
		maxx = x;
		maxy = y;
	}
	public void refresh(){
		if(!enable)
			return;
		for(int cnt = 0;cnt< move.size();cnt++){
			move.get(cnt).getResorce();
			move.get(cnt).addXY(0,20);
			if(move.get(cnt).getY() > maxy){
				Random rm = new Random();
				stay.add(move.remove(cnt));
			}
		}
		if(time++ > timemax){
			time = 0;
			if(stay.size() > 0){
				Random rm = new Random();
				int r= rm.nextInt(stay.size());
				StringSorce SS = stay.remove(r);
				SS.setInit(rm.nextInt(maxx), 0);
				move.add(SS);
				//System.out.println("stay = "+stay.size()+" r = "+r);
			}
		}
	}
	public void show(Graphics g){
		if(!enable)
			return;
		for(int cnt = 0;cnt< move.size();cnt++){
			move.get(cnt).show(g);
		}
	}
	public void setEnable(boolean en){
		enable = en;
		
	}
}
