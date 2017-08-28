/*
문제
수 N개가 주어졌을 때, i번째 수부터 j번째 수까지 합을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 수의 개수 N (1 ≤ N ≤ 100,000), 합을 구해야 하는 횟수 M (1 ≤ M ≤ 100,000)이 주어진다. 
둘째 줄에는 N개의 수가 주어진다. 수는 1,000보다 작거나 같은 자연수이다. 
셋째 줄부터 M개의 줄에는 합을 구해야 하는 구간 i와 j가 주어진다.

출력
총 M개의 줄에 입력으로 주어진 i번째 수부터 j번째 수까지 합을 출력한다.

예제 입력  복사
5 3
5 4 3 2 1
1 3
2 4
5 5

예제 출력  복사
12
9
1
*/

import java.util.Scanner;

public class _11659_PrefixSum {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int arrCnt = sc.nextInt();
		int queryCnt = sc.nextInt();
		
		int [] array = new int[arrCnt+1];
		int [] sum = new int[arrCnt+1];
		
		sum[0] = 0;
		
		for( int i = 1; i <= arrCnt; ++i ) {
			array[i] = sc.nextInt();						
			sum[i] = sum[i-1] + array[i];
			
			//System.out.println("array["+i+"]: " + array[i]);
			//System.out.println("sum["+i+"]: " + sum[i]);
		}
		
		for( int j = 1; j <= queryCnt; ++j ) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			

			//System.out.println("sum["+end+"]: " + sum[end]);
			//System.out.println("sum["+start+"]: " + sum[start]);
			
			System.out.println(sum[end]-sum[start-1]);
		}	
	}
}