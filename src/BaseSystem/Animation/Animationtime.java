package BaseSystem.Animation;

public class Animationtime {

	private boolean loop;
	private double time;
	private double diftime;
	private double min;
	private double max;
	private boolean pdcheck;
	private boolean error;
	private boolean fin;
	private boolean looped;
	
	public Animationtime(double t,double min,double max,boolean loop) {
		diftime = t;
		this.loop = loop;
		this.setLooped(false);
		this.min = min;
		this.max = max;
		if(min >= max)
			error = true;
		if(diftime < 0.0){
			time = max;
			pdcheck = false;
		}else if(diftime > 0.0){
			time = min;
			pdcheck = true;
		}else{
			error = true;
		}
	}
	public void refresh(){
		if(error || fin)
			return;
		time+=diftime;
		if(pdcheck){
			if(time > this.max)
				if(loop){
					setLooped(true);
					time = this.min;
				}else{
					fin = true;
				}
		}else{
			if(time < this.min)
				if(loop){
					setLooped(true);
					time = this.max;
				}else{
					fin = true;
				}
		}
	}
	public double getTime() {
		return time;
	}
	public void stop(){
		fin = true;
	}
	public boolean isStop(){
		return fin;
	}
	public boolean isError() {
		return error;
	}
	public boolean isLooped() {
		boolean l = looped;
		if(l){
			looped = false;
		}
		return l;
	}
	public void setLooped(boolean looped) {
		this.looped = looped;
	}
}
