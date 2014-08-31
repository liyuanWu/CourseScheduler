package liyuan.wu.classschedulor.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import liyuan.wu.classschedulor.accessor.AccessorPool;
import liyuan.wu.classschedulor.accessor.BoatAccessor;
import liyuan.wu.classschedulor.beans.CourseCombo;
import liyuan.wu.classschedulor.beans.UnarrangedCourse;

public class PanelManager<T> extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Map<T, Panel<T>> panelMap;
	private final LinkedList<T> panelIndex;
	private final AccessorPool<T, BoatAccessor<T, CourseCombo>> schedulerPool;
	private final AccessorPool<T, BoatAccessor<T, UnarrangedCourse>> arrangePoolSwitcher;
	private final RefreshNotificationManager refreshNotificaitonManager;
	private final Comparator<T> comparator;
	private ObjectActionListener addPanelListener;

	public PanelManager(RefreshNotificationManager refreshNotificaitonManager,
			AccessorPool<T, BoatAccessor<T, CourseCombo>> schedulerPool,
			AccessorPool<T, BoatAccessor<T, UnarrangedCourse>>  arrangePoolSwitcher,
			Comparator<T> comparator) {
		super(JTabbedPane.TOP);
		this.comparator = comparator;
		this.refreshNotificaitonManager = refreshNotificaitonManager;
		this.schedulerPool = schedulerPool;
		this.arrangePoolSwitcher = arrangePoolSwitcher;
		this.panelMap = new HashMap<T, Panel<T>>();
		this.panelIndex = new LinkedList<>();
		this.addTab("+ ", null);
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(arg0.getButton()==MouseEvent.BUTTON2){
					for(int i=0;i<getTabCount()-1;i++){
						if(PanelManager.this.getTabComponentAt(i)!=null){
							if(PanelManager.this.getTabComponentAt(i).getBounds().contains(arg0.getX(),arg0.getY())){
								closePanel(PanelManager.this.panelIndex.get(i));
							}	
						}
					}
				}else if(arg0.getButton()==MouseEvent.BUTTON1){
					 if(getSelectedIndex()==getTabCount()-1){
						 	if(addPanelListener!=null){
						 		addPanelListener.objectAction(arg0.getLocationOnScreen());
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
			public void mouseClicked(MouseEvent arg0) {
			}
		});
	}
	
	public T getSelected() {
		if (this.getSelectedComponent() instanceof Panel<?>) {
			return ((Panel<T>) this.getSelectedComponent()).getIdentifier();
		}
		return null;
	}

	private int getInsertIndex(T t){
		int i=0;
		for(;i<this.panelIndex.size();i++){
//			System.out.println(t+"\t" + this.panelIndex.get(i)+"\t"+comparator.compare(t, this.panelIndex.get(i)));
			if(comparator.compare(t, this.panelIndex.get(i))<0){
				return i;
			}
		}
		return i;
	}
	
	private int getIndex(T t){
		for(int i=0;i<this.panelIndex.size();i++){
			if(this.panelIndex.get(i).equals(t)){
				return i;
			}
		}
		return -1;
	}
	public void showPanel(T t,ButtonListDecorator<T> buttonListDecorator, CellDecorator<T> cellDecorator) {
		Integer index = getIndex(t);
		if (index == -1) {
			BoatAccessor<T,CourseCombo> scheduler = schedulerPool.getBoat(t);
			BoatAccessor<T,UnarrangedCourse> arrangePool = arrangePoolSwitcher.getBoat(t);
			if (scheduler == null || arrangePool == null) {
				System.out.println(t + " not exist");
				return;
			}
			Panel<T> panel = new Panel<T>(
					refreshNotificaitonManager, scheduler, arrangePool,buttonListDecorator,cellDecorator);
			this.panelMap.put(t,panel);
			Integer newIndex =  getInsertIndex(t);
			this.insertTab(t.toString(), null, panel, t.toString(),newIndex);
			this.setTabComponentAt(newIndex, new JLabel(t.toString()));
			this.panelIndex.add(newIndex,t);
			index = newIndex;
		}
		this.setSelectedIndex(index);
		this.panelMap.get(t).refresh();
	}

	public void refreshPane(T t) {
		Panel<T> panel = panelMap.get(t);
		if (panel != null) {
			panel.refresh();
		}
	}

	public void closePanel(T t) {
		Integer index = getIndex(t);
		this.remove(index);
		this.panelMap.remove(t);
		this.panelIndex.remove(t);
	}

	public void setSelectedIndex(int index) {
		Component component = this.getComponentAt(index);
		if (component instanceof Panel) {
			((Panel<T>) component).refresh();
		}
		super.setSelectedIndex(index);
	}

	public void setAddPanelListener(ObjectActionListener addPanelListener) {
		this.addPanelListener = addPanelListener;
	}
	
	

}
