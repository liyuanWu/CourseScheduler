package liyuan.wu.classschedulor.view;

public abstract class DialogReturnListener<T> implements ObjectActionListener {

	protected PanelManager<T> panelManager;

	public DialogReturnListener(PanelManager<T> panelManager){
		this.panelManager = panelManager;
	}
	
	public abstract void objectAction(Object object);

}
