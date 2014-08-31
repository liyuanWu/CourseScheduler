package liyuan.wu.classschedulor.beans;

public class Course implements Cloneable{
	private final String course;
	public Course(String course){
		this.course = course;
	}
	public String getName() {
		return course;
	}
	public Object clone(){
		return new Course(this.course);
	}
	public boolean equals(Object obj){
		if(obj instanceof Course && ((Course)obj).getName().equals(this.getName())){
			return true;
		}
		return false;
	}
	public int hashCode(){
		return this.course.hashCode();
	}
}
