package Node;

import java.awt.Color;
import java.util.Random;

public class Noderandom  extends Node{

	public Noderandom() {
		super();
		Random r = new Random();
		color = new Color(0.3f+0.7f*r.nextFloat(),0.3f+0.7f*r.nextFloat(),0.3f+0.7f*r.nextFloat());
	}
	
}
