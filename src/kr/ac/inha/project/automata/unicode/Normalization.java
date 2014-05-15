package kr.ac.inha.project.automata.unicode;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * �ʱ� ������ ü���ڵ忡�� ������ �����ϰ�, 
 * ������ ���·� ����ȭ �ϴ� class
 */

public class Normalization {
	
	private StringTokenizer stok;
	
	public Normalization(String inputData){
		stok = new StringTokenizer(inputData,"-",true);
		
	}
	
	public String normalizer(){				
		String result = "";			// ����ȭ �� ���
		
		String duplicatePattern = "(\\d)\\1{3,}";		// ���� ���ڰ� 3�� �̻� ���ӵ� ���ڿ��� �ν�, 2�� ���ϴ� ������ ������ ����
	    Pattern p = Pattern.compile(duplicatePattern);
	    
	    while(stok.hasMoreTokens()){
	    	String phrase = stok.nextToken();
	    	if(phrase.equals("-"))
	    		result += phrase;
	    	else{
	    		Matcher m = p.matcher(phrase);
			    while (m.find()) {
			    	char temp = m.group().charAt(0);	// �νĵ� ���ڿ����� ��ǥ���� �ϳ��� ������ ������� �Է�
			    	if(result.length() == 0)
			    		result += temp;
			    	else if(result.charAt(result.length()-1) != temp)	// ���� ����� ���ӵǴ� ���ڰ� �ԷµǴ� ���� ���ڿ��� �߰����� ����
			    		result += temp;
			    }
	    	}		    
	    }
	    
	    System.out.println(result);
	    
		return result;		
	}
}
