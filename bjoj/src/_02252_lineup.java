import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class _2252_lineup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int numOfPerson = sc.nextInt();
		int numOfComparision = sc.nextInt();
		sc.nextLine();
		
		ArrayList<ArrayList<Integer>> edgeList = new ArrayList<ArrayList<Integer>> (numOfPerson+1);
		int [] indegreeCnt = new int[numOfPerson+1];
		
		for( int i = 0; i <= numOfPerson; ++i )
		{
			edgeList.add(new ArrayList<Integer> ());
		}
		
		for( int i = 0; i < numOfComparision; ++i )
		{
			int startPersonIndex = sc.nextInt();
			int endPersonIndex = sc.nextInt();			
			
			edgeList.get(startPersonIndex).add(endPersonIndex);
			indegreeCnt[endPersonIndex]++;
			//sc.nextLine();
		}
		
		for( ArrayList<Integer> curEdgeList : edgeList )
			Collections.sort(curEdgeList);
		
		Queue<Integer> orderQueue = new LinkedList<Integer> ();
		for( int i = 1; i <= numOfPerson; ++i )
		{
			if( indegreeCnt[i] == 0 )
				orderQueue.offer(i);
		}
		
		while( orderQueue.peek() != null )
		{
			int curPersonIndex = orderQueue.poll();
			System.out.print(curPersonIndex + " ");
			
			if( edgeList.get(curPersonIndex) != null )
			{
				for( int curEndPersonIndex : edgeList.get(curPersonIndex) )
				{
					indegreeCnt[curEndPersonIndex]--;
					
					if( indegreeCnt[curEndPersonIndex] <= 0 )
						orderQueue.offer(curEndPersonIndex);
				}
			}
		}
	}
}
