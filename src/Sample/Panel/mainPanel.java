package Sample.Panel;

import gamePanel.selectPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import BackResoce.StringSorce;
import BackResoce.downString;
import BaseSystem.FrameSystem.FrameWork;
import BaseSystem.ImageTool.ImageSever;
import BaseSystem.PanelSystem.PanelWork;
import BaseSystem.maintools.MessagePack;
import Sample.Frame.mainFrame;
import musicPanckmain.MusicGame;


public class mainPanel extends JPanel implements ActionListener,PanelWork{
	private ImageSever IS;
	private JButton start;
	private JButton stop;

	private FrameWork parent;
	private MusicGame MusicG;

	private downString DS;

	public mainPanel(mainFrame mainFrame) {
		parent = (FrameWork) mainFrame;
		System.out.println(""+mainFrame.getHeight());

		setLayout(null);
		setBounds(0, 0,mainFrame.getWidth(), mainFrame.getHeight());
//		IS = new ImageSever();
//		IS.createImage("たいとる", "title.jpg");
		start = new JButton("Start");
		start.setBounds(mainFrame.getWidth()/2-50, mainFrame.getHeight()/2-10,100,20);
		start.addActionListener(this);
		stop = new JButton("stop");
		stop.setBounds(mainFrame.getWidth()/2-50, mainFrame.getHeight()/2+20,100,20);
		stop.addActionListener(this);
		this.add(start);
		this.add(stop);
		DS = new downString();
		DS.set_max_xy(mainFrame.getWidth(), mainFrame.getHeight());
		ArrayList<String> list = new ArrayList<String>();
		for(int cnt = 0;cnt< 10;cnt++){
			list.add(""+(char)(48+cnt));
		}
		for(int cnt = 0;cnt < 100;cnt++){
			StringSorce S = new StringSorce();
			S.setList(list, 20);
			DS.addS(S);
		}
		setBackground(Color.black);
		DS.setEnable(true);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);	
		DS.show(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)){
			System.out.println("すたーと");
			MessagePack AMP = new MessagePack();
			AMP.setMeta_A("Chiled_Change");
			AMP.setPresent(new selectPanel());
			this.parent.setmessage(AMP);
		}
		if(e.getSource().equals(stop)){
		}
	}



	public void setParent(FrameWork parent) {
		this.parent = parent;

	}

	@Override
	public void panelrun() {

		DS.refresh();
		repaint();
	}
}
