package kr.ac.inha.project.automata.unicode;

/**
 * 정규화 된 체인코드를 이용해 한글 자,모음을 찾는다.
 */
public class Consonant {
	
	public int getInit_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("24") || chaincode.equals("25"))		// ㄱ
			char_unicode = 0;
		else if(chaincode.equals("24-24") || chaincode.equals("25-25"))						// ㄲ
			char_unicode = 1;
		else if(chaincode.equals("42"))						// ㄴ
			char_unicode = 2;
		else if(chaincode.equals("2-42") || chaincode.equals("642"))						// ㄷ
			char_unicode = 3;
		else if(chaincode.equals("2-42-2-42") || chaincode.equals("642-642") || chaincode.equals("642-4") || chaincode.equals("2-42-4"))						// ㄸ
			char_unicode = 4;
		else if(chaincode.equals("24642") || chaincode.equals("24-2-42"))	// ㄹ
			char_unicode = 5;
		else if(chaincode.equals("4-246") || chaincode.equals("4-24-2") || chaincode.equals("2460") || chaincode.equals("4206"))						// ㅁ
			char_unicode = 6;
		else if(chaincode.equals("4-2-4-2") || chaincode.equals("420-2"))		// ㅂ
			char_unicode = 7;
		else if(chaincode.equals("4-2-4-2-4") || chaincode.equals("420-2-4"))		// ㅃ
			char_unicode = 8;
		else if(chaincode.equals("5-3"))						// ㅅ
			char_unicode = 9;
		else if(chaincode.equals("5-3-5-3"))						// ㅆ
			char_unicode = 10;
		else if(chaincode.equals("5317") || chaincode.equals("64207"))						// ㅇ
			char_unicode = 11;
		else if(chaincode.equals("25-3") || chaincode.equals("2-5-3") || chaincode.equals("24-3"))		// ㅈ 
			char_unicode = 12;
		else if(chaincode.equals("25-3-25-3"))		// ㅉ
			char_unicode = 13;
		else if(chaincode.equals("3-25-3") || chaincode.equals("2-2-5-3") || chaincode.equals("3-25-2") || chaincode.equals("2-25-3") || chaincode.equals("3-2-5-3"))	// ㅊ
			char_unicode = 14;
		else if(chaincode.equals("24-2") || chaincode.equals("25-2"))			// ㅋ
			char_unicode = 15;
		else if(chaincode.equals("2-42-2"))						// ㅌ
			char_unicode = 16;
		else if(chaincode.equals("2-4-4-2"))						// ㅍ
			char_unicode = 17;
		else if(chaincode.equals("3-2-5317") || chaincode.equals("3-2-64207") || chaincode.equals("2-2-5317") || chaincode.equals("2-2-64207"))						// ㅎ
			char_unicode = 18;
		else
			char_unicode = -1;
		
		
		return char_unicode;
	}
	
	public int getMid1_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("4-2"))			// ㅏ 
			char_unicode = 0;
		else if(chaincode.equals("4-2-4"))	// ㅐ
			char_unicode = 1;
		else if(chaincode.equals("4-2-2"))	// ㅑ
			char_unicode = 2;
		else if(chaincode.equals("4-2-2-4"))// ㅒ
			char_unicode = 3;
		else if(chaincode.equals("2-4"))	// ㅓ
			char_unicode = 4;
		else if(chaincode.equals("2-4-4"))	// ㅔ
			char_unicode = 5;
		else if(chaincode.equals("2-2-4"))	// ㅕ
			char_unicode = 6;
		else if(chaincode.equals("2-2-4-4"))// ㅖ
			char_unicode = 7;
		else if(chaincode.equals("4"))		// ㅣ
			char_unicode = 20;
		else
			char_unicode = -1;
	
		return char_unicode;	
	}
	
	public int getMid2_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("4-2"))			// ㅗ
			char_unicode = 8;
		else if(chaincode.equals("4-4-2"))	// ㅛ
			char_unicode = 12;
		else if(chaincode.equals("2-4"))	// ㅜ
			char_unicode = 13;
		else if(chaincode.equals("2-4-4"))	// ㅠ
			char_unicode = 17;
		else if(chaincode.equals("4"))		// ㅡ
			char_unicode = 18;
		else
			char_unicode = -1;
		
		return char_unicode;
	}
	
	public int getMidf_Con(int mid1, int mid2){
		int char_unicode = 0;
		
		if(mid1 != -1 && mid2 != -1){
			if(mid1 == 8 && mid2 == 0)
				char_unicode = 9;
			else if(mid1 == 8 && mid2 ==  1)
				char_unicode = 10;
			else if(mid1 == 8 && mid2 == 20)
				char_unicode = 11;
			else if(mid1 == 13 && mid2 == 4)
				char_unicode = 14;
			else if(mid1 == 13 && mid2 == 5)
				char_unicode = 15;
			else if(mid1 == 13 && mid2 == 20)
				char_unicode = 16;
			else if(mid1 == 18 && mid2 == 20)
				char_unicode = 19;
			
		}
		else if(mid1 == -1)
			char_unicode = mid2;
		else
			char_unicode = mid1;
		
		return char_unicode;
	}
	
	public int getFinal_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("24") || chaincode.equals("25"))		// ㄱ
			char_unicode = 1;
		else if(chaincode.equals("24-24") || chaincode.equals("25-25"))			// ㄲ
			char_unicode = 2;
		else if(chaincode.equals("24-5-3") || chaincode.equals("25-5-3"))			// ㄳ
			char_unicode = 3;
		else if(chaincode.equals("42"))						// ㄴ
			char_unicode = 4;
		else if(chaincode.equals("42-25-3"))						// ㄵ
			char_unicode = 5;
		else if(chaincode.equals("42-3-2-5317") || chaincode.equals("42-3-2-64207") || chaincode.equals("42-2-2-5317") || chaincode.equals("42-2-2-64207"))	// ㄶ
			char_unicode = 6;
		else if(chaincode.equals("2-42") || chaincode.equals("642"))						// ㄷ
			char_unicode = 7;
		else if(chaincode.equals("24642") || chaincode.equals("24-2-42"))	// ㄹ
			char_unicode = 8;
		else if(chaincode.equals("24642-24") || chaincode.equals("24-2-42-24") || chaincode.equals("24642-25") || chaincode.equals("24-2-42-25"))	// ㄺ
			char_unicode = 9;
		else if(chaincode.equals("24642-2460") || chaincode.equals("24-2-42-2460") || chaincode.equals("24642-4206") || chaincode.equals("24-2-42-4206"))	// ㄻ
			char_unicode = 10;
		else if(chaincode.equals("24642-4-2-4-2") || chaincode.equals("24-2-42-4-2-4-2") || chaincode.equals("24642-420-2") || chaincode.equals("24-2-42-420-2"))	// ㄼ
			char_unicode = 11;
		else if(chaincode.equals("24642-5-3") || chaincode.equals("24-2-42-5-3") || chaincode.equals("24642-5-3") || chaincode.equals("24-2-42-5-3"))	// ㄽ
			char_unicode = 12;
		else if(chaincode.equals("24642-2-42-2") || chaincode.equals("24-2-42-2-42-2"))	// ㄾ
			char_unicode = 13;
		else if(chaincode.equals("24642-2-4-4-2") || chaincode.equals("24-2-42-2-4-4-2"))	// ㄿ
			char_unicode = 14;
		else if(chaincode.equals("24642-2-2-5317") || chaincode.equals("24-2-42-2-2-5317") || chaincode.equals("24642-2-2-64207") || chaincode.equals("24-2-42-2-2-64207"))	// ㅀ
			char_unicode = 15;
		else if(chaincode.equals("4-246") || chaincode.equals("4-24-2") || chaincode.equals("2460") || chaincode.equals("4206"))		// ㅁ
			char_unicode = 16;
		else if(chaincode.equals("4-2-4-2") || chaincode.equals("420-2"))		// ㅂ
			char_unicode = 17;
		else if(chaincode.equals("4-2-4-2-5-3") || chaincode.equals("420-2-5-3"))		// ㅄ
			char_unicode = 18;
		else if(chaincode.equals("5-3"))						// ㅅ
			char_unicode = 19;
		else if(chaincode.equals("5-3-5-3"))						// ㅆ
			char_unicode = 20;
		else if(chaincode.equals("5317") || chaincode.equals("64207"))						// ㅇ
			char_unicode = 21;
		else if(chaincode.equals("25-3") || chaincode.equals("2-5-3") || chaincode.equals("24-3"))		// ㅈ 
			char_unicode = 22;
		else if(chaincode.equals("3-25-3") || chaincode.equals("2-2-5-3") || chaincode.equals("3-25-2") || chaincode.equals("2-25-3") || chaincode.equals("3-2-5-3"))	// ㅊ
			char_unicode = 23;
		else if(chaincode.equals("24-2") || chaincode.equals("25-2"))			// ㅋ
			char_unicode = 24;
		else if(chaincode.equals("2-42-2"))						// ㅌ
			char_unicode = 25;
		else if(chaincode.equals("2-4-4-2"))						// ㅍ
			char_unicode = 26;
		else if(chaincode.equals("3-2-5317") || chaincode.equals("3-2-64207") || chaincode.equals("2-2-5317") || chaincode.equals("2-2-64207"))						// ㅎ
			char_unicode = 27;
		else
			char_unicode = 0;
		
		return char_unicode;
		
	}
	
}
