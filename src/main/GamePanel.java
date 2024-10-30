package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
public class GamePanel extends JPanel implements Runnable{

//	screen settings
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;

	public final int screenWidth = tileSize * maxScreenCol; //48*16 = 768
	public final int screenHeight = tileSize * maxScreenRow; //48 * 12 = 576
	
	
//	world setting
	public final int maxWorldCol = 50, maxWorldRow=50, worldWidth = tileSize*maxWorldCol,worldheight = tileSize*maxWorldRow;
	
	int fps = 60;
	
//	Tile maanger
	TileManager tileM = new TileManager(this);
//	keyhandler
	KeyHandler keyH = new KeyHandler();

	Sound bgMusic = new Sound();
	Sound soundSE = new Sound();
	
	public UI ui = new UI(this);
	
//	for time
	Thread gameThread;
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter = new AssetSetter(this);
	
	public Player player = new Player(this,keyH);
	
//	set player default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public SuperObject obj[]= new SuperObject[10];
//	constructor
	public GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
// gameloop called by thread
	
/*
	@Override
	public void run() {
//		FPS control
//		method 1: sleep method
		double drawInterval = 1000000000/fps; //0.01666 seconds
		double nextDrawTime = System.nanoTime()+drawInterval;
		
		while(gameThread!=null) {
//			System.out.println("running...");
			
//			long currentTime = System.nanoTime();
			

//			1. Update : update info such as character positon
			update();
//			2. Draw : draw the screen with updated information
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000; //converting to milisecs
				
				if(remainingTime<0) {
					
					remainingTime=0;
				}
				Thread.sleep((long)remainingTime); //sleep only takes long milisecs
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
*/
	
	
//	fps delta/accumulator method
	public void run() {
		
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread!=null) {
			currentTime = System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			timer+= currentTime-lastTime;
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta--;
//				delta = 0;
				
				drawCount++;
			}
			if(timer>=1000000000) {
//				System.out.println("fps: "+drawCount);
				drawCount=0;
				timer=0;
			}
			
		}
	}
	public void update() {
		player.update();
//		if(keyH.upPressed==true) {
//			playerY-=playerSpeed;
//		}
//		else if(keyH.downPressed==true) {
//			playerY+=playerSpeed;
//		}
//		else if(keyH.leftPressed==true) {
//			playerX-=playerSpeed;
//		}
//		else if(keyH.rightPressed==true) {
//			playerX+=playerSpeed;
//		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		
//		Debug
		long drawStart = 0;
		if(keyH.debugMode) {	
			drawStart = System.nanoTime();
		}
		
		
		for(int i = 0; i < obj.length; i++) {
			if(obj[i]==null) continue;
			obj[i].draw(g2, this);
		}
		player.draw(g2);
//		g2.setColor(Color.white);
//		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		ui.draw(g2);
		if(keyH.debugMode) {
			long drawEnd = System.nanoTime();
			long passTime = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("draw time: "+passTime, 10, 400);
			System.out.println("draw time : "+passTime);
		}
		g2.dispose();
	}
	
	public void playMusic(int i) {
		bgMusic.setFile(i);
		bgMusic.play();
		bgMusic.loop();
	}
	public void stopMusic() {
		bgMusic.stop();
	}
	public void playSE(int i) {
		soundSE.setFile(i);
		soundSE.play();
	}
}
