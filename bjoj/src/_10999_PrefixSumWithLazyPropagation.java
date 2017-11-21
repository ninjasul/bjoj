/*
구간 합 구하기 2 풀이
문제
어떤 N개의 수가 주어져 있다. 
그런데 중간에 수의 변경이 빈번히 일어나고 그 중간에 어떤 부분의 합을 구하려 한다. 
만약에 1,2,3,4,5 라는 수가 있고, 3번째부터 4번째 수에 6을 더하면 1, 2, 9, 10, 5가 되고, 
여기서 2번째부터 5번째까지 합을 구하라고 한다면 26을 출력하면 되는 것이다. 
그리고 그 상태에서 1번째부터 3번째 수에 2를 빼고 2번째부터 5번째까지 합을 구하라고 한다면 22가 될 것이다.

입력
첫째 줄에 수의 개수 N(1<=N<=1,000,000)과 M(1<=M<=10,000), K(1<=k<=10,000) 가 주어진다. 
M은 수의 변경이 일어나는 회수이고, K는 구간의 합을 구하는 회수이다. 
그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 
그리고 N+2번째 줄부터 N+M+K+1번째 줄까지 세 개의 정수 a,b,c 또는 a,b,c,d가 주어지는데, 
a가 1인 경우 b번째 수부터 c번째 수에 d로 더하고, 
a가 2인 경우에는 b부터 c까지의 합을 구하여 출력하면 된다.

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
1 3 4 6
2 2 5
1 1 3 -2
2 2 5

예제 출력  복사
26
22
*/

import java.math.BigInteger;
import java.util.Scanner;

public class _10999_PrefixSumWithLazyPropagation {
	
	private static int[] array;
	private static int[] lazy;
	private static BigInteger[] tree;
	private static int treeCnt;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int arrCnt = sc.nextInt();
		int cmdCnt = sc.nextInt() + sc.nextInt();
		
		array = new int[arrCnt+1];
		for( int i = 1; i <= arrCnt; ++i ) {
			array[i] = sc.nextInt();
		}
		
		int treeHeight = (int)Math.ceil(Math.log(arrCnt)/Math.log(2));
		treeCnt = ( 1 << (treeHeight+1) );
		
		tree = new BigInteger[treeCnt+1];
		for( int i = 1; i <= treeCnt; ++i ) {
			tree[i] = new BigInteger("0");
		}
		lazy = new int[treeCnt+1];
		
		init( 1, 1, arrCnt );
		displaySegmentTree();
		
