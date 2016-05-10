package musicPanckmain;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.omg.PortableInterceptor.SUCCESSFUL;

import Node.Node;
import Node.NodeStreetBase;
import Node.Noderandom;
import SampleNode.NodeS1;
import SampleNode.NodeS2;
import SampleNode.NodeS3;
import SampleNode.NodeS4;
import midtimeconv.MIDIPlayer;
import midtimeconv.midsequence;

public class MusicGame {

	private ArrayList<NodeStreetBase> NSB;
	private midsequence ms;
	private MIDIPlayer MP;

	private int setIndex;

	private double settime;
	private double starttime;
	private double endtime;
	private double oldtime;
	private double nowtime;
	private double stoprag;
	
	private double settingtime;
	private double settingtimeMax;
	
	private int oldkey;
	private boolean startflag;
	private boolean endflag;
	private boolean finishflag;
	
	private boolean stopFlag;
	
	private boolean enable;

	public MusicGame() {
		
		this(null,"data/turtolLong.mid",500);
		//		for(int cnt = 0 ;cnt < 1;cnt++){
		//			NSB.add(new NodeStreetBase());
		//		}
		NSB = new ArrayList<NodeStreetBase>();
		NSB.add(new NodeS1());
		NSB.add(new NodeS2());
		NSB.add(new NodeS3());
		NSB.add(new NodeS4());
//		NSB.add(new NodeS5());
//		NSB.add(new NodeS6());
//		NSB.add(new NodeS7());
//		NSB.add(new NodeS8());
		setMusic("data/turtolLong.mid");
		nowtime  =System.currentTimeMillis();
		settime = 0;
		starttime = 0;
		
		settingtime = 500;
		
		setIndex = 0;
		startflag = false;
		enable = true;
		oldkey = -1;
		stopFlag = false;
		ScheduledFuture<?> future;
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		future = scheduler.schedule(MP, 5000, TimeUnit.MILLISECONDS);
	}

	public MusicGame(ArrayList<NodeStreetBase> NSB,String file,int settime){

		this.NSB = NSB;
		setMusic(file);
		nowtime  =System.currentTimeMillis();
		this.settime = 0;
		starttime = 0;
		
		stoprag = 0.0;
		
		settingtime = settime;
		settingtimeMax = settime;
		setIndex = 0;
		startflag = false;
		enable = true;
		oldkey = -1;
		
		endflag = false;
		finishflag = false;

		stopFlag = false;
		ScheduledFuture<?> future;
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		future = scheduler.schedule(MP, 5000, TimeUnit.MILLISECONDS);
		System.out.println(System.currentTimeMillis());
	}
	public void setSettingtime(double settime){
		settingtimeMax = settime;
	}
	public void refresh(){
		if(!enable || finishflag||stopFlag)
			return;
		oldtime = nowtime;
		nowtime = System.currentTimeMillis();
		double time = (nowtime-oldtime);
		for(int cnt = 0 ;cnt < NSB.size();cnt++){
			NSB.get(cnt).refresh(time);
		}
		starttime += time;
		settime += time;
		settingtime += time;
		//System.out.println("settime"+starttime);
		if(setIndex < ms.getTickontiming().size())
			for(int cnt = 0;cnt < ms.getTickontiming().size()-setIndex;cnt++){
				if(settime > (double)ms.getTickontiming().get(setIndex)){
					settime -= ((double)ms.getTickontiming().get(setIndex));
					if(settingtime > settingtimeMax){					
						Node node = new Noderandom();
						node.start(5000-settime);
						NSB.get(ms.getnoteonkey().get(setIndex)).add(node);
						oldkey = ms.getnoteonkey().get(setIndex);
						if(settingtime > settingtimeMax)
						settingtime = 0;
					}
					//System.out.println("setindex"+setIndex);
					setIndex++;
					if(setIndex == ms.getTickontiming().size()){
						System.out.println("終了時間"+starttime+"+5000");
						endtime = starttime+10000;
						endflag = true;
					}
				}else{
					break;
				}
			}
		if(!startflag)
			if(starttime >= 5000){
				//MP.start();
				startflag = true;
				System.out.println("starttime = "+starttime);
			}
		if(endflag){
			if(starttime > endtime)
				finishflag = true;
		}
	}

	public void show(Graphics g){
		if(!enable)
			return;
		for(int cnt = 0 ;cnt < NSB.size();cnt++){
			NSB.get(cnt).show(g);
		}
	}
	public void setEnable(boolean en){
		enable = en;
	}
	private void setMusic(String file){
		if(NSB == null)
			return;
		if(MP != null)
			MP.close();
		ms = new midsequence(new File(file), NSB.size());
		MP = new MIDIPlayer(ms.getSequence());
	}
	public boolean isFinish(){
		return finishflag;
	}
	public void stop() {
		if(stopFlag)
			return;
		MP.stop();
		oldtime = nowtime;
		nowtime = System.currentTimeMillis();
		double time = (nowtime-oldtime);
		stoprag = time;
		stopFlag = true;
	}
	public void restart(){
		if(!stopFlag)
		return;
		oldtime = System.currentTimeMillis()-stoprag;
		nowtime = System.currentTimeMillis();
		MP.start();
		stopFlag = false;
	}
	public void stopandrestart(){
		if(stopFlag){
			restart();
		}else{
			stop();
		}
	}
	public void close(){
		MP.stop();
		MP.close();
		MP = null;
	}
}
