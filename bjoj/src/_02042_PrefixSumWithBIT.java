/*
 * 구간 합 구하기
 * 
문제
어떤 N개의 수가 주어져 있다. 
그런데 중간에 수의 변경이 빈번히 일어나고 그 중간에 어떤 부분의 합을 구하려 한다. 
만약에 1,2,3,4,5 라는 수가 있고, 3번째 수를 6으로 바꾸고 2번째부터 5번째까지 합을 구하라고 한다면 
17을 출력하면 되는 것이다. 
그리고 그 상태에서 다섯 번째 수를 2로 바꾸고 3번째부터 5번째까지 합을 구하라고 한다면 12가 될 것이다.

입력
첫째 줄에 수의 개수 N(1<=N<=1,000,000)과 M(1<=M<=10,000), K(1<=k<=10,000) 가 주어진다. 
M은 수의 변경이 일어나는 횟수이고, K는 구간의 합을 구하는 횟수이다. 
그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 
그리고 N+2번째 줄부터 N+M+K+1번째 줄까지 세 개의 정수 a,b,c가 주어지는데, 
a가 1인 경우 b번째 수를 c로 바꾸고 a가 2인 경우에는 b번째 수부터 c번째 수까지의 합을 구하여 출력하면 된다.

출력
첫째 줄부터 K줄에 걸쳐 구한 구간의 합을 출력한다. (단 정답은 long long 범위를 넘지 않는다)

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

public class _02042_PrefixSumWithBIT {

	private static BigInteger [] array;
	private static BigInteger [] tree;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int arrCnt = sc.nextInt();
		int cmdCnt = sc.nextInt() + sc.nextInt();
		
		array = new BigInteger[arrCnt+1];
		tree = new BigInteger[arrCnt+1];
		
		for( int i = 1; i <= arrCnt; ++i ) {
			array[i] = sc.nextBigInteger();
			tree[i] = new BigInteger("0");			
		}
		
		for( int i = 1; i <= arrCnt; ++i ) {
			update(i, array[i]);
		}
		
		for( int i = 1; i <= cmdCnt; ++i ) {
			int cmd = sc.nextInt();
			
			// 변경
			if( cmd == 1 ) {
				int index = sc.nextInt();
				BigInteger diff = sc.nextBigInteger().subtract(array[index]);
				
				update( index, diff );
			}
			// 구간 합
			else if( cmd == 2 ) {
				int startIndex = sc.nextInt();
				int endIndex = sc.nextInt();
				
				System.out.println(sum(endIndex).subtract(sum(startIndex-1)));
			}
		}
	}

	private static void update( int i, BigInteger diff ) {
		
		int treeCnt = tree.length-1;
		//System.out.println("---------------------------------------------------------------------------------");
		while( i <= treeCnt ) {
			tree[i] = tree[i].add(diff);
			//System.out.println("update - i: " + i + ", tree[" + i + "]: " + tree[i] + ", diff: " + diff);
			i += ( i & -i );		
		}
		//System.out.println("---------------------------------------------------------------------------------");
	}
	
	private static BigInteger sum( int i ) {
		
		BigInteger answer = new BigInteger("0");
		//System.out.println("---------------------------------------------------------------------------------");
		while( i > 0 ) {
			answer = answer.add(tree[i]);
			//System.out.println("sum - i: " + i + ", answer: " + answer + ", tree[" + i + "]: " + tree[i]);			
			i -= (i & -i);
		}
		//System.out.println("---------------------------------------------------------------------------------");
		return answer;		
	}
}