		for( int i = 1; i <= cmdCnt; ++i ) {
			int cmd = sc.nextInt();
			
			// 구간 변경
			if( cmd == 1 ) {
				int queryStartIndex = sc.nextInt();
				int queryEndIndex = sc.nextInt();
				BigInteger diff = sc.nextBigInteger();
				
				System.out.println("queryStartIndex: " + queryStartIndex + ", queryEndIndex: " + queryEndIndex + ", diff: " + diff );
				displaySegmentTree();
				displayLazy();
				update( 1, 1, arrCnt, queryStartIndex, queryEndIndex, diff );
				displaySegmentTree();
				displayLazy();
			}
			// 구간 합
			else if( cmd == 2 ) {
				int arrayStartIndex = sc.nextInt();
				int arrayEndIndex = sc.nextInt();				
								
				System.out.println(sum( 1, 1, arrCnt, arrayStartIndex, arrayEndIndex ));
			}
		}
	}
	
	
	private static void displaySegmentTree() {
		System.out.println("-----------------   SegmentTree   ---------------");
		for( int i = 1; i <= treeCnt; ++i ) {
			System.out.print(tree[i]+ " ");
		}
		System.out.println();
	}
	
	private static void displayLazy() {
		System.out.println("--------------------   Lazy  ------------------");
		for( int i = 1; i <= treeCnt; ++i ) {
			System.out.print(lazy[i]+ " ");
		}
		System.out.println();
	}
	
	private static void init( int treeNodeIndex, int startArrayIndex, int endArrayIndex ) {
		if( startArrayIndex == endArrayIndex ) {
			tree[treeNodeIndex] = BigInteger.valueOf(array[startArrayIndex]);
		}
		else {
			int mid = (startArrayIndex+endArrayIndex)/2;
			init( treeNodeIndex*2, startArrayIndex, mid );
			init( treeNodeIndex*2+1, mid+1, endArrayIndex );
			tree[treeNodeIndex] = tree[treeNodeIndex*2].add(tree[treeNodeIndex*2+1]);
		}
	}
	
	private static void updateLazy( int treeNodeIndex, int startArrayIndex, int endArrayIndex ) {
		
		// lazyUpdate 값이 존재하면 
		if( lazy[treeNodeIndex] != 0 ) {
			
			// 현재 트리 노드의 자식 개수 만큼 업데이트 값을 곱하여 기존 트리 노드 값에 더 해 줌.
			tree[treeNodeIndex] = tree[treeNodeIndex].add(BigInteger.valueOf((startArrayIndex-endArrayIndex+1) * lazy[treeNodeIndex]));
			
			// leaf 노드가 아니면 자식 노드들을 업데이트 해 줌.
			if( startArrayIndex != endArrayIndex ) {
				lazy[treeNodeIndex*2] += lazy[treeNodeIndex];
				lazy[treeNodeIndex*2+1] += lazy[treeNodeIndex];
			}
			lazy[treeNodeIndex] = 0;
		}		
	}
	
	private static void update( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex, int queryStartIndex, int queryEndIndex, BigInteger diff ) {
		updateLazy( treeNodeIndex, arrayStartIndex, arrayEndIndex );
		
		// 업데이트 대상 범위가 현재 트리 노드 범위 밖인 경우
		if( arrayEndIndex < queryStartIndex || arrayStartIndex > queryEndIndex ) {
			return;
		}
		
		// 현재 트리 노드 범위가 업데이트 대상 범위에 완전히 속하는 경우(해당 노드와 자식 노드들만 업데이트하고 나머지는 lazy 업데이트 수행) 
		if( queryStartIndex <= arrayStartIndex && queryEndIndex >= arrayEndIndex ) {
			tree[treeNodeIndex] = tree[treeNodeIndex].add(BigInteger.valueOf(arrayEndIndex-arrayStartIndex+1).multiply(diff));
			
			if( arrayStartIndex != arrayEndIndex ) {
				tree[treeNodeIndex*2] = tree[treeNodeIndex*2].add(diff);
				tree[treeNodeIndex*2+1] = tree[treeNodeIndex*2+1].add(diff);
			}
			return;
		}
		
		int mid = (arrayStartIndex+arrayEndIndex)/2;
		update( treeNodeIndex*2, arrayStartIndex, mid, queryStartIndex, queryEndIndex, diff );
		update( treeNodeIndex*2+1, mid+1, arrayEndIndex, queryStartIndex, queryEndIndex, diff );
		tree[treeNodeIndex] = tree[treeNodeIndex*2].add(tree[treeNodeIndex*2+1]);
	}
	
	private static BigInteger sum( int treeNodeIndex, int arrayStartIndex, int arrayEndIndex, int queryStartIndex, int queryEndIndex ) {
		updateLazy( treeNodeIndex, arrayStartIndex, arrayEndIndex );
		
		// 합계 대상 범위가 현재 트리 노드 범위 밖인 경우
		if( queryEndIndex < arrayStartIndex || queryStartIndex > arrayEndIndex ) 
			return new BigInteger("0");
		
		// 현재 트리 노드 범위가 합계 대상 범위에 완전히 속하는 경우(해당 트리 노드만 리턴)
		if( queryStartIndex <= arrayStartIndex && arrayEndIndex <= queryEndIndex ) {
			return tree[treeNodeIndex];			
		}
		
		int mid = (arrayStartIndex+arrayEndIndex)/2;
		return sum( treeNodeIndex*2, arrayStartIndex, mid, queryStartIndex, queryEndIndex ).add(sum( treeNodeIndex*2+1, mid+1, arrayEndIndex, queryStartIndex, queryEndIndex ));
	}
}
