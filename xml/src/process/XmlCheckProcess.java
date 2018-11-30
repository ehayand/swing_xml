package process;

import model.Node;

import java.util.List;

/**
 * Created by ehay@naver.com on 2018-11-21
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

public class XmlCheckProcess {

    boolean error;
    StringBuilder message;

    public XmlCheckProcess() {
        message = new StringBuilder();
        error = false;
    }

    public void process(final Node node) {

        if(!node.isClosed()) {
            error = true;
            message.append("Line " + node.getIdx() + " : 닫히지않음").append("\n");
        }


        message.append(node.getUserObject().toString()).append("\n");
        if (node.getXml() != null) {
            String temp = node.getXml().getCommand();
            message.append(temp).append("\n");

            if (",".equals(temp)) {
                int errorLine = seq(node);
                message.append(errorLine).append("\n");
                if(errorLine != 0) {
                    error = true;
                    message.append("Line " + errorLine + " : 원소 오류").append("\n");
                }
            } else if ("|".equals(temp)) {
                int errorLine = or(node);
                message.append(errorLine).append("\n");
                if(errorLine != 0) {
                    error = true;
                    message.append("Line " + errorLine + " : 원소 오류").append("\n");
                }
            }
        }

        if (node.getChildren() == null) return;

        for (Node child : node.getChildren()) {
            process(child);
        }
    }

    private int seq(final Node node) {
        List<String> targets = node.getXml().getTargets();
        List<Node> children = node.getChildren();

        if (children == null || targets.size() != children.size()) return node.getIdx();

        for (int i = 0; i < targets.size(); i++) {
            if (!targets.get(i).equals(children.get(i).getUserObject().toString())) return node.getIdx();
        }

        return 0;
    }

    private int or(final Node node) {
        List<String> targets = node.getXml().getTargets();
        List<Node> children = node.getChildren();

        if (children == null) return node.getIdx();

        for (String target : targets) {
            for(Node child : children) {
                if(target.equals(child.getUserObject().toString())) return 0;
            }
        }

        return node.getIdx();
    }

    private void and(final Node node) {

    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return this.message.toString();
    }
}
