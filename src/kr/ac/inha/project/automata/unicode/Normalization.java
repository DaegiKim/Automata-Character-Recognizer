package kr.ac.inha.project.automata.unicode;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * 초기 생성된 체인코드에서 오류를 제거하고, 
 * 간단한 형태로 정규화 하는 class
 */

public class Normalization {
	
	private StringTokenizer stok;
	
	public Normalization(String inputData){
		stok = new StringTokenizer(inputData,"-",true);
		
	}
	
	public String normalizer(){				
		String result = "";			// 정규화 된 결과
		
		String duplicatePattern = "(\\d)\\1{3,}";		// 같은 문자가 3개 이상 연속된 문자열을 인식, 2개 이하는 오류로 생각해 제거
	    Pattern p = Pattern.compile(duplicatePattern);
	    
	    while(stok.hasMoreTokens()){
	    	String phrase = stok.nextToken();
	    	if(phrase.equals("-"))
	    		result += phrase;
	    	else{
	    		Matcher m = p.matcher(phrase);
			    while (m.find()) {
			    	char temp = m.group().charAt(0);	// 인식된 문자열에서 대표문자 하나만 추출해 결과값에 입력
			    	if(result.length() == 0)
			    		result += temp;
			    	else if(result.charAt(result.length()-1) != temp)	// 최종 결과에 연속되는 문자가 입력되는 경우는 문자열을 추가하지 않음
			    		result += temp;
			    }
	    	}		    
	    }
	    
	    System.out.println(result);
	    
		return result;		
	}
}
