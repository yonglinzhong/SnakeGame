package com.yonglinzhong.snake;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.yonglinzhong.game.*;

// snake body data structure
public class Body {
	Coordinate coor;// coordination
	JLabel label;
	public Body(int x,int y,ImageIcon icon)
	{
		coor = new Coordinate(x,y);
		label = new JLabel(icon);
	}
	
	public Body(Coordinate coor,ImageIcon icon){
		this.coor = coor;
		label = new JLabel(icon);
	}
	
	public Body(Body temp){
		coor = temp.coor;
		label = temp.label;
	}
}
