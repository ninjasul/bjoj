/*
최소비용 구하기

문제
n(1≤n≤1,000)개의 도시가 있다. 그리고 한 도시에서 출발하여 다른 도시에 도착하는 m(1≤m≤100,000)개의 버스가 있다. 
우리는 A번째 도시에서 B번째 도시까지 가는데 드는 버스 비용을 최소화 시키려고 한다. 
그러면 A번째 도시에서 B번째 도시 까지 가는데 드는 최소비용을 출력하여라.

입력
첫째 줄에 도시의 개수 n(1≤n≤1,000)이 주어지고 둘째 줄에는 버스의 개수 m(1≤m≤100,000)이 주어진다. 
그리고 셋째 줄부터 m+2줄까지 다음과 같은 버스의 정보가 주어진다. 
먼저 처음에는 그 버스의 출발 도시의 번호가 주어진다. 
그리고 그 다음에는 도착지의 도시 번호가 주어지고 또 그 버스 비용이 주어진다. 
버스 비용은 0보다 크거나 같고, 100,000보다 작은 정수이다.
그리고 m+3째 줄에는 우리가 구하고자 하는 구간 출발점의 도시번호와 도착점의 도시번호가 주어진다. 
출발점에서 도착점을 갈 수 있는 경우만 입력으로 주어진다.

출력
첫째 줄에 출발 도시에서 도착 도시까지 가는데 드는 최소 비용을 출력한다.

예제 입력 복사
5
8
1 2 2
1 3 3
1 4 1
1 5 10
2 4 2
3 4 1
3 5 1
4 5 3
1 5

예제 출력 복사
4
 */

import java.util.*;

public class _01916_MinimumCostWithDijkstra {

	static class Edge {
		public int to;
		public int cost;
		
		public Edge( int to, int cost ) {
			this.to = to;
			this.cost = cost;
		}
	}
	
	static boolean [] visited;			// 각 정점의 방문여부 배열
	static int [] costs;				// 시작점으로부터 각 정점까지의 방문비용
	static List<Edge> [] edgeLists;		// 간선리스트
	static final int INFINITE_COST = Integer.MAX_VALUE-1;	// 비용 초기값
	
	static public void main(String [] args) {
		
		Scanner sc = new Scanner(System.in);
		int vertexCnt = sc.nextInt();
		int edgeCnt = sc.nextInt();
		
		// 정점의 개수만큼 방문여부 배열, 방문비용, 간선리스트 선언
		visited = new boolean [vertexCnt+1];
		costs = new int [vertexCnt+1];
		edgeLists = (List<Edge> []) new List [vertexCnt+1]; 
		
		// 초기화
		for( int i = 1; i <= vertexCnt; ++i ) {
			visited[i] = false;
			costs[i] = INFINITE_COST;
			edgeLists[i] = new ArrayList<Edge>();
		}
		
		// 간선 초기화
		for( int i = 0; i < edgeCnt; ++i ) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int cost = sc.nextInt();
			
			edgeLists[from].add(new Edge(to, cost));
			//edgeLists[to].add(new Edge(from, cost));
		}
		
		// 시작점과 끝점을 입력 
		int startVertex = sc.nextInt();
		int endVertex = sc.nextInt();
				
		costs[startVertex] = 0;
		int minVertex = 0;
		
		do {				
			minVertex = -1;
			int minCost = Integer.MAX_VALUE;			
			
			// 방문하지 않은 정점 중 최소비용 정점을 찾아냄.   
			for( int j = 1; j <= vertexCnt; ++j ) {
				if( !visited[j] && minCost > costs[j] ) {
					minCost = costs[j];
					minVertex = j;
				}
			}
		
			// 미방문 정점이 존재하는 경우
			if( minVertex != -1 ) {
								
				// 해당 정점을 방문처리
				visited[minVertex] = true;
				
				// 해당 정점으로 부터 연결된 모든 간선에 대해
				for( Edge curEdge : edgeLists[minVertex] ) {
					int to = curEdge.to;
					int cost = curEdge.cost;
					
					// 비용 배열을 업데이트
					if( costs[minVertex] != INFINITE_COST && costs[to] > costs[minVertex] + cost ) {
						costs[to] = costs[minVertex] + cost;
					}
				}
			}
			
		} while( minVertex != -1 ); // 미방문 정점이 존재하는 동안 루프 수행
		
		System.out.println(( costs[endVertex] == INFINITE_COST ) ? -1 : costs[endVertex] );
	}
}
