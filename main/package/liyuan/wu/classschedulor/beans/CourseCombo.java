package liyuan.wu.classschedulor.beans;


public class CourseCombo implements Cloneable,BasicCombo{

	private final WeekDay weekDay;
	private final Time time;
	private final Course course;
	private final Classroom classRoom;
	private final Teacher teacher;

	public CourseCombo(WeekDay weekDay, Time time, Course course,
			Classroom classRoom, Teacher teacher) {
		super();
		this.weekDay = weekDay;
		this.time = time;
		this.course = course;
		this.classRoom = classRoom;
		this.teacher = teacher;
	}

	public WeekDay getWeekDay() {
		return weekDay;
	}

	public Time getTime() {
		return time;
	}

	public Course getCourse() {
		return course;
	}

	public Classroom getClassRoom() {
		return classRoom;
	}

	public Teacher getTeacher() {
		return teacher;
	}
	
	public int hashCode(){
		return weekDay.hashCode() + time.hashCode()*8 + classRoom.hashCode() *64 + course.hashCode()*256 + teacher.hashCode() * 512;
	}

	public boolean equals(Object obj) {
		if(obj.hashCode() == this.hashCode()){
			if (!(obj instanceof CourseCombo)) {
				return false;
			}
			CourseCombo courseCombo = (CourseCombo) obj;
			return (courseCombo.getWeekDay().equals(this.getWeekDay())
					&& courseCombo.getClassRoom().equals(this.getClassRoom())
					&& courseCombo.getCourse().equals(this.getCourse())
					&& courseCombo.getTeacher().equals(this.getTeacher()) && courseCombo
					.getTime().equals(this.getTime()));
		}
		return false;
		
	}

	public Object clone() {
		WeekDay weekDay = (WeekDay) this.weekDay.clone();
		Time time = (Time) this.time.clone();
		Course course = (Course) this.course.clone();
		Classroom classRoom = (Classroom) this.classRoom.clone();
		Teacher teacher = (Teacher) this.teacher.clone();
		return new CourseCombo(weekDay, time, course, classRoom, teacher);
	}

}
