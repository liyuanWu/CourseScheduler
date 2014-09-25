package liyuan.wu.classschedulor.beans;

import java.util.HashSet;
import java.util.Set;

public class Scheduler <T>  implements Boat<T,CourseCombo>{
	private final T identifier;
	private final Set<CourseCombo> courses;
	
	public Scheduler(T identifier) {
		this.identifier = identifier;
		this.courses = new HashSet<CourseCombo>();
	}
	public T getIdentifier() {
		return identifier;
	}

	@Override
	public void add(CourseCombo s) {
		this.courses.add(s);
	}

	@Override
	public void remove(CourseCombo s) {
		this.courses.remove(s);
	}

	@Override
	public CourseCombo[] getAll() {
		return courses.toArray(new CourseCombo[this.courses.size()]);
	}

	@Override
	public int numberOf(CourseCombo s) {
		return this.courses.contains(s)?1:0;
	}
	public void deleteCourse(WeekDay day, Time time){
		CourseCombo toDetele = null;
		for(CourseCombo combo:courses){
			if(combo.getWeekDay().equals(day) && combo.getTime().equals(time)){
				toDetele = combo;
			}
		}
		this.remove(toDetele);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Scheduler) {
			if (obj.hashCode() == this.hashCode()) {
				return  ((Scheduler<?>)obj).courses.equals(this.courses)
						&& ((Scheduler<?>) obj).identifier
								.equals(this.identifier);
			}
		}
		return false;
	}

	public int hashCode() {
		return courses.hashCode() + identifier.hashCode() * 31;
	}

	@Override
	public Object clone() {
		Scheduler<T> clone = new Scheduler<>(this.getIdentifier());
		for (CourseCombo combo : this.getAll()) {
			clone.add(combo);
		}
		return clone;
	}
	@Override
	public boolean canAdd(WeekDay weekday, Time time) {
		for(CourseCombo courseCombo:this.courses){
			if(courseCombo.getWeekDay().equals(weekday) && courseCombo.getTime().equals(time)){
				return false;
			}
		}
		return true;
	}
}
