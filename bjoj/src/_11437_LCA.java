/*
문제
N(2 ≤ N ≤ 50,000)개의 정점으로 이루어진 트리가 주어진다. 
트리의 각 정점은 1번부터 N번까지 번호가 매겨져 있으며, 루트는 1번이다.

두 노드의 쌍 M(1 ≤ M ≤ 10,000)개가 주어졌을 때, 두 노드의 가장 가까운 공통 조상이 몇 번인지 출력한다.

입력
첫째 줄에 노드의 개수 N이 주어지고, 
다음 N-1개 줄에는 트리 상에서 연결된 두 정점이 주어진다. 
그 다음 줄에는 가장 가까운 공통 조상을 알고싶은 쌍의 개수 M이 주어지고, 
다음 M개 줄에는 정점 쌍이 주어진다.

출력
M개의 줄에 차례대로 입력받은 두 정점의 가장 가까운 공통 조상을 출력한다.

예제 입력  복사
15
1 2
1 3
2 4
3 7
6 2
3 8
4 9
2 5
5 11
7 13
10 4
11 15
12 5
14 7
6
6 11
10 9
2 6
7 6
8 13
8 15

예제 출력  복사
2
4
2
1
3
1
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class _11437_LCA {
	static public void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int vertexCnt = sc.nextInt();
		List<Integer> [] edgeList = (List<Integer>[]) new List[vertexCnt+1];
		
		for( int i = 1; i <= vertexCnt; ++i ) {
			edgeList[i] = new ArrayList<Integer>();
		}
				
		for( int i = 1; i < vertexCnt; ++i ) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			
			edgeList[from].add(to);
			edgeList[to].add(from);
		}
		
		// BFS 를 통해 각 정점들의 depth 를 구함
		int [] depth = new int[vertexCnt+1];
		int [] parent = new int[vertexCnt+1];
		boolean [] visited = new boolean[vertexCnt+1];
		Queue<Integer> queue = new LinkedList<Integer>();
		
		// 시작점은 1
		parent[1] = 1;
		depth[1] = 1;
		visited[1] = true;
		queue.add(1);		
		
		while( !queue.isEmpty() ) {
						
			int curVertex = queue.remove();
			
			for( int nextVertex : edgeList[curVertex] ) {
				if( !visited[nextVertex] ) {
					depth[nextVertex] = depth[curVertex] + 1;
					parent[nextVertex] = curVertex;
					visited[nextVertex] = true;
					queue.add(nextVertex);
				}				
			}
		}
		
		// 구하고자 하는 LCA 개수
		int lcaCnt = sc.nextInt();
		
		for( int i = 0; i < lcaCnt; ++i ) {
			int vertex1 = sc.nextInt();
			int vertex2 = sc.nextInt();
			
			// 두 정점의 depth 를 같게 맞춰준다.
			while( depth[vertex1] != depth[vertex2] ) {
				if( depth[vertex1] < depth[vertex2] )
					vertex2 = parent[vertex2];
				else
					vertex1 = parent[vertex1];
			}
			// depth 가 같은 상태에서는 두 정점이 만날 때 까지 하나씩 위로 올라감.  
			while( vertex1 != vertex2 ) {
				vertex1 = parent[vertex1];
				vertex2 = parent[vertex2];
			}
			// 만난 정점을 출력
			System.out.println(vertex1);
		}		
	}
}
