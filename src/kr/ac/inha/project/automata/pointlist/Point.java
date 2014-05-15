package kr.ac.inha.project.automata.pointlist;

/**
 * ��ũ�� ����Ʈ�� �̷�� Point Ŭ����
 * x,y ��ǥ�� ������ �� �ִ�.
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
