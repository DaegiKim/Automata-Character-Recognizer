package kr.ac.inha.project.automata.pointlist;

/**
 * Point 클래스로 이루어진 링크드 리스트 클래스
 * add 메소드를 호출해 새로운 Point를 추가할 수 있다.
 * getChaincode 메소드를 호출해 체인코드를 얻어낼 수 있다.
 * @author DaegiKim
 *
 */

public class PList
{
	public Point trailer;
	public String chaincode;
	public boolean flag = false;
	
	public PList()
	{
		this.trailer = null;
		this.chaincode = "";
	}
	
	public void add(int x, int y)
	{
		Point temp = new Point(x, y);
		
		if(trailer == null)
		{
			trailer = temp;
			temp.isStart = true;
		}
		else if(flag)
		{
			chaincode = chaincode.concat("-");
			flag = !flag;
			
			temp.prev = trailer;
			trailer.next = temp;
			trailer = temp;
			temp.isStart = true;
		}
		else
		{
			int dx = x - trailer.x;
			int dy = y - trailer.y;
			String direction = getDirection(dx, dy);	// 바로 이전의 Point와의 기울기를 도출해내어
			chaincode = chaincode.concat(direction);	// chaincode 값을 갱신한다.
			
//			int tx = trailer.x/10;
//			int ty = trailer.y/10;
//			x/=10;
//			y/=10;
//			
//			if ((tx - (int)x) == 0) {
//				if ((tx - (int)y) > 0)
//					chaincode = chaincode.concat("0");
//				else if ((ty - (int)y) < 0)
//					chaincode = chaincode.concat("4");
//			} else if ((tx - (int)x) > 0) {
//				if ((ty - (int)y) > 0)
//					chaincode = chaincode.concat("7");
//				else if ((ty - (int)y) < 0)
//					chaincode = chaincode.concat("5");
//				else
//					chaincode = chaincode.concat("6");
//			} else if ((tx - (int)x) < 0) {
//				if ((ty - (int)y) > 0)
//					chaincode = chaincode.concat("1");
//				else if (ty - (int)y < 0)
//					chaincode = chaincode.concat("3");
//				else
//					chaincode = chaincode.concat("2");
//			}
			
			temp.prev = trailer;
			trailer.next = temp;
			trailer = temp;
		}
	}
	
	/**
	 * 탄젠트 기울기를 구한다.
	 * @param dx
	 * @param dy
	 * @return 8 방향 값 중 하나
	 */
	public String getDirection(int dx, int dy)
	{
		double d;
		if(dx == 0)
		{
			dx = 1;
			if(dy > 0) d = 10.0;
			else d = -10.0;
		}
		else
		{
			d = (double)dy / dx;
		}

		if(dx > 0)
		{
			if(d > 2.4142) return "4";
			else if(d < -2.4142) return "0";
			else if(d > 0.4142) return "3";
			else if(d < -0.4142) return "1";
			else return "2";
		} 
		else
		{
			if(d > 2.4142) return "0";
			else if(d < -2.4142) return "4";
			else if(d > 0.4142) return "7";
			else if(d < -0.4142) return "5";
			else return "6";
		}
	}
	
	public String getChaincode()
	{
		return chaincode;
	}
	public void setFlag() 
	{ 
		this.flag = true; 
	}
	public void putHyphen()
	{
		this.chaincode = chaincode.concat("-");
	}
}