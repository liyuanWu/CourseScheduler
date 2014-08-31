package liyuan.wu.classschedulor.beans;

public class WeekDay implements Cloneable{
	public enum WEEKDAY{Monday,Tuesday,Wednesday,Thrusday,Friday}
	private final WEEKDAY weekDay;
	public WeekDay(WEEKDAY weekDay){
		this.weekDay=weekDay;
	}
	public WeekDay(int weekDayIndex){
		switch(weekDayIndex){
		case 0:this.weekDay=WEEKDAY.Monday;break;
		case 1:this.weekDay=WEEKDAY.Tuesday;break;
		case 2:this.weekDay=WEEKDAY.Wednesday;break;
		case 3:this.weekDay=WEEKDAY.Thrusday;break;
		case 4:this.weekDay=WEEKDAY.Friday;break;
		default:this.weekDay=null;break;
		}
	}
	public WEEKDAY getWeekDay() {
		return weekDay;
	}
	public Object clone(){
		return new WeekDay(this.weekDay);
	}
	public boolean equals(Object obj){
		if( obj instanceof WeekDay && ((WeekDay)obj).getWeekDay()==this.getWeekDay() ){
			return true;
		}
		return false;
	}
	public int hashCode(){
		return this.weekDay.hashCode();
	}
	public int getWeekDayInteger(){
		switch(this.weekDay){
		case Monday:return 0;
		case Tuesday:return 1;
		case Wednesday:return 2;
		case Thrusday:return 3;
		case Friday:return 4;
		default:return -1;
		}
	}
}
