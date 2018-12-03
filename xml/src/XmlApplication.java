import io.Input;
import model.Node;
import process.NodeProcess;
import process.XmlCheckProcess;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by ehay@naver.com on 2018-11-21
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

public class XmlApplication extends JFrame implements TreeSelectionListener, ActionListener {

    JTree tree;
    JLabel label;
    JPanel panel;
    JTextArea textArea;

    public XmlApplication() throws IOException {
        //프레임 설정
        setSize(500, 500);
        setTitle("XML 에디터");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());


        //파일 읽기, 트리구조화
        Input in = new Input();
        Node root = in.process();

        NodeProcess nodeProcess = new NodeProcess(root, in.getXmls());
        nodeProcess.process();
        XmlCheckProcess xmlCheckProcess = new XmlCheckProcess();
        xmlCheckProcess.process(root);


        //그래픽
        panel = new JPanel();
        label = new JLabel("TEXT");

        if (!xmlCheckProcess.isError()) {
            tree = new JTree(root);

            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(null);
            renderer.setOpenIcon(null);
            renderer.setClosedIcon(null);

            tree.setCellRenderer(renderer);
            tree.addTreeSelectionListener(this);

            container.add(new JScrollPane(tree), BorderLayout.CENTER);
        } else {
            container.add(new JTextField("ERROR"), BorderLayout.CENTER);
        }

        textArea = new JTextArea(xmlCheckProcess.getErrorMessage(), 5, 42);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.SOUTH);

        //컨테이너 설정
        container.add(panel, BorderLayout.NORTH);
        container.add(label, BorderLayout.SOUTH);

        //프레임 설정
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        Node node = (Node) tree.getLastSelectedPathComponent();
        if (node == null) return;
        StringBuilder newText = new StringBuilder();

        newText.append("선택노드 : ");
        newText.append(node.getIdx() + " ");
        newText.append((String) node.getUserObject());
        Vector<Node> children = node.getChildren();

        newText.append(" , 자식노드 : ");
        if (children != null) {
            for (Node child : children) {
                newText.append(child.getIdx() + " ");
                newText.append((String) child.getUserObject()).append(", ");
            }

            newText.delete(newText.length() - 2, newText.length());
        } else {
            newText.append("없음");
        }

        label.setText(newText.toString());
    }

    public static void main(String[] args) throws IOException {
        new XmlApplication();
    }
}
