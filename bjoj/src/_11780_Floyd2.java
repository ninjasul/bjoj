/*
11780 - 플로이드 2 스페셜 저지
문제집 
시간 제한
메모리 제한
제출
정답
맞은 사람
정답 비율
1 초
256 MB
287
156
112
58.639%

문제
n(1≤n≤100)개의 도시가 있다. 
그리고 한 도시에서 출발하여 다른 도시에 도착하는 m(1≤m≤100,000)개의 버스가 있다. 
각 버스는 한 번 사용할 때 필요한 비용이 있다.
모든 도시의 쌍 (A, B)에 대해서 도시 A에서 B로 가는데 
필요한 비용의 최소값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 도시의 개수 n(1≤n≤100)이 주어지고 
둘째 줄에는 버스의 개수 m(1≤m≤100,000)이 주어진다. 
그리고 셋째 줄부터 m+2줄까지 다음과 같은 버스의 정보가 주어진다. 
먼저 처음에는 그 버스의 출발 도시의 번호가 주어진다. 
버스의 정보는 버스의 시작 도시 a, 도착 도시 b, 한 번 타는데 필요한 비용 c로 이루어져 있다. 
시작 도시와 도착 도시가 같은 경우는 없다. 비용은 100,000보다 작거나 같은 자연수이다.

출력
먼저, N개의 줄을 출력해야 한다. 
i번째 줄에 출력하는 j번째 숫자는 도시 i에서 j로 가는데 필요한 최소 비용이다. 
만약, i에서 j로 갈 수 없는 경우에는 그 자리에 0을 출력한다.
그 다음에는 N*N개의 줄을 출력해야 한다. 
i*N+j번째 줄에는 도시 i에서 도시 j로 가는 최소 비용에 포함되어 있는 도시의 개수 K를 출력한다. 
그 다음, 도시 i에서 도시 j로 가는 경로를 공백으로 구분해 출력한다. 
이 때, 도시 i와 도시 j도 출력해야 한다. 만약, i에서 j로 갈 수 없는 경우에는 0을 출력한다.

예제 입력 복사
5
14
1 2 2
1 3 3
1 4 1
1 5 10
2 4 2
3 4 1
3 5 1
4 5 3
3 5 10
3 1 8
1 4 2
5 1 7
3 4 2
5 2 4

예제 출력 복사
0 2 3 1 4 
12 0 15 2 5 
8 5 0 1 1 
10 7 13 0 3 
7 4 10 6 0 
0
2 1 2 
2 1 3 
2 1 4 
3 1 3 5 
4 2 4 5 1 
0
5 2 4 5 1 3 
2 2 4 
3 2 4 5 
2 3 1 
3 3 5 2 
0
2 3 4 
2 3 5 
3 4 5 1 
3 4 5 2 
4 4 5 1 3 
0
2 4 5 
2 5 1 
2 5 2 
3 5 1 3 
3 5 2 4 
0
*/
import java.util.*;

public class _11780_Floyd2 {
	
	static int [][] costs;
	static int [][] passthrus;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int vertexCnt = sc.nextInt();
		int edgeCnt = sc.nextInt();
		
		costs = new int[vertexCnt+1][vertexCnt+1];
		passthrus = new int[vertexCnt+1][vertexCnt+1];
		
		for( int i = 0; i < edgeCnt; ++i ) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int cost = sc.nextInt();
			
			if( costs[from][to] == 0 ) {
				costs[from][to] = cost;
			}
			else {
				costs[from][to] = Math.min(costs[from][to], cost);
			}			
		}
		
		for( int k = 1; k <= vertexCnt; ++k ) {
			for( int i = 1; i <= vertexCnt; ++i ) {
				for( int j = 1; j <= vertexCnt; ++j ) {
					
					if( i == j ) continue;
					
					if( costs[i][k] != 0 && costs[k][j] != 0 ) {
						if( costs[i][j] == 0 ) {
							costs[i][j] = costs[i][k] + costs[k][j];
							passthrus[i][j] = k;
						}
						else {
							if( costs[i][j] > costs[i][k] + costs[k][j] ) {
								costs[i][j] = costs[i][k] + costs[k][j];
								passthrus[i][j] = k;
							}
						}
					}
				}
			}
		}
		
		for( int i = 1; i <= vertexCnt; ++i ) {
			for( int j = 1; j <= vertexCnt; ++j ) {
				System.out.print( costs[i][j] + " " );
			}
			System.out.println();
		}
		
		for( int i = 1; i <= vertexCnt; ++i ) {
			for( int j = 1; j <= vertexCnt; ++j ) {				
				if( costs[i][j] != 0 ) {
					StringBuilder sb = new StringBuilder();
					sb.append(i + " " );
					sb = getPassthrus( i, j, sb );					
					sb.append(j);
					
					String printString = sb.toString();
					int length = printString.split(" ").length;
					System.out.println(length + " " + printString);
				}
				else {
					System.out.println(0);
				}
			}
		}
	}
	
	public static StringBuilder getPassthrus( int from, int to, StringBuilder sb  ) {
		
		if( passthrus[from][to] != 0 ) {
			int nextPassThru = passthrus[from][to];
			getPassthrus( from, nextPassThru, sb );
			sb.append(nextPassThru + " " );
			getPassthrus( nextPassThru, to, sb );
		}		
		
		return sb;
	}
}
