package Node;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class NodeStreetBase {
	protected int x;
	protected int y;
	private LinkedList<Node> Nodes;
	private ArrayList<Node> removeList;

	protected int range;

	protected int split;
	
	protected float nodespeed;
	
	protected NodeScore NScore;

	protected double maxTime;

	protected boolean Robo;

	public NodeStreetBase() {
		x = 40;
		y = 100;
		split = 12;
		range = 0;

		Nodes = new LinkedList<Node>();
		removeList = new ArrayList<Node>();

		NScore = new NodeScore();

		maxTime = 5000.0;
		
		nodespeed = 1.0f;
		
	}
	public void setNScore(NodeScore NS) {
		NScore = NS;
	}
	public void add(Node N){
		Nodes.addLast(N);
	}
	public void refresh(double time){

		if(Nodes != null && Nodes.size() > 0){
			int cnt = 0;
			for(Node node:Nodes){
				node.refresh(time);
//				node.setpos(x, y+(int)((maxTime-node.gettime())/12));
				setNodeXY(node,x, y+(int)((maxTime-(node.gettime())*nodespeed)/split));
				//System.out.println("Y"+(y+(int)((5000-Nodes.get(cnt).gettime())/15)));
				if(node.gettime() < -range && cnt == 0){
					removeList.add(node);
					NScore.addMiss();
				}else{
					if(Robo && cnt == 0){
						Node N = Nodes.getFirst();
						double robotime = N.gettime();
						if((robotime*robotime) < (range*range)/4){
							pushButton();
							
						}
					}
				}
				cnt++;
			}
		}
		if(removeList.size() > 0){
			for(int cnt = removeList.size()-1;cnt > -1;cnt--){
				if(Nodes.indexOf(removeList.get(cnt)) > -1){
				removeNode(Nodes.indexOf(removeList.get(cnt)));
				}else{
					System.out.println("リムーブエラー");
				}
			}
			removeList = new ArrayList<Node>();
		}

	}
	public void pushButton(){
		if(Nodes.size() <= 0)
			return;
		Node N = Nodes.getFirst();
		double time = N.gettime();
		double time2 = time*time;
		double rang2 = range*range;
		//System.out.println("time = "+time);
		if(time2 <= rang2){
			if(time2 <= rang2*3/4){
				if(time2 <= rang2/2){
					if(time2 <= rang2/4){
						NScore.addExcellent();
					}else{
						NScore.addgreat();
					}
				}else {
					NScore.addgood();
				}
			}else{
				NScore.addbad();
			}
			removeList.add(Nodes.getFirst());
		}
	}
	public void show(Graphics g){

		if(Nodes != null && Nodes.size() > 0){
			for(Node node:Nodes){
				node.show(g);
			}
		}

	}
	protected void setNodeXY(Node N,int x,int y){
		N.setpos(x, y);
	}
	public void setRange(int range) {
		this.range = range;
	}
	protected void removeNode(int Index) {
		Nodes.remove(Index);
	}
	public void setRobo(boolean robo) {
		this.Robo = robo;
	}
	public void setSpeed(float speed){
		this.nodespeed = speed;
	}
}
