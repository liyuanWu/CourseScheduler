package liyuan.wu.classschedulor.view;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import liyuan.wu.classschedulor.accessor.BoatAccessor;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;

public class Panel<T> extends JSplitPane {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private final BoatAccessor<T,CourseCombo> scheduler;
	private final BeautifulTable<T> table;
	private final ButtonList<T> buttonList;
	private static final int height = 200;

	public Panel(RefreshNotificationManager refreshNotificaitonManager, BoatAccessor<T,CourseCombo> scheduler,
			BoatAccessor<T,UnarrangedCourse> arrangePool,ButtonListDecorator<T> listDecorator, CellDecorator<T> cellDecorator) {
		this.scheduler = scheduler;
		this.table = new BeautifulTable<T>(height,cellDecorator,refreshNotificaitonManager,scheduler,arrangePool);
		this.buttonList = new ButtonList<T>(this.table,refreshNotificaitonManager,arrangePool,listDecorator);
		this.init(scheduler);
	}

	private final void init(BoatAccessor<T,CourseCombo>  scheduler) {
		JScrollPane tablePane = new JScrollPane();
		tablePane.setViewportView(this.table);
		JScrollPane buttonPane = new JScrollPane();
		buttonPane.setViewportView(this.buttonList);
		this.setLeftComponent(tablePane);
		this.setRightComponent(buttonPane);
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
			@Override
			public void componentResized(ComponentEvent arg0) {
				setDividerLocation(getWidth()-130);
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {
			}
			@Override
			public void componentHidden(ComponentEvent arg0) {
			}
		});
	}
	public void switchContent(){
		Component left = this.getLeftComponent();
		this.setLeftComponent(this.getRightComponent());
		this.setRightComponent(left);
	}

	public T getIdentifier() {
		if (this.scheduler != null) {
			return scheduler.getIdentifier();
		}
		return null;
	}

	public void refresh() {
		table.refresh();
		buttonList.refresh();
	}
}
