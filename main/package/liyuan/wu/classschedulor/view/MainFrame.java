package liyuan.wu.classschedulor.view;


import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.*;

import java.awt.BorderLayout;

import liyuan.wu.classschedulor.accessor.AccessorPool;
import liyuan.wu.classschedulor.accessor.BoatAccessor;
import liyuan.wu.classschedulor.beans.ArrangePool;
import liyuan.wu.classschedulor.beans.Boat;
import liyuan.wu.classschedulor.beans.Classroom;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.FileRecord;
import liyuan.wu.classschedulor.beans.Scheduler;
import liyuan.wu.classschedulor.beans.Teacher;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;
import liyuan.wu.classschedulor.data.FileRecorder;
import liyuan.wu.classschedulor.data.Pool;
import liyuan.wu.classschedulor.data.Synchronizator;
import liyuan.wu.classschedulor.report.ReportProducer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;














import org.apache.commons.io.IOUtils;


public class MainFrame {

	private JFrame frame;
	private JSplitPane splitPane;
	private JSplitPane splitPane_2;

	private PanelManager<Teacher> teacherSchedulerPaneManager;
	private AccessorPool<Teacher,BoatAccessor<Teacher,UnarrangedCourse>> teacherArrangePoolAccessorSwitcher;
	private AccessorPool<Teacher,BoatAccessor<Teacher,CourseCombo>>  teacherSchedulerAccessorPool;
	private PanelManager<Classroom> classroomSchedulerPaneManager;
	private AccessorPool<Classroom,BoatAccessor<Classroom,UnarrangedCourse>> classroomArrangePoolAccessorSwitcher;
	private AccessorPool<Classroom,BoatAccessor<Classroom,CourseCombo>>  classroomSchedulerAccessorPool;
	
	private Pool<Teacher,Boat<Teacher,UnarrangedCourse>> teacherArrangePoolSwitcher;
	private Pool<Teacher, Boat<Teacher, CourseCombo>>  teacherSchedulerPool;
	private Pool<Classroom, Boat<Classroom, UnarrangedCourse>> classroomArrangePoolSwitcher;
	private Pool<Classroom, Boat<Classroom, CourseCombo>>  classroomSchedulerPool;
	
	private RefreshNotificationManager refreshNotificatioNManager;
	private Synchronizator<CourseCombo> schedulerSynchronizator;
	private  Synchronizator<UnarrangedCourse> arrangePoolSynchronizator;
	private FileRecorder recorder;
	private List<Teacher> teacherSet;
	private List<Classroom> classroomSet;
	private PanelManager<Teacher> teacherSchedulerPaneManager_2;
	private PanelManager<Classroom> classroomSchedulerPaneManager_2;
	private ReportProducer reportProducer;
	private JSplitPane splitPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public MainFrame() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
//		String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//		UIManager.setLookAndFeel(windows);
		UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
//		UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		teacherSchedulerAccessorPool = new AccessorPool<Teacher, BoatAccessor<Teacher,CourseCombo>>();
		classroomSchedulerAccessorPool = new AccessorPool<Classroom,  BoatAccessor<Classroom,CourseCombo>>();
		classroomArrangePoolAccessorSwitcher = new AccessorPool<Classroom,  BoatAccessor<Classroom,UnarrangedCourse>>();
		teacherArrangePoolAccessorSwitcher = new AccessorPool<Teacher, BoatAccessor<Teacher,UnarrangedCourse>>();
		
		teacherSchedulerPool = new Pool<Teacher, Boat<Teacher,CourseCombo>>();
		classroomSchedulerPool = new Pool<Classroom,  Boat<Classroom,CourseCombo>>();
		classroomArrangePoolSwitcher = new Pool<Classroom,  Boat<Classroom,UnarrangedCourse>>();
		teacherArrangePoolSwitcher = new Pool<Teacher, Boat<Teacher,UnarrangedCourse>>();
		
