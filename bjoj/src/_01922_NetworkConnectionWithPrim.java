
/*
01922 - 네트워크 연결

문제집 

문제
도현이는 컴퓨터와 컴퓨터를 모두 연결하는 네트워크를 구축하려 한다. 
하지만 아쉽게도 허브가 있지 않아 컴퓨터와 컴퓨터를 직접 연결하여야 한다. 
런데 모두가 자료를 공유하기 위해서는 모든 컴퓨터가 연결이 되어 있어야 한다. 
(a와 b가 연결이 되어 있다는 말은 a에서 b로의 경로가 존재한다는 것을 의미한다. 
a에서 b를 연결하는 선이 있고, b와 c를 연결하는 선이 있으면 a와 c는 연결이 되어 있다.)
그런데 이왕이면 컴퓨터를 연결하는 비용을 최소로 하여야 
컴퓨터를 연결하는 비용 외에 다른 곳에 돈을 더 쓸 수 있을 것이다. 
이제 각 컴퓨터를 연결하는데 필요한 비용이 주어졌을 때 
모든 컴퓨터를 연결하는데 필요한 최소비용을 출력하라. 
모든 컴퓨터를 연결할 수 없는 경우는 없다.

입력
첫째 줄에 컴퓨터의 수(1<=N<=1000)가 주어진다.
둘째 줄에는 연결할 수 있는 선의 수(1<=M<=100,000)가 주어진다.
셋째 줄부터 M+2번째 줄까지 총 M개의 줄에 각 컴퓨터를 연결하는데 드는 비용이 주어진다. 
이 비용의 정보는 세 개의 정수로 주어지는데, 만약에 a b c 가 주어져 있다고 하면 
a컴퓨터와 b컴퓨터를 연결하는데 비용이 c만큼 든다는 것을 의미한다.

출력
모든 컴퓨터를 연결하는데 필요한 최소비용을 첫째 줄에 출력한다.

예제 입력
6
9
1 2 5
1 3 4
2 3 2
2 4 7
3 4 6
3 5 11
4 5 3
4 6 8
5 6 8

예제 출력
23
 */

import java.util.*;


class Edge {
	public int from;
	public int to;
	public int cost;
	
	public Edge() {
		this(0, 0, 0);
	}
	
	public Edge(int from, int to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}
}

public class _01922_NetworkConnectionWithPrim {
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int nodeCnt = sc.nextInt();
		int edgeCnt = sc.nextInt();
		
		
		// 간선 리스트 선언
		List<Edge> [] edgeList = (List<Edge>[]) new List[nodeCnt+1];
		for( int i = 1; i <= nodeCnt; ++i )
		{
			edgeList[i] = new ArrayList<Edge>();
		}
		
		// 양방향 그래프로 간선리스트 초기화
		for (int i = 0; i < edgeCnt; ++i) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int cost = sc.nextInt();

			edgeList[from].add(new Edge(from, to, cost));
			edgeList[to].add(new Edge(to, from, cost));
		}

		// 비용 오름차순으로 정렬된 간선의 minHeap을 선언, 비용 오름차순 구현을 위해 compare() 메소드 구현
		Queue<Edge> edgeMinHeap = new PriorityQueue<Edge>(edgeCnt, new Comparator<Edge>() {
			public int compare(Edge source, Edge target) {
				return Integer.compare(source.cost, target.cost);
			}
		});
		
		// 정점 별 방문 여부 배열 선언 
		boolean[] visited = new boolean[nodeCnt+1];
		
		// 정점 1 부터 시작
		visited[1] = true;
		
		// 정점 1부터 시작하는 간선리스트를 비용 오름 차순으로 minHeap에 삽입 
		for( Edge curEdge : edgeList[1] )
		{
			edgeMinHeap.add(curEdge);
		}

		// 결과 값인 총 비용 선언
		int costSum = 0;

		for( int i = 0; i < nodeCnt; ++i )
		{		
			Edge curEdge = new Edge();
			
			while ( edgeMinHeap.peek() != null )
			{
				curEdge = edgeMinHeap.remove();
				
				if( visited[curEdge.to] == false )
					break;
			}

			visited[curEdge.to] = true;
			costSum += curEdge.cost;
			
			for( Edge nextEdge : edgeList[curEdge.to] )
			{
				edgeMinHeap.add(nextEdge);
			}
		}		

		System.out.println(costSum);
	}
}
