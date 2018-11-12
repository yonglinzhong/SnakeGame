package com.yonglinzhong.game;

// coordination data structure, it's used to represend the snake body, food, obstacles
public class Coordinate {
	public int x,y;
	
	public Coordinate(int x0,int y0){
		x = x0;
		y = y0;
	}
	
	public Coordinate(Coordinate temp){
		x = temp.x;
		y = temp.y;
	}
}
