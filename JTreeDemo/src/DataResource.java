import java.util.Collections;
import java.util.List;

import javax.swing.Icon;


public class DataResource {
    private String name;
    private Icon image;
    private ColorEnum color;
    
    public DataResource(String name, ColorEnum color) {
        super();
        this.name = name;
        this.color = color;
    }
    
    public DataResource() {
        super();
    }

    private List<DataResource> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    public List<DataResource> getChildren() {
        return children != null ? children: Collections.EMPTY_LIST;
    }

    public void setChildren(List<DataResource> children) {
        this.children = children;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }
}
