
/*
	02056 - 작업
	https://www.acmicpc.net/problem/2056
	
	문제
	
	수행해야 할 작업 N개 (3 <= N <= 10000)가 있다. 각각의 작업마다 걸리는 시간(1 <= 시간 <= 100)이 정수로 주어진다.
	몇몇 작업들 사이에는 선행 관계라는 게 있어서, 어떤 작업을 수행하기 위해 반드시 먼저 완료되어야 할 작업들이 있다. 이 작업들은 번호가 아주 예쁘게 매겨져 있어서, K번 작업에 대해 선행 관계에 있는(즉, K번 작업을 시작하기 전에 반드시 먼저 완료되어야 하는) 작업들의 번호는 모두 1 이상 (K-1) 이하이다. 작업들 중에는, 그것에 대해 선행 관계에 있는 작업이 하나도 없는 작업이 반드시 하나 이상 존재한다. (1번 작업이 항상 그러하다)
	모든 작업을 완료하기 위해 필요한 최소 시간을 구하여라. 물론, 서로 선행 관계가 없는 작업들은 동시에 수행 가능하다.
	
	입력
	첫째 줄에 N이 주어진다.
	두번째 줄부터 N+1번째 줄까지 N개의 줄이 주어진다. 2번째 줄은 1번 작업, 3번째 줄은 2번 작업, ..., N+1번째 줄은 N번 작업을 각각 나타낸다. 각 줄의 처음에는, 해당 작업에 걸리는 시간이 먼저 주어지고, 그 다음에 그 작업에 대해 선행 관계에 있는 작업들의 개수(0 <= 개수 <= 100)가 주어진다. 그리고 선행 관계에 있는 작업들의 번호가 주어진다.
	
	출력
	첫째 줄에 모든 작업을 완료하기 위한 최소 시간을 출력한다.
	
	
	예제 입력 복사
	7
	5 0
	1 1 1
	3 1 2
	6 1 1
	1 2 2 4
	8 2 2 4
	4 3 3 5 6
		
	예제 출력 복사	
	23
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class _02056_job {
	static public void main(String[] args) 	{
		Scanner sc = new Scanner(System.in);
		int jobCnt = sc.nextInt();
		//sc.nextLine();
		
		// 간선 리스트 초기화
		List<Integer> [] edgeList = (List<Integer>[]) new List[jobCnt+1];
		
		for( int i = 1; i <= jobCnt; ++i )
		{
			edgeList[i] = new ArrayList<Integer>();
		}

		// 작업시간과 간선 개수 선언
		int [] jobTime = new int[jobCnt+1];
		int [] indegreeCnt = new int[jobCnt+1];
		int [] totalJobTime = new int[jobCnt+1];
		
		for( int i = 1; i <= jobCnt; ++i )
		{
			// 작업 시간 입력
			jobTime[i] = sc.nextInt();
			
			// 선행 작업 개수
			int prevJobCnt = sc.nextInt();
			for( int j = 1; j <= prevJobCnt; ++j )
			{
				// 선행 작업 번호 입력
				int prevJob = sc.nextInt();
				
				// 간선 리스트와 간선 개수 입력
				edgeList[prevJob].add(i);
				indegreeCnt[i] += 1;
			}
		}
		
		// 작업 큐 생성		
		Queue<Integer> jobQueue = new LinkedList<Integer>();
		for( int i = 1; i <= jobCnt; ++i )
		{
			// indgreeCnt 가 0이면 Queue에 삽입
			if( indegreeCnt[i] == 0 )
			{
				jobQueue.add(i);
				
				// 우선 최종 작업 시간을 자신의 작업시간으로 셋팅
				totalJobTime[i] = jobTime[i];
			}
		}
		
		// 큐가 빌 때까지 작업 진행
		while( jobQueue.peek() != null )
		{
			// 큐에서 꺼내온 작업에 대해 작업시간 계산
			int curJob = jobQueue.remove();
			
			// 간선 리스트 방문
			for( int nextJob : edgeList[curJob] )
			{
				// indegreeCnt 감소
				indegreeCnt[nextJob] -= 1;
				
				// 다음 작업의 총 작업시간이 (현재까지의 작업 시간 + 다음 작업의 작업시간)보다 작으면 
				if( totalJobTime[nextJob] < totalJobTime[curJob] + jobTime[nextJob] ) {
					totalJobTime[nextJob] = totalJobTime[curJob] + jobTime[nextJob];
				}
				
				// indegreeCnt 가 0이면 큐에 삽입
				if( indegreeCnt[nextJob] == 0 )
					jobQueue.add(nextJob);
			}
		}
		
		// 총 작업 시간 중 최대값을 구함
		int answer = 0;
		for( int i = 1; i <= jobCnt; ++i )
		{
			answer = Math.max(answer, totalJobTime[i]);
		}
		
		System.out.println(answer);
	}
}
