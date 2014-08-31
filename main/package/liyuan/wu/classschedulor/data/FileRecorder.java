package liyuan.wu.classschedulor.data;

import java.io.File;

import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.FileRecord;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class FileRecorder {
	private final RecordEncoder recordEncoder;
	private final RecordDecoder recordDecoder;
	public FileRecorder(){
		this.recordEncoder = new RecordEncoder();
		this.recordDecoder = new RecordDecoder();
	}
	
	public void saveFileRecord(File saveFile, FileRecord fileRecord){
		XMLConfiguration xmlConfiguration = new XMLConfiguration();
		xmlConfiguration.addNodes("teachers", recordEncoder.getTeachersNode(fileRecord));
		xmlConfiguration.addNodes("classrooms", recordEncoder.getclassroomsNode(fileRecord));
		xmlConfiguration.addNodes("arrangePool", recordEncoder.getArrangePoolNode(fileRecord));
		xmlConfiguration.addNodes("scheduler", recordEncoder.getSchedulerNode(fileRecord));
		try {
			xmlConfiguration.setEncoding("UTF-8");
			xmlConfiguration.save(saveFile);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public FileRecord loadFileRecord(File loadFile){
		return recordDecoder.readRecordFromFile(loadFile);
	}
	
	
	
}
