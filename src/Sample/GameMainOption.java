package Sample;

import BaseSystem.maintools.GameEngin;
import Sample.Frame.mainFrame;

public class GameMainOption {
	GameEngin GE;
	private boolean memo;
	public GameMainOption(GameEngin GE) {
		this.GE = GE;
	}
	public void init(){
		GE.createFrame(new mainFrame());
		memo = true;
	}
	public boolean memoricheck(){
		return memo;
	}
	
}
