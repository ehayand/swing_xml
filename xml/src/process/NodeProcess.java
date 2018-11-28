package process;

import model.Node;
import model.Xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehay@naver.com on 2018-11-28
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

public class NodeProcess {
    private Node root;
    private List<String> elements;
    private List<Xml> xmls;

    public NodeProcess(final Node root, final List<Xml> xmls) {
        this.root = root;
        this.xmls = xmls;
    }

    public void process() {
        setElemnets();
        setNode(root);
    }

    private void setElemnets() {
        elements = new ArrayList<>();

        if (xmls == null) return;

        for (Xml xml : xmls) {
            elements.add(xml.getElement());
        }
    }

    private void setNode(final Node node) {
        String element = node.getUserObject().toString();
        int index = 0;

        for (String str : elements) {
            if (element.equals(str)) break;
            index++;
        }

        if (index < elements.size()) node.setXml(xmls.get(index));

        if (node.getChildren() == null) return;

        for (final Node child : node.getChildren()) {
            setNode(child);
        }
    }

}
