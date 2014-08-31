package liyuan.wu.classschedulor.view;

import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.Teacher;

public class RefreshNotification {

	private final Classroom refreshClassroom;
	private final Teacher refreshTeacher;
	public RefreshNotification(Classroom refreshClassroom,
			Teacher refreshTeacher) {
		super();
		this.refreshClassroom = refreshClassroom;
		this.refreshTeacher = refreshTeacher;
	}
	public Classroom getRefreshClassroom() {
		return refreshClassroom;
	}
	public Teacher getRefreshTeacher() {
		return refreshTeacher;
	}
	
}
