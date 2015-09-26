import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class DnDTree extends LabelTree implements DragSourceListener, DropTargetListener, DragGestureListener {

	DragSource dragSource;	
	DropTarget dropTarget; 
	TreeNode dropTargetNode = null; 
	TreeNode draggedNode = null;

	
	public DnDTree() {
		super();
		initDnDGestureListener();
	}
	
	private void initDnDGestureListener() {
		dragSource = new DragSource();
		DragGestureRecognizer dgr = dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
		dropTarget = new DropTarget(this, this);
	}
	
	@Override
	protected void initCellRenderer() {
		// Create a tree node cell renderer
		setCellRenderer(new DnDRenderer());
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		System.out.println("dragGestureRecognized");
		// find object at this x,y
		Point clickPoint = dge.getDragOrigin();
		TreePath path = getPathForLocation(clickPoint.x, clickPoint.y);
		if (path == null) {

			System.out.println("not on a node");
			return;
		}
		draggedNode = (TreeNode) path.getLastPathComponent();
		Transferable trans = new TreeNodeTransferable(draggedNode);
		dragSource.startDrag(dge, Cursor.getDefaultCursor(), trans, this);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		System.out.println("dragEnter");

		dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);

		System.out.println("accepted dragEnter");
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// figure out which cell it's over, no drag to self
		Point dragPoint = dtde.getLocation();
		TreePath path = getPathForLocation(dragPoint.x, dragPoint.y);
		if (path == null)
			dropTargetNode = null;
		else
			dropTargetNode = (TreeNode) path.getLastPathComponent();
		repaint();
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		System.out.println("drop()!");
		Point dropPoint = dtde.getLocation();
		// int index = locationToIndex (dropPoint);
		TreePath path = getPathForLocation(dropPoint.x, dropPoint.y);
		System.out.println("drop path is " + path);
		boolean dropped = false;
		try {
			dtde.acceptDrop(DnDConstants.ACTION_MOVE);
			System.out.println("accepted");
			Object droppedObject =

			dtde.getTransferable().getTransferData(TreeNodeTransferable.localObjectFlavor);
			MutableTreeNode droppedNode = null;
			if (droppedObject instanceof MutableTreeNode) {
				// remove from old location
				droppedNode = (MutableTreeNode) droppedObject;
				((DefaultTreeModel) getModel()).removeNodeFromParent(droppedNode);
			} else {
				droppedNode = new DefaultMutableTreeNode(droppedObject);
			}
			// insert into spec'd path. if dropped into a parent
			// make it last child of that parent
			DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			if (dropNode.isLeaf()) {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dropNode.getParent();
				int index = parent.getIndex(dropNode);
				((DefaultTreeModel) getModel()).insertNodeInto(droppedNode, parent, index);
			} else {
				((DefaultTreeModel) getModel()).insertNodeInto(droppedNode, dropNode, dropNode.getChildCount());
			}
			dropped = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		dtde.dropComplete(dropped);

	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		

	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		System.out.println ("dragDropEnd()");
		dropTargetNode = null;
		draggedNode = null;
		repaint();
	}
	
	public class DnDRenderer extends LabelTreeRenderer {
		boolean isTargetNode;
		boolean isTargetNodeLeaf;
		boolean isLastItem;
		int	BOTTOM_PAD = 30;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			isTargetNode = (value == dropTargetNode);
			isTargetNodeLeaf = (isTargetNode &&

			((TreeNode) value).isLeaf());
			// isLastItem = (index == list.getModel().getSize()-1);
			boolean showSelected = selected & (dropTargetNode == null);
			return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (isTargetNode) {
				g.setColor(Color.black);
				if (isTargetNodeLeaf) {
					g.drawLine(0, 0, getSize().width, 0);
				} else {
					g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
				}
			}
		}

	}

}
