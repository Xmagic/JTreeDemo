import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;


public class LabelTreeNode extends DefaultMutableTreeNode {
    protected static final Icon _closedIcon = UIManager.getIcon("Tree.closedIcon");
    protected static final Icon _leafIcon = UIManager.getIcon("Tree.leafIcon");
    protected static final Icon _openIcon = UIManager.getIcon("Tree.openIcon");
    
    
    public LabelTreeNode(DataResource resource) {
        // TODO Auto-generated constructor stub
        super(resource);
    }

    public String getDisplayName()
    {
        return ((DataResource)getUserObject()).getName();
    }
    
    public Icon getImage(boolean isExpand)
    {
        return isExpand ? _closedIcon : _openIcon;
    }   
}
