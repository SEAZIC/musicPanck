package BaseSystem.maintools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import BaseSystem.FpsSetting.FpsManager;
import BaseSystem.FrameSystem.FrameWork;

public class GameEngin implements Callable<String>{
	Thread myThread;
	LinkedList<FrameWork> frame;
	FpsManager fpsM;
	LinkedList<MessagePack> messageManager;
	String[] FrameName;
	HashMap<String,FrameWork> FrameMap;
	EngineListener EL;
	
	public GameEngin() {
		MessagePack MP = new MessagePack();
		fpsM = new FpsManager(60, 0);

		frame = new LinkedList<FrameWork>();
		FrameMap  =new HashMap<String,FrameWork>();
		FrameName = new String[frame.size()];

		for(int cnt = 0; cnt < frame.size();cnt++){
			FrameName[cnt] = frame.get(cnt).getFrameName();
			FrameMap.put(frame.get(cnt).getFrameName(), frame.get(cnt));
			System.out.println(frame.get(cnt).getFrameName());
		}

		MP.setMeta_A("FrameNameSet");
		MP.setPresent(FrameName);

		messageManager  =new LinkedList<MessagePack>();

	}
	@Override
	public String call() {
		while(true){
			try {
				for(int cnt = 0 ; cnt < frame.size(); cnt++){
					frame.get(cnt).run();

					MessagePack MP;

					MP = frame.get(cnt).getmessage();
					if(MP != null){
						System.out.println("MP_create");
						System.out.println(MP.getMeta_A());
						if(messageManager.indexOf(MP) < 0){
							messageManager.add(MP);
							System.out.println("Message_add");
						}
						frame.get(cnt).removeMessage();
					}
				}
				for(int cnt = 0;cnt < messageManager.size();cnt++){
					String S = messageManager.get(cnt).getMeta_A();
					if(FrameMap.containsKey(S)){
						FrameMap.get(S).setmessage(messageManager.get(cnt));
					}
				}
				messageManager.removeAll(messageManager);
				TimeUnit.NANOSECONDS.sleep(fpsM.fpsClc());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (fpsM.getovertimeflag()){

				Thread.yield();
			}
		}
	}

	public void createFrame(Class F,String S){
		for(FrameWork fff: frame){
			if(fff.getFrameName()!=null)
				if(fff.getFrameName().equalsIgnoreCase(S)){
					return;
				}
		}
		System.out.println("frameCreate");
		FrameWork df;
		try {
			df = (FrameWork) F.newInstance();
			df.setFrameName(S);
			createFrame(df);
			FrameName = new String[frame.size()];
			FrameMap.put(df.getFrameName(), df);
			for(int cnt = 0; cnt < frame.size();cnt++){
				FrameName[cnt] = frame.get(cnt).getFrameName();

				System.out.println(frame.get(cnt).getFrameName());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException  | SecurityException e) {
			e.printStackTrace();
		}
	}
	public void createFrame(FrameWork F){
		frame.add(F);
		if(F instanceof JFrame){
			System.out.println("リスナー追加");
			((JFrame) F).addWindowListener(EL);
		}
	}
	public String[] getFrameName() {
		return FrameName;
	}
	public void setEL(EngineListener eL) {
		EL = eL;
	}
}
