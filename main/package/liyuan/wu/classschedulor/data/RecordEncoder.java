package liyuan.wu.classschedulor.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import liyuan.wu.classschedulor.beans.ArrangePool;
import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.FileRecord;
import liyuan.wu.classschedulor.beans.Scheduler;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;

import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.DefaultConfigurationNode;

public class RecordEncoder {
	public  Collection<ConfigurationNode> getTeachersNode(FileRecord fileRecord){
		List<ConfigurationNode> nodeList = new LinkedList<ConfigurationNode>();
		for(Teacher teacher:fileRecord.getTeachers()){
			ConfigurationNode teacherNode = new DefaultConfigurationNode();
			teacherNode.setName("name");
			teacherNode.setValue(teacher.getName());
			nodeList.add(teacherNode);
		}
		return nodeList;
	}
	public  Collection<ConfigurationNode> getclassroomsNode(FileRecord fileRecord){
		List<ConfigurationNode> nodeList = new LinkedList<ConfigurationNode>();
		for(Classroom classroom:fileRecord.getClassrooms()){
			ConfigurationNode classroomNode = new DefaultConfigurationNode();
			classroomNode.setName("classroom");
			classroomNode.setValue(classroom.getGrade()+"/"+classroom.getClassNumber());
			nodeList.add(classroomNode);
		}
		return nodeList;
	}
	public  Collection<ConfigurationNode> getSchedulerNode(FileRecord fileRecord){
		List<ConfigurationNode> nodeList = new LinkedList<ConfigurationNode>();
		for(Scheduler<Classroom> schedulers:fileRecord.getClassroomScheduler()){
			ConfigurationNode classroomNode = new DefaultConfigurationNode();
			classroomNode.setName("classroom");
			
			DefaultConfigurationNode grade = new DefaultConfigurationNode();
			grade.setName("grade");
			grade.setValue(schedulers.getIdentifier().getGrade());
			classroomNode.addChild(grade);
			
			DefaultConfigurationNode classNumber = new DefaultConfigurationNode();
			classNumber.setName("classNumber");
			classNumber.setValue(schedulers.getIdentifier().getClassNumber());
			classroomNode.addChild(classNumber);
			
			for(CourseCombo unarrangeNode:schedulers.getAll()){
				classroomNode.addChild(getCourseComboNode(unarrangeNode));
			}

			nodeList.add(classroomNode);
		}
		return nodeList;
	}
	
	public  Collection<ConfigurationNode> getArrangePoolNode(FileRecord fileRecord){
		List<ConfigurationNode> nodeList = new LinkedList<ConfigurationNode>();
		for(ArrangePool<Classroom> arrangePool:fileRecord.getClassroomArrangePools()){
			ConfigurationNode classroomNode = new DefaultConfigurationNode();
			classroomNode.setName("classroom");
			
			DefaultConfigurationNode grade = new DefaultConfigurationNode();
			grade.setName("grade");
			grade.setValue(arrangePool.getIdentifier().getGrade());
			classroomNode.addChild(grade);
			
			DefaultConfigurationNode classNumber = new DefaultConfigurationNode();
			classNumber.setName("classNumber");
			classNumber.setValue(arrangePool.getIdentifier().getClassNumber());
			classroomNode.addChild(classNumber);
			
			for(UnarrangedCourse unarrangeNode:arrangePool.getAll()){
				classroomNode.addChild(getUnarrangedCourseNode(unarrangeNode,arrangePool.numberOf(unarrangeNode)));
			}

			nodeList.add(classroomNode);
		}
		return nodeList;
	}
	public ConfigurationNode getCourseComboNode(CourseCombo courseCombo){
		DefaultConfigurationNode resultNode = new DefaultConfigurationNode();
		resultNode.setName("course");
		
		DefaultConfigurationNode courseName = new DefaultConfigurationNode();
		courseName.setName("name");
		courseName.setValue(courseCombo.getCourse().getName());
		DefaultConfigurationNode teacher = new DefaultConfigurationNode();
		teacher.setName("teacher");
		teacher.setValue(courseCombo.getTeacher().getName());
		DefaultConfigurationNode classroom = new DefaultConfigurationNode();
		classroom.setName("classroom");
		classroom.setValue(courseCombo.getClassRoom().getGrade()+"/"+courseCombo.getClassRoom().getClassNumber());
		DefaultConfigurationNode weekday = new DefaultConfigurationNode();
		weekday.setName("weekDay");
		weekday.setValue(courseCombo.getWeekDay().getWeekDayInteger());
		DefaultConfigurationNode time = new DefaultConfigurationNode();
		time.setName("time");
		time.setValue(courseCombo.getTime().getTime());
		
		resultNode.addChild(courseName);
		resultNode.addChild(teacher);
		resultNode.addChild(classroom);
		resultNode.addChild(weekday);
		resultNode.addChild(time);
		return resultNode;
	}
	public ConfigurationNode getUnarrangedCourseNode(UnarrangedCourse unarrangeNode, int number){
		DefaultConfigurationNode resultNode = new DefaultConfigurationNode();
		resultNode.setName("course");
		
		DefaultConfigurationNode courseName = new DefaultConfigurationNode();
		courseName.setName("name");
		courseName.setValue(unarrangeNode.getCourse().getName());
		DefaultConfigurationNode teacher = new DefaultConfigurationNode();
		teacher.setName("teacher");
		teacher.setValue(unarrangeNode.getTeacher().getName());
		DefaultConfigurationNode classroom = new DefaultConfigurationNode();
		classroom.setName("classroom");
		classroom.setValue(unarrangeNode.getClassRoom().getGrade()+"/"+unarrangeNode.getClassRoom().getClassNumber());
		DefaultConfigurationNode count = new DefaultConfigurationNode();
		count.setName("count");
		count.setValue(number);
		
		resultNode.addChild(courseName);
		resultNode.addChild(teacher);
		resultNode.addChild(classroom);
		resultNode.addChild(count);
		return resultNode;
	}
}
