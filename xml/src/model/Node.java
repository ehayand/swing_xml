package model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Vector;

/**
 * Created by ehay@naver.com on 2018-11-21
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

public class Node extends DefaultMutableTreeNode {
    private Xml xml;
    private String status;

    public Node(Object userObject) {
        super(userObject);
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(Xml xml) {
        this.xml = xml;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vector<Node> getChildren() {return super.children; }
}
