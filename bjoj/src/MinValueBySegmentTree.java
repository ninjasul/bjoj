/*
문제
N(1 ≤ N ≤ 100,000)개의 정수들이 있을 때, a번째 정수부터 b번째 정수까지 중에서
제일 작은 정수를 찾는 것은 어려운 일이 아니다.
하지만 이와 같은 a, b의 쌍이 M(1 ≤ M ≤ 100,000)개 주어졌을 때는 어려운 문제가 된다.
이 문제를 해결해 보자.

여기서 a번째라는 것은 입력되는 순서로 a번째라는 이야기이다.
예를 들어 a=1, b=3이라면 입력된 순서대로 1번, 2번, 3번 정수 중에서 최소값을 찾아야 한다.
각각의 정수들은 1이상 1,000,000,000이하의 값을 갖는다.

입력
첫째 줄에 N, M이 주어진다. 다음 N개의 줄에는 N개의 정수가 주어진다. 다음 M개의 줄에는 a, b의 쌍이 주어진다.

출력
M개의 줄에 입력받은 순서대로 각 a, b에 대한 답을 출력한다.

예제 입력  복사
10 4
75
30
100
38
50
51
52
20
81
5
1 10
3 5
6 9
8 10

예제 출력  복사
5
38
20
5
*/

import java.util.Scanner;

public class _10868_MinValueBySegmentTree {
	
	private static int cnt;
	private static int treeCnt;
	private static int [] array;
	private static int [] segmentTree;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		cnt = sc.nextInt();
		array = new int[cnt+1];
		int treeHeight = (int)Math.ceil(Math.log(cnt)/Math.log(2));
		treeCnt = (1 << treeHeight);
		segmentTree =  new int[treeCnt+1];
				
		for( int i = 1; i <= cnt; ++i ) {
			array[i] = sc.nextInt();
		}
		
		initSegmentTree( 1, 1, cnt );		
	}
	
	private static void initSegmentTree( int treeNodeIndex, int startIndex, int endIndex ) {
		
		System.out.println("initSegmentTree - treeNodeIndex: " + treeNodeIndex + ", startIndex: " + startIndex + ", endIndex: " + endIndex );
		
		for( int i = 1; i <= treeCnt; ++i ) {
			System.out.print(segmentTree[i] + " ");
		}
		System.out.println();
		
		if( startIndex == endIndex ) {
			segmentTree[treeNodeIndex] = array[startIndex];
		}
		else {
			initSegmentTree( treeNodeIndex*2, startIndex, (startIndex+endIndex)/2 );
			initSegmentTree( treeNodeIndex*2+1, (startIndex+endIndex)/2+1, endIndex );
			segmentTree[treeNodeIndex] = Math.min( segmentTree[treeNodeIndex*2], segmentTree[treeNodeIndex*2+1] );
		}
	}
		
}
