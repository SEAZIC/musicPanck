package gamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import BaseSystem.FrameSystem.FrameWork;
import BaseSystem.PanelSystem.PanelWork;
import BaseSystem.maintools.MessagePack;
import Node.NodeScore;
import Node.NodeStreetBase;
import Node.NodeStreetXY;
import Node.NodeStreetXY_A;
import musicPanckmain.MusicGame;

public class musicGames extends JPanel implements PanelWork,KeyListener{
	private MusicGame MG;
	private NodeScore NScore;
	ArrayList<NodeStreetBase> N;

	ArrayList<String> key;
	
	private FrameWork parent;
	
	private int min;
	
	private int def;
	
	private boolean pose;
	
	public musicGames(String file,int fil) {
		this(file,fil,120,6);
	}
	public musicGames(String file,int fil,int deff,int line) {

		N = new ArrayList<NodeStreetBase>();
		key = new ArrayList<String>();
		NScore = new NodeScore();

		setBackground(Color.black);
		int cut = line;
		int sizex = 320/cut;
		for(int cnt = 0;cnt < cut;cnt++){
			N.add(new NodeStreetXY_A(sizex*(cnt+1), 0,sizex,640));
		}
		int keycut = cut;
		String[] Keys1 = new String[]{
				"a",
				"s",
				"d",
				"f",
				"g",
				"h",
				"j",
				"k",
				"l"			
		};
		for(int cnt = 0;cnt < Keys1.length;cnt++){
			if(cnt == (keycut/2)){
					cnt = Keys1.length-cnt-(keycut%2);
			}
			key.add(Keys1[cnt]);
		}
		
		for(int cnt = 0;cnt < N.size();cnt++){
			N.get(cnt).setRange(150);
			N.get(cnt).setSpeed((cnt/2.0f)+1.0f);
			N.get(cnt).setNScore(NScore);
		}
		MG = new MusicGame(N, file, fil);
		
		//MG = new MusicGame();
		min = fil/2;
		def = deff;
		pose = false;
		MG.setEnable(true);
		System.out.println("すたーと");
	}
	@Override
	public void setParent(FrameWork parent) {
		this.parent = parent;
		if(parent instanceof JFrame){
			JFrame pframe = (JFrame)parent; 
			setBounds(0, 0, pframe.getWidth(), pframe.getHeight());
			pframe.addKeyListener(this);
			pframe.requestFocusInWindow();	
		}
	}

	@Override
	public void panelrun() {
		MG.refresh();
		if(MG.isFinish()){
			MG.close();
			System.out.println("すたーと");
			MessagePack AMP = new MessagePack();
			AMP.setMeta_A("Chiled_Change");
			AMP.setPresent(new selectPanel());
			this.parent.setmessage(AMP);
		}
		if(NScore.getExcellent() > def){
			MG.setSettingtime(min);
		}
		repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		MG.show(g);
		String[] S = NScore.getText();
		g.setColor(Color.white);
		for(int cnt = 0;cnt < S.length;cnt++){
			g.drawString(S[S.length-cnt-1], 400, (cnt*30)+50);
		}
		//g.drawString("asdfasdfad", 100, 300);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		String S = ""+(char)e.getKeyChar();
		//System.out.println("key "+S);
		for(int cnt = 0;cnt < key.size();cnt++){
			if(S.equalsIgnoreCase(key.get(cnt))){
				N.get(cnt).pushButton();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_0){
			for(int cnt = 0;cnt < N.size();cnt++){
				N.get(cnt).setRobo(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_9){
			for(int cnt = 0;cnt < N.size();cnt++){
				N.get(cnt).setRobo(false);
			}
		}
		if(pose){
			if(e.getKeyCode() == KeyEvent.VK_1){
				MG.close();
				System.out.println("すたーと");
				MessagePack AMP = new MessagePack();
				AMP.setMeta_A("Chiled_Change");
				AMP.setPresent(new selectPanel());
				this.parent.setmessage(AMP);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			MG.stopandrestart();
			if(!pose)
				pose = true;
			else
				pose= false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
