package liyuan.wu.classschedulor.accessor;

import liyuan.wu.classschedulor.beans.BasicCombo;
import liyuan.wu.classschedulor.beans.Boat;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;
import liyuan.wu.classschedulor.data.Synchronizator;
import liyuan.wu.classschedulor.view.QueryResult;

public class BoatAccessor <T,X extends BasicCombo>{

	private final Boat<T,X> boat;
	private final Synchronizator<X> synchronizator;
	public BoatAccessor(Boat<T,X> boat, Synchronizator<X> synchronizator) {
		super();
		this.boat = boat;
		this.synchronizator = synchronizator;
	}
	
	public QueryResult query(UnarrangedCourse unarrangedCourse){
		return getSynchronizator().query(unarrangedCourse);
	}
	
	public boolean canAdd(CourseCombo courseCombo){
		return getSynchronizator().canAdd(courseCombo);
	}
	
	public void add(X x){
		this.boat.add(x);
		this.getSynchronizator().sychronizeAdd(this.boat, x);
	}
	public void remove(X x){
		this.boat.remove(x);
		this.getSynchronizator().sychronizeDelete(this.boat, x);
	}

	public T getIdentifier() {
		return this.boat.getIdentifier();
	}

	public X[] getAll() {
		return this.boat.getAll();
	}

	public int numberOf(X s) {
		return this.boat.numberOf(s);
	}
	
	public Boat<T,X> getBoat(){
		return this.boat;
	}

	public Synchronizator<X> getSynchronizator() {
		return synchronizator;
	}
	
	
	
}
