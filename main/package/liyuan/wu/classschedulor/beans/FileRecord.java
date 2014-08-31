package liyuan.wu.classschedulor.beans;

import java.util.HashSet;
import java.util.Set;

public class FileRecord {
	private final Set<ArrangePool<Teacher>> teacherArrangePools;
	private final Set<ArrangePool<Classroom>> classroomArrangePools;
	private final Set<Scheduler<Classroom>> classroomSchedulers;
	private final Set<Scheduler<Teacher>> teacherSchedulers;
	private final Set<Teacher> teachers;
	private final Set<Classroom> classrooms;
	
	public FileRecord(){
		this.teacherArrangePools = new HashSet<>();
		this.classroomArrangePools = new HashSet<>();
		this.classroomSchedulers = new HashSet<>();
		this.teacherSchedulers = new HashSet<>();
		this.teachers = new HashSet<>();
		this.classrooms = new HashSet<>();
	}
	
	public void  addTeacherArrangePool(ArrangePool<Teacher> arrangePool){
		this.teacherArrangePools.add(arrangePool);
	}
	public void  addClassroomArrangePools(ArrangePool<Classroom> arrangePool){
		this.classroomArrangePools.add(arrangePool);
	}
	public void  addClassroomScheduler(Scheduler<Classroom> classroomScheduler){
		this.classroomSchedulers.add(classroomScheduler);
	}
	public void  addTeacherScheduler(Scheduler<Teacher> teacherScheduler){
		this.teacherSchedulers.add(teacherScheduler);
	}
	public void addTeacher(Teacher teacher){
		this.teachers.add(teacher);
	}
	public void addClassroom(Classroom classroom){
		this.classrooms.add(classroom);
	}
	
	public ArrangePool<Teacher>[] getTeacherArrangePools(){
		return this.teacherArrangePools.toArray(new ArrangePool[this.teacherArrangePools.size()]);
	}
	public ArrangePool<Classroom>[] getClassroomArrangePools(){
		return this.classroomArrangePools.toArray(new ArrangePool[this.classroomArrangePools.size()]);
	}
	public Scheduler<Teacher>[] getTeacherSchedulers(){
		return this.teacherSchedulers.toArray(new Scheduler[this.teacherSchedulers.size()]);
	}
	public Scheduler<Classroom>[] getClassroomScheduler(){
		return this.classroomSchedulers.toArray(new Scheduler[this.classroomSchedulers.size()]);
	}
	public Teacher[] getTeachers(){
		return this.teachers.toArray(new Teacher[this.teachers.size()]);
	}
	public Classroom[] getClassrooms(){
		return this.classrooms.toArray(new Classroom[this.classrooms.size()]);
	}
	
 }
