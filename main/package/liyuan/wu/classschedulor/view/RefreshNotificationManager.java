package liyuan.wu.classschedulor.view;

import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.Teacher;

import java.util.LinkedList;

public class RefreshNotificationManager {

	private final LinkedList<PanelManager<?>> panelList;
	private PanelManager<Teacher> teacherPreview;
	private PanelManager<Classroom> classroomPreview;
	
	public RefreshNotificationManager(){
		this.panelList = new LinkedList<>();
	}
	
	public void regist(PanelManager<?> classroomSchedulerPaneManager){
		if(!panelList.contains(classroomSchedulerPaneManager)){
			this.panelList.add(classroomSchedulerPaneManager);
		}
	}
	
	public void registTeacherPreview(PanelManager<Teacher> teacherPreview){
		this.teacherPreview = teacherPreview;
	}
	public void registClassroomPreview(PanelManager<Classroom> classroomPreview){
		this.classroomPreview = classroomPreview;
	}
	

	
	public void refresh(RefreshNotification refreshNotification){
			for(PanelManager<?> panelManager:this.panelList){
				if(refreshNotification.getRefreshClassroom()!=null){
					if(panelManager.getSelected()!=null && panelManager.getSelected() .equals(refreshNotification.getRefreshClassroom())){
						((PanelManager<Classroom>)panelManager).refreshPane(((PanelManager<Classroom>)panelManager).getSelected());
					}
				}
				if(refreshNotification.getRefreshTeacher()!=null){
					if(panelManager.getSelected()!=null && panelManager.getSelected().equals(refreshNotification.getRefreshTeacher())){
						((PanelManager<Teacher>)panelManager).refreshPane(((PanelManager<Teacher>)panelManager).getSelected());
					}
				}
			}
	}
	
	public void showInPreview(RefreshNotification refreshNotification){
			if(refreshNotification.getRefreshClassroom()!=null){
				if(classroomPreview!=null){
					
					classroomPreview.showPanel(refreshNotification.getRefreshClassroom(), new ButtonListDecorator.ClassroomButtonListDecorator(), new CellDecorator.ClassroomCellDecorator());
				}
			}
			if(refreshNotification.getRefreshTeacher()!=null){
				if(teacherPreview!=null){
					teacherPreview.showPanel(refreshNotification.getRefreshTeacher(), new ButtonListDecorator.TeacherButtonListDecorator(), new CellDecorator.TeacherCellDecorator());
				}
			}
	}
}
