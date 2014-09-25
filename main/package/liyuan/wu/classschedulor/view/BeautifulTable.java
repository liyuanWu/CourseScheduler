package liyuan.wu.classschedulor.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import liyuan.wu.classschedulor.accessor.BoatAccessor;
import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.Time;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;
import liyuan.wu.classschedulor.beans.WeekDay;
import liyuan.wu.classschedulor.view.SingleQueryResult.CELLATTRIBUTE;

public class BeautifulTable<T> extends JTable {

	private static final Map<CELLATTRIBUTE, Color> COLORMAP = new HashMap<CELLATTRIBUTE, Color>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(CELLATTRIBUTE.EMPTY, Color.green);
			put(CELLATTRIBUTE.EXCHANGABLE, Color.orange);
			put(CELLATTRIBUTE.UNEXCHANGABLE, Color.red);
		}
	};
	private final BoatAccessor<T,CourseCombo> scheduler;
	private final BoatAccessor<T,UnarrangedCourse> arrangePool;
	private final BeautifulTableRender tableRender = new BeautifulTableRender();
	private final Map<Point, Color> cellColors = new HashMap<Point, Color>();
	private final Queue<UnarrangedCourse> hookCourses = new LinkedList<UnarrangedCourse>();
	private final RefreshNotificationManager refreshNotificaitonManager;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rowNumber = 10;
	private final CellDecorator<T> cellDecorator;

	public BeautifulTable(int totalHeight, final CellDecorator<T> cellDecorator,
			final RefreshNotificationManager refreshNotificaitonManager,
			final BoatAccessor<T,CourseCombo> scheduler, final BoatAccessor<T,UnarrangedCourse> arrangePool) {
		this.scheduler = scheduler;
		this.arrangePool = arrangePool;
		this.cellDecorator = cellDecorator;
		this.refreshNotificaitonManager = refreshNotificaitonManager;
		this.setRowHeight(totalHeight / rowNumber);
		this.setColumnSelectionAllowed(true);

		this.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null},
				{ null, null, null, null, null},
				{ null, null, null, null, null},
				{ null, null, null, null, null},
				{ null, null, null, null, null},
				{ null, null, null, null, null}, }, new String[] {
				"\u5468\u4E00", "\u5468\u4E8C", "\u5468\u4E09", "\u5468\u56DB",
				"\u5468\u4E94"}));

		for (int i = 0; i < this.getColumnCount(); i++) {
			TableColumn column = this.getTableHeader().getColumnModel()
					.getColumn(i);
			DefaultTableCellRenderer cellRenderer = tableRender;
			cellRenderer.setHorizontalAlignment(JLabel.CENTER);
			column.setHeaderRenderer(cellRenderer);
			column.setCellRenderer(cellRenderer);
		}

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					if(hookCourses.isEmpty()){
						for (int i = 0; i < BeautifulTable.this.getColumnCount(); i++) {
							for (int j = 0; j < BeautifulTable.this.getRowCount(); j++) {
//								System.out.print(e.getPoint() + "\t" + i + "\t" + j);
								if (BeautifulTable.this.getCellRect(j, i, true)
										.contains(e.getPoint())) {
									for (CourseCombo course : BeautifulTable.this.scheduler
											.getAll()) {
										if (course.getWeekDay().equals(
												new WeekDay(i))
												&& course.getTime().equals(
														new Time(j))) {
											BeautifulTable.this.scheduler
													.remove(course);
											BeautifulTable.this.arrangePool
													.add(new UnarrangedCourse(
															course));
											BeautifulTable.this.refreshNotificaitonManager
													.refresh(new RefreshNotification(
															course.getClassRoom(),
															course.getTeacher()));
											return;
										}
									}
								}
							}
						}
					}else{
						hookCourses.clear();
						removeAllColors();
					}
				} else {
					if (hookCourses.isEmpty()) {
						for (int i = 0; i < BeautifulTable.this.getColumnCount(); i++) {
							for (int j = 0; j < BeautifulTable.this.getRowCount(); j++) {
								if (BeautifulTable.this.getCellRect(j, i, true)
										.contains(e.getPoint())) {
									for(CourseCombo course:BeautifulTable.this.scheduler.getAll()){
										if(course.getWeekDay().getWeekDayInteger() == i && course.getTime().getTime() == j){
											if(scheduler.getIdentifier() instanceof Teacher){
												BeautifulTable.this.refreshNotificaitonManager.showInPreview(new RefreshNotification(course.getClassRoom(),null));
											}
											if(scheduler.getIdentifier() instanceof Classroom){
												BeautifulTable.this.refreshNotificaitonManager.showInPreview(new RefreshNotification(null, course.getTeacher()));
											}
											
										}
									}
								}
							}
						}
						return;
					}
					removeAllColors();
					for (int i = 0; i < BeautifulTable.this.getColumnCount(); i++) {
						for (int j = 0; j < BeautifulTable.this.getRowCount(); j++) {
							if (BeautifulTable.this.getCellRect(j, i, true)
									.contains(e.getPoint())) {

								UnarrangedCourse unarrangedCourse = hookCourses
											.poll();
									CourseCombo courseCombo = new CourseCombo(
											new WeekDay(i), new Time(j),
											unarrangedCourse.getCourse(),
											unarrangedCourse.getClassRoom(),
											unarrangedCourse.getTeacher());
									if(BeautifulTable.this.scheduler.canAdd(courseCombo)){
										BeautifulTable.this.scheduler.add(courseCombo);
										BeautifulTable.this.arrangePool
												.remove(unarrangedCourse);
										BeautifulTable.this.refreshNotificaitonManager
												.refresh(new RefreshNotification(
														courseCombo.getClassRoom(),
														courseCombo.getTeacher()));
										if(BeautifulTable.this.arrangePool.numberOf(unarrangedCourse)>0){
											BeautifulTable.this.hookCourse(unarrangedCourse);
										}
									}else{
//										CellPopupMenu<T> cellPopupMenu = new CellPopupMenu<>(BeautifulTable.this.scheduler, BeautifulTable.this.arrangePool, BeautifulTable.this.refreshNotificaitonManager,courseCombo);
//										cellPopupMenu.show(e.getComponent(), e.getX(), e.getY());
									}
									return;
								
							}
						}
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
	}

	public void refresh() {
		this.clearTable();
		for (CourseCombo combo : this.scheduler.getAll()) {
			this.addContent(combo, this.cellDecorator);
		}
	}

	private void addContent(CourseCombo combo, CellDecorator<T> cellDecorator) {
		WeekDay weekDay = combo.getWeekDay();
		Time time = combo.getTime();
		this.setValueAt(cellDecorator.decorate(combo),time.getTime(),
				weekDay.getWeekDayInteger()) ;
		// System.out.println("Set Teacher Table value:" + this.getValueAt(
		// time.getTime(), weekDay.getWeekDayInteger()+1));
	}

	public void hookCourse(UnarrangedCourse unarrangedCourse) {
		this.hookCourses.clear();
		this.hookCourses.add(unarrangedCourse);
		this.refreshCellsByQueryResults(this.scheduler.query(unarrangedCourse));
	}
	
	public void removeAllColors(){
		this.cellColors.clear();
		this.repaint();
		this.revalidate();
	}

	public void refreshCellsByQueryResults(QueryResult queryResult) {
		for (final SingleQueryResult singleQueryResult : queryResult
				.getSingleQueryResults()) {
			int column = singleQueryResult.getWeekDay().getWeekDayInteger();
			int row = singleQueryResult.getTime().getTime();
			this.cellColors.put(new Point(column, row),
					COLORMAP.get(singleQueryResult.getCellAttribute()));
		}
		this.repaint();
	}

	protected void clearTable() {
		for (int i = 0; i < this.getColumnCount(); i++) {
			for (int j = 0; j < this.getRowCount(); j++) {
				this.setValueAt(null, j, i);
			}
		}
	}
	
	class CellPopupMenu<T> extends JPopupMenu{
		BoatAccessor<T,CourseCombo> scheduler;
		BoatAccessor<T,UnarrangedCourse> arrangePool;
		CourseCombo toInsert;
		Set<CourseCombo> toDeletes;
		RefreshNotificationManager refreshNotificationManager;
		public CellPopupMenu(BoatAccessor<T,CourseCombo> scheduler,BoatAccessor<T,UnarrangedCourse> arrangePool,RefreshNotificationManager refreshNotificationManager,CourseCombo toInsert){
			this.scheduler = scheduler;
			this.arrangePool = arrangePool;
			this.toInsert = toInsert;
			this.refreshNotificationManager = refreshNotificationManager;

			this.toDeletes = new HashSet<CourseCombo>();
			for(CourseCombo courseCombo:CellPopupMenu.this.scheduler.getAll()){
				if(courseCombo.getWeekDay().equals(CellPopupMenu.this.toInsert.getWeekDay()) && courseCombo.getTime().equals(CellPopupMenu.this.toInsert.getTime())){
					toDeletes.add(courseCombo);
					break;
				}
			}
//			CellPopupMenu.this.scheduler.getSynchronizator().getBoat(toIn)
			JMenuItem remove = new JMenuItem("ÒÆ³ý");
			remove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CourseCombo toDelete = null;
					for(CourseCombo courseCombo:CellPopupMenu.this.scheduler.getAll()){
						if(courseCombo.getWeekDay().equals(CellPopupMenu.this.toInsert.getWeekDay()) && courseCombo.getTime().equals(CellPopupMenu.this.toInsert.getTime())){
							toDelete = courseCombo;
							break;
						}
					}
					CellPopupMenu.this.scheduler.remove(toDelete);
					CellPopupMenu.this.scheduler.add(CellPopupMenu.this.toInsert);
					CellPopupMenu.this.arrangePool.remove(new UnarrangedCourse(CellPopupMenu.this.toInsert));
					CellPopupMenu.this.refreshNotificationManager.refresh(new RefreshNotification(
							CellPopupMenu.this.toInsert.getClassRoom(),
							CellPopupMenu.this.toInsert.getTeacher()));
				}
			});
			this.add(remove);
	
		}
	}

	class BeautifulTableRender extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JComponent comp = (JComponent) super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column);
			comp.setForeground(new Color(51, 51, 153));
			comp.setFont(new Font("Serif", Font.BOLD, 17));
			comp.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(new Color(230, 230, 230)),
					null));
			comp.setToolTipText(row + "  " + column);
			Color backgroundColor = cellColors.get(new Point(column, row));
			comp.setBackground(backgroundColor);
			return comp;
		}
	}
}
