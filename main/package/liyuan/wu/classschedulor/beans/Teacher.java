package liyuan.wu.classschedulor.beans;

import java.util.Comparator;

public class Teacher implements Cloneable{

	private final String name;
	
	public Teacher(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean equals(Object teacher){
		if(teacher instanceof Teacher){
			if(teacher.hashCode() == this.hashCode()){
				if(this.name.equals(((Teacher)teacher).getName())){
					return true;
				}
			}
		}
		return false;
	}
	
	public Object clone(){
		return new Teacher(this.name);
	}
	public int hashCode(){
		return this.name.hashCode();
	}
	public String toString(){
		return this.getName();
	}
	public static class TeacherComparator implements Comparator<Teacher>{
		@Override
		public int compare(Teacher o1, Teacher o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
