package liyuan.wu.classschedulor.view;

import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.Teacher;


public interface CellDecorator<T>{
	public String decorate(CourseCombo courseCombo);
	class TeacherCellDecorator implements CellDecorator<Teacher>{
		@Override
		public String decorate(CourseCombo courseCombo) {
			return courseCombo.getCourse().getName()+"\r\n"+courseCombo.getClassRoom().getGrade()+"-"+courseCombo.getClassRoom().getClassNumber();
		}
	}

	class ClassroomCellDecorator implements CellDecorator<Classroom>{
		@Override
		public String decorate(CourseCombo courseCombo) {
			return courseCombo.getCourse().getName()+"\r\n"+
		(courseCombo.getTeacher().getName().equals("_universe")?"":courseCombo.getTeacher().getName());
		}
	}
}


