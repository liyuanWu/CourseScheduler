package liyuan.wu.classschedulor.view;

import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;

public interface ButtonListDecorator<T> {
	public String decorate(UnarrangedCourse unarrangedCourse, int number);
	
	class TeacherButtonListDecorator implements ButtonListDecorator<Teacher>{

		@Override
		public String decorate(UnarrangedCourse unarrangedCourse, int number) {
			return unarrangedCourse.getClassRoom().toString()+ "-"
					+ unarrangedCourse.getCourse().getName() + "-" + number;
		}
		
	}
	class ClassroomButtonListDecorator implements ButtonListDecorator<Classroom>{

		@Override
		public String decorate(UnarrangedCourse unarrangedCourse, int number) {
			return unarrangedCourse.getTeacher().getName() + "-"
					+ unarrangedCourse.getCourse().getName() + "-" + number;
		}
		
	}
}
