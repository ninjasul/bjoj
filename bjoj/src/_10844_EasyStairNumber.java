import java.util.Arrays;
import java.util.Scanner;

/*
쉬운 계단 수 풀이

문제
45656이란 수를 보자.
이 수는 인접한 모든 자리수의 차이가 1이 난다. 이런 수를 계단 수라고 한다.
세준이는 수의 길이가 N인 계단 수가 몇 개 있는지 궁금해졌다.
N이 주어질 때, 길이가 N인 계단 수가 총 몇 개 있는지 구하는 프로그램을 작성하시오. 
(0으로 시작하는 수는 없다.)

입력
첫째 줄에 N이 주어진다. N은 1보다 크거나 같고, 100보다 작거나 같은 자연수이다.

출력
첫째 줄에 정답을 1,000,000,000으로 나눈 나머지를 출력한다.

예제 입력  복사
1
예제 출력  복사
9

예제 입력 2  복사
2
예제 출력 2  복사
17
*/
public class _10844_EasyStairNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int [][] d = new int[n+1][n+1];
		
		d[1][0] = 0;
		for( int i = 1; i < n; ++i ) {
			d[1][i] = 1;
		}
		
		for( int i = 1; i <= n; ++i ) {
			for( int j = 0; j <= n; ++j ) {
				//System.out.println("i: " + i + ", j: " + j );
				d[i][j] += (((j > 0) ? d[i-1][j-1] : 0 )+ ((j < n) ? d[i-1][j+1] : 0));
				System.out.println("d[" + i + "][" + j + "]: " + d[i][j]);
			}
		}
		
		
	}

}
