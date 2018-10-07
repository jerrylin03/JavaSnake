package com.jerry.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Snake extends Canvas implements Runnable {
	
	private static final long serialVersionUID=1L;
	
	public static final int WIDTH=100;
	public static final int HEIGHT=100; 
	//screen scale
	public static final int SCALE=6;
	
	public static final String NAME="Snake by Jerry Lin";
	
	private JFrame frame;
	public KeyInputs input;
	
	public boolean isRunning=true;
	public boolean gameOver=false;
	//thread sleep delay
	public int delay=75; 
	
	public int score;
	public int highScore=0;
	
	public static final int S_SIZE=10;
	//snake base color
	public int snakeR=170;
	public int snakeG=190;
	public int snakeB=50;
	//snake border color
	public int snakeR_B=255;
	public int snakeG_B=255;
	public int snakeB_B=255;
	//background color
	public Color back=Color.black;
	
	public boolean changeColor;
	//center of screen
	public int xPos=WIDTH*SCALE/2;
	public int yPos=HEIGHT*SCALE/2;
	
	public static final int S_MAX_LENGTH=200;
	public int currentLength; 
	//hold points of snake
	public int[] boxX=new int[S_MAX_LENGTH];
	public int[] boxY=new int[S_MAX_LENGTH];
	
	public int xSpeed;
	public int ySpeed;
	
	public static final int A_SIZE=10;
	public int appleX;
	public int appleY;
	//apple base color
	public int appleR=180;
	public int appleG=10;
	public int appleB=60;
	
	public Snake(){
		
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		frame=new JFrame(NAME);
		//terminates game when you close it
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		
		//creates menus and sub menus
		JMenuBar menubar=new JMenuBar();
		
		JMenu file=new JMenu("File");
		menubar.add(file);
		//exits game
		JMenuItem quit=new JMenuItem("Quit");
		quit.addActionListener((ActionEvent event)->{
			
		    System.exit(0);
		    
		});
		file.add(quit);
		file.addSeparator();
		//restarts game and resets values
		JMenuItem restart=new JMenuItem("Restart");
		restart.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE));
		restart.addActionListener((ActionEvent event)->{
			
			gameOver=false;
		    init();
		    
		});
		file.add(restart);
		file.addSeparator();
		//changes difficulty based on speed and thread sleep
		JMenu difficulty=new JMenu("Difficulty");
		menubar.add(difficulty);
		
		JMenuItem easy=new JMenuItem("Easy");
		easy.addActionListener((ActionEvent event)->{
			
		    delay=75;
		    gameOver=false;
			init();
		    
		});
		difficulty.add(easy);
		difficulty.addSeparator();
		
		JMenuItem medium=new JMenuItem("Medium");
		medium.addActionListener((ActionEvent event)->{
			
			delay=50;
			gameOver=false;
			init();
		    
		});
		difficulty.add(medium);
		difficulty.addSeparator();
		
		JMenuItem hard=new JMenuItem("Hard");
		hard.addActionListener((ActionEvent event)->{
			
		    delay=25;
		    gameOver=false;
			init();
			
		});
		difficulty.add(hard);
		difficulty.addSeparator();
		//assigns color to snake
		JMenu color=new JMenu("Color");
		menubar.add(color);
		
		JMenuItem normal=new JMenuItem("Normal");
		normal.addActionListener((ActionEvent event)->{
			
			snakeR=170;
			snakeG=190;
			snakeB=50;
			
			snakeR_B=255;
			snakeG_B=255;
			snakeB_B=255;
			
			gameOver=false;
		    init();
		    
		    changeColor=false;
		    
		});
		color.add(normal);
		color.addSeparator();
		
		JMenuItem dGreen=new JMenuItem("Dark Green");
		dGreen.addActionListener((ActionEvent event)->{
			
			snakeR=50;
			snakeG=115;
			snakeB=80;
			
			snakeR_B=30;
			snakeG_B=45;
			snakeB_B=35;
			
			gameOver=false;
		    init();
			
		    changeColor=false;
		    
		});
		color.add(dGreen);
		color.addSeparator();
		
		JMenuItem bWhite=new JMenuItem("Retro");
		bWhite.addActionListener((ActionEvent event)->{
			
			snakeR=255;
			snakeG=255;
			snakeB=255;
			
			snakeR_B=0;
			snakeG_B=0;
			snakeB_B=0;
			
			gameOver=false;
			init();
			
			changeColor=false;
					
		});
		color.add(bWhite);
		color.addSeparator();
		//changes color of snake every tick, also sets game to hard
		JMenuItem rainbow=new JMenuItem("DJ Snake");
		rainbow.addActionListener((ActionEvent event)->{
			
			snakeR=(int)(Math.random()*255);
			snakeG=(int)(Math.random()*255);
			snakeB=(int)(Math.random()*255);
			
			snakeR_B=(int)(Math.random()*255);
			snakeG_B=(int)(Math.random()*255);
			snakeB_B=(int)(Math.random()*255);
			
			delay=25;
			
			gameOver=false;
			init();
			
			changeColor=true;

		});
		color.add(rainbow);
		color.addSeparator();
		
		JMenu backGround=new JMenu("Background");
		menubar.add(backGround);
		//changes background color
		JMenuItem back_B=new JMenuItem("Black");
		back_B.addActionListener((ActionEvent event)->{
			
		   back=Color.BLACK;
		   
		   gameOver=false;
		   init();
		    
		});
		backGround.add(back_B);
		backGround.addSeparator();
		
		JMenuItem back_W=new JMenuItem("White");
		back_W.addActionListener((ActionEvent event)->{
			
			back=Color.WHITE;
			
			gameOver=false;
			init();
		    
		});
		backGround.add(back_W);
		backGround.addSeparator();
		
		JMenuItem back_G=new JMenuItem("Gray");
		back_G.addActionListener((ActionEvent event)->{
			
			back=Color.GRAY;
			
			gameOver=false;
			init();
		    
		});
		backGround.add(back_G);
		backGround.addSeparator();
		
		frame.setJMenuBar(menubar);
		
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public synchronized void start(){
		
		isRunning=true;
		new Thread(this).start();
		
	}
	
    public synchronized void stop(){
    	
		isRunning=false;
		
	}
	
    
    //limits how often values update, graphics paint, initializes values
    @Override
	public void run(){		
    	
		long lastTime=System.nanoTime();
		double nsPerTick=1000000000D/60D;
		
		int frames=0;
		int ticks=0;
		
		long lastTimer=System.currentTimeMillis();
		double delta=0;
		
		init();
		
		while(isRunning){
			
			long now=System.nanoTime();
			delta+=(now-lastTime)/nsPerTick;
			lastTime=now;
			boolean shouldTick=true;
			
			while(delta>=1){
				
				frames++;
				render();
				delta-=1;
				shouldTick=true;
				
			}
			
			try{
				//15 for 60 frames, 75 for 15 frames
				Thread.sleep(delay);
				
			}catch(InterruptedException e){
				
				e.printStackTrace();
				
			}
			
			if(shouldTick){
				
				ticks++;
				tick();
				
			}
			
			if(System.currentTimeMillis()-lastTimer>=1000){
				
				lastTimer+=1000;
				System.out.println("Ticks: "+ticks+" Frames: "+frames);
				frames=0;
				ticks=0;
				
			}
		}
		
	}
    //resets game values and changes snake back to the center
    public void init(){
    	
   	 input=new KeyInputs(this);
   	 score=0;
   	 currentLength=2;
   	 xSpeed=0;
   	 ySpeed=0;
   	 
   	 for (int i=0; i<currentLength; i++){
   		 
        boxX[i]=50-i*10;
        boxY[i]=50;
        
     }
   	 
   	 boxX[0]=xPos;
  	 boxY[0]=yPos;
  	 
   	 locate();
   	 
   }
   //updates values with keys and checks for collision
    public void tick(){
			
		 if ((input.up.isPressed())&&(ySpeed!=10)){
			 
			 xSpeed=0;
			 ySpeed=-10;
			 
		 }
		 
		 if ((input.down.isPressed())&&(ySpeed!=-10)){
			 
			 xSpeed=0;
			 ySpeed=10;
			 
		 }
		 
		 if ((input.left.isPressed())&&(xSpeed!=10)){
			 
			 xSpeed=-10;
			 ySpeed=0;
			 
		 }
		 
		 if ((input.right.isPressed())&&(xSpeed!=-10)){
			 
			 xSpeed=10;
			 ySpeed=0;
			 
		 }
		 
		if(!gameOver){
			
			move();
			eat();
			checkCollision();
			change();
		
		}
	
	}	
	//updates graphics
	public void render(){
		
		BufferStrategy bs=getBufferStrategy();
		if (bs==null){
			
			createBufferStrategy(3);
			return;
			
		}
		
		Graphics g=bs.getDrawGraphics();
		
		g.setColor(back);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawSnake(g);
		addSnake(g);
		drawApple(g);
		//if game ends, outputs text and scores
		if(gameOver){
			
			g.setColor(new Color(0,0,0,127));
	        g.fillRect(0, 0, WIDTH*SCALE+10, HEIGHT*SCALE+10);
	        
			if(score>highScore){
			
				highScore=score;
				
			}
	        
			String dText="You Died";
			String sText=("Score: "+score);
			String hText=("High Score: "+highScore);
	        Font dSouls=new Font("Times New Roman", Font.BOLD, 4*SCALE);
	        Font font=new Font("Monospaced", Font.PLAIN, 3*SCALE);
	        FontMetrics Metric1=getFontMetrics(dSouls);
	        FontMetrics Metric2=getFontMetrics(font);
	  
	        g.setColor(Color.RED);
	        g.setFont(dSouls);
	        g.drawString(dText, (WIDTH*SCALE-Metric1.stringWidth(dText))/2, HEIGHT*SCALE/2);
	        
	        g.setColor(Color.WHITE);
	        g.setFont(font);
	        g.drawString(sText, (WIDTH*SCALE-Metric2.stringWidth(sText))/2, HEIGHT*SCALE/2+(5*SCALE));
	        g.drawString(hText, (WIDTH*SCALE-Metric2.stringWidth(hText))/2, HEIGHT*SCALE/2+(10*SCALE));
	        
		}
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args){ 
		
		new Snake().start();	
		
	}
	
	public void drawSnake(Graphics g){

		g.setColor(new Color(snakeR, snakeG, snakeB));
		g.fillRect(boxX[0], boxY[0], S_SIZE, S_SIZE);
		
		g.setColor(new Color(snakeR_B, snakeG_B, snakeB_B));
		g.drawRect(boxX[0], boxY[0], S_SIZE, S_SIZE);
		
	}
	//adds snake body when snake length increases
	public void addSnake(Graphics g){
		
		for(int i=0; i<currentLength;i++){
			
			g.setColor(new Color(snakeR, snakeG, snakeB));
			g.fillRect(boxX[i], boxY[i], S_SIZE, S_SIZE);
			
			g.setColor(new Color(snakeR_B, snakeG_B, snakeB_B));
			g.drawRect(boxX[i], boxY[i], S_SIZE, S_SIZE);
			
		}
		
	}
	
	public void drawApple(Graphics g){
		
		g.setColor(new Color(appleR, appleG, appleB));
		g.fillRect(appleX, appleY, A_SIZE, A_SIZE);
		
	}
	//updates snake location and moves each segment to previous location
	public void move(){
		
		boxX[0]+=xSpeed;
		boxY[0]+=ySpeed;
		
		for (int i=currentLength; i>0; i--){
			
		    boxX[i]=boxX[i-1];
		    boxY[i]=boxY[i-1];
		    
		}
			
	}
	//ends game if snake hits top, bottom, left, right of screen or hits itself
	public void checkCollision(){
		
		for (int i=currentLength; i>0; i--) {

	            if((i>4)&&(boxX[0]==boxX[i])&&(boxY[0]==boxY[i])){
	            	
	                gameOver=true;
	                
	            }
		}        
		
		if(boxX[0]<0){
			
			boxX[0]=0;
			gameOver=true;
			
		}
		
		if(boxX[0]>WIDTH*SCALE){
			
			boxX[0]=WIDTH*SCALE;
			gameOver=true;
			
		}
		
		if(boxY[0]<0){
			
			boxY[0]=0;
			gameOver=true;
			
		}
		
		if(boxY[0]>HEIGHT*SCALE){
			
			boxY[0]=HEIGHT*SCALE;
			gameOver=true;
			
		}
	}
	//puts new location for apple and adds snake segment
	public void eat(){
		
		if((boxX[0]==appleX)&&(boxY[0]==appleY)){
			
			currentLength++;
			locate();
			score+=currentLength*5;
	
		}
			
	}
	//random location on screen
	public void locate(){
		
		appleX=(int)(Math.random()*(WIDTH/10)*SCALE)*10;
		appleY=(int)(Math.random()*(HEIGHT/10)*SCALE)*10;
		
	}
	//changes colors randomly for snake
	public void change(){
		
		if(changeColor){
			
			snakeR=(int)(Math.random()*255);
			snakeG=(int)(Math.random()*255);
			snakeB=(int)(Math.random()*255);
			
			snakeR_B=(int)(Math.random()*255);
			snakeG_B=(int)(Math.random()*255);
			snakeB_B=(int)(Math.random()*255);
			
		}
	}
	
}
