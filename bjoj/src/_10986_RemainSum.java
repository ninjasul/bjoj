/*
문제
수 N개 A1, A2, ..., AN이 주어진다. 이 때, 연속된 부분 구간의 합이 
M으로 나누어 떨어지는 구간의 개수를 구하는 프로그램을 작성하시오.

즉, Ai + ... + Aj (i ≤ j) 의 합이 M으로 나누어 떨어지는 (i, j) 쌍의 개수를 구해야 한다.

입력
첫째 줄에 N과 M이 주어진다. (1 ≤ N ≤ 106, 2 ≤ M ≤ 103)

둘째 줄에 N개의 수 A1, A2, ..., AN이 주어진다. (0 ≤ Ai ≤ 109)

출력
첫째 줄에 연속된 부분 구간의 합이 M으로 나누어 떨어지는 구간의 개수를 출력한다.

예제 입력  복사
5 3
1 2 3 1 2

예제 출력  복사
7
*/

import java.util.Scanner;

public class _10986_RemainSum {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int arrCnt = sc.nextInt();
		int divisor = sc.nextInt();
		
		int [] array = new int[arrCnt];
		int [] remain = new int[divisor];
		
		long sum = 0;
		remain[0] = 1;
		for( int i = 0; i < arrCnt; ++i ) {
			array[i] = sc.nextInt();
			sum += array[i];
			sum %= divisor;
			remain[(int)sum]++;
			
			System.out.println("i: " + i + ", sum: " + sum + ", remain[" + sum + "]: " + remain[(int)sum] );
		}
		
		long answer = 0;		
		for( int j = 0; j < divisor; ++j ) {
			answer += ( (long)remain[j] * (long)(remain[j]-1) / 2 );
		}
		
		System.out.println(answer);
	}
}
