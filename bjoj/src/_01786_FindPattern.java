/*
찾기

문제
워드프로세서 등을 사용하는 도중에 찾기 기능을 이용해 본 일이 있을 것이다. 
이 기능을 여러분이 실제로 구현해 보도록 하자.

두 개의 문자열 P와 T에 대해, 문자열 P가 문자열 T 중간에 몇 번, 어느 위치에서 나타나는지 알아내는 문제를 '문자열 매칭'이라고 한다. 
워드프로세서의 찾기 기능은 이 문자열 매칭 문제를 풀어주는 기능이라고 할 수 있다. 
이때의 P는 패턴이라고 부르고 T는 텍스트라고 부른다.

편의상 T의 길이를 n, P의 길이를 m이라고 하자. 일반적으로, n>=m이라고 가정해도 무리가 없다.  
n<m이면 어차피 P는 T중간에 나타날 수 없기 때문이다. 
또, T의 i번째 문자를 T[i]라고 표현하도록 하자. 
그러면 물론, P의 i번째 문자는 P[i]라고 표현된다.


입력
첫째 줄에 문자열 T가, 둘째 줄에 문자열 P가 주어진다. 
문자열 내에 공백이 포함되어 있을 수도 있음에 유의한다. 
물론 공백도 하나의 문자로 인정된다. T와 P의 길이 n, m은 1이상 100만 이하이다.

출력
첫째 줄에, T 중간에 P가 몇 번 나타나는지를 나타내는 음이 아닌 정수를 출력한다. 
둘째 줄에는 P가 나타나는 위치를 차례대로 출력한다. 
예컨대, T의 i～i+m-1번 문자와 P의 1～m번 문자가 차례로 일치한다면, i를 출력하는 식이다.

예제 입력  복사
ABC ABCDAB ABCDABCDABDE
ABCDABD
예제 출력  복사
1
16

 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class _01786_FindPattern {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String text = sc.nextLine();
		String pattern = sc.nextLine();

		List<Integer> answerList = kmp( text, pattern );
	
		System.out.println(answerList.size());
		for( int curAnswer : answerList ) {
			System.out.print((curAnswer+1) + " ");
		}
		System.out.println();
	}
	
	private static int [] getPi( String pattern ) {
		
		if( pattern == null || pattern.length() <= 0 ) return null;
		
		int [] pi = new int[pattern.length()];
		int j = 0;
		
		for( int i = 1, len = pattern.length(); i < len; ++i ) {
			
			while( j > 0 && pattern.charAt(i) != pattern.charAt(j) ) {
				j = pi[j-1];
			}
			
			if( pattern.charAt(i) == pattern.charAt(j) ) {
				pi[i] = ++j;
			}
		}
		
		return pi;
	}
	
	private static List<Integer> kmp(String text, String pattern) {
		List<Integer> answerList = new ArrayList<Integer>();
		int [] pi = getPi(pattern);		
		int j = 0;
		int patternLen = pattern.length();
		
		for( int i = 0, len=text.length(); i < len; i++ ) {
			
			while( j > 0 && text.charAt(i) != pattern.charAt(j) ) {
				j = pi[j-1];
			}
			
			if( text.charAt(i) == pattern.charAt(j) ) {
				if( j == patternLen-1 ) {
					answerList.add(i-patternLen+1);
					j = pi[j];					
				}
				else {
					j++;
				}
			}
		}
		
		return answerList;		
	}
	
}
