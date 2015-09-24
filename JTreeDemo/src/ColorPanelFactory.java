import java.awt.Color;

import javax.swing.JPanel;


public class ColorPanelFactory {
    public static JPanel getColorPanel(DataResource data) {
        switch (data.getColor()) {
        case blue:
            return createPanel(Color.BLUE);
        case red:
            return createPanel(Color.red);
        case green:
            return createPanel(Color.green);
        case pink:
            return createPanel(Color.pink);
        default:
            return createPanel(Color.BLACK);
        }
    }

    private static JPanel createPanel(Color color) {
        JPanel p = new JPanel();
        p.setBackground(color);
        return p;
    }
}
