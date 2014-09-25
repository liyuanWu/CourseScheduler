package liyuan.wu.classschedulor.beans;

import java.util.Comparator;

public class Classroom implements Cloneable {

	private final int grade;
	private final int classNumber;

	public Classroom(int grade, int classNumber) {
		this.grade = grade;
		this.classNumber = classNumber;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public int getGrade() {
		return grade;
	}
	
	public String toString(){
		return grade+"\u5e74\u7ea7"+classNumber;
		}

	public Object clone() {
		return new Classroom(this.grade, this.classNumber);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Classroom
				&& ((Classroom) obj).getGrade() == this.grade
				&& ((Classroom) obj).getClassNumber() == this.getClassNumber()) {
			return true;
		}
		return false;
	}
	public int hashCode(){
		return this.grade + this.classNumber*6;
	}
	
	public static class ClassroomComparator implements Comparator<Classroom>{
		@Override
		public int compare(Classroom o1, Classroom o2) {
			return o1.getGrade() * 10000 + o1.getClassNumber()
					- (o2.getGrade() * 10000 + o2.getClassNumber());
		}
	}
}
