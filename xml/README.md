# XML Check
이 문서는 XML 문법 검사기에 대한 동작 과정 및 사용 방법에 대해 기술합니다.

## Class

#### 1. 모델

| 클래스 | 멤버변수                                                     | 멤버함수           | 상속관계               | 기능                                                         |
| ------ | ------------------------------------------------------------ | ------------------ | ---------------------- | ------------------------------------------------------------ |
| Node   | int idx;<br />Xml xml;<br />boolean closed;                  | getter<br />setter | defaultMutableTreeNode | Xml 트리 구조를 위한 노드 객체로 사용, Swing JTree를 사용하기 위해 TreeNode 상속 |
| Xml    | String element;<br />String command;<br />List<String> targets; | getter<br />setter | -                      | 실제 XML 문법을 가지고 있는 객체                             |

