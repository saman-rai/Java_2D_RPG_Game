package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	Graphics2D g2;
	GamePanel gp;
	Font arial_40, arial_80B;
	
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
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setColor(Color.white);
		g2.setFont(arial_40);
		if(gp.gameState == gp.playState) {
//			something
		}
		else if(gp.gameState == gp.pauseState) {
			
			drawPauseScreen();
		}
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSE";
		int x = getXCenterPosition(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	public int getXCenterPosition(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
