package io;

import model.Node;
import model.Xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by ehay@naver.com on 2018-11-21
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

public class Input {
    private Node root;
    private List<Xml> xmls;
    private Queue<String> info;

    public List<Xml> getXmls() {
        return xmls;
    }

    public Node process() throws IOException {
        FileInputStream fin = new FileInputStream("C:/IOTest/xmlTest.txt");
        Scanner sc = new Scanner(fin);

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.charAt(1) == '!') {
                if(xmls == null) xmls = new ArrayList<>();
                xmls.add(getXml(line));
            }
            else setInfo(line);
        }

        if(info != null) setNode(root);

        return root;
    }

    private void setNode(final Node node) {
        if(info.isEmpty()) return;

        while(!info.isEmpty()) {
            String element = info.poll();

            if(element.charAt(0) == '/') return;

            if(node == null){
                root = new Node(element);
                setNode(root);
            }
            else {
                Node child = new Node(element);
                node.add(child);
                setNode(child);
            }
        }
    }

    private void setInfo(String str) {
        if (info == null) info = new LinkedList<>();

        String[] temp = str.trim().split(">");

        for (String s : temp) info.add(getValue(s));
    }

    private Xml getXml(String str) {
        String[] temp = str.trim().split(" ");
        List<String> targets = new ArrayList<>();
        String element = getValue(temp[0]);
        String command = getValue(temp[1]);

        for (int i = 2; i < temp.length; i++) targets.add(getValue(temp[i]));

        return new Xml(element, command, targets);
    }

    private String getValue(String str) {
        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c != '<' && c != '>' && c != '!' && c != '[' && c != '(' && c != ')') {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
