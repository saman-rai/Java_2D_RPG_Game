package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter =0 ;
	
	public boolean gameFinished = false;
	
	public double playTime=0;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arail", Font.PLAIN, 40);
		arial_80B = new Font("Arail", Font.BOLD, 80);
		
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		if(gameFinished) {
			
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x,y;
			
			text = "You Found the treasure";
			textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			
			g2.drawString(text, x, y);
			
			text = "Your time is : "+dFormat.format(playTime);
			textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*4);
			
			g2.drawString(text, x, y);
			
			g2.setFont(arial_80B);
			g2.setColor(Color.yellow);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		}
		else {
			
	//		g2.setFont(new Font("Arial", Font.PLAIN, 40)); bad example, will be called 50 times bacause it is in game loop
				g2.setFont(arial_40);
				g2.setColor(Color.white);
				g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
				g2.drawString(Integer.toString(gp.player.hasKey), 82,70);
				
//				Time
				playTime+=(double)1/60;
				g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize*11, 65);
				
	//		Message
				if(messageOn) {
					g2.setFont(g2.getFont().deriveFont(30F));
					g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
					messageCounter++;
				}
				if(messageCounter>90) {
					messageCounter =0;
					messageOn = false;
			}
		}
	}
}
