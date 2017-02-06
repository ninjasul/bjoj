
/*
01197 - 네트워크 연결

최소 스패닝 트리

문제
그래프가 주어졌을 때, 그 그래프의 최소 스패닝 트리를 구하는 프로그램을 작성하시오.
최소 스패닝 트리는, 주어진 그래프의 모든 정점들을 연결하는 부분 
그래프 중에서 그 가중치의 합이 최소인 트리를 말한다.

입력
첫째 줄에 정점의 개수 V(1≤V≤10,000)와 간선의 개수 E(1≤E≤100,000)가 주어진다. 
다음 E개의 줄에는 각 간선에 대한 정보를 나타내는 세 정수 A, B, C가 주어진다. 
이는 A번 정점과 B번 정점이 가중치 C인 간선으로 연결되어 있다는 의미이다. 
C는 음수일 수도 있으며, 절대값이 1,000,000을 넘지 않는다.

출력
첫째 줄에 최소 스패닝 트리의 가중치를 출력한다.

예제 입력
7 22
1 2 2
2 1 2
1 5 3
5 1 3
2 5 1
5 2 1
2 3 4
3 2 4
2 4 3
4 2 3
3 4 1
4 3 1
5 4 5
4 5 5
3 7 4
7 3 4
4 6 7
6 4 7
4 7 5
7 4 5
6 7 2
7 6 2


예제 출력
13
 */

import java.util.*;

class Edge implements Comparable<Edge> {
	public int from;
	public int to;
	public int cost;

	public Edge(int from, int to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Edge target) {
		return new Integer(cost).compareTo(new Integer(target.cost));

	}
}

public class _01197_MSTWithKruscal {
	
	static int [] parent = null;
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int nodeCnt = sc.nextInt();
		int edgeCnt = sc.nextInt();

		// 우선순위 큐 선언 - cost ASC로 간선을 정렬
		Queue<Edge> edgeQueue = new PriorityQueue<Edge>(edgeCnt, new Comparator<Edge>() {
			public int compare(Edge source, Edge target) {
				return source.compareTo(target);
			}
		});

		// 집합 여부를 판단하기 위한 parent 배열 선언
		parent = new int[nodeCnt+1];
		for( int i = 0; i <= nodeCnt; ++i )
			parent[i] = i;
				
		// 간선 리스트를 큐에 삽입
		for (int i = 0; i < edgeCnt; ++i) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int cost = sc.nextInt();

			edgeQueue.add(new Edge(from, to, cost));				
		}


		// 큐가 빌 때 까지 루프를 수행
		int costSum = 0;
		while (edgeQueue.peek() != null) {
			Edge curEdge = edgeQueue.remove();
			
			// find() 메소드를 통해 각 정점의 부모 노드 번호를 얻어옴.
			int rootOfFrom = find(curEdge.from);
			int rootOfTo = find(curEdge.to);
			
			// 시작 정점과 끝 정점의 부모가 다를 경우
			if( rootOfFrom != rootOfTo )  {
				//System.out.println("from: " + curEdge.from + ", to: " + curEdge.to + ", cost: " + curEdge.cost);
				
				// 같은 집합으로 만들어 줌
				union( rootOfFrom, rootOfTo );
				
				// 최종 결과인 비용 합에 더해 줌.
				costSum += curEdge.cost;
			}			
		}

		System.out.println(costSum);
	}
	
	public static void union(int nodeNum1, int nodeNum2 ) {	
		int parent1 = find(nodeNum1);
		int parent2 = find(nodeNum2);
		parent[parent2] = parent1;
	}
	
	public static int find( int nodeNum ) {
		
		if( nodeNum == parent[nodeNum] ) {
			return nodeNum;
		}
		else {
			int foundNum = find(parent[nodeNum]);
			parent[nodeNum] = foundNum;
			return parent[nodeNum];
		}		
	}
	
}
