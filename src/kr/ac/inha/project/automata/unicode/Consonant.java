package kr.ac.inha.project.automata.unicode;

/**
 * ����ȭ �� ü���ڵ带 �̿��� �ѱ� ��,������ ã�´�.
 */
public class Consonant {
	
	public int getInit_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("24") || chaincode.equals("25"))		// ��
			char_unicode = 0;
		else if(chaincode.equals("24-24") || chaincode.equals("25-25"))						// ��
			char_unicode = 1;
		else if(chaincode.equals("42"))						// ��
			char_unicode = 2;
		else if(chaincode.equals("2-42") || chaincode.equals("642"))						// ��
			char_unicode = 3;
		else if(chaincode.equals("2-42-2-42") || chaincode.equals("642-642") || chaincode.equals("642-4") || chaincode.equals("2-42-4"))						// ��
			char_unicode = 4;
		else if(chaincode.equals("24642") || chaincode.equals("24-2-42"))	// ��
			char_unicode = 5;
		else if(chaincode.equals("4-246") || chaincode.equals("4-24-2") || chaincode.equals("2460") || chaincode.equals("4206"))						// ��
			char_unicode = 6;
		else if(chaincode.equals("4-2-4-2") || chaincode.equals("420-2"))		// ��
			char_unicode = 7;
		else if(chaincode.equals("4-2-4-2-4") || chaincode.equals("420-2-4"))		// ��
			char_unicode = 8;
		else if(chaincode.equals("5-3"))						// ��
			char_unicode = 9;
		else if(chaincode.equals("5-3-5-3"))						// ��
			char_unicode = 10;
		else if(chaincode.equals("5317") || chaincode.equals("64207"))						// ��
			char_unicode = 11;
		else if(chaincode.equals("25-3") || chaincode.equals("2-5-3") || chaincode.equals("24-3"))		// �� 
			char_unicode = 12;
		else if(chaincode.equals("25-3-25-3"))		// ��
			char_unicode = 13;
		else if(chaincode.equals("3-25-3") || chaincode.equals("2-2-5-3") || chaincode.equals("3-25-2") || chaincode.equals("2-25-3") || chaincode.equals("3-2-5-3"))	// ��
			char_unicode = 14;
		else if(chaincode.equals("24-2") || chaincode.equals("25-2"))			// ��
			char_unicode = 15;
		else if(chaincode.equals("2-42-2"))						// ��
			char_unicode = 16;
		else if(chaincode.equals("2-4-4-2"))						// ��
			char_unicode = 17;
		else if(chaincode.equals("3-2-5317") || chaincode.equals("3-2-64207") || chaincode.equals("2-2-5317") || chaincode.equals("2-2-64207"))						// ��
			char_unicode = 18;
		else
			char_unicode = -1;
		
		
		return char_unicode;
	}
	
	public int getMid1_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("4-2"))			// �� 
			char_unicode = 0;
		else if(chaincode.equals("4-2-4"))	// ��
			char_unicode = 1;
		else if(chaincode.equals("4-2-2"))	// ��
			char_unicode = 2;
		else if(chaincode.equals("4-2-2-4"))// ��
			char_unicode = 3;
		else if(chaincode.equals("2-4"))	// ��
			char_unicode = 4;
		else if(chaincode.equals("2-4-4"))	// ��
			char_unicode = 5;
		else if(chaincode.equals("2-2-4"))	// ��
			char_unicode = 6;
		else if(chaincode.equals("2-2-4-4"))// ��
			char_unicode = 7;
		else if(chaincode.equals("4"))		// ��
			char_unicode = 20;
		else
			char_unicode = -1;
	
		return char_unicode;	
	}
	
	public int getMid2_Con(String chaincode){
		int char_unicode = 0;
		
		if(chaincode.equals("4-2"))			// ��
			char_unicode = 8;
		else if(chaincode.equals("4-4-2"))	// ��
			char_unicode = 12;
		else if(chaincode.equals("2-4"))	// ��
			char_unicode = 13;
		else if(chaincode.equals("2-4-4"))	// ��
			char_unicode = 17;
		else if(chaincode.equals("4"))		// ��
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
		
		if(chaincode.equals("24") || chaincode.equals("25"))		// ��
			char_unicode = 1;
		else if(chaincode.equals("24-24") || chaincode.equals("25-25"))			// ��
			char_unicode = 2;
		else if(chaincode.equals("24-5-3") || chaincode.equals("25-5-3"))			// ��
			char_unicode = 3;
		else if(chaincode.equals("42"))						// ��
			char_unicode = 4;
		else if(chaincode.equals("42-25-3"))						// ��
			char_unicode = 5;
		else if(chaincode.equals("42-3-2-5317") || chaincode.equals("42-3-2-64207") || chaincode.equals("42-2-2-5317") || chaincode.equals("42-2-2-64207"))	// ��
			char_unicode = 6;
		else if(chaincode.equals("2-42") || chaincode.equals("642"))						// ��
			char_unicode = 7;
		else if(chaincode.equals("24642") || chaincode.equals("24-2-42"))	// ��
			char_unicode = 8;
		else if(chaincode.equals("24642-24") || chaincode.equals("24-2-42-24") || chaincode.equals("24642-25") || chaincode.equals("24-2-42-25"))	// ��
			char_unicode = 9;
		else if(chaincode.equals("24642-2460") || chaincode.equals("24-2-42-2460") || chaincode.equals("24642-4206") || chaincode.equals("24-2-42-4206"))	// ��
			char_unicode = 10;
		else if(chaincode.equals("24642-4-2-4-2") || chaincode.equals("24-2-42-4-2-4-2") || chaincode.equals("24642-420-2") || chaincode.equals("24-2-42-420-2"))	// ��
			char_unicode = 11;
		else if(chaincode.equals("24642-5-3") || chaincode.equals("24-2-42-5-3") || chaincode.equals("24642-5-3") || chaincode.equals("24-2-42-5-3"))	// ��
			char_unicode = 12;
		else if(chaincode.equals("24642-2-42-2") || chaincode.equals("24-2-42-2-42-2"))	// ��
			char_unicode = 13;
		else if(chaincode.equals("24642-2-4-4-2") || chaincode.equals("24-2-42-2-4-4-2"))	// ��
			char_unicode = 14;
		else if(chaincode.equals("24642-2-2-5317") || chaincode.equals("24-2-42-2-2-5317") || chaincode.equals("24642-2-2-64207") || chaincode.equals("24-2-42-2-2-64207"))	// ��
			char_unicode = 15;
		else if(chaincode.equals("4-246") || chaincode.equals("4-24-2") || chaincode.equals("2460") || chaincode.equals("4206"))		// ��
			char_unicode = 16;
		else if(chaincode.equals("4-2-4-2") || chaincode.equals("420-2"))		// ��
			char_unicode = 17;
		else if(chaincode.equals("4-2-4-2-5-3") || chaincode.equals("420-2-5-3"))		// ��
			char_unicode = 18;
		else if(chaincode.equals("5-3"))						// ��
			char_unicode = 19;
		else if(chaincode.equals("5-3-5-3"))						// ��
			char_unicode = 20;
		else if(chaincode.equals("5317") || chaincode.equals("64207"))						// ��
			char_unicode = 21;
		else if(chaincode.equals("25-3") || chaincode.equals("2-5-3") || chaincode.equals("24-3"))		// �� 
			char_unicode = 22;
		else if(chaincode.equals("3-25-3") || chaincode.equals("2-2-5-3") || chaincode.equals("3-25-2") || chaincode.equals("2-25-3") || chaincode.equals("3-2-5-3"))	// ��
			char_unicode = 23;
		else if(chaincode.equals("24-2") || chaincode.equals("25-2"))			// ��
			char_unicode = 24;
		else if(chaincode.equals("2-42-2"))						// ��
			char_unicode = 25;
		else if(chaincode.equals("2-4-4-2"))						// ��
			char_unicode = 26;
		else if(chaincode.equals("3-2-5317") || chaincode.equals("3-2-64207") || chaincode.equals("2-2-5317") || chaincode.equals("2-2-64207"))						// ��
			char_unicode = 27;
		else
			char_unicode = 0;
		
		return char_unicode;
		
	}
	
}
