import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class _01766_workbook {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numOfProblem = sc.nextInt();
		int numOfComparision = sc.nextInt();
		sc.nextLine();
		
		// 간선 리스트 선언
		List<Integer> [] edgeList = (List<Integer>[]) new List[numOfProblem+1];
		for( int i = 1; i <= numOfProblem; ++i )
		{
			edgeList[i] = new ArrayList<Integer>();
		}

		// indegreeCnt 선언
		int [] indegreeCnt = new int[numOfProblem+1];
		
		// 각 비교 데이터를 가지고 간선 리스트 초기화
		for( int i = 0; i < numOfComparision; ++i )
		{
			int curProblem = sc.nextInt();
			int nextProblem = sc.nextInt();
			
			edgeList[curProblem].add(nextProblem);
			indegreeCnt[nextProblem]++;			
		}
		
		// 각 문제 별로 in-degree count 가 0인 문제를 Queue에 삽입
		Queue<Integer> orderQueue = new PriorityQueue<Integer>();
		for( int i = 1; i <= numOfProblem; ++i )
		{
			if( indegreeCnt[i] == 0 )
				orderQueue.add(i);
		}

		// Queue 가 빌 때까지 진행
		while( orderQueue.peek() != null )
		{
			// Queue 에 들어가 있는 우선순위대로 in-degree count를 조사
			int curProblem = orderQueue.remove();
			System.out.print(curProblem + " ");
			
			for( int nextProblem : edgeList[curProblem] )
			{
				// 다음 문제의 in-degree count를 감소
				indegreeCnt[nextProblem]--;
				
				// in-degree count가 0인 문제를 Queue 에 삽입
				if( indegreeCnt[nextProblem] <= 0 )
					orderQueue.add(nextProblem);					
			}
		}
	}
}
