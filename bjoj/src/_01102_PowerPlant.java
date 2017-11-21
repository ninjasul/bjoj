/*
발전소

문제
은진이는 발전소에서 근무한다. 
은진이가 회사에서 잠깐 잘 때마다, 몇몇 발전소가 고장이난다. 
게다가, 지금 은진이의 보스 형택이가 은진이의 사무실로 걸어오고 있다. 
만약 은진이가 형택이가 들어오기 전까지 발전소를 고쳐놓지 못한다면, 은진이는 해고당할 것이다.

발전소를 고치는 방법은 간단하다. 고장나지 않은 발전소를 이용해서 고장난 발전소를 재시작하면 된다. 
하지만, 이 때 비용이 발생한다. 이 비용은 어떤 발전소에서 어떤 발전소를 재시작하느냐에 따라 다르다.
적어도 P개의 발전소가 고장나 있지 않도록, 발전소를 고치는 비용의 최솟값을 구하는 프로그램을 작성하시오.
 

입력
첫째 줄에 발전소의 개수 N이 주어진다. N은 16보다 작거나 같은 자연수이다. 
둘째 줄부터 N개의 줄에는 발전소 i를 이용해서 발전소 j를 재시작할 때 드는 비용이 주어진다. i줄의 j번째 값이 그 값이다. 비용은 50보다 작거나 같은 음이 아닌 정수이다. 
그 다음 줄에는 각 발전소가 켜져있으면 Y, 꺼져있으면 N이 순서대로 주어진다. 
마지막 줄에는 P가 주어진다. P는 0보다 크거나 같고, N보다 작거나 같은 정수이다.

출력
첫째 줄에 문제의 정답을 출력한다. 불가능한 경우에는 -1을 출력한다.

예제 입력  복사
3
0 10 11
10 0 12
12 13 0
YNN
3

예제 출력  복사
21
 */

import java.util.Arrays;
import java.util.Scanner;

public class _01102_PowerPlant {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int pCnt = sc.nextInt();
		
		int [][] W = new int [pCnt][pCnt];
		int [] dp = new int [1<<pCnt];
		
		// 비용 초기화
		for( int i = 0; i < pCnt; ++i ) {
			for( int j = 0; j < pCnt; ++j ) {
				W[i][j] = sc.nextInt();
			}
		}
		
		sc.nextLine();		
		String turnedOnStatusStr = sc.nextLine();
		int i = 1;
		int start = 0;
		for( char curChar : turnedOnStatusStr.toCharArray() ) {
			if( curChar == 'Y' ) {
				start |= (1 << (i-1));
			}
			++i;
		}
		
		int targetPCnt = sc.nextInt();
		Arrays.fill(dp,  -1);
		dp[start] = 0;
		
		for( i = 0; i < (1<<pCnt); i++ ) {
			if( dp[i] == -1 ) {
				continue;
			}
			
			for( int j = 0; j < pCnt; ++j ) {
				// 공장 j가 가동 중인 경우
				if( ( i & ( 1 << j )) != 0 ) {
					for( int k = 0; k < pCnt; ++k ) {						
						// 공장 k가 미가동 중인 경우
						if( (i & ( 1 << k )) == 0 ) {							
							// 원 상태에서 미가동 공장 k가 켜졌을 때의 최소 비용이 존재하지 않거나
							// 최소 비용이 j 공장에서 k를 가동했을 때 보다 클 경우(더이상 최소 비용이 아니므로) 
							if( dp[i|(1<<k)] == -1 || dp[i|(1<<k)] > dp[i] + W[j][k] ) {
								dp[i|(1<<k)] = dp[i] + W[j][k];
							}
							
						}
					}
				}
			}
		}
		
	    int minCost = -1;
	    
	    for( i = 0; i < (1<<pCnt); i++ ) {
	    	// 최소 비용이 존재하지 않는 경우 skip
	    	if( dp[i] == -1 ) {
	    		continue;
	    	}
	    	
	    	int onPCnt = 0;
	    	for( int j = 0; j < pCnt; ++j ) {
	    		// 공장 가동 여부 체크 
	    		if( (i&(1<<j)) != 0 ) {
	    			onPCnt++;
	    		}
	    	}
	    	
	    	if( onPCnt >= targetPCnt ) {
	    		if( minCost == -1 || minCost > dp[i] ) {
	    			minCost = dp[i];
	    		}
	    	}
	    }
		
		System.out.println(minCost);
	}
	
}
