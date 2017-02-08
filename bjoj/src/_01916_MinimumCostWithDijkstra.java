import java.util.Scanner;

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
	
	static boolean [] isVisited;
	static int [] costs;
	static List<Edge> [] edgeLists;
	static final int INFINITE_COST = Integer.MAX_VALUE;
	
	static public void main(String [] args) {
		
		Scanner sc = new Scanner(System.in);
		int vertexCnt = sc.nextInt();
		int edgeCnt = sc.nextInt();
		
		isVisited = new boolean [vertexCnt+1];
		costs = new int [vertexCnt+1];
		edgeLists = (ArrayList<Edge> []) new ArrayList [vertexCnt+1]; 
		
		for( int i = 1; i <= vertexCnt; ++i ) {
			isVisited[i] = false;
			costs[i] = INFINITE_COST;
			edgeLists[i] = new ArrayList<Edge>();
		}
		
		for( int i = 0; i < edgeCnt; ++i ) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int cost = sc.nextInt();
			
			edgeLists[from].add(new Edge(to, cost));
			edgeLists[to].add(new Edge(from, cost));
		}
		
		int startVertex = sc.nextInt();
		int endVertex = sc.nextInt();
				
		Queue<Integer> nextVertexes = new LinkedList<Integer>();
		nextVertexes.add(startVertex);
		costs[startVertex] = 0;
		
		while( !nextVertexes.isEmpty() ) {
			int from = nextVertexes.remove();
			
			isVisited[from] = true;
			
			for( Edge curEdge : edgeLists[from] ) {
				int to = curEdge.to;
				int cost = curEdge.cost;
				
				if( !isVisited[to] )
					nextVertexes.add(to);
				
				if( costs[from] != INFINITE_COST && costs[to] > costs[from] + cost ) {
					costs[to] = costs[from] + cost;
				}
			}			
		}
		
		System.out.println(( costs[endVertex] == INFINITE_COST ) ? -1 : costs[endVertex] );
	}
}
