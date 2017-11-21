import java.util.Scanner;

/*
계단 수 풀이

문제
45656이란 수를 보자.
이 수는 인접한 모든 자리수의 차이가 1이 난다. 이런 수를 계단 수라고 한다.

그럼, 오늘도 역시 세준이는 0부터 9까지 모든 한 자리수가 자리수로 등장하면서, 
수의 길이가 N인 계단 수가 몇 개 있는지 궁금해졌다.

N이 주어질 때, 길이가 N이면서 0에서 9가 모두 등장하는 
계단 수가 총 몇 개 있는 지 구하는 프로그램을 작성하시오. 
(0으로 시작하는 수는 없다.)

입력
첫째 줄에 N이 주어진다. N은 1보다 크거나 같고, 100보다 작거나 같은 자연수이다.

출력
첫째 줄에 정답을 1,000,000,000으로 나눈 나머지를 출력한다.

예제 입력  복사
10

예제 출력  복사
1
 */

public class _01562_StairNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		int [] d = new int[1<<n];
	}

}
