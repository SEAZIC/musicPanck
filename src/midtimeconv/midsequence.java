package midtimeconv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class midsequence {

	private Sequence midsc;

	private Track[] midtrack;

	private int timerate = 0;

	private boolean timeSetFlag_Delta;

	ArrayList<MidiEvent> MidEv = new ArrayList<MidiEvent>();
	ArrayList<ArrayList<MidiEvent>> midlistAll = new ArrayList<ArrayList<MidiEvent>>();
	LinkedList<MidiMessage> noteon = new LinkedList<MidiMessage>();
	LinkedList<Integer> noteontiming = new LinkedList<Integer>();
	LinkedList<Long> Ticknoteon = new LinkedList<Long>();

	long timemax = 0L;
	byte[] temp;
	int addcnt = 0;
	int Resl;

	long MidD;
	long Midtick;

	double tickTime;

	public midsequence(File midfile,int timing){
		timeSetFlag_Delta = true;
		if(timing < 1){
			timing = 12;
		}
		try {
			midsc = MidiSystem.getSequence(midfile);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			System.out.println("Midiじゃねえぜ？");
			e.printStackTrace();
		}catch( IOException e){
			System.out.println("エラーだお");
			e.printStackTrace();
		}
		if(midsc.getDivisionType() == Sequence.PPQ){
			System.out.println("シーケンスタイムタイプ　＝PPQーー"+midsc.getResolution());
			timerate = midsc.getResolution();
			if(timerate > 480)
				timeSetFlag_Delta = false;

		}else{
			System.out.println("What Sequencetype");
		}
		////////////////////////////////////////////////////////
		MidD = midsc.getMicrosecondLength();
		System.out.println("マイクロ秒指定"+MidD);

		Midtick = midsc.getTickLength();
		System.out.println("Tick指定"+Midtick);

		Resl = midsc .getResolution();
		System.out.println("レソリューション"+ Resl);
		long times = 0;
		tickTime = (double)(MidD/1000)/Midtick;
		//tickTime = (double)1000/1040;
		System.out.println("１ティックに対するミリ秒"+(double)(tickTime));
		////////////////////////////////////////////////////////
		midtrack = midsc.getTracks();
		System.out.println("トラック数読込場所"+midtrack.length);
		int cntx = 0;
		for(int cnt = 0;cnt < midtrack.length;cnt++){
			System.out.println("Track数"+cnt+"");
			//midlistAll.add(new ArrayList<MidiEvent>());
			ArrayList<MidiEvent> set = new ArrayList<MidiEvent>();
			for(int cnt2 = 0;cnt2 < midtrack[cnt].size();cnt2++){
				cntx++;
				set.add(midtrack[cnt].get(cnt2));

				//midlistAll.get(cnt2).add(midtrack[cnt].get(cnt2));

				//System.out.println("イベント数"+cnt2+"");		

				//MidEv.add(midtrack[cnt].get(cnt2));

			}
			System.out.println("track+");
			midlistAll.add(set);
			set=null;
		}
		System.out.println("cntx = "+cntx);
		arrenge(midlistAll);
		int cnt2 = 0;
		long oldtime = 0L;
		System.out.println("Mv ---- "+MidEv.size());
		for(int cnt = 0;cnt < MidEv.size();cnt++){
			if(timemax > MidEv.get(cnt).getTick()){
				cnt2++;
				System.out.println("cnt2 = "+cnt2+"a "+MidEv.get(cnt).getMessage().getMessage().length+" "+(MidEv.get(cnt).getMessage().getStatus()));
			}
			if(timeSetFlag_Delta){
				timemax = MidEv.get(cnt).getTick();
				//System.out.println("MGGGG---"+MidEv.get(cnt).getMessage().getStatus()+" "+MidEv.get(cnt).getMessage().getMessage().length);
			}else{
				timemax = MidEv.get(cnt).getTick();				
			}
			if(MidEv.get(cnt).getMessage().getStatus() == (int)(0xFF & 0xFF)){
				for(int cnt21 = 0;cnt21 < MidEv.get(cnt).getMessage().getLength();cnt21++){
					System.out.println("制御コードーー"+"cnt "+cnt21+"-"+MidEv.get(cnt).getMessage().getMessage()[cnt21]);
				}
				
			}
			if(MidEv.get(cnt).getMessage().getStatus() >= (int)(0x80 & 0xFF)){

				temp = MidEv.get(cnt).getMessage().getMessage();
				if(temp.length < 2){
					continue;
				}
				if(temp.length > 2){
					//System.out.println("test = "+timemax/timerate +" タイム　＝"
					//					+(long)(timemax*tickTime)+"データ値　＝　"+temp[2]);
					if(temp[2] == 0x00){
						//System.out.println(" "+MidEv.get(cnt).getMessage().getStatus());
						continue;
					}
				}
				if(MidEv.get(cnt).getMessage().getStatus() >= (int)(0xA0 & 0xFF)){
					//System.out.println("制御" + cnt+" "+MidEv.get(cnt).getMessage().getStatus());
					if(MidEv.get(cnt).getMessage().getStatus() >= (int)(0xE0 & 0xFF)){
						
					}else{
						System.out.println("A0~E0");
					}
					continue;
				}
				addcnt++;
				noteon.add(MidEv.get(cnt).getMessage());

				noteontiming.add(((int)(temp[1] & 0xFF))%timing);
				times += (long)((timemax-oldtime)*tickTime);
				//System.out.println("aa"+times);
				Ticknoteon.add((long)((timemax-oldtime)*tickTime));
				oldtime = timemax;
			}
		}
		////////////////////////////////////////////////////////
		MidD = midsc.getMicrosecondLength();
		System.out.println("マイクロ秒指定"+MidD);

		Midtick = midsc.getTickLength();
		System.out.println("Tick指定"+Midtick);

		Resl = midsc .getResolution();
		System.out.println("レソリューション"+ Resl);

		tickTime = (double)(MidD/1000)/Midtick;
		System.out.println("１ティックに対するミリ秒"+(double)(tickTime));

		System.out.println("e==="+times);
		
		System.out.println("葬トラック数　＝　"+midsc.getTracks().length);
		
		System.out.println("MV = "+MidEv.size()+" keynum = "+noteontiming.size()+" timenum = "+Ticknoteon.size());
		////////////////////////////////////////////////////////
	}

	private void arrenge(ArrayList<ArrayList<MidiEvent>> midlistAlls) {
		// TODO Auto-generated method stub

		//MidEv.add(midlistAll.get(0).get(0));
		LinkedList<MidiEvent> MidEv = new LinkedList<MidiEvent>();
		if(MidEv.isEmpty()){
			System.out.println("初期化の正常を確認");
		}
		for(int cnt = 0;cnt<midlistAlls.size();cnt++){
			//System.out.println("midlistAlls----"+midlistAlls.size()+"--midlistAlls.get(cnt).size()--"+midlistAlls.get(cnt).size());
			MidiEvent set =null;
			boolean errorcheck = true;
			int cnt3 = MidEv.size()-1;
			for(int cnt2 = midlistAlls.get(cnt).size()-1;cnt2 > -1;cnt2--){
				set=midlistAlls.get(cnt).get(cnt2);
				errorcheck = true;
				if(MidEv.isEmpty()){
					//System.out.println("cnt = "+cnt+"cnt2 = "+cnt2);
				}else{
					//System.out.println("ういー");
					for(;cnt3 > -1;cnt3--){
						//System.out.println(""+MidEv.get(cnt3).getTick()+"-"+set.getTick());
						if(MidEv.get(cnt3).getTick() <= set.getTick()){
							
							MidEv.add(cnt3+1, set);

							errorcheck = false;
							break;
							
						}

					}
				}
				if(errorcheck){
					errorcheck = false;
					MidEv.addFirst(set);
				}
			}
			if(errorcheck){
				System.out.println("トラック結合時にエラーが発生しました");
			}
		}
		for(MidiEvent me: MidEv){
			//this.MidEv.add(me);
			midAdd(me);
		}
	}
	protected void midAdd(MidiEvent me){
		if(me.getMessage().getStatus() < (int)(0xF0 & 0xFF)){
			this.MidEv.add(me);
			if(me.getMessage().getStatus() < (int)(0xFF & 0xFF)){
				//System.out.println("FF");
			}else
				System.out.println("!FF");
		}
	}
	public Sequence getSequence(){
		return midsc;
	}
	
	public LinkedList<Integer> getnoteonkey(){
		return noteontiming;
	}
	public LinkedList<Long> getTickontiming(){
		return Ticknoteon;
	}

}
