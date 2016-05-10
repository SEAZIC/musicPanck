package Node;

public class NodeScore {

	private int miss;
	private int bad;
	private int good;
	private int great;
	private int excellent;
	
	private String lasttext;
	
	public NodeScore() {

		miss = 0;
		bad = 0;
		good = 0;
		great = 0;
		excellent = 0;
	
		lasttext = "";
		
	}
	
	public int getMiss() {
		return miss;
	}

	public int getBad() {
		return bad;
	}

	public int getGood() {
		return good;
	}

	public int getGreat() {
		return great;
	}

	public int getExcellent() {
		return excellent;
	}

	public void addMiss(){
		miss++;
		lasttext = "miss";
	}
	public void addbad(){
		bad++;
		lasttext = "bad";
	}
	public void addgood(){
		good++;
		lasttext = "good";
	}
	public void addgreat(){
		great++;
		lasttext = "great";
	}
	public void addExcellent(){
		excellent++;
		lasttext = "excellent";
	}
	public String[] getText(){
		String[] S = new String[]{
				"miss      : "+miss,
				"bad       : "+bad,
				"good      : "+good,
				"great     : "+great,
				"excellent : "+excellent
				
		};
		
	return S;	
	}
	public String getLast(){
		return lasttext;
	}
}
