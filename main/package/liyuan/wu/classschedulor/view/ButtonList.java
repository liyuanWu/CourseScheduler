package liyuan.wu.classschedulor.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;



import liyuan.wu.classschedulor.accessor.BoatAccessor;
import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;

public class ButtonList<T> extends JPanel {
	private static final long serialVersionUID = 1L;
	private final BoatAccessor<T, UnarrangedCourse> boat;
	private final BeautifulTable<T> beautifulTable;
	private final Map<UnarrangedCourse, JButton> buttonMap;
	private final ButtonListDecorator<T> buttonListDecorator;
	private final RefreshNotificationManager refreshNotificationManager;

	public ButtonList(BeautifulTable<T> table, RefreshNotificationManager refreshNotificationManager,BoatAccessor<T, UnarrangedCourse> boat,
			ButtonListDecorator<T> buttonListDecorator) {
		this.beautifulTable = table;
		this.refreshNotificationManager = refreshNotificationManager;
		this.boat = boat;
		this.buttonListDecorator = buttonListDecorator;
		this.buttonMap = new HashMap<UnarrangedCourse, JButton>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.refresh();
	}

	class UnarrangedCourseComparator implements Comparator<UnarrangedCourse>{

		@Override
		public int compare(UnarrangedCourse arg0, UnarrangedCourse arg1) {
			return arg0.getClassRoom().getGrade() * 100 + arg0.getClassRoom().getClassNumber()
					- (arg1.getClassRoom().getGrade() * 100 + arg1.getClassRoom().getClassNumber());
		}
		
	}
	
	public void refresh() {
		this.removeAllButton();
		UnarrangedCourse[] unarrangedCourses = boat.getAll();
		Arrays.sort(unarrangedCourses, new UnarrangedCourseComparator());
		for (UnarrangedCourse unarrangedCourse : unarrangedCourses) {
			this.addCourse(buttonListDecorator, unarrangedCourse, boat.numberOf(unarrangedCourse));
		}
		this.repaint();
		this.validate();
	}

	private void removeAllButton() {
		for (Entry<UnarrangedCourse, JButton> set : buttonMap.entrySet()) {
			this.remove(set.getValue());
		}

	}

	private void addCourse(ButtonListDecorator<T> buttonListDecorator, UnarrangedCourse unarrangedCourse, int number) {
		JButton button = new JButton(buttonListDecorator.decorate(unarrangedCourse, number));
		buttonMap.put(unarrangedCourse, button);
		button.addActionListener(new ButtonActionListener(unarrangedCourse));
		this.add(button);
	}

	class ButtonActionListener implements ActionListener {
		private final UnarrangedCourse unarrangedCourse;

		public ButtonActionListener(UnarrangedCourse unarrangedCourse) {
			this.unarrangedCourse = unarrangedCourse;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ButtonList.this.beautifulTable
					.hookCourse(this.unarrangedCourse);
			if(boat.getIdentifier() instanceof Teacher){
				ButtonList.this.refreshNotificationManager.showInPreview(new RefreshNotification(this.unarrangedCourse.getClassRoom(),null));
			}
			if(boat.getIdentifier() instanceof Classroom){
				ButtonList.this.refreshNotificationManager.showInPreview(new RefreshNotification(null, this.unarrangedCourse.getTeacher()));
			}
		}
	}
}

