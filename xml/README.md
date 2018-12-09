# XML Check
이 문서는 XML 문법 검사기에 대한 동작 과정 및 사용 방법에 대해 기술합니다.

## Class

#### 1. 모델

| 클래스 | 멤버변수                                                     | 멤버함수           | 상속관계               | 기능                                                         |
| ------ | ------------------------------------------------------------ | ------------------ | ---------------------- | ------------------------------------------------------------ |
| Node   | int idx;<br />Xml xml;<br />boolean closed;                  | getter<br />setter | defaultMutableTreeNode | Xml 트리 구조를 위한 노드 객체로 사용, Swing JTree를 사용하기 위해 TreeNode 상속 |
| Xml    | String element;<br />String command;<br />List<String> targets; | getter<br />setter | -                      | 실제 XML 문법을 가지고 있는 객체                             |

#### 2. 입력

| 클래스 | 멤버변수                                                     | 멤버함수                                  | 상속관계 | 기능                                                         |
| ------ | ------------------------------------------------------------ | ----------------------------------------- | -------- | ------------------------------------------------------------ |
| Input  | int idx;<br />Node root;<br />List<Xml> xmls;<br />Queue<String> info; | List<Xml> getXmls();<br />Node process(); | -        | 텍스트파일을 읽어서 XML문법을 저장하고, 원소들을 트리형태로 구조화 시켜 저장 |

#### 4. 조건식

	공통 로직 read
	(1) 노드의 XML 적용 대상(타겟)을 읽음
	(2) 노드의 실제 자식노드를 읽음

	에러
	True = 해당 노드 번호
	False = 0

	1) SEQUENCE – 순서에 맞게 타겟 모두 포함
		private int seq(final Node node);

		(1) read
		(2) 타겟 노드의 수와 자식 노드의 수가 같지 않으면 에러(true) 반환
		(3) 타겟 노드의 순서와 자식 노드의 순서를 비교해서 같지 않으면 에러(true) 반환
		(4) 에러(false) 반환

	2) OR – 타겟 중 하나를 포함
		private int or(final Node node);

		(1) read
		(2) 자식 노드가 없으면 에러(true) 반환
		(3) 타겟 노드들 중 하나라도 자식 노드와 일치하면 에러(false) 반환
		(4) 에러(true) 반환
		
	3) AND – 타겟 모두 포함, 순서와 상관없음 
		private int and(final Node node);

		(1) read
		(2) check 배열 생성
		(3) 타겟 노드의 수와 자식 노드의 수가 같지 않으면 에러(true) 반환
		(4) 타겟 노드와 자식 노드를 비교하고 일치하는 노드를 check 배열에 표시
		(5) check 배열이 하나라도 false가 있으면 에러(true) 반환
		(6) 에러(false) 반환

	4) QUESTION – 0 개 이상
		private int quest(final Node node);

		(1) read
		(2) 자식 노드가 없으면 에러(false) 반환
		(3) 타겟 노드들 중 자식 노드와 같지 않은 노드가 있으면 에러(true) 반환
		(4) 에러(false) 반환

	5) STAR – 0 개 이상의 반복
		private int star(final Node node);
		
		(1) read
		(2) 자식 노드가 없으면 에러(false) 반환
		(3) 타겟 노드들 중 자식 노드와 같지 않은 노드가 있으면 에러(true) 반환
		(4) 에러(false) 반환

	6) PLUS – 1 개 이상의 반복
		private int plus(final Node node);

		(1) read
		(2) 자식 노드가 없으면 에러(true) 반환
		(3) 타겟 노드들 중 자식 노드와 같지 않은 노드가 있으면 에러(true) 반환
		(4) 에러(false) 반환