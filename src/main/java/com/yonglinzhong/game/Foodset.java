package com.yonglinzhong.game;

import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Foodset {
	
	private MainWindow GameUI;
	private List<Food> food = new LinkedList<Food>();// food array
	private static final int MAXSIZE = 8;
	private static final int MINSIZE = 3;
	
	private static final int FOODKIND = 6;
	private int[] point = new int[FOODKIND];// points for each food
	private ImageIcon[] foodIcon = new ImageIcon[FOODKIND];// icons for each food
	
	private Thread run;
	private int time = 10000;// fresh every 10s
	private boolean quit = false;
	
	public Foodset(MainWindow GameUI){
		this.GameUI = GameUI;
		
		// initialize food point
	    point[0] = 50;
	    point[1] = 40;
	    point[2] = 30;
	    point[3] = 20;
	    point[4] = 10;
	    point[5] = 0;
	    
	    // load food picture
	    for(int i = 0;i < FOODKIND;i++)
	    {
	    	foodIcon[i] = new ImageIcon("food//food" + i + ".png");
			foodIcon[i].setImage(foodIcon[i].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
	    }
	    
	    produceFood();
	    show();
	    AutoMoveThread();
	}
	
	public void quit(){
		quit = true;
	}
	
	public synchronized int getFoodPoint(Coordinate coor){
		for (Iterator<Food> iter = food.iterator(); iter.hasNext();) {
			Food node = iter.next();
			if(node.coor.x == coor.x && node.coor.y == coor.y)
			{
				node.label.setVisible(false);// remove food from UI
				GameUI.remove(node.label);
				iter.remove();// remove the food from the linklist if it's eated
				
				produceFood();
				
				GameUI.getAmountLabel().setText("" + food.size());
				return point[node.kind];// return food point
			}
		}
		return -1;
	}
	
	public void produceFood(){
		Random rand = new Random();
		int amount = rand.nextInt(MINSIZE);//[0,MINSIZE-1]
		double prob;
		int foodtag = 2;
		Food newfood;
		
		//P();
		if(food.size() == 0)
		{
			amount = MINSIZE;
		}
		else
		{
			while(amount + food.size() < MINSIZE || amount + food.size() > MAXSIZE)
			{
				amount = rand.nextInt(MINSIZE);//[0,MINSIZE-1];
			}
		}
		//V();
		
		for(int i = 0;i < amount;i++)
		{
			Coordinate coor = GameUI.produceRandomCoordinate();
			Coordinate _coor = new Coordinate(coor.y,coor.x);
			prob = rand.nextDouble();
			if(prob >= 0 && prob <0.1) 		    foodtag = 0;//10%
			else if(prob >= 0.1  && prob <0.25) foodtag = 4;//15%
			else if(prob >= 0.25 && prob <0.5)  foodtag = 3;//25%
			else if(prob >= 0.5  && prob <0.8)  foodtag = 2;//30%
			else if(prob >= 0.8 && prob <0.95)  foodtag = 1;//15%
			else if(prob >= 0.95 && prob <1) 	foodtag = 5;//5%

			GameUI.setMap(coor.x, coor.y, 2);

			newfood = new Food(foodtag,_coor,foodIcon[foodtag]);
			food.add(newfood);
			GameUI.add(newfood.label);
		}
		
		GameUI.getAmountLabel().setText("" + food.size());// refresh the label to show food count
		show();
		System.out.print("Generate food:" + amount + "\t");
		String Time = SysTime.getSysTime();
		System.out.println(Time);
	}
	
	public synchronized void show(){
		for (Iterator<Food> iter = food.iterator(); iter.hasNext();) {
			Food node = iter.next();
			node.label.setBounds(IndexToPixel.getXPixel(node.coor.x), 
					IndexToPixel.getYPixel(node.coor.y), 20, 20);
			node.label.setVisible(true);
		}
	}
	
	public synchronized void removeAll(){// remove all food picture
		for (Iterator<Food> iter = food.iterator(); iter.hasNext();) {
			Food node = iter.next();
			
			GameUI.setMap(node.coor.y, node.coor.x, 0);
			
			node.label.setVisible(false);
			GameUI.remove(node.label);
		}
		food.clear();
	}
	
	public void AutoMoveThread(){
		run = new Thread() {
			public void run() {
				while (!quit) 
				{
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
					
					if(!GameUI.getPause() && GameUI.getIsrun())
					{
						removeAll();
						produceFood();
						Write2file.PrintMap(GameUI.getMap(),"map.txt");
						if(quit)
							break;
						show();
					}
				}
				System.out.println("Food thread exit...");
			}
		};
		run.start();
	}

	// food data structure
	public class Food {
		int kind;
		JLabel label; 
		Coordinate coor;
		public Food(int kind,int x,int y,ImageIcon icon){
			this.kind = kind;
			label = new JLabel(icon);
			coor = new Coordinate(x,y);
		}
		
		public Food(int kind,Coordinate coor,ImageIcon icon){
			this.kind = kind;
			label = new JLabel(icon);
			this.coor = coor;
		}
	}
}
