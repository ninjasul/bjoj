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

import java.math.BigInteger;
import java.util.Scanner;

public class _02042_SegmentTreeNonRecursiveSum {
		
	static int leafNodeIndex = 1;  
	static BigInteger [] tree;
	static int treeCnt;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int arrCnt = sc.nextInt();
		int cmdCnt = sc.nextInt() + sc.nextInt();
		
		int [] array = new int[arrCnt+1];
		int treeHeight = (int)Math.ceil(Math.log(arrCnt)/Math.log(2));
		treeCnt = ( 1 << (treeHeight+1) );
		leafNodeIndex = ( 1 << treeHeight )-1;
		
		/*
		System.out.println("treeHeight: " + treeHeight);
		System.out.println("treeCnt: " + treeCnt);
		System.out.println("leafNodeIndex: " + leafNodeIndex);
		*/
		
		tree = new BigInteger[treeCnt+1];		
		for( int i = 1; i <= treeCnt; ++i ) {
			tree[i] = new BigInteger("0");
		}
		
		for( int i = 1+leafNodeIndex; i <= arrCnt+leafNodeIndex; ++i ) {
			updateSegmentTree( i, BigInteger.valueOf(sc.nextInt()) );			
		}
		
		//displaySegmentTree();
		
		while( cmdCnt > 0 ) {
			int cmd = sc.nextInt();
			
			if( cmd == 1 ) {
				int treeIndex = sc.nextInt() + leafNodeIndex;
				BigInteger treeValue = sc.nextBigInteger().subtract(tree[treeIndex]);
								
				updateSegmentTree( treeIndex, treeValue );
				//displaySegmentTree();
			}
			else if( cmd == 2 ) {
				int queryStartIndex = sc.nextInt() + leafNodeIndex;
				int queryEndIndex = sc.nextInt() + leafNodeIndex;
								
				System.out.println(querySegmentTree( queryStartIndex, queryEndIndex ));				
			}
			cmdCnt--;
		}
	}

	public static void displaySegmentTree() {
		System.out.print("tree: ");
		 for( int i = 1; i <= treeCnt; ++i ) {
			 System.out.print(tree[i] + " ");
		 }
		 System.out.println();
	}
	
	public static void updateSegmentTree( int treeIndex, BigInteger diff ) {
		tree[treeIndex] = tree[treeIndex].add(diff);
		//System.out.println("treeIndex: " + treeIndex);
		
		while( treeIndex > 1 ) {
			tree[treeIndex/2] = tree[treeIndex/2].add(diff);;			
			treeIndex /= 2;
			//System.out.println("treeIndex: " + treeIndex);
		}
	}
	
	public static BigInteger querySegmentTree( int queryStartIndex, int queryEndIndex ) {
		
		BigInteger answer = new BigInteger("0");
		while( queryStartIndex < queryEndIndex ) {
			// 왼쪽 자식일 때(부모 노드의 범위가 자식노드의 범위를 포함함)
			if( queryStartIndex%2 == 0 ) {
				queryStartIndex /= 2;
				
			}
			// 오른쪽 자식일 때(  
			else {
				answer = answer.add(tree[queryStartIndex]);
				queryStartIndex += 1;
				queryStartIndex /= 2;
			}
			
			// 오른쪽 자식일 때
			if( queryEndIndex%2 == 1 ) {
				queryEndIndex /= 2;
			}
			// 왼쪽 자식일 때
			else {
				answer = answer.add(tree[queryEndIndex]);				
				queryEndIndex -= 1;
				queryEndIndex /= 2;				
			}			
		}
		
		if( queryStartIndex == queryEndIndex ) {
			answer = answer.add(tree[queryStartIndex]);
		}
		
		return answer;
	}
}
