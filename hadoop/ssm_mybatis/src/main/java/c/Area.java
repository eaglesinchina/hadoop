package c;

import java.util.ArrayList;
import java.util.List;

/**
 * 国家--->
 *         省----->市
 */
public class Area {

    //属性
    private int id;
    private String name;
    private Area parent;
    private List<Area> children=new ArrayList<Area>();

    //构造
    public Area(String name) {
        this.name = name;
    }
    public Area() { }

    //添加元素
    public void add(Area... areas){
        for (Area area : areas) {
            this.children.add(area);
            area.setParent(this);
        }

    }
    //get,set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }

    public List<Area> getChildren() {
        return children;
    }

    public void setChildren(List<Area> children) {
        this.children = children;
    }
}
