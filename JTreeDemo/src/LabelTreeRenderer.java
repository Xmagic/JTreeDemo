import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;


public class LabelTreeRenderer extends JLabel implements TreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        LabelTreeNode node = (LabelTreeNode) value;
        setText(node.getDisplayName());
        setIcon(node.getImage(expanded));
        
        // Change background color on selected or not
        this.setOpaque(true);
        this.setBackground(UIManager.getColor(selected ? "Tree.selectionBackground": "Tree.textBackground"));
       
        return this;
    }

}
