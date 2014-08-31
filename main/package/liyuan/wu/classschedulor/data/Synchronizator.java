package liyuan.wu.classschedulor.data;

import liyuan.wu.classschedulor.beans.BasicCombo;
import liyuan.wu.classschedulor.beans.Boat;
import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.Time;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;
import liyuan.wu.classschedulor.beans.WeekDay;
import liyuan.wu.classschedulor.view.QueryResult;
import liyuan.wu.classschedulor.view.SingleQueryResult;
import liyuan.wu.classschedulor.view.SingleQueryResult.CELLATTRIBUTE;

public class Synchronizator <X extends BasicCombo>{
	private final Pool<Classroom, ? extends Boat<Classroom,X>> classroomPool;
	private final Pool<Teacher, ? extends Boat<Teacher,X>> teacherPool;
	public Synchronizator(
			Pool<Classroom, ? extends Boat<Classroom,X>> classroomPool,
			Pool<Teacher, ? extends Boat<Teacher,X>> teacherPool) {
		this.classroomPool = classroomPool;
		this.teacherPool = teacherPool;
	}

	public void sychronizeAdd(Boat<?,X> invoker, X x){
		Boat<Classroom,X> classroomArrangePool = classroomPool.getBoat(x.getClassRoom());
		if(classroomArrangePool!=null && !classroomArrangePool.equals(invoker)){
			classroomArrangePool.add(x);
		}
		Boat<Teacher, X> teacherArrangePool = teacherPool.getBoat(x.getTeacher());
		if(teacherArrangePool!=null && !teacherArrangePool.equals(invoker)){
			teacherArrangePool.add(x);
		}
	}
	
	public Boat<Teacher, X> getBoat(Teacher teacher){
		return teacherPool.getBoat(teacher);
	}
	
	public Boat<Classroom, X> getBoat(Classroom classroom){
		return classroomPool.getBoat(classroom);
	}
	
	public boolean canAdd(CourseCombo courseCombo){
		return classroomPool.getBoat(courseCombo.getClassRoom()).canAdd(courseCombo.getWeekDay(), courseCombo.getTime()) &&
				(courseCombo.getTeacher().getName().equals("_universe")||
				(teacherPool.getBoat(courseCombo.getTeacher()).canAdd(courseCombo.getWeekDay(), courseCombo.getTime())));
	}
	
	public void sychronizeDelete(Boat<?,X> invoker, X x){
		Boat<Classroom,X> classroomArrangePool = classroomPool.getBoat(x.getClassRoom());
		if(classroomArrangePool!=null && !classroomArrangePool.equals(invoker)){
			classroomArrangePool.remove(x);
		}
		Boat<Teacher,X> teacherArrangePool = teacherPool.getBoat(x.getTeacher());
		if(teacherArrangePool!=null && !teacherArrangePool.equals(invoker)){
			teacherArrangePool.remove(x);
		}
	}

	public QueryResult query(UnarrangedCourse unarrangedCourse) {
		QueryResult queryResult = new QueryResult();
		for(int i=0;i<5;i++){
			for(int j=0;j<6;j++){
				// (classroom has space) && (teacher has space(unless teacher =  _universe))
				if(classroomPool.getBoat(unarrangedCourse.getClassRoom()).canAdd(new WeekDay(i), new Time(j)) 
						&&((unarrangedCourse.getTeacher().getName().equals("_universe"))
								|| (teacherPool.getBoat(unarrangedCourse.getTeacher()).canAdd(new WeekDay(i), new Time(j))))){
					queryResult.addQueryResult(new SingleQueryResult(new WeekDay(i), new Time(j), unarrangedCourse.getTeacher(),unarrangedCourse.getClassRoom(),CELLATTRIBUTE.EMPTY));
				}else{
					queryResult.addQueryResult(new SingleQueryResult(new WeekDay(i), new Time(j), unarrangedCourse.getTeacher(),unarrangedCourse.getClassRoom(),CELLATTRIBUTE.UNEXCHANGABLE));
					
				}
			}
		}
		return queryResult;
	}
}
