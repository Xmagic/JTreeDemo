import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;


public class ViewPanel extends JPanel implements TreeSelectionListener{
    
    LabelTree _tree;
    JScrollPane _scrollPane;
    JSplitPane _splitPane;
    
    public ViewPanel() {
        onInit();
    }
    
    private void onInit()
    {
        _tree = buildResourceTree();

        _scrollPane = new JScrollPane(_tree);
        _scrollPane.setPreferredSize(new Dimension(200, _scrollPane.getPreferredSize().height));
        _scrollPane.setMinimumSize(new Dimension(200, _scrollPane.getMinimumSize().height));

        _splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        _splitPane.setLeftComponent(getLeftComponent());
        _splitPane.setDividerLocation(0.4);

        setLayout(new BorderLayout());
        add(_splitPane, BorderLayout.CENTER);

        // Select the first node by default;
        _tree.setSelectionRow(0);
    }

    protected Component getLeftComponent() {
        return _scrollPane;
    }

    /**
     * Build resource tree
     */
    protected LabelTree buildResourceTree() {
        _tree = new DnDTree();

        // Only allow one entry at a time to be selected.
        _tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        _tree.getSelectionModel().addTreeSelectionListener(this);

        // build tree nodes
        _tree.build();
        return _tree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        onTreeNodeSelected();
        
    }

    private void onTreeNodeSelected() {
        LabelTreeNode node = (LabelTreeNode) _tree.getLastSelectedPathComponent();
        if (node != null) {
            JPanel panel = createColorPanel((DataResource) node.getUserObject());
            _splitPane.setRightComponent(panel);
        } else {
            _splitPane.setRightComponent(new JPanel());
        }
    }

    private JPanel createColorPanel(DataResource userObject) {
        return ColorPanelFactory.getColorPanel(userObject);
    }
}
