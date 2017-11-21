/*
외판원 순회

문제
외판원 순회 문제는 영어로 Traveling Salesman problem (TSP) 라고 불리는 문제로 
computer science 분야에서 가장 중요하게 취급되는 문제 중 하나이다. 
여러 가지 변종 문제가 있으나, 여기서는 가장 일반적인 형태의 문제를 살펴보자.

1번부터 N번까지 번호가 매겨져 있는 도시들이 있고, 도시들 사이에는 길이 있다. 
(길이 없을 수도 있다) 이제 한 외판원이 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 
다시 원래의 도시로 돌아오는 순회 여행 경로를 계획하려고 한다. 단, 한번 갔던 도시로는 다시 갈 수 없다. 
(맨 마지막에 여행을 출발했던 도시로 돌아오는 것은 예외) 
이런 여행 경로는 여러 가지가 있을 수 있는데, 
가장 적은 비용을 들이는 여행 계획을 세우고자 한다.

각 도시간에 이동하는데 드는 비용은 행렬 W[i][j]형태로 주어진다. 
W[i][j]는 도시 i에서 도시 j로 가기 위한 비용을 나타낸다. 
비용은 대칭적이지 않다; 즉, W[i][j] 는 W[j][i]와 다를 수 있다. 
모든 도시간의 비용은 양의 정수이다. W[i][i]는 항상 0이다. 
경우에 따라서 도시 i에서 도시 j로 갈 수 없는 경우도 있으며 이럴 경우 W[i][j]=0이라고 하자.

N과 비용 행렬이 주어졌을 때, 가장 적은 비용을 들이는 외판원의 순회 여행 경로를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 도시의 수 N이 주어진다. (2<=N<=16) 
다음 N개의 줄에는 비용 행렬이 주어진다. 
각 행렬의 성분은 1,000,000 이하의 양의 정수이며, 갈 수 없는 경우는 0이 주어진다. 
W[i][j]는 도시 i에서 j로 가기 위한 비용을 나타낸다.

항상 순회할 수 있는 경우만 입력으로 주어진다.

출력
첫째 줄에 외판원의 순회에 필요한 최소 비용을 출력한다.

예제 입력  복사
4
0 10 15 20
5  0  9 10
6 13  0 12
8  8  9  0

예제 출력  복사
35
*/

import java.util.Arrays;
import java.util.Scanner;

public class _02098_TSP {
	
	public static int[][] W;
	public static int[][] dp;
	public static int N;
	public static final int INF = 1000000000;
	
	/**
	 * 
	 * @param current 현재 정점 위치
	 * @param visitedPath 현재까지 경로 ( 0000 0000 0000 0001 ~ 1111 1111 1111 1111 )
	 * @return
	 */
	public static int getShortestPath(int current, int visitedPath) {
		
		// 모든 정점을 다 들른 경우
		// 즉, N이 16일 때 1111 1111 1111 1111 인 경우
		// 마지막 정점에서 시작점으로의 경로를 리턴
		if (visitedPath == (1 << N) - 1)
			return W[current][1];

		// 이미 들렀던 경로이므로 바로 return
		if (dp[current][visitedPath] >= 0)
			return dp[current][visitedPath];

		int ret = INF;

		// 집합에서 다음에 올 원소를 고르자!
		for (int i = 1; i <= N; i++) {
			int next = i;

			System.out.println( "current: " + current + ", next: " + next + ", visitedPath: " + visitedPath );
			
			// 다음 방문 예정 정점이 기존 방문 경로에 포함이 되어 있을 경우 처리하지 않음.
			if ((visitedPath & (1 << (next - 1))) != 0)
				continue;
			
			// 간선 배열의 값이 0 이라는 것은 미연결 경로 이므로 처리하지 않음.
			if(W[current][next] == 0)
				continue;
			
			
			int temp = W[current][next] + getShortestPath(next, visitedPath + (1 << (next - 1)));
			ret = Math.min(ret, temp);
			
		}

		return dp[current][visitedPath] = ret;

	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		W = new int[N + 1][N + 1];
		dp = new int[N + 1][1 << N];
		
		// 간선 비용 초기화
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				W[i][j] = sc.nextInt();
			}
		}
		
		// 2차원 배열의 모든 원소를 -1로
		for (int i = 1; i <= N; i++) {
			Arrays.fill(dp[i], -1);
		}
		
		int start = 1;
		System.out.println(getShortestPath(start, 1));

	}
}
