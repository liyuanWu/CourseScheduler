package liyuan.wu.classschedulor.view;

import liyuan.wu.classschedulor.beans.*;

public class SingleQueryResult {

	public enum CELLATTRIBUTE{
		VACANT,EMPTY,EXCHANGABLE,UNEXCHANGABLE
	}
	private final WeekDay weekDay;
	private final Time time; 
	private final Teacher teacher;
	private final Classroom classRoom;
	private final CELLATTRIBUTE cellAttribute;
	
	public SingleQueryResult(final CourseCombo courseCombo,final CELLATTRIBUTE cellAttribute) {
		super();
		this.weekDay = courseCombo.getWeekDay();
		this.time = courseCombo.getTime();
		this.teacher = courseCombo.getTeacher();
		this.classRoom = courseCombo.getClassRoom();
		this.cellAttribute = cellAttribute;
	}
	
	public SingleQueryResult(final WeekDay weekDay,final Time time,final Teacher teacher,
			final Classroom classRoom,final CELLATTRIBUTE cellAttribute) {
		super();
		this.weekDay = weekDay;
		this.time = time;
		this.teacher = teacher;
		this.classRoom = classRoom;
		this.cellAttribute = cellAttribute;
	}

	public WeekDay getWeekDay() {
		return weekDay;
	}

	public Time getTime() {
		return time;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Classroom getClassRoom() {
		return classRoom;
	}

	public CELLATTRIBUTE getCellAttribute() {
		return cellAttribute;
	}
	
	
	
}
