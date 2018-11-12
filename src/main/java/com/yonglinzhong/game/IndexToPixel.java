package com.yonglinzhong.game;

public class IndexToPixel {
	//x axis：i -> 7+i*22
	//y axis：i -> 12+i*22
	// the index starts from 0
	//
	public static int getXPixel(int i)
	{
		return 7+i*22;
	}
		
	public static int getYPixel(int i)
	{
		return 12+i*22;
	}
}
