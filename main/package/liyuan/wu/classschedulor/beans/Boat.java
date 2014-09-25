package liyuan.wu.classschedulor.beans;

public interface Boat<T,X> {
	public T getIdentifier();
	public void add(X x);
	public void remove(X x);
	public X[] getAll();
	public int numberOf(X x);
	public boolean canAdd(WeekDay weekday, Time time);
}
