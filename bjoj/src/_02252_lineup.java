import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class _02252_lineup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int numOfPerson = sc.nextInt();
		int numOfComparision = sc.nextInt();
		sc.nextLine();
		
		List<Integer> [] edgeList = (List<Integer>[]) new List[numOfPerson+1];
		int [] indegreeCnt = new int[numOfPerson+1];
		
		// 간선 리스트 선언
		for( int i = 0; i <= numOfPerson; ++i )
		{
			edgeList[i] = new ArrayList<Integer>();
		}
		
		// 각 비교 데이터를 가지고 간선 리스트 초기화
		for( int i = 0; i < numOfComparision; ++i )
		{
			int startPersonIndex = sc.nextInt();
			int endPersonIndex = sc.nextInt();			
			
			edgeList[startPersonIndex].add(endPersonIndex);
			indegreeCnt[endPersonIndex]++;
		}
		
		// 간선 오름차순 정렬 
		for( List<Integer> curEdgeList : edgeList )
			Collections.sort(curEdgeList);
		
		// 각 Person 별로 in-degree count 가 0인 Person을 Queue에 삽입
		Queue<Integer> orderQueue = new LinkedList<Integer> ();
		for( int i = 1; i <= numOfPerson; ++i )
		{
			if( indegreeCnt[i] == 0 )
				orderQueue.add(i);
		}
		
		// Queue 가 빌 때까지 진행
		while( orderQueue.peek() != null )
		{
			// Queue 에 들어가 있는 순서대로 in-degree count를 조사
			int curPersonIndex = orderQueue.remove();
			System.out.print(curPersonIndex + " ");
			
			for( int curEndPersonIndex : edgeList[curPersonIndex] )
			{
				// 비교 대상이 되는 Person의 in-degree count를 감소
				indegreeCnt[curEndPersonIndex]--;
				
				// in-degree count가 0인 Person을 Queue 에 삽입
				if( indegreeCnt[curEndPersonIndex] <= 0 )
					orderQueue.add(curEndPersonIndex);
			}
		}
	}
}
