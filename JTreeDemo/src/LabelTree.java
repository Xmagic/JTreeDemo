import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;



public class LabelTree extends JTree {
    

    LabelTreeNode _rootNode;
    
    public LabelTree()
    {
        super(new DefaultTreeModel(new LabelTreeNode(new DataResource("root", ColorEnum.blue))));
    }
    
    
    public void build()
    {
        _rootNode = (LabelTreeNode) ((DefaultTreeModel) getModel()).getRoot();
        
        initTree();
        
        List<DataResource> dataList = initData();
        
        for(DataResource data : dataList)
            createResourceNode((DefaultTreeModel)getModel(), _rootNode, data);
    }
    
    private void createResourceNode(DefaultTreeModel model, LabelTreeNode parent, DataResource resource) {
        LabelTreeNode node = new LabelTreeNode(resource);
        model.insertNodeInto(node, parent, parent.getChildCount());

        for (DataResource child : resource.getChildren()) {
            createResourceNode(model, node, child);
        }
    }
    
    /**
     * Initialize the tree
     */
    private void initTree() {
        // Hide the root node
//        setRootVisible(false);

        // Do not let the user edit the tree
//        setEditable(false);

        // Show the handles used for expanding and collapsing with a single click.
        setShowsRootHandles(true);

        // Create a tree node cell renderer
        setCellRenderer(new LabelTreeRenderer());
    }
    
    public List<DataResource> initData() {
        List<DataResource> dataList = new ArrayList<DataResource>();
        
        for (int i = 0; i < 10; i++) {
            DataResource data = new DataResource();
            data.setName("Node" + i);
            data.setColor(ColorEnum.red);

            if (i % 5 == 0) {
                List<DataResource> childlist = new ArrayList<DataResource>();
                DataResource c = new DataResource();
                c.setName("childA");
                c.setColor(ColorEnum.green);
                childlist.add(c);
                DataResource c2 = new DataResource();
                c2.setName("childB");
                c2.setColor(ColorEnum.pink);
                childlist.add(c2);

                data.setChildren(childlist);
            }

            dataList.add(data);
        }

        return dataList;
    }
}
