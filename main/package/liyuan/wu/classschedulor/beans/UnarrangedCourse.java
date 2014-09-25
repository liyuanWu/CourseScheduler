package liyuan.wu.classschedulor.beans;

public class UnarrangedCourse implements Cloneable,BasicCombo{

	private final Course course;
	private final Classroom classRoom;
	private final Teacher teacher;

	public UnarrangedCourse(Course course,
			Classroom classRoom, Teacher teacher) {
		super();
		this.course = course;
		this.classRoom = classRoom;
		this.teacher = teacher;
	}
	
	public UnarrangedCourse(CourseCombo combo) {
		super();
		this.course = combo.getCourse();
		this.classRoom = combo.getClassRoom();
		this.teacher = combo.getTeacher();;
	}
	
	public CourseCombo transformToCourseCombo(WeekDay weekday, Time time){
		CourseCombo combo = new CourseCombo(weekday, time, course, classRoom, teacher);
		return combo;
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
		return classRoom.hashCode() *64 + course.hashCode()*256 + teacher.hashCode() * 512;
	}

	public boolean equals(Object obj) {
		if(obj.hashCode() == this.hashCode()){
			if (!(obj instanceof UnarrangedCourse)) {
				return false;
			}
			UnarrangedCourse unarrangedCourse = (UnarrangedCourse) obj;
			return (unarrangedCourse.getClassRoom().equals(this.getClassRoom())
					&& unarrangedCourse.getCourse().equals(this.getCourse())
					&& unarrangedCourse.getTeacher().equals(this.getTeacher()));
		}
		return false;
		
	}

	public Object clone() {
		Course course = (Course) this.course.clone();
		Classroom classRoom = (Classroom) this.classRoom.clone();
		Teacher teacher = (Teacher) this.teacher.clone();
		return new UnarrangedCourse(course, classRoom, teacher);
	}
}
