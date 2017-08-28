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
		int queryCnt = sc.nextInt();
		
		array = new int[cnt+1];
		int treeHeight = (int)Math.ceil(Math.log(cnt)/Math.log(2));
		treeCnt = (1 << (treeHeight+1));
		segmentTree =  new int[treeCnt+1];
				
		for( int i = 1; i <= cnt; ++i ) {
			array[i] = sc.nextInt();
		}
		System.out.println();
		
		initSegmentTree( 1, 1, cnt );	
		
		for( int j = 0; j < queryCnt; ++j ) {
			int queryStartArrayIndex = sc.nextInt();
			int queryEndArrayIndex = sc.nextInt();
			System.out.println(querySegmentTree( 1, 1, cnt, queryStartArrayIndex, queryEndArrayIndex ));			
		}
		/*
		for( int i = 1; i < treeCnt; ++i ) {
			System.out.print(segmentTree[i] + " ");
		}
		System.out.println();
		*/
	}
	
	private static void initSegmentTree( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex ) {
		
		/*System.out.println("initSegmentTree - treeNodeIndex: " + treeNodeIndex + ", arrayStartIndex: " + arrayStartIndex + ", arrayEndIndex: " + arrayEndIndex );
		
		for( int i = 1; i < treeCnt; ++i ) {
			System.out.print(segmentTree[i] + " ");
		}
		System.out.println();*/

		if( arrayStartIndex == arrayEndIndex ) {
			segmentTree[treeNodeIndex] = array[arrayStartIndex];
			//System.out.println("initSegmentTree - set Leaf Node: " + treeNodeIndex );
		}
		else {
			int arrayMidIndex = (arrayStartIndex+arrayEndIndex)/2;
			initSegmentTree( treeNodeIndex*2, arrayStartIndex, arrayMidIndex  );
			initSegmentTree( treeNodeIndex*2+1, arrayMidIndex+1, arrayEndIndex );
			segmentTree[treeNodeIndex] = Math.min( segmentTree[treeNodeIndex*2], segmentTree[treeNodeIndex*2+1] );
			//System.out.println("initSegmentTree - set Node: " + treeNodeIndex );
		}
	}
	
	private static int querySegmentTree( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex, int queryStartArrayIndex, int queryEndArrayIndex ) {
		if( queryStartArrayIndex > arrayEndIndex || queryEndArrayIndex < arrayStartIndex ) {
			return -1;
		}
		else if( queryStartArrayIndex <= arrayStartIndex && queryEndArrayIndex >= arrayEndIndex ) {
			return segmentTree[treeNodeIndex];
		}
		
		int arrayMidIndex = (arrayStartIndex+arrayEndIndex)/2;
		int minValue1 = querySegmentTree( 2*treeNodeIndex, arrayStartIndex, arrayMidIndex, queryStartArrayIndex, queryEndArrayIndex );
		int minValue2 = querySegmentTree( 2*treeNodeIndex+1, arrayMidIndex+1, arrayEndIndex, queryStartArrayIndex, queryEndArrayIndex );
		
		if( minValue1 == -1 ) return minValue2;
		else if( minValue2 == -1 ) return minValue1;
		else return Math.min(minValue1, minValue2);
	}
}
