package kr.ac.inha.project.automata.unicode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 파일에서 유니코드를 검색하는 클래스
 * 현재는 사용하지 않는다.
 *
 */

public class CharFind {
	
	private Scanner filescan1;
//	private Scanner filescan2;
//	private Scanner filescan3;
//	private Scanner filescan4;

	
	public CharFind() throws FileNotFoundException{
		File file1 = new File("data/unicode");
//		File file2 = new File("data/projection2.txt");
//		File file3 = new File("data/projection3.txt");
//		File file4 = new File("data/projection4.txt");
		
		filescan1 = new Scanner(file1);
//		filescan2 = new Scanner(file2);
//		filescan3 = new Scanner(file3);
//		filescan4 = new Scanner(file4);
	}
	
	public String Finder(int inputData){
		String result = "";
		String readline;
		String findvalue = Integer.toString(inputData);
		
		while(filescan1.hasNext()){
			readline = filescan1.next();
			
			//StringTokenizer stok = new StringTokenizer(readline,"#");
			//String str = stok.nextToken();
			
			String unicode = readline.substring(1,readline.length());
			if(findvalue.equals(unicode)){
				result = readline.substring(0,1);
				return result;
			}
		}
		
//		while(filescan2.hasNext()){
//			readline = filescan2.next();
//			
//			StringTokenizer stok = new StringTokenizer(readline,"#");
//			String str = stok.nextToken();
//			
//			String unicode = str.substring(1);
//			if(findvalue.equals(unicode)){
//				result = str.substring(0,1);
//				return result;
//			}				
//		}
//		
//		while(filescan3.hasNext()){
//			readline = filescan3.next();
//			
//			StringTokenizer stok = new StringTokenizer(readline,"#");
//			String str = stok.nextToken();
//			
//			String unicode = str.substring(1);
//			if(findvalue.equals(unicode)){
//				result = str.substring(0,1);
//				return result;
//			}				
//		}
//		
//		while(filescan4.hasNext()){
//			readline = filescan4.next();
//			
//			StringTokenizer stok = new StringTokenizer(readline,"#");
//			String str = stok.nextToken();
//			
//			String unicode = str.substring(1);
//			if(findvalue.equals(unicode)){
//				result = str.substring(0,1);
//				return result;
//			}				
//		}

		return result;
	}

}
