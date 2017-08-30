import java.util.Scanner;

/*
구간 합 구하기 풀이

문제
어떤 N개의 수가 주어져 있다. 
그런데 중간에 수의 변경이 빈번히 일어나고 
그 중간에 어떤 부분의 합을 구하려 한다. 
만약에 1,2,3,4,5 라는 수가 있고, 
3번째 수를 6으로 바꾸고 2번째부터 5번째까지 합을 구하라고 한다면 17을 출력하면 되는 것이다. 
그리고 그 상태에서 다섯 번째 수를 2로 바꾸고 3번째부터 5번째까지 합을 구하라고 한다면 12가 될 것이다.

입력
첫째 줄에 수의 개수 N(1<=N<=1,000,000)과 M(1<=M<=10,000), K(1<=k<=10,000) 가 주어진다. 
M은 수의 변경이 일어나는 회수이고, K는 구간의 합을 구하는 회수이다. 
그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 
그리고 N+2번째 줄부터 N+M+K+1번째 줄까지 세 개의 정수 a,b,c가 주어지는데, 
a가 1인 경우 b번째 수를 c로 바꾸고 a가 2인 경우에는 b번째 수부터 c번째 수까지의 합을 구하여 출력하면 된다.

출력
첫째 줄부터 K줄에 걸쳐 구한 구간의 합을 출력한다. 
(단 정답은 long long 범위를 넘지 않는다)

예제 입력  복사
5 2 2
1
2
3
4
5
1 3 6
2 2 5
1 5 2
2 3 5

예제 출력  복사
17
12
*/
public class _02042_SegmentTreeRecursiveSum {

	static int [] array;
	static int [] tree;
	static int treeCnt;
	
	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		 int arrCnt = sc.nextInt();
		 int updateCnt = sc.nextInt();
		 int queryCnt = sc.nextInt();

		 int treeHeight = (int)Math.ceil(Math.log(arrCnt)/Math.log(2));
		 treeCnt = (1 << (treeHeight+1));
		 
		 array = new int [arrCnt+1];
		 tree = new int [treeCnt+1];
		 
		 /*System.out.println("Math.log() of arrCnt: " + Math.log(arrCnt));
		 System.out.println("Math.log(2): " + Math.log(2));
		 System.out.println("treeHeight: " + treeHeight);
		 System.out.println("treeCnt: " + treeCnt);*/
		 
		 for( int i = 1; i <= arrCnt; ++i ) {
			 array[i] = sc.nextInt();
		 }
		 
		 initSegmentTree( 1, 1, arrCnt );
		 
		 int loopCnt = updateCnt + queryCnt;
		 for( int i = 1; i <= loopCnt; ++i ) {
			 int commandType = sc.nextInt();
			 
			 // UPDATE
			 if( commandType == 1 ) {
				 int queryIndex = sc.nextInt();
				 long diff = sc.nextInt() - array[queryIndex];
				 
				 
				 System.out.println("queryIndex: " + queryIndex + ", diff: " + diff );
						 
				 updateSegmentTree( 1, 1, arrCnt, queryIndex, diff );
				 displaySegmentTree();
			 }
			 // QUERY
			 else if( commandType == 2 ) {
				 int startQueryIndex = sc.nextInt();
				 int endQueryIndex = sc.nextInt();
				 
				 System.out.println("startQueryIndex: " + startQueryIndex + ", endQueryIndex: " + endQueryIndex );
				 
				 System.out.println(querySegmentTree(1, 1, arrCnt, startQueryIndex, endQueryIndex));				 
			 }
		 }
		
	}
	
	public static void displaySegmentTree() {
		System.out.print("tree: ");
		 for( int i = 1; i <= treeCnt; ++i ) {
			 System.out.print(tree[i] + " ");
		 }
		 System.out.println();
	}
	
	public static void initSegmentTree( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex ) {
		
		if( arrayStartIndex == arrayEndIndex ) {
			tree[treeNodeIndex] = array[arrayStartIndex];
		}
		else {
			int mid = ( arrayStartIndex + arrayEndIndex ) / 2;
			initSegmentTree( treeNodeIndex*2, arrayStartIndex, mid );
			initSegmentTree( treeNodeIndex*2+1, mid+1, arrayEndIndex );
			tree[treeNodeIndex] = tree[treeNodeIndex*2] + tree[treeNodeIndex*2+1]; 
		}
		
	}
	
	public static void updateSegmentTree( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex, int queryIndex, long diff ) {
		
		if( queryIndex < arrayStartIndex || queryIndex > arrayEndIndex ) {
			return;
		}
		
		tree[treeNodeIndex] += diff;		
		
		if( arrayStartIndex != arrayEndIndex ) {
			int mid = (arrayStartIndex+arrayEndIndex)/2;
			updateSegmentTree( treeNodeIndex*2, arrayStartIndex, mid, queryIndex, diff );
			updateSegmentTree( treeNodeIndex*2+1, mid+1, arrayEndIndex, queryIndex, diff );
		}			
	}
	
	public static int querySegmentTree( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex, int queryStartIndex, int queryEndIndex ) {
		
		if( queryStartIndex > arrayEndIndex || queryEndIndex < arrayStartIndex ) {
			return 0;
		}
		
		if( queryStartIndex <= arrayStartIndex && arrayEndIndex <= queryEndIndex ) {
			return tree[treeNodeIndex];
		}
		
		int mid = (arrayStartIndex+arrayEndIndex)/2;
		int sum1 = querySegmentTree( treeNodeIndex*2, arrayStartIndex, mid, queryStartIndex, queryEndIndex );
		int sum2 = querySegmentTree( treeNodeIndex*2+1, mid+1, arrayEndIndex, queryStartIndex, queryEndIndex );
		return sum1 + sum2;
	}
}