		schedulerSynchronizator = new Synchronizator<CourseCombo>(
				classroomSchedulerPool, teacherSchedulerPool);
		arrangePoolSynchronizator = new Synchronizator<UnarrangedCourse>(
				classroomArrangePoolSwitcher, teacherArrangePoolSwitcher);
		recorder = new FileRecorder();
		reportProducer=new ReportProducer();
		initialize();
		this.frame.setVisible(true);
	}

//	private void testFunction() {
//		Teacher teacher = new Teacher("TRY");
//		Classroom classroom = new Classroom(3, 2);
//		CourseCombo combo = new CourseCombo(new WeekDay(WEEKDAY.Monday),
//				new Time(2), new Course("Math"), classroom, teacher);
//
//		Scheduler<Teacher> teacherSchedule = new Scheduler<Teacher>(teacher,
//				schedulerSynchronizator);
//		teacherSchedule.addCourse(combo);
//		teacherSchedulerPool.addScheduler(teacherSchedule);
//		ArrangePool<Teacher> teacherArrangePool = new ArrangePool<Teacher>(
//				arrangePoolSynchronizator, teacher);
//		teacherArrangePoolSwitcher.addArrangePool(teacherArrangePool);
//		teacherSchedulerPaneManager.showTeacherPane(teacher);
//
//		ClassroomScheduler classroomScheduler = new ClassroomScheduler(
//				classroom, schedulerSynchronizator);
//		classroomScheduler.addCourse(combo);
//		classroomSchedulerPool.addScheduler(classroomScheduler);
//		ArrangePool<Classroom> classroomArrangePool = new ArrangePool<Classroom>(
//				arrangePoolSynchronizator, classroom);
//		classroomArrangePool.addUnarrangedCourse(new UnarrangedCourse(combo));
//		classroomArrangePoolSwitcher.addArrangePool(classroomArrangePool);
//		classroomSchedulerPaneManager.showClassroomPane(classroom);
//		refreshNotificatioNManager.refresh(new RefreshNotification(classroom,
//				teacher));
//
//		// QueryResult queryResult = new QueryResult();
//		// queryResult.addQueryResult(new SingleQueryResult(new
//		// WeekDay(WEEKDAY.Monday), new Time(2), new Teacher("Xiao"), new
//		// Classroom(3,2), CELLATTRIBUTE.EMPTY));
//		// queryResult.addQueryResult(new SingleQueryResult(new
//		// WeekDay(WEEKDAY.Monday), new Time(3), new Teacher("Xiao"), new
//		// Classroom(3,2), CELLATTRIBUTE.EXCHANGABLE));
//		// queryResult.addQueryResult(new SingleQueryResult(new
//		// WeekDay(WEEKDAY.Monday), new Time(4), new Teacher("Xiao"), new
//		// Classroom(3,2), CELLATTRIBUTE.UNEXCHANGABLE));
//		// teacherTable.refreshCellsByQueryResults(queryResult);
//	}

