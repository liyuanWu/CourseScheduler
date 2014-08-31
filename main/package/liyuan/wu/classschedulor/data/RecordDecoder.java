package liyuan.wu.classschedulor.data;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import liyuan.wu.classschedulor.beans.ArrangePool;
import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.Course;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.FileRecord;
import liyuan.wu.classschedulor.beans.Scheduler;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.Time;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;
import liyuan.wu.classschedulor.beans.WeekDay;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

public class RecordDecoder {
	private final Map<Course,Course> coursePool;
	private final Map<Teacher,Teacher> teacherPool;
	private final Map<WeekDay,WeekDay> weekDayPool;
	private final Map<Time,Time> timePool;
	private final Map<Classroom,Classroom> classroomPool;
	public RecordDecoder(){
		this.coursePool = new HashMap<Course,Course>();
		this.teacherPool = new HashMap<Teacher,Teacher>();
		this.weekDayPool = new HashMap<WeekDay,WeekDay>();
		this.timePool = new HashMap<Time,Time>();
		this.classroomPool = new HashMap<Classroom,Classroom>();
	}
	public FileRecord readRecordFromFile(File file){
		XMLConfiguration config = new XMLConfiguration();
		try {
			config.load(file);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		FileRecord fileRecord = new FileRecord();
		List<Object> teacherConfig = config.getList("teachers.name");
		for(Teacher teacher:getTeachers(teacherConfig)){
			fileRecord.addTeacher(teacher);
		}
		List<Object> classroomConfig = config.getList("classrooms.classroom");
		for(Classroom classroom:getClassroom(classroomConfig)){
			fileRecord.addClassroom(classroom);
		}
		SubnodeConfiguration arrangePoolConfig = config.configurationAt("arrangePool");
		Map<Teacher,ArrangePool<Teacher>> teacherMap = new HashMap<Teacher,ArrangePool<Teacher>> ();
		for(ArrangePool<Classroom> arrangePool:getClassroomArrangePool(arrangePoolConfig.configurationsAt("classroom"))){
			fileRecord.addClassroomArrangePools(arrangePool);
			for(UnarrangedCourse unarrangedCourse:arrangePool.getAll()){
				if(!teacherMap.containsKey(unarrangedCourse.getTeacher())){
					teacherMap.put(unarrangedCourse.getTeacher(), new ArrangePool<Teacher>(unarrangedCourse.getTeacher()));
				}
				for(int i=0;i<arrangePool.numberOf(unarrangedCourse);i++){
					teacherMap.get(unarrangedCourse.getTeacher()).add(unarrangedCourse);
				}
			}
		}
		for(Entry<Teacher, ArrangePool<Teacher>> entry:teacherMap.entrySet()){
			fileRecord.addTeacherArrangePool(entry.getValue());
		}
		if(config.configurationsAt("scheduler").size()!=0){
			SubnodeConfiguration schedulerConfig = config.configurationAt("scheduler");
			Map<Teacher,Scheduler<Teacher>> teacherSchedulerMap = new HashMap<Teacher,Scheduler<Teacher>> ();
			for(Scheduler<Classroom> schedulers:getClassroomScheduler(schedulerConfig.configurationsAt("classroom"))){
				fileRecord.addClassroomScheduler(schedulers);
				for(CourseCombo course:schedulers.getAll()){
					if(!teacherSchedulerMap.containsKey(course.getTeacher())){
						teacherSchedulerMap.put(course.getTeacher(), new Scheduler<Teacher>(course.getTeacher()));
					}
					teacherSchedulerMap.get(course.getTeacher()).add(course);
				}
			}
			for(Entry<Teacher, Scheduler<Teacher>> entry:teacherSchedulerMap.entrySet()){
				fileRecord.addTeacherScheduler(entry.getValue());
			}

		}
	
		return fileRecord;
	}
	private Set<Teacher> getTeachers(List<Object> list){
		Set<Teacher> resultSet = new HashSet<Teacher>();
		for(Object config:list){
			String teacherName = config.toString();
			if(teacherName==null||teacherName.equals("")){
				continue;
			}
			Teacher teacher = new Teacher(teacherName);
			if(teacherPool.containsKey(teacher)){
				teacher = teacherPool.get(teacher);
			}else{
				teacherPool.put(teacher, teacher);
			}
			resultSet.add(teacher);
		}
		return resultSet;
	}
	private Set<Classroom> getClassroom(List<Object> classroomConfig){
		Set<Classroom> resultSet = new HashSet<Classroom>();
		for(Object config:classroomConfig){
			String classroomString = config.toString();
			if(classroomString==null||classroomString.equals("")){
				continue;
			}
			int classGrade,classNumber;
			try{
			classGrade = Integer.parseInt(classroomString.split("/")[0]);
			classNumber = Integer.parseInt(classroomString.split("/")[1]);
			}catch(Throwable t){
				break;
			}
			Classroom classroom = new Classroom(classGrade,classNumber);
			if(classroomPool.containsKey(classroom)){
				classroom = classroomPool.get(classroom);
			}else{
				classroomPool.put(classroom, classroom);
			}
			resultSet.add(classroom);
		}
		return resultSet;
	}
	private Set<Scheduler<Classroom>> getClassroomScheduler(List<HierarchicalConfiguration> list){
		Set<Scheduler<Classroom>> resultSet = new HashSet<Scheduler<Classroom>>();
		for(HierarchicalConfiguration config:list){
			int grade = Integer.parseInt(config.getString("grade"));
			int classNumber = Integer.parseInt(config.getString("classNumber"));
			Classroom classroom = new Classroom(grade,classNumber);
			if(classroomPool.containsKey(classroom)){
				classroom = classroomPool.get(classroom);
			}else{
				classroomPool.put(classroom, classroom);
			}
			
			Scheduler<Classroom> classroomScheduler = new Scheduler<>(classroom);
			List<HierarchicalConfiguration> courseConfigs = config.configurationsAt("course");
			for(HierarchicalConfiguration courseConfig : courseConfigs){
				String courseName = courseConfig.getString("name");
				String teacherName = courseConfig.getString("teacher");
				String weekDayString = courseConfig.getString("weekDay");
				String timeString = courseConfig.getString("time");
				if(courseName==null || teacherName==null||weekDayString==null||timeString==null){
					break;
				}
				if(courseName.equals("")||teacherName.equals("")||weekDayString.equals("")||timeString.equals("")){
					break;
				}
				int weekDay,time;
				try{
				weekDay = Integer.parseInt(weekDayString);
				time = Integer.parseInt(timeString);
				}catch(Throwable t){
					break;
				}
				Teacher teacher = new Teacher(teacherName);
				if(teacherPool.containsKey(teacher)){
					teacher = teacherPool.get(teacher);
				}else{
					teacherPool.put(teacher, teacher);
				}
				Course course = new Course(courseName);
				if(coursePool.containsKey(course)){
					course = coursePool.get(course);
				}else{
					coursePool.put(course, course);
				}
				WeekDay weekDayObj = new WeekDay(weekDay);
				if(weekDayPool.containsKey(weekDayObj)){
					weekDayObj = weekDayPool.get(weekDayObj);
				}else{
					weekDayPool.put(weekDayObj, weekDayObj);
				}
				Time timeObj = new Time(time);
				if(timePool.containsKey(timeObj)){
					timeObj = timePool.get(timeObj);
				}else{
					timePool.put(timeObj, timeObj);
				}
				CourseCombo courseCombo = new CourseCombo(weekDayObj, timeObj, course,classroom,teacher);
				classroomScheduler.add(courseCombo);
			}
			resultSet.add(classroomScheduler);
		}
		return resultSet;
	}
	private Set<ArrangePool<Classroom>> getClassroomArrangePool(List<HierarchicalConfiguration> list){
		Set<ArrangePool<Classroom>> resultSet = new HashSet<ArrangePool<Classroom>>();
		for(HierarchicalConfiguration config:list){
			int grade = Integer.parseInt(config.getString("grade"));
			int classNumber = Integer.parseInt(config.getString("classNumber"));
			Classroom classroom = new Classroom(grade,classNumber);

			if(classroomPool.containsKey(classroom)){
				classroom = classroomPool.get(classroom);
			}else{
				classroomPool.put(classroom, classroom);
			}
			ArrangePool<Classroom> arrangePool = new ArrangePool<Classroom>(classroom);
			List<HierarchicalConfiguration> courseConfigs = config.configurationsAt("course");
			for(HierarchicalConfiguration courseConfig : courseConfigs){
				String courseName = courseConfig.getString("name");
				String teacherName = courseConfig.getString("teacher");
				String countString = courseConfig.getString("count");
				if(courseName==null || teacherName==null||countString==null){
					break;
				}
				if(courseName.equals("")||teacherName.equals("")||countString.equals("")){
					break;
				}
				int count;
				try{
					count = Integer.parseInt(countString);
				}catch(Throwable t){
					break;
				}
				Teacher teacher = new Teacher(teacherName);
				if(teacherPool.containsKey(teacher)){
					teacher = teacherPool.get(teacher);
				}else{
					teacherPool.put(teacher, teacher);
				}
				Course course = new Course(courseName);
				if(coursePool.containsKey(course)){
					course = coursePool.get(course);
				}else{
					coursePool.put(course, course);
				}
				UnarrangedCourse unarrangedCourse = new UnarrangedCourse(course,classroom,teacher);
				for(int i=0;i<count;i++){
					arrangePool.add(unarrangedCourse);
				}
			}
			resultSet.add(arrangePool);
		}
		return resultSet;
	}
}
