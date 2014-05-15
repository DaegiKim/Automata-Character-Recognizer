package kr.ac.inha.project.automata.pointlist;

/**
 * 링크드 리스트를 이루는 Point 클래스
 * x,y 좌표를 저장할 수 있다.
 *
 */

public class Point {
	public int x;
	public int y;
	public Point next;
	public Point prev;
	public boolean isStart;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		next = null;
		prev = null;
		isStart = false;
	}
}