//	private void testTabSwitchFunction() {
//		for (int i = 0; i < 40; i++) {
//			Teacher teacher = new Teacher("TRY" + i);
//			Classroom classroom = new Classroom(i, i);
//			CourseCombo combo = new CourseCombo(new WeekDay(WEEKDAY.Monday),
//					new Time(2), new Course("Math"), classroom, teacher);
//
//			TeacherScheduler teacherSchedule = new TeacherScheduler(teacher,
//					schedulerSynchronizator);
//			teacherSchedule.addCourse(combo);
//			teacherSchedulerPool.addScheduler(teacherSchedule);
//			ArrangePool<Teacher> teacherArrangePool = new ArrangePool<Teacher>(
//					arrangePoolSynchronizator, teacher);
//			teacherArrangePoolSwitcher.addArrangePool(teacherArrangePool);
//			teacherSchedulerPaneManager.showTeacherPane(teacher);
//
//			ClassroomScheduler classroomScheduler = new ClassroomScheduler(
//					classroom, schedulerSynchronizator);
//			classroomScheduler.addCourse(combo);
//			classroomSchedulerPool.addScheduler(classroomScheduler);
//			ArrangePool<Classroom> classroomArrangePool = new ArrangePool<Classroom>(
//					arrangePoolSynchronizator, classroom);
//			classroomArrangePool
//					.addUnarrangedCourse(new UnarrangedCourse(combo));
//			classroomArrangePoolSwitcher.addArrangePool(classroomArrangePool);
//			classroomSchedulerPaneManager.showClassroomPane(classroom);
//		}
//	}
//
//	private void testButtonListFunction() {
//		for (int i = 0; i < 40; i++) {
//			Teacher teacher = new Teacher("TRY");
//			Classroom classroom = new Classroom(3, 2);
//			CourseCombo combo = new CourseCombo(new WeekDay(WEEKDAY.Monday),
//					new Time(2), new Course("Math" + i), classroom, teacher);
//
//			teacherArrangePoolSwitcher.getArrangePool(new Teacher("TRY"))
//					.addUnarrangedCourse(new UnarrangedCourse(combo));
//			teacherSchedulerPaneManager.refreshPane(new Teacher("TRY"));
//			classroomArrangePoolSwitcher.getArrangePool(new Classroom(3, 2))
//					.addUnarrangedCourse(new UnarrangedCourse(combo));
//			classroomSchedulerPaneManager.refreshPane(new Classroom(3, 2));
//		}
//	}

	// QueryResult queryResult = new QueryResult();
	// queryResult.addQueryResult(new SingleQueryResult(new
	// WeekDay(WEEKDAY.Monday), new Time(2), new Teacher("Xiao"), new
	// Classroom(3,2), CELLATTRIBUTE.EMPTY));
	// queryResult.addQueryResult(new SingleQueryResult(new
	// WeekDay(WEEKDAY.Monday), new Time(3), new Teacher("Xiao"), new
	// Classroom(3,2), CELLATTRIBUTE.EXCHANGABLE));
	// queryResult.addQueryResult(new SingleQueryResult(new
	// WeekDay(WEEKDAY.Monday), new Time(4), new Teacher("Xiao"), new
	// Classroom(3,2), CELLATTRIBUTE.UNEXCHANGABLE));
	// teacherTable.refreshCellsByQueryResults(queryResult);

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u6587\u4EF6");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("\u65B0\u5EFA");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("\u65b0\u5efa");
				fileChooser	.setFileFilter(new XMLFileFilter());
				int fresult = fileChooser.showOpenDialog(MainFrame.this.frame);
				if (fresult == JFileChooser.APPROVE_OPTION) {
					clearAllBuffer();
	
					if(fileChooser.getSelectedFile()!=null){
						FileRecord fileRecord = recorder.loadFileRecord(fileChooser.getSelectedFile());
						teacherSet =Arrays.asList(fileRecord.getTeachers());
						classroomSet = Arrays.asList(fileRecord.getClassrooms());
						for(ArrangePool<Teacher> arrangePool:fileRecord.getTeacherArrangePools()){
							teacherArrangePoolSwitcher.addBoat(arrangePool);
							teacherArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Teacher,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
						}
						for(ArrangePool<Classroom> arrangePool:fileRecord.getClassroomArrangePools()){
							classroomArrangePoolSwitcher.addBoat(arrangePool);
							classroomArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Classroom,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
						}
						
						for(Teacher teacher : fileRecord.getTeachers()){
							if(teacherArrangePoolAccessorSwitcher.getBoat(teacher)==null){
								ArrangePool<Teacher> arrangePool = new ArrangePool<Teacher>(teacher);
								teacherArrangePoolSwitcher.addBoat(arrangePool);
								teacherArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Teacher,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
							}
							if(teacherSchedulerAccessorPool.getBoat(teacher)==null){
								Scheduler<Teacher> scheduler = new Scheduler<Teacher>(teacher);
								teacherSchedulerPool.addBoat(scheduler);
								teacherSchedulerAccessorPool.addBoat(new BoatAccessor<Teacher,CourseCombo>(scheduler,schedulerSynchronizator));
							}
						}
						for(Classroom classroom : fileRecord.getClassrooms()){
							if(classroomArrangePoolAccessorSwitcher.getBoat(classroom)==null){
								ArrangePool<Classroom> arrangePool = new ArrangePool<Classroom>(classroom);
								classroomArrangePoolSwitcher.addBoat(arrangePool);
								classroomArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Classroom,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
							}
							if(classroomSchedulerAccessorPool.getBoat(classroom)==null){
								Scheduler<Classroom> scheduler = new Scheduler<Classroom>(classroom);
								classroomSchedulerPool.addBoat(scheduler);
								classroomSchedulerAccessorPool.addBoat(new BoatAccessor<Classroom,CourseCombo>(scheduler,schedulerSynchronizator));
							}
						}
						showAll();
					}
				}
			}
		});
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("\u6253\u5F00");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("\u6253\u5f00");
				fileChooser	.setFileFilter(new UORFileFilter());
				int fresult = fileChooser.showOpenDialog(MainFrame.this.frame);
				if (fresult == JFileChooser.APPROVE_OPTION) {
					clearAllBuffer();

					if(fileChooser.getSelectedFile()!=null){
						FileRecord fileRecord = recorder.loadFileRecord(fileChooser.getSelectedFile());
						teacherSet =Arrays.asList(fileRecord.getTeachers());
						classroomSet = Arrays.asList(fileRecord.getClassrooms());
						for(ArrangePool<Teacher> arrangePool:fileRecord.getTeacherArrangePools()){
							teacherArrangePoolSwitcher.addBoat(arrangePool);
							teacherArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Teacher,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
						}
						for(ArrangePool<Classroom> arrangePool:fileRecord.getClassroomArrangePools()){
							classroomArrangePoolSwitcher.addBoat(arrangePool);
							classroomArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Classroom,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
						}
						for(Scheduler<Teacher> teacherScheduler:fileRecord.getTeacherSchedulers()){
							teacherSchedulerPool.addBoat(teacherScheduler);
							teacherSchedulerAccessorPool.addBoat(new BoatAccessor<Teacher,CourseCombo>(teacherScheduler,schedulerSynchronizator));
						}
						for(Scheduler<Classroom> classroomScheduler:fileRecord.getClassroomScheduler()){
							classroomSchedulerPool.addBoat(classroomScheduler);
							classroomSchedulerAccessorPool.addBoat(new BoatAccessor<Classroom,CourseCombo>(classroomScheduler,schedulerSynchronizator));
						}
						
						for(Teacher teacher : fileRecord.getTeachers()){
							if(teacherArrangePoolAccessorSwitcher.getBoat(teacher)==null){
								ArrangePool<Teacher> arrangePool = new ArrangePool<Teacher>(teacher);
								teacherArrangePoolSwitcher.addBoat(arrangePool);
								teacherArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Teacher,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
							}
							if(teacherSchedulerAccessorPool.getBoat(teacher)==null){
								Scheduler<Teacher> scheduler = new Scheduler<Teacher>(teacher);
								teacherSchedulerPool.addBoat(scheduler);
								teacherSchedulerAccessorPool.addBoat(new BoatAccessor<Teacher,CourseCombo>(scheduler,schedulerSynchronizator));
							}
						}
						for(Classroom classroom : fileRecord.getClassrooms()){
							if(classroomArrangePoolAccessorSwitcher.getBoat(classroom)==null){
								ArrangePool<Classroom> arrangePool = new ArrangePool<Classroom>(classroom);
								classroomArrangePoolSwitcher.addBoat(arrangePool);
								classroomArrangePoolAccessorSwitcher.addBoat(new BoatAccessor<Classroom,UnarrangedCourse>(arrangePool,arrangePoolSynchronizator));
							}
							if(classroomSchedulerAccessorPool.getBoat(classroom)==null){
								Scheduler<Classroom> scheduler = new Scheduler<Classroom>(classroom);
								classroomSchedulerPool.addBoat(scheduler);
								classroomSchedulerAccessorPool.addBoat(new BoatAccessor<Classroom,CourseCombo>(scheduler,schedulerSynchronizator));
							}
						}
						showAll();
				}
			
				}
			}
		});
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u4FDD\u5B58");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("\u4FDD\u5B58");
				fileChooser	.setFileFilter(new UORFileFilter());
				int fresult;
				fresult = fileChooser.showSaveDialog(MainFrame.this.frame);
				if (fresult == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile(); 
				  UORFileFilter filter = (UORFileFilter)fileChooser.getFileFilter();
				  String ends = filter.getEnds();
				  File newFile = null;
				  if (file.getAbsolutePath().toUpperCase().endsWith(ends.toUpperCase())) {
				    newFile = file;
				  } else {
				    newFile = new File(file.getAbsolutePath() + ends);
				  }
				  FileRecord fileRecord = new FileRecord();
				  for(Teacher teacher:teacherSet){
					  fileRecord.addTeacher(teacher);
				  }
				  for(Classroom classroom:classroomSet){
					  fileRecord.addClassroom(classroom);  
				  }
					for(Boat<Classroom, ?> arrangePool:MainFrame.this.classroomArrangePoolSwitcher.getAllBoats()){
						fileRecord.addClassroomArrangePools((ArrangePool)arrangePool);
					}
					for(Boat<Classroom, ?> teacherScheduler:MainFrame.this.classroomSchedulerPool.getAllBoats()){
						fileRecord.addClassroomScheduler((Scheduler)teacherScheduler);
					}
					recorder.saveFileRecord(newFile,fileRecord);
				}
			}
		});
		menu.add(menuItem_2);
		
		JMenuItem menuItem_9 = new JMenuItem("\u5BFC\u51FA");
		menuItem_9.addActionListener(new ActionListener() {
			class StadentClassroomDecorator implements CellDecorator<Classroom>{

				@Override
				public String decorate(CourseCombo courseCombo) {
					return courseCombo.getCourse().getName();
				}
				
			}
			class StadentTeacherDecorator implements CellDecorator<Teacher>{

				@Override
				public String decorate(CourseCombo courseCombo) {
					return courseCombo.getCourse().getName();
				}
				
			}
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("\u5BFC\u51FA");
				fileChooser	.setFileFilter(new HTMLFileFilter());
				int fresult;
				fresult = fileChooser.showSaveDialog(MainFrame.this.frame);
				if (fresult == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile(); 
				  HTMLFileFilter filter = (HTMLFileFilter)fileChooser.getFileFilter();
				  String ends = filter.getEnds();
				  File newFile = null;
				  if (file.getAbsolutePath().toUpperCase().endsWith(ends.toUpperCase())) {
				    newFile = file;
				  } else {
				    newFile = new File(file.getAbsolutePath() + ends);
				  }
				  if(newFile.exists()){
					  newFile.delete();
				  }
				  try {
					FileOutputStream outputStream = new FileOutputStream(newFile);
					reportProducer.addContent(teacherSchedulerAccessorPool, new CellDecorator.TeacherCellDecorator(),new Teacher.TeacherComparator(),"higher");
					reportProducer.addContent(teacherSchedulerAccessorPool, new StadentTeacherDecorator(),new Teacher.TeacherComparator());
//					reportProducer.addContent(classroomSchedulerAccessorPool, new CellDecorator.ClassroomCellDecorator(),new Classroom.ClassroomComparator());
					reportProducer.addContent(classroomSchedulerAccessorPool, new StadentClassroomDecorator(),new Classroom.ClassroomComparator());
					IOUtils.write(reportProducer.getReport(), outputStream,Charset.forName("UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				  

				}
			}
		});
		menu.add(menuItem_9);

		menu.addSeparator();

		JMenuItem menuItem_3 = new JMenuItem("\u9000\u51FA");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(menuItem_3);
		
		JMenu menu_1 = new JMenu("\u5E2E\u52A9");
		menuBar.add(menu_1);

		JMenuItem menuItem_5 = new JMenuItem("\u5E2E\u52A9");
		menu_1.add(menuItem_5);
		menu_1.addSeparator();
		JMenuItem menuItem_4 = new JMenuItem("\u5173\u4E8E");
		menu_1.add(menuItem_4);

		JMenu mnTesrt = new JMenu("Test");
		menuBar.add(mnTesrt);

		JMenuItem menuItem_6 = new JMenuItem("1");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				testFunction();
			}
		});
		mnTesrt.add(menuItem_6);

		JMenuItem menuItem_7 = new JMenuItem("2");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				testTabSwitchFunction();
			}
		});
		mnTesrt.add(menuItem_7);

		JMenuItem menuItem_8 = new JMenuItem("3");
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				testButtonListFunction();
			}
		});
		mnTesrt.add(menuItem_8);
		

		this.refreshNotificatioNManager = new RefreshNotificationManager();
		this.initPanes(this.refreshNotificatioNManager, teacherArrangePoolAccessorSwitcher, 
				teacherSchedulerAccessorPool, classroomArrangePoolAccessorSwitcher, classroomSchedulerAccessorPool);
		this.frame.setSize(1000, 800);
	}

	private void showAll(){
		for(Teacher teacher:this.teacherSet){
			this.teacherSchedulerPaneManager.showPanel(teacher,new ButtonListDecorator.TeacherButtonListDecorator(), new CellDecorator.TeacherCellDecorator());
			this.teacherSchedulerPaneManager_2.showPanel(teacher,new ButtonListDecorator.TeacherButtonListDecorator(), new CellDecorator.TeacherCellDecorator());
		}
		for(Classroom classroom:this.classroomSet){
			this.classroomSchedulerPaneManager.showPanel(classroom,new ButtonListDecorator.ClassroomButtonListDecorator(), new CellDecorator.ClassroomCellDecorator());
			this.classroomSchedulerPaneManager_2.showPanel(classroom,new ButtonListDecorator.ClassroomButtonListDecorator(), new CellDecorator.ClassroomCellDecorator());
		}
		this.frame.revalidate();
	}
	
	private void clearAllBuffer(){
		teacherSchedulerAccessorPool = new AccessorPool<Teacher, BoatAccessor<Teacher,CourseCombo>>();
		classroomSchedulerAccessorPool = new AccessorPool<Classroom,  BoatAccessor<Classroom,CourseCombo>>();
		classroomArrangePoolAccessorSwitcher = new AccessorPool<Classroom,  BoatAccessor<Classroom,UnarrangedCourse>>();
		teacherArrangePoolAccessorSwitcher = new AccessorPool<Teacher, BoatAccessor<Teacher,UnarrangedCourse>>();
		
		teacherSchedulerPool = new Pool<Teacher, Boat<Teacher,CourseCombo>>();
		classroomSchedulerPool = new Pool<Classroom,  Boat<Classroom,CourseCombo>>();
		classroomArrangePoolSwitcher = new Pool<Classroom,  Boat<Classroom,UnarrangedCourse>>();
		teacherArrangePoolSwitcher = new Pool<Teacher, Boat<Teacher,UnarrangedCourse>>();
		
		schedulerSynchronizator = new Synchronizator<CourseCombo>(
				classroomSchedulerPool, teacherSchedulerPool);
		arrangePoolSynchronizator = new Synchronizator<UnarrangedCourse>(
				classroomArrangePoolSwitcher, teacherArrangePoolSwitcher);
		recorder = new FileRecorder();
		this.resetPanes(this.refreshNotificatioNManager, teacherArrangePoolAccessorSwitcher, 
				teacherSchedulerAccessorPool, classroomArrangePoolAccessorSwitcher, classroomSchedulerAccessorPool);
		
	}
	
	private void initPanes(RefreshNotificationManager refreshNotificationManager,	
			 AccessorPool<Teacher,BoatAccessor<Teacher,UnarrangedCourse>> teacherArrangePoolAccessorSwitcher,
			 AccessorPool<Teacher,BoatAccessor<Teacher,CourseCombo>>  teacherSchedulerAccessorPool,
			 AccessorPool<Classroom,BoatAccessor<Classroom,UnarrangedCourse>> classroomArrangePoolAccessorSwitcher,
			 AccessorPool<Classroom,BoatAccessor<Classroom,CourseCombo>>  classroomSchedulerAccessorPool){
		
		this.splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent e) {
				splitPane_1.setDividerLocation((frame.getHeight()-50)/2);
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});

		frame.getContentPane().add(splitPane_1, BorderLayout.CENTER);
				splitPane = new JSplitPane();
				splitPane.setDividerLocation(0.5);
				splitPane.addComponentListener(new ComponentListener() {
					@Override
					public void componentShown(ComponentEvent e) {}
					@Override
					public void componentResized(ComponentEvent e) {
						splitPane.setDividerLocation(splitPane_1.getWidth()/2);
					}
					@Override
					public void componentMoved(ComponentEvent e) {}
					@Override
					public void componentHidden(ComponentEvent e) {}
				});
				splitPane_2 = new JSplitPane();
				splitPane_2.setDividerLocation(0.5);
				splitPane_2.addComponentListener(new ComponentListener() {
					@Override
					public void componentShown(ComponentEvent e) {}
					@Override
					public void componentResized(ComponentEvent e) {
						splitPane_2.setDividerLocation(splitPane_1.getWidth()/2);
					}
					@Override
					public void componentMoved(ComponentEvent e) {}
					@Override
					public void componentHidden(ComponentEvent e) {}
				});
				splitPane_1.setLeftComponent(splitPane);
				splitPane_1.setRightComponent(splitPane_2);
				
				resetPanes(refreshNotificationManager, teacherArrangePoolAccessorSwitcher, 
						teacherSchedulerAccessorPool, classroomArrangePoolAccessorSwitcher, classroomSchedulerAccessorPool);
	}
	
	private void resetPanes(RefreshNotificationManager refreshNotificationManager,	
		 AccessorPool<Teacher,BoatAccessor<Teacher,UnarrangedCourse>> teacherArrangePoolAccessorSwitcher,
		 AccessorPool<Teacher,BoatAccessor<Teacher,CourseCombo>>  teacherSchedulerAccessorPool,
		 AccessorPool<Classroom,BoatAccessor<Classroom,UnarrangedCourse>> classroomArrangePoolAccessorSwitcher,
		 AccessorPool<Classroom,BoatAccessor<Classroom,CourseCombo>>  classroomSchedulerAccessorPool){
		

		this.teacherSchedulerPaneManager = new PanelManager<Teacher>(
				refreshNotificatioNManager, teacherSchedulerAccessorPool,
				teacherArrangePoolAccessorSwitcher, new Teacher.TeacherComparator());
		this.teacherSchedulerPaneManager.setAddPanelListener(new ObjectActionListener() {
			@Override
			public void objectAction(Object object) {
				addTab(teacherSchedulerPaneManager, Teacher.class, (Point)object);
			}
		});
		splitPane.setRightComponent(this.teacherSchedulerPaneManager);
		this.refreshNotificatioNManager
				.regist(this.teacherSchedulerPaneManager);
		
				this.classroomSchedulerPaneManager = new PanelManager<Classroom>(
						refreshNotificatioNManager, classroomSchedulerAccessorPool,
						classroomArrangePoolAccessorSwitcher, new Classroom.ClassroomComparator());
				this.classroomSchedulerPaneManager.setAddPanelListener(new ObjectActionListener() {
					@Override
					public void objectAction(Object object) {
						addTab(classroomSchedulerPaneManager, Classroom.class, (Point)object);
					}
				});
				splitPane.setLeftComponent(this.classroomSchedulerPaneManager);
				this.refreshNotificatioNManager
						.regist(this.classroomSchedulerPaneManager);
			
		this.teacherSchedulerPaneManager_2 = new PanelManager<Teacher>(
				refreshNotificatioNManager, teacherSchedulerAccessorPool,
				teacherArrangePoolAccessorSwitcher, new Teacher.TeacherComparator());
		this.teacherSchedulerPaneManager_2.setAddPanelListener(new ObjectActionListener() {
			@Override
			public void objectAction(Object object) {
				addTab(teacherSchedulerPaneManager_2, Teacher.class, (Point)object);
			}
		});
		splitPane_2.setLeftComponent(this.teacherSchedulerPaneManager_2);
		this.refreshNotificatioNManager
				.regist(teacherSchedulerPaneManager_2);
		
		this.classroomSchedulerPaneManager_2 = new PanelManager<Classroom>(
				refreshNotificatioNManager, classroomSchedulerAccessorPool,
				classroomArrangePoolAccessorSwitcher,new Classroom.ClassroomComparator());
		splitPane_2.setRightComponent(this.classroomSchedulerPaneManager_2);
		this.refreshNotificatioNManager
				.regist(classroomSchedulerPaneManager_2);
		this.classroomSchedulerPaneManager_2.setAddPanelListener(new ObjectActionListener() {
			@Override
			public void objectAction(Object object) {
				addTab(classroomSchedulerPaneManager_2, Classroom.class, (Point)object);
			}
		});
		
		refreshNotificationManager.registTeacherPreview(teacherSchedulerPaneManager_2);
		refreshNotificationManager.registClassroomPreview(classroomSchedulerPaneManager_2);

		this.splitPane.setSize(this.frame.getWidth(), this.frame.getHeight()/2);
		this.splitPane_2.setSize(this.frame.getWidth(), this.frame.getHeight()/2);
	}
	
	public void addTab(PanelManager<?> panelManager, Class<?> invokerClass, Point p){
		if(invokerClass.equals(Teacher.class)){
			AddPanelDialog<Teacher> panelDialog = new AddPanelDialog<>(this.teacherSet.toArray(new Teacher[this.teacherSet.size()]));
			panelDialog.setLocation(p);
			panelDialog.setVisible(true);
			panelDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panelDialog.setDialogReturnListener(new DialogReturnListener<Teacher>((PanelManager<Teacher>) panelManager) {
				@Override
				public void objectAction(Object object) {
					this.panelManager.showPanel((Teacher)object, new ButtonListDecorator.TeacherButtonListDecorator(), new CellDecorator.TeacherCellDecorator());
				}
			});
		}else if(invokerClass.equals(Classroom.class)){
			AddPanelDialog<Classroom> panelDialog = new AddPanelDialog<>(this.classroomSet.toArray(new Classroom[this.classroomSet.size()]));
			panelDialog.setLocation(p);
			panelDialog.setVisible(true);
			panelDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panelDialog.setDialogReturnListener(new DialogReturnListener<Classroom>((PanelManager<Classroom>) panelManager) {
				@Override
				public void objectAction(Object object) {
					this.panelManager.showPanel((Classroom)object, new ButtonListDecorator.ClassroomButtonListDecorator(), new CellDecorator.ClassroomCellDecorator());
				}
			});
		}

	}
}
class XMLFileFilter extends javax.swing.filechooser.FileFilter{
	public boolean accept(File f) {
		if (f.getName().endsWith(".xml")
				|| f.isDirectory()) {
			return true;
		}
		return false;
	}

	public String getDescription() {
		return "XML\u6587\u4ef6(*.xml)";
	}
	
	public String getEnds(){
		return ".xml";
	}
}
class HTMLFileFilter extends javax.swing.filechooser.FileFilter{
public boolean accept(File f) {
	if (f.getName().endsWith(".html")
			|| f.isDirectory()) {
		return true;
	}
	return false;
}

public String getDescription() {
	return "HTML\u6587\u4ef6(*.html)";
}

public String getEnds(){
	return ".html";
}
}
class UORFileFilter extends javax.swing.filechooser.FileFilter{
public boolean accept(File f) {
	if (f.getName().endsWith(".uor")
			|| f.isDirectory()) {
		return true;
	}
	return false;
}

public String getDescription() {
	return "SCR\u6587\u4ef6(.uor)";
}

public String getEnds(){
	return ".uor";
}


}


