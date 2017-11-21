/*
타일 채우기 풀이

문제
3×N 크기의 벽을 2×1, 1×2 크기의 타일로 채우는 경우의 수를 구해보자.

입력
첫째 줄에 N(1 ≤ N ≤ 30)이 주어진다.

출력
첫째 줄에 경우의 수를 출력한다.

예제 입력  복사
2
예제 출력  복사
3
*/

import java.util.Scanner;

public class _02133_Tiles {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int number = sc.nextInt();
		long [][] dynamic = new long[number+1][8]; 
		
		dynamic[0][7] = 1;
		for( int i = 1; i <= number; ++i ) {
			dynamic[i][0] = dynamic[i-1][7];
			dynamic[i][1] = dynamic[i-1][6];
			dynamic[i][2] = dynamic[i-1][2];
			dynamic[i][3] = dynamic[i-1][4] + dynamic[i-1][7];
			dynamic[i][4] = dynamic[i-1][3];
			dynamic[i][5] = dynamic[i-1][2];
			dynamic[i][6] = dynamic[i-1][1] + dynamic[i-1][7];
			dynamic[i][7] = dynamic[i-1][0] + dynamic[i-1][3] + dynamic[i-1][6];
		}
		
		System.out.println(dynamic[number][7]);		
	}
}
