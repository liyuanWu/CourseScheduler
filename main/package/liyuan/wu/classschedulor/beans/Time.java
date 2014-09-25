package liyuan.wu.classschedulor.beans;

public class Time implements Cloneable{

	private final int time;

	public Time(int time){
		this.time = time;
	}
	public int getTime() {
		return time;
	}
	public Object clone(){
		return new Time(this.time);
	}
	public boolean equals(Object obj){
		if(obj instanceof Time && ((Time)obj).getTime()==this.time){
			return true;
		}
		return false;
	}
	public int hashCode(){
		return this.time;
	}
}
