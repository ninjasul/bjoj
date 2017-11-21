/*
문제
N×N개의 수가 N×N 크기의 표에 채워져 있다. 그런데 중간에 수의 변경이 빈번히 일어나고 
그 중간에 어떤 부분의 합을 구하려 한다. 표의 i행 j열은 (i, j)로 나타낸다.

예를 들어, N = 4이고, 표가 아래와 같이 채워져 있는 경우를 살펴보자.

1	2	3	4
2	3	4	5
3	4	5	6
4	5	6	7

여기서 (2, 2)부터 (3, 4)까지 합을 구하면 3+4+5+4+5+6 = 27이 된다. 
(2, 3)을 7로 바꾸고 (2, 2)부터 (3, 4)까지 합을 구하면 3+7+5+4+5+6=30 이 된다.

표에 채워져 있는 수와 변경하는 연산과 합을 구하는 연산이 주어졌을 때, 
이를 처리하는 프로그램을 작성하시오.

입력
첫째 줄에 표의 크기 N과 수행해야 하는 연산의 수 M이 주어진다. 
(1 ≤ N ≤ 1024, 1 ≤ M ≤ 100,000) 둘째 줄부터 N개의 줄에는 표에 채워져있는 수가 1행부터 차례대로 주어진다. 
다음 M개의 줄에는 네 개의 정수 w, x, y, c 또는 다섯 개의 정수 w, x1, y1, x2, y2 가 주어진다. 
w = 0인 경우는 (x, y)를 c로 바꾸는 연산이고, w = 1인 경우는 (x1, y1)부터 (x2, y2)의 합을 구해 출력하는 연산이다. 
(1 ≤ c ≤ 1,000) 표에 채워져 있는 수는 1,000보다 작거나 같은 자연수이다.

출력
w = 1인 입력마다 구한 합을 순서대로 한 줄에 하나씩 출력한다.

예제 입력  복사
4 5
1 2 3 4
2 3 4 5
3 4 5 6
4 5 6 7
1 2 2 3 4
0 2 3 7
1 2 2 3 4
0 3 4 5
1 3 4 3 4

예제 출력  복사
27
30
5
 */

import java.util.Scanner;

public class _11658_PrefixSumWithBIT2 {
	
	static private int [][] array;
	static private int [][] tree;
	static private int arrCnt;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		arrCnt = sc.nextInt();
		int cmdCnt = sc.nextInt();
		
		array = new int[arrCnt+1][arrCnt+1];
		tree = new int[arrCnt+1][arrCnt+1];
		
		for( int i = 1; i <= arrCnt; ++i ) {
			for( int j = 1; j <= arrCnt; ++j ) {
				int curNum = sc.nextInt();
				array[i][j] = curNum; 
				update( i, j, curNum );
				//displayArray();
				//displayTree();
			}
		}
						
		for( int i = 1; i <= cmdCnt; ++i ) {
			int cmdType = sc.nextInt();
			
			// 구간 변경
			if( cmdType == 0 ) {
				int row = sc.nextInt();
				int col = sc.nextInt();
				int value = sc.nextInt();
								
				update( row, col, value - array[row][col] );
				array[row][col] = value;
				
				//displayArray();
				//displayTree();
			}
			// 구간 합
			else if( cmdType == 1 ) {
				int startRow = sc.nextInt();
				int startCol = sc.nextInt();
				int endRow = sc.nextInt();
				int endCol = sc.nextInt();
				
				System.out.println(sum( endRow, endCol) - sum(startRow-1, endCol) - sum(endRow, startCol-1) + sum(startRow-1, startCol-1));
			}
		}	
	}
	
	private static void displayArray() {
		for( int i = 1; i <= arrCnt; ++i ) {
			for( int j = 1; j <= arrCnt; ++j ) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private static void displayTree() {
		for( int i = 1; i <= arrCnt; ++i ) {
			for( int j = 1; j <= arrCnt; ++j ) {
				System.out.print(tree[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private static void update( int row, int col, int diff ) {
		
		//System.out.println("-------------------- begin of update ---------------------------" );
		for( int i = row; i <= arrCnt; i+=(i&(-i)) ) {
			//System.out.println("update - begin of row: " + i );
			for( int j = col; j <= arrCnt; j+=(j&(-j)) ) {
				tree[i][j] += diff;
				//System.out.println("update - i: " + i + ", j: " + j + ", diff: " + diff + ", tree[" + i + "][" + j + "]: " + tree[i][j] );
			}
			//System.out.println("update - end of row: " + i);
		}
		//System.out.println("-------------------- end of update ---------------------------" );
	}
	
	private static int sum( int row, int col ) {
		
		int sum = 0;
		//System.out.println("-------------------- begin of sum ---------------------------" );
		for( int i = row; i > 0; i-=(i&(-i)) ) {
			//System.out.println("sum - begin of row: " + i );
			for( int j = col; j > 0; j-=(j&(-j)) ) {				
				sum += tree[i][j];
				//System.out.println("sum - i: " + i + ", j: " + j + ", sum: " + sum + ", tree[" + i + "][" + j + "]: " + tree[i][j] );
			}
			//System.out.println("sum - end of row: " + i);
		}
		//System.out.println("-------------------- end of sum ---------------------------" );
		
		return sum;
	}
}
