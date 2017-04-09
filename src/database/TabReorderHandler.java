package database;

import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.event.MouseInputAdapter;

public class TabReorderHandler extends MouseInputAdapter {
	public static void enableReordering(JTabbedPane pane) {
		TabReorderHandler handler = new TabReorderHandler(pane);
		pane.addMouseListener(handler);
		pane.addMouseMotionListener(handler);
	}
	
	private JTabbedPane tabPane;
	private int draggedTabIndex;
	
	protected TabReorderHandler(JTabbedPane pane) {
		this.tabPane = pane;
		draggedTabIndex = -1;
	}
	
	public void mouseReleased(MouseEvent e) {
		draggedTabIndex = -1;
	}

	public void mouseDragged(MouseEvent e) {
		if(draggedTabIndex == -1) {
			return;
		}
		
		int targetTabIndex = tabPane.getUI().tabForCoordinate(tabPane,
				e.getX(), e.getY());
		if(targetTabIndex != -1 && targetTabIndex != draggedTabIndex) {
			boolean isForwardDrag = targetTabIndex > draggedTabIndex;
			tabPane.insertTab(tabPane.getTitleAt(draggedTabIndex),
					tabPane.getIconAt(draggedTabIndex),
					tabPane.getComponentAt(draggedTabIndex),
					tabPane.getToolTipTextAt(draggedTabIndex),
					isForwardDrag ? targetTabIndex+1 : targetTabIndex);
			draggedTabIndex = targetTabIndex;
			tabPane.setSelectedIndex(draggedTabIndex);
		}
	}
}