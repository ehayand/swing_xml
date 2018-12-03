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

        if (node.getXml() != null) {
            String temp = node.getXml().getCommand();

            if (",".equals(temp)) {
                int errorLine = seq(node);
                if(errorLine != 0) {
                    error = true;
                    message.append("Line " + errorLine + " : 원소 오류(SEQ)").append("\n");
                }
            } else if ("|".equals(temp)) {
                int errorLine = or(node);
                if(errorLine != 0) {
                    error = true;
                    message.append("Line " + errorLine + " : 원소 오류(OR)").append("\n");
                }
            } else if ("&".equals(temp)) {
                int errorLine = and(node);
                if(errorLine != 0) {
                    error = true;
                    message.append("Line " + errorLine + " : 원소 오류(AND)").append("\n");
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

    private int and(final Node node) {
        List<String> targets = node.getXml().getTargets();
        List<Node> children = node.getChildren();
        boolean[] checked = new boolean[targets.size()];

        if (children == null || targets.size() != children.size()) return node.getIdx();

        for (String target : targets) {
            for(int i=0; i<targets.size(); i++) {
                if(!checked[i] && target.equals(children.get(i).getUserObject().toString())) checked[i] = true;
            }
        }

        for (int i = 0; i < targets.size(); i++) {
            if(!checked[i]) return node.getIdx();
        }

        return 0;
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
