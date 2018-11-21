import io.Input;
import model.Node;
import process.NodeProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by ehay@naver.com on 2018-11-21
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

public class XmlApplication {

    public static void main(String[] args) throws IOException {
        Input input = new Input();
        Node root = input.process();
        NodeProcess nodeProcess = new NodeProcess(root, input.getXmls());

        nodeProcess.process();
        dfs(root);
    }

    private static void dfs(Node node) {
        System.out.println(node.getUserObject().toString());
        if(node.getXml() != null) System.out.println(node.getXml().getCommand());

        if (node.getChildren() == null) return;

        for (Node child : node.getChildren()) {
            dfs(child);
        }
    }
}
