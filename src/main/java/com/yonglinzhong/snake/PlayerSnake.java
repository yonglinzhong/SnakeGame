package com.yonglinzhong.snake;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.awt.Image;
import java.util.Deque;
import java.util.Iterator;

import com.yonglinzhong.game.*;

public class PlayerSnake {
	private MainWindow GameUI;// main winodw
	private Foodset food;
	private Obstacle obstacle;
	private Thread run;
	private Direction direction = Direction.RIGHT;// snake current direction, default as right
	private int speed = 300;
	private int defaultSpeed = 300;
	private Deque<Body> body = new LinkedList<Body>();// store the body of snake's coordination
	private int point = 0;// current point
	private int bulletNumber = 20;// snake's bullet
	
	private ImageIcon[] headIcon = new ImageIcon[4];// snake head pictures
	private int headIconTag = 0;// default the first picture as head
	private ImageIcon[] bodyIcon = new ImageIcon[4];// snake body picture
	private int bodyIconTag = 0;// default the first picture as body
	private boolean quit = false;
	
	public PlayerSnake(MainWindow GameUI,Foodset food,Obstacle obstacle){
		this.GameUI = GameUI;
		this.food = food;
		this.obstacle = obstacle;
		// load 4 snake head and 4 snake body pictures
		for(int i = 0;i < 4;i++)
		{
			headIcon[i] = new ImageIcon("head//head" + i + ".png");
			headIcon[i].setImage(headIcon[i].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));// keep picture quality
			
			bodyIcon[i] = new ImageIcon("body//body" + i + ".png");
			bodyIcon[i].setImage(bodyIcon[i].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));// keep picuture quality
		}
		
		Body head = new Body(0,0,headIcon[headIconTag]);// initiate the snake at the point (0,0)
		body.addFirst(head);
		GameUI.add(head.label);
		head.label.setBounds(IndexToPixel.getXPixel(head.coor.x), IndexToPixel.getYPixel(head.coor.y), 20, 20);

		GameUI.setMap(0, 0, 1);
		MoveThread();
	}
	
	// move the snake body
	public void move(){
		Coordinate head,next_coor = new Coordinate(0,0);
		if(direction == Direction.UP){
			head = body.getFirst().coor;
			next_coor = new Coordinate(head.x,head.y - 1);// move snake head up
		}
		else if(direction == Direction.DOWN){
			head = body.getFirst().coor;
			next_coor = new Coordinate(head.x,head.y + 1);// move snake head down
		}
		else if(direction == Direction.LEFT){
			head = body.getFirst().coor;
			next_coor = new Coordinate(head.x - 1,head.y);// move snake head left
		}
		else if(direction == Direction.RIGHT){
			head = body.getFirst().coor;
			next_coor = new Coordinate(head.x + 1,head.y);// move snake head right
		}
		
		if(checkDeath(next_coor))// condiction to check if game end in the next step
		{
			new Music("music//over.wav").start();
			GameUI.setIsrun(false);
			quit();
			
			
			int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", 
					"Information", JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_NO_OPTION)
			{
				GameUI.restart();
			}
			else
			{
				GameUI.setPause(true);
			}
		}
		else
		{
			Body next_node = new Body(next_coor,headIcon[headIconTag]);
			body.addFirst(next_node);//add head
			GameUI.setMap(next_node.coor.y, next_node.coor.x, 1);
			next_node.label.setVisible(true);
			GameUI.add(next_node.label);
			
			if(!checkEat(next_coor))// if didn't eat food then remove the tail
			{
				Body tail = body.pollLast();//remove tail
				GameUI.setMap(tail.coor.y, tail.coor.x, 0);
				tail.label.setVisible(false);
				GameUI.remove(tail.label); // add head and remove tail to achieve moving
			}
		}
	}
	
	// check if the snake is death for a given coordination
	public boolean checkDeath(Coordinate coor){
		if( coor.x < 0 || coor.x >= GameUI.getAreaWidth()||
			coor.y < 0 || coor.y >= GameUI.getAreaHeight()||
			GameUI.getMap()[coor.y][coor.x] == 3)
			//GameUI.map[coor.y][coor.x] == 3)
			return true;
		else
			return false;
	}
	
	public boolean checkEat(Coordinate coor){
		int _point = food.getFoodPoint(coor);
		if(_point == -1)// didn't eat food
			return false;
		else// ate food
		{
			new Music("music//eat.wav").start();
			point += _point;
			if(_point == 0)
			{
				bulletNumber ++;
				GameUI.getWeaponLabel().setText("" + bulletNumber);
			}
			GameUI.getScoreLabel().setText("" + point);// refresh point
			GameUI.getLengthLabel().setText("" + body.size());// refresh body size
			GameUI.setMap(coor.y, coor.x, 1);
			return true;
		}
	}
	
	public void quit(){
		quit = true;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public void resetSpeed(){
		this.speed = defaultSpeed;
	}
	
	public void setDefaultSpeed(int speed){
		this.defaultSpeed = speed;
	}
	
	public void setHeadIcon(int tag){
		headIconTag = tag;
	}
	
	public void setBodyIcon(int tag){
		bodyIconTag = tag;
	}
	
	public int getBulletNum(){
		return bulletNumber;
	}
	
	public Coordinate getHeadCoor(){
		return body.getFirst().coor;
	}
	
	public synchronized void goDie(){
		quit();
		for (Iterator<Body> iter = body.iterator(); iter.hasNext();) {
			Body node = iter.next();
			node.label.setVisible(false);
			GameUI.remove(node.label);
		}
	}
	
	public void fire(Coordinate snakehead,Coordinate target,Direction d){
		new Fire(snakehead,target,d);
		bulletNumber--;
		GameUI.getWeaponLabel().setText("" + bulletNumber);// refresh bullet count
	}
	
	public synchronized void show(){
		for (Iterator<Body> iter = body.iterator(); iter.hasNext();) {
			Body node = iter.next();
			node.label.setBounds(IndexToPixel.getXPixel(node.coor.x), 
					IndexToPixel.getYPixel(node.coor.y), 20, 20);
			node.label.setIcon(bodyIcon[bodyIconTag]);
		}
		Body node = body.getFirst();
		node.label.setIcon(headIcon[headIconTag]);
	}
	
	public void MoveThread(){
		run = new Thread() {
			public void run() {
				while (!quit) 
				{
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
					
					if(!GameUI.getPause() && GameUI.getIsrun())
					{	
						move();
						Write2file.PrintMap(GameUI.getMap(),"map.txt");
						if(quit)
							break;
						show();
					}
				}
				System.out.println("Player thread exit...");
			}
		};
		run.start();
	}
	
	public class Fire extends Thread{
		private Coordinate fireCoor;
		private ImageIcon fireIcon;
		private JLabel fireLabel;
		private Coordinate target;
		private boolean quit = false;
		private Direction moveDirection;
		private Coordinate snakehead;
		
		public Fire(Coordinate snakehead,Coordinate target,Direction d){
			fireIcon = new ImageIcon("image//fire.png");
		    fireIcon.setImage(fireIcon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
		    fireLabel = new JLabel(fireIcon);
		    
		    this.target = target;
		    this.moveDirection = d;
		    this.snakehead = snakehead;
		    // initilize the fire position
		    if(moveDirection == Direction.UP)
			{
				fireCoor = new Coordinate(snakehead.x,snakehead.y-1);
				
			}
			else if(moveDirection == Direction.DOWN)
			{
				fireCoor = new Coordinate(snakehead.x,snakehead.y+1);
			}
			else if(moveDirection == Direction.LEFT)
			{
				fireCoor = new Coordinate(snakehead.x-1,snakehead.y);
			}
			else if(moveDirection == Direction.RIGHT)
			{
				fireCoor = new Coordinate(snakehead.x+1,snakehead.y);
			}
		    
		    GameUI.add(fireLabel);
		    show();
		    
		    this.start();
		}
		
		public void show(){
			if(fireCoor.x == target.x && fireCoor.y == target.y)
			{
				if(target.x < 0 || target.x > GameUI.getAreaWidth() || target.y < 0 || target.y > GameUI.getAreaHeight()){}
				else new Music("music//explode.wav").start();// play the audio when hit the obstacle
				
				fireLabel.setVisible(false);
				obstacle.removeOne(target);
				
				GameUI.remove(fireLabel);
				quit = true;
			}
			fireLabel.setVisible(false);
			fireLabel.setBounds(IndexToPixel.getXPixel(fireCoor.x), 
					IndexToPixel.getYPixel(fireCoor.y), 20, 20);
			fireLabel.setVisible(true);
		}
		
		public void move(){
			if(moveDirection == Direction.UP)
			{
				fireCoor.y--;
			}
			else if(moveDirection == Direction.DOWN)
			{
				fireCoor.y++;
			}
			else if(moveDirection == Direction.LEFT)
			{
				fireCoor.x--;
			}
			else if(moveDirection == Direction.RIGHT)
			{
				fireCoor.x++;
			}
		}
		
		public void run(){
			while(!quit)
			{
				try {
					Thread.sleep(50);// refresh every 50ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				move();
				if(quit)
					return;
				show();
			}
		}
	}
}

