
/*
집합의 표현 성공 스페셜 저지

문제집 
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	128 MB	4235	1244	790	26.635%

문제
초기에 {0}, {1}, {2}, ... {n} 이 각각 n+1개의 집합을 이루고 있다. 
여기에 합집합 연산과, 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산을 수행하려고 한다.

집합을 표현하는 프로그램을 작성하시오.

입력
첫째 줄에 n(1≤n≤1,000,000), m(1≤m≤100,000)이 주어진다. 
m은 입력으로 주어지는 연산의 개수이다. 
다음 m개의 줄에는 각각의 연산이 주어진다. 
합집합은 0 a b의 형태로 입력이 주어진다. 
이는 a가 포함되어 있는 집합과, b가 포함되어 있는 집합을 합친다는 의미이다. 

두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산은 1 a b의 형태로 입력이 주어진다. 
이는 a와 b가 같은 집합에 포함되어 있는지를 확인하는 연산이다. 
a와 b는 n 이하의 자연수또는 0이며 같을 수도 있다.

출력
1로 시작하는 입력에 대해서 한 줄에 하나씩 YES/NO로 결과를 출력한다. 
(yes/no 를 출력해도 된다)

예제 입력  복사
7 8
0 1 3
1 1 7
0 7 6
1 7 1
0 3 7
0 4 2
0 1 1
1 1 1

예제 출력  복사
NO
NO
YES
 */
import java.util.Scanner;

public class _01717_ExpressionOfSet {
	
	static int [] parent;
	
	// 집합의 root 인덱스를 리턴해 주는 함수 
	static int find (int child ) {
		if( child == parent[child] ) {
			return child;
		}
		else {
			return parent[child] = find(parent[child]);
		}
	}
	
	// 각 집합의 루트를 찾아서 합집합을 만들어 주는 함수
	static void union( int a, int b )
	{
		int root1 = find(a);
		int root2 = find(b);
		
		parent[root2] = root1;		
	}
	
	// 테스트 용 배열 출력 함수
	static void displayParents( int cnt ) 
	{		
		System.out.println("***************  BEGIN  ********************");
		for( int i = 1; i <= cnt; ++i )
		{
			System.out.print(i + " ");
		}
		System.out.println();
		for( int i = 1; i <= cnt; ++i )
		{
			System.out.print(parent[i] + " ");
		}
		System.out.println();
		System.out.println("****************  END  *********************");
		System.out.println();
	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int elementCnt = sc.nextInt();
		int operationCnt = sc.nextInt();
		
		parent = new int[elementCnt+1];
		
		for( int i = 0; i <= elementCnt; ++i )
		{
			parent[i] = i;
		}
		
		for( int i = 0; i < operationCnt; ++i )
		{
			int cmd = sc.nextInt();
			int a = sc.nextInt();
			int b = sc.nextInt();
			
			// union 연산
			if( cmd == 0 )
			{
				union(  a, b );
				//System.out.println("UNION " + a + " AND " + b);
			}
			// union 여부 확인
			else if( cmd == 1 )
			{
				//System.out.println("CHECK " + a + " AND " + b);
				System.out.println( find( a ) == find( b ) ? "YES" : "NO");				
			}
			
			//displayParents(elementCnt);
		}
	}

}
