package midtimeconv;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MIDIPlayer implements Runnable {

	private Sequencer Player;
	
	public MIDIPlayer(Sequence S) {

		try {
			Player = MidiSystem.getSequencer();
			//Player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			Player.open();
			try {
				Player.setSequence(S);
				System.out.println(Player.getTempoInMPQ());
				System.out.println(Player.getTempoFactor());
				System.out.println(Player.getTempoInBPM());
				
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setLoop(){
		Player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	}
	public  void start(){
		if(!Player.isRunning())
		Player.start();
		System.out.println(System.currentTimeMillis());
	}
	public void stop(){
		if(Player.isRunning())
		Player.stop();
	}
	public void close() {
		stop();
		Player.close();
		
	}
	@Override
	public void run() {
		start();
		System.out.println("run"+System.currentTimeMillis());
	}
	public boolean isFinish(){
		return !Player.isRunning();
	}
}
